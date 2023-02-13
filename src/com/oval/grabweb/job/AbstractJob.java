package com.oval.grabweb.job;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.oval.grabweb.action.ActionFacade;
import com.oval.util.DateUtil;
import com.oval.util.FileUtil;
import com.oval.util.GlobalInfo;
import com.oval.util.WebUtil;

public abstract class AbstractJob implements Job {
	private static Logger logger = Logger.getLogger(AbstractJob.class);
	public HttpClient client = null;
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		client = WebUtil.getDefaultHttpClient1();
//		HttpClient client = WebUtil.getDefaultHttpClient();
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
 	    String dir = dataMap.getString("rootDir");
 	    List<JobInfo>  jobInfoList = (List<JobInfo>) dataMap.get("jobInfoList"); 
 	    GlobalInfo.endDate=DateUtil.getBeforeDayAgainstToday(1,"yyyy-MM-dd");
 	    GlobalInfo.beginDate=DateUtil.getBeforeDayAgainstToday(60,"yyyy-MM-dd");
 	    String beginDate= getBeginDate();
 	    String endDate= getEndDate();
 	    dir = dir + "/auto/sph/" + endDate;
 	    FileUtil.checkAndCreateDir(dir);	    
		try {
			ActionFacade.doActions(client,dir,jobInfoList);
		} catch (Exception e) {
			logger.error(endDate+"日报执行过程中出错");
			e.printStackTrace();
		}
		finally {
//			client.getConnectionManager().shutdown();
		}
	}

	
	protected abstract String getBeginDate();
	protected abstract String getEndDate();
}
