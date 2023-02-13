package com.oval.grabweb.listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.oval.grabweb.job.CopyFileJob;
import com.oval.grabweb.job.DayJob;
import com.oval.grabweb.job.JobInfo;
import com.oval.util.FileUtil;
import com.oval.util.GlobalInfo;
import com.oval.util.ReadXMLUtil;
import com.oval.util.StringUtil;

public class StartupListener implements ServletContextListener {
	private static Logger logger = Logger.getLogger(StartupListener.class);

    public StartupListener() {
    }

    public void contextInitialized(ServletContextEvent event) {
    	ServletContext context = event.getServletContext();
    	GlobalInfo.rootPath = context.getRealPath("/");
    	GlobalInfo.verifyStorePath = GlobalInfo.rootPath + "/verifycode";
    	//System.out.println(GlobalInfo.verifyStorePath);
    	try {
    		FileUtils.forceMkdir(new File(GlobalInfo.verifyStorePath));
		} catch (Exception e) {
			logger.error("create verify code store path error!");
			e.printStackTrace();
		}
    	
		Map<String,List<JobInfo>> mapActions= null;
		try {
			mapActions = getActions(context); //获取定时器信息
		} catch (Exception e) {
			logger.error("action.properties error!");
			e.printStackTrace();
		} 
		
    	String rootSrc = context.getInitParameter("FileDir");
    	String destDir = context.getInitParameter("DestDir");
    	rootSrc = StringUtil.isEmpty(rootSrc) ? "d:" : rootSrc;
    	FileUtil.checkAndCreateDir(rootSrc);
		try {
			String copyExpr =context.getInitParameter("copyExpr"); 
			Iterator it = mapActions.keySet().iterator();
			Scheduler sche = StdSchedulerFactory.getDefaultScheduler();
			while (it.hasNext()) {
				String dailyExpr = (String) it.next();	
				List<JobInfo> jobInfoList = mapActions.get(dailyExpr);	
		        event.getServletContext().setAttribute("JobScheduler", sche);
		        createJob(sche, rootSrc, DayJob.class,jobInfoList,dailyExpr); //定时执行任务
			}
			createCopyJob(sche, rootSrc, destDir, "", copyExpr);
			
		} catch (SchedulerException e) {
			logger.error("生成定时任务出错：" + e.getMessage());
		}
    }

    public void contextDestroyed(ServletContextEvent event) {
		try {
			Scheduler sche = (Scheduler)event.getServletContext().getAttribute("JobScheduler");
			sche.shutdown();
			event.getServletContext().removeAttribute("JobScheduler");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

    }
    
	private static void createJob(Scheduler sche,String dir,Class clazz, List<JobInfo> jobInfoList,String dailyExpr){
		JobDetail jobDetail = new JobDetail(dailyExpr,clazz);
		JobDataMap dataMap = jobDetail.getJobDataMap();
		dataMap.put("jobInfoList", jobInfoList);		
		dataMap.put("rootDir", dir);
		
		CronTrigger cronTrigger = new CronTrigger(dailyExpr,dailyExpr);
		try {
			CronExpression expression = new CronExpression(dailyExpr);
			cronTrigger.setCronExpression(expression);
			sche.scheduleJob(jobDetail, cronTrigger);
			sche.start();
		} catch (Exception e) {
			logger.error("生成日报定时任务出错：" + e.getMessage());
		}
	}
	
	private static void createCopyJob(Scheduler sche,String rootSrc,String rootDest,String rootTestDest, String expr){
		JobDetail jobDetail = new JobDetail("copy",CopyFileJob.class);
		JobDataMap dataMap = jobDetail.getJobDataMap();
		dataMap.put("rootSrc", rootSrc);		
		dataMap.put("rootDest", rootDest);
		dataMap.put("rootTestDest", rootTestDest);
		
		CronTrigger cronTrigger = new CronTrigger("copy","copy");
		try {
			CronExpression expression = new CronExpression(expr);
			cronTrigger.setCronExpression(expression);
			sche.scheduleJob(jobDetail, cronTrigger);
			sche.start();
		} catch (Exception e) {
			logger.error("生成文件复制定时任务出错：" + e.getMessage());
		}
	}
	
	
	
	private  Map<String,List<JobInfo>> getActions(ServletContext servletContext) throws FileNotFoundException, IOException, DocumentException{
		Map<String,List<JobInfo>> map = new HashMap<String,List<JobInfo>>();
		StringBuffer xmlsb=new StringBuffer();
		ReadXMLUtil strxml=new ReadXMLUtil();
        xmlsb=strxml.jobXML();
        String[] m=xmlsb.toString().split("#");
        List<JobInfo> list = null;
        for(int i=1;i<m.length;i++){
        	String[] z = m[i].split(",",-1);//不忽略任何一个分隔符，当分割符之间的内容为空时不忽略,取值时可能会有数组越界错误 --dongyf
        	JobInfo jobInfo = new JobInfo(); //设置job执行的类名、客户代码、名称、时间
        	jobInfo.setClassname(z[0]);
        	jobInfo.setOrgcode(z[1]);
        	jobInfo.setOrgName(z[2]);
        	jobInfo.setDailyExpr(z[3]);
        	jobInfo.setIsmerge(Boolean.valueOf(z[4]));
        	jobInfo.setUserNameKey(z[5]);//用户名key
        	jobInfo.setPasswordKey(z[7]);//密码key
        	jobInfo.setUserNameValue(z[6]);//用户名集合
        	jobInfo.setPasswordValue(z[8]);//密码集合
        	//使用DDI4.0获取账号信息
        	if(z.length>10){
        		jobInfo.setDistIdKey(z[5]);
            	jobInfo.setDistIdValue(z[6]);
            	jobInfo.setCallIdKey(z[7]);
            	jobInfo.setCallIdValue(z[8]);
            	jobInfo.setUuidKey(z[9]);
            	jobInfo.setUuidValue(z[10]);
        	}
        	
        	
        	// 同一时间启动的事务放在一起
    		if(map.containsKey(z[3])){
    			list = map.get(z[3]);
    			list.add(jobInfo);
    			map.put(z[3],list);
    		} else {
    			list = new ArrayList<JobInfo>();
    			list.add(jobInfo);
    			map.put(z[3],list);
    		}
        }
		return map;
	}
	
	public static  void main(String args[])  throws FileNotFoundException, IOException{
		Properties prop = new Properties(); 
		prop.load(StartupListener.class.getClassLoader().getResourceAsStream("action.properties"));
		Map<String, String> actions = new HashMap<String, String>();
		Set keys =prop.keySet(); 
		for (Object key : keys) {
			actions.put((String)key, (String)prop.get(key));
			System.out.println((String)prop.get(key));
		}	
	}
	
}
