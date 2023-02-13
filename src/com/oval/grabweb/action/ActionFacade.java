package com.oval.grabweb.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpVersion;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.oval.grabweb.auto.TotalAutoTasks;
import com.oval.grabweb.job.JobInfo;
import com.oval.util.WebUtil;

public class ActionFacade extends GetXMLInfo{
	private static Logger logger = Logger.getLogger(ActionFacade.class);
	public static String DirPath;
	public static Thread runner;
	public static List<Thread> runningThreads = new ArrayList<Thread>(); 
	public static int dealerlength=0;
	public static int runnsumlength=0;
	public static boolean taskop=false;
	public static DefaultHttpClient htpclientManual;
	
	
	public static DefaultHttpClient getDefaultHttpClientManual() {
		HttpParams params = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(params, 300);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params,
				HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https", PlainSocketFactory
				.getSocketFactory(), 433));
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
				params, schReg);
		htpclientManual = new DefaultHttpClient(conMgr, params);
		HttpClientParams.setCookiePolicy(htpclientManual.getParams(),
				CookiePolicy.BROWSER_COMPATIBILITY);
		htpclientManual.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		htpclientManual
				.getParams()
				.setParameter(
						CoreProtocolPNames.USER_AGENT,
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1))");
		htpclientManual.getParams().setParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		htpclientManual.setHttpRequestRetryHandler(requestRetryHandler);
		return htpclientManual;
	}
	
	private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
		public boolean retryRequest(IOException exception, int executionCount,
				HttpContext context) {
			if (executionCount >= 3) {
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				return false;
			}
			HttpRequest request = (HttpRequest) context
					.getAttribute(ExecutionContext.HTTP_REQUEST);
			boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
			if (!idempotent) {
				return true;
			}
			return false;
		}
	};	
	
	public boolean manualAction() throws DocumentException, InterruptedException {
		boolean result=false;
		StringBuffer sb = new StringBuffer();
		taskop=false;
		if((runnsumlength==dealerlength-1) || (runnsumlength==0)){		   
		   Iterator it=super.getTaskmap().entrySet().iterator();
		   String[] m = super.getXmlsb().toString().split("#");
		   HttpClient clientmanual = getDefaultHttpClientManual();
		   while(it.hasNext()){
			  taskop=true;
			  Map.Entry entry = (Map.Entry) it.next() ; 
			  for (int i = 1; i < m.length; i++) {
				if(m[i].trim().contains(entry.getKey().toString()) || (m[i].trim().contains(entry.getValue().toString()))){				  				   
				   z=m[i].split(",");
				   
				   try {
					// TotalAutoTasks->半自动转自动
						if ("TotalAutoTasks".equals(z[1])) {
							TotalAutoTasks.main(new String[] { "" });
						} else {

							AbstractAction action = (AbstractAction) Class.forName(z[0]).newInstance();
							action.setIsmerge(Boolean.valueOf(z[4])); // 设置是否合并
							// 若存在多账号的则以@@区分来分别执行
							for (int j = 0; j < z[6].split("@@").length; j++) {
								Map<String, String> userMap = new HashMap<String, String>();
								HttpClient client = WebUtil.getDefaultHttpClient();
//								HttpClient client = new SSLClient();
								userMap.put(z[5], z[6].split("@@")[j]);
								userMap.put(z[7], z[8].split("@@")[j]);
								userMap.put(z[9], z[10].split("@@")[j]);
								sb = new StringBuffer(z[6].split("@@")[j]); // 账号信息
								action.exec(client, userMap, z[1], z[2], sb);
							}
						}
				   } catch (Exception e) {
						logger.error(e.getMessage());
				   }				   
				   result=true;
				   break;
				}					  
			  }		   
		   }
		   clientmanual.getConnectionManager().shutdown();
		}	
		super.getTaskmap().clear();
		System.out.println("清空列表!!!");
		taskop=false;
		return result;	
	}

	/**
	 * 
	* @Title: doActions 
	* @Description: job到达指定时间执行指定任务
	* @param client
	* @param Dir
	* @throws DocumentException
	* @throws InterruptedException 
	* @return void
	 */
	public static void doActions(HttpClient client,String Dir,List<JobInfo> jobInfoList) throws DocumentException, InterruptedException {
		dealerlength=jobInfoList.size()+1;
		GetThread[] threads = new GetThread[jobInfoList.size()];
		while(taskop){
			Thread.sleep(2000);
		}
		ExecutorService executor = Executors.newFixedThreadPool(3);
		runnsumlength=0;
		for (int n = 0; n < threads.length; n++) {
			StringBuffer orgInfo = new StringBuffer();
			orgInfo.append(jobInfoList.get(n).getClassname()).append(",");
			orgInfo.append(jobInfoList.get(n).getOrgcode()).append(",");
			orgInfo.append(jobInfoList.get(n).getOrgName()).append(",");
			orgInfo.append(jobInfoList.get(n).isIsmerge());
			threads[n] = new GetThread(client,orgInfo.toString(),jobInfoList.get(n));			
		}
		
		for (int j = 0; j < threads.length; j++) {
			executor.execute(threads[j]);
			Thread.sleep(35000);
		}
		System.out.println("关闭");
		executor.shutdown();
	}
	

	
	static class GetThread extends Thread {
		private final HttpClient httpClient;
		private final String orginfo;
		private final JobInfo jobInfo;


		public GetThread(HttpClient httpClient, String orginfo,JobInfo jobInfo) {
			this.httpClient = httpClient;
			this.orginfo = orginfo;
			this.jobInfo = jobInfo;

		}
		public void run() {
				String[] zz=null;
				regist(this);				
					try {
						zz=orginfo.split(",");
						// TotalAutoTasks->半自动转自动
						if ("TotalAutoTasks".equals(zz[1])) {
							TotalAutoTasks.main(new String[] { "" });
						} else {
						StringBuffer sb = new StringBuffer();
						System.out.println(zz[2] + " - about to get something from ");
						AbstractAction action = (AbstractAction) Class.forName(zz[0]).newInstance();
						action.setIsmerge(jobInfo.isIsmerge());//是否多账号
						
						//若存在多账号的则以@@区分来分别执行
						for(int j=0;j<jobInfo.getUserNameValue().split("@@").length;j++){
							Map<String,String> userMap = new HashMap<String,String>();
							if(jobInfo.getUuidKey()==null){
								userMap.put(jobInfo.getUserNameKey(), jobInfo.getUserNameValue().split("@@")[j]);//用户
								userMap.put(jobInfo.getPasswordKey(), jobInfo.getPasswordValue().split("@@")[j]);//密码
							}else{
								userMap.put(jobInfo.getCallIdKey(), jobInfo.getCallIdValue().split("@@")[j]);//callid
								userMap.put(jobInfo.getDistIdKey(), jobInfo.getDistIdValue().split("@@")[j]);//distid
								userMap.put(jobInfo.getUuidKey(), jobInfo.getUuidValue().split("@@")[j]);//uuid
							}
							
							sb = new StringBuffer(jobInfo.getUserNameValue().split("@@")[j]);//账号信息
							action.exec(httpClient, userMap, zz[1], zz[2], sb);
						}
						}
					} catch (Exception e) {
						logger.error(e.getMessage());
					}					
				unRegist(this);
				System.out.println(runningThreads.size());
				System.out.println("执行完成的经销商:"+runnsumlength);
				System.out.println("总的经销商:"+(dealerlength-1));
				if((runningThreads.size()==0) && (runnsumlength==dealerlength-1)){				
					System.out.println("httpClient关闭");
					httpClient.getConnectionManager().shutdown();
				}				
		}
		
		public void regist(Thread t){   
			    synchronized(runningThreads){ 
			    	runnsumlength=runnsumlength+1;
			        runningThreads.add(t);   
			   }   
			}   
		public void unRegist(Thread t){   
			    synchronized(runningThreads){    
			       runningThreads.remove(t);   
		    }   
		} 		
	}	
	
	public static void main(String[] args) throws IOException {
		Properties prop = new Properties(); 
		prop.load(ActionFacade.class.getClassLoader().getResourceAsStream("action.properties"));
		Map<String, String> actions = new HashMap<String, String>();
		Set keys =prop.keySet(); 
		for (Object key : keys) {
			actions.put((String)key, (String)prop.get(key));
		}
		
		HttpClient client = WebUtil.getDefaultHttpClient();
		try {
//			ActionFacade.doActions(client,"D:/");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			client.getConnectionManager().shutdown();
		}
	}

}
