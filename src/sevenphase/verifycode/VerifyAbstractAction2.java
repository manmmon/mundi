package sevenphase.verifycode;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import servlet.VerifyManager2;

import com.oval.grabweb.action.AbstractAction2;
import com.oval.grabweb.action.Constant;
import com.oval.util.DateUtil;
import com.oval.util.FileUtil;
import com.oval.util.GlobalInfo;
import com.oval.util.WebUtil;

public abstract class VerifyAbstractAction2 extends AbstractAction2{

	public List<Boolean> status;
	public VerifyAbstractAction2(){

	}
	
	protected void login() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.putAll(loginParams);
		WebUtil.repeatPost(client, params, getLoginUrl());
	}

	public void getVerifyCode() throws Exception{
		client = HttpClientBuilder.create().build();
		FileUtils.deleteQuietly(new File(GlobalInfo.verifyStorePath + "/" + orgCode + ".jpg"));
		WebUtil.repeatGet(client, getLoginUrl());
		WebUtil.requestFile(client, getPictureUrl(), GlobalInfo.verifyStorePath + "/" + orgCode + ".jpg");
	}
	

	public  void exec() throws Exception{
		try {
			if (HasExistsFile(orgCode, orgName)) {
				return;
			}
			doExec();
		}finally {
			if(client == null) {
				return;
			}
			client.getConnectionManager().shutdown();
			client = null;
		}
	}
	
	public  void doExec() throws Exception{
		String suffix =  orgCode + "_" + orgName + "_" + DateUtil.getLastDay("yyyyMMdd") +Constant.FILE_LASTFIX +".xls";

 	    String dir = file_prefix;
 	    File verifyCodeDir = new File(dir);
 	    if(!verifyCodeDir.exists()){
 	    	verifyCodeDir.mkdirs();
 	    }		
		
		logger.info(orgName + "  " + DateUtil.getBeforeDayAgainstToday(1,"yyyy-MM-dd") + "  日报");
		try {
			login();
			//登录失败
			if(!loginsuccess){
				return;
			}
		} catch (Exception e) {
			logger.error(orgName + "的网站不可访问！");
			throw new Exception(e.getMessage());
		}
		//创建子文件
//		String childDir = dir+"/"+orgCode1+"_"+orgName;
		try{
			String stockFile = file_prefix  + "/ID_" + suffix;
			String bakFileV = bak_prefix + "/ID_" + suffix;
			//System.out.println("bakFileV"+bakFileV);
//			if(!ismerge){
				if ((FileUtil.checkFile(stockFile)||FileUtil.checkFile(bakFileV))){
					 logger.info("库存已生成 ");	
				} else {
						String[] stockInfo=getStock(client,orgName); 							
						if (stockInfo!=null){
						  if (stockInfo.length == 1){
						  	logger.info(orgName + DateUtil.getLastDay("yyyy-MM-dd")+"日的库存数据为空！");
						  }
						  FileUtil.createFile(stockInfo,stockFile.replaceAll("TWO", "").trim(),column_speractor);				 
						  logger.info("生成库存 " + stockFile);
					}
				}
		}catch (Exception e){
			logger.error("获取"+orgName+"库存数据过程中发生错误！");		
		}
			
		try{
			String saleFile = file_prefix + "/SD_" + suffix;
			String bakFileS = bak_prefix + "/SD_" + suffix;

			if ((FileUtil.checkFile(saleFile) || FileUtil.checkFile(bakFileS))
					&& (VerifyManager2.MultiVerCode.equals("false"))) {
				logger.info("流向已生成");
			} else {
					String[] saleInfo = getSale(client, orgName);
					if (saleInfo != null) {
						if (saleInfo.length == 1) {
							logger.info(orgName + "流向数据为空！");
						}

						FileUtil.createFile(saleInfo,
								saleFile.replaceAll("TWO", "").trim(),
								column_speractor);
						logger.info("生成流向" + saleFile);
					}
				}

		} catch (Exception e) {
			logger.error("获取" + orgName + "流向数据过程中发生错误！");
			throw new Exception(e.getMessage());
		}
		
		try{//ActionFacade.DirPath
			String purchasFile = file_prefix  + "/PD_" + suffix;
			String bakFileP = bak_prefix  + "/PD_" +suffix;
//			if(!ismerge) {
			//System.out.println("bakFileV"+bakFileV);
			if ((FileUtil.checkFile(purchasFile)||FileUtil.checkFile(bakFileP))&&(VerifyManager2.MultiVerCode.equals("false"))){
				 logger.info("采购已生成 ");	
			}else{
				String[] purchasInfo=getPurchas(client,orgName); 							
				if (purchasInfo!=null){
				  if (purchasInfo.length == 1){
				  	logger.info(orgName + DateUtil.getBeforeDayAgainstToday(1,"yyyy-MM-dd")+"日的采购数据为空！");
				  }
				  FileUtil.createFile(purchasInfo,purchasFile.replaceAll("TWO", "").trim(),column_speractor);				 
				  logger.info("生成采购" + purchasFile);
				}
			}
		}catch (Exception e){
			logger.error("获取"+orgName+"采购数据过程中发生错误！");		
		}
		
		try {
			 //record(orgCode);	
			 logger.info("记录抓取日志");
		} catch (Exception e) {
			logger.error("记录"+ orgName+ "抓取日志过程中发生错误！");
			throw new Exception(e.getMessage());
		}

		 logger.info(orgName + "  " + DateUtil.getLastDay("yyyy-MM-dd") + "  日报完成");
		 logger.info("------------------------------------------------------------------------------------------------------");
		 
	}
	
	protected abstract String getLoginUrl();
	public abstract void addVerifyCodeParam(String verifyCode);
	protected abstract String getPictureUrl();
	
	Map<String, String> getLoginParams(){
		return loginParams;
	}
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public void addLoginParam(String name, String value) {
		loginParams.put(name, value);
	}
	
	public void removeLoginParam(String name) {
		loginParams.remove(name);
	}
    public void setClient(CloseableHttpClient client) {
		this.client = client;
	}
	public HttpClient getHttpClient() {
		return client;
	}
	
	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}

	public List<Boolean> getStatus() {
		return status;
	}

	public void setStatus(List<Boolean> status) {
		this.status = status;
	}

	protected int[] getContentsIndexs(String[] title,String[] contents){
		int[] indexs =new int[title.length];
		for(int i=0;i<title.length;i++){
			for(int j=0;j<contents.length;j++){
				if(contents[j].equals(title[i])){
//					title[i]=j+"";
					indexs[i] = j;
					break;
				}
				indexs[i] = -1;
			}
		}
		return indexs;
	}
	protected String account;
	protected CloseableHttpClient client;
	protected String orgCode;
    protected String orgName;
    protected Map<String, String> loginParams = new HashMap<String, String>();
}
