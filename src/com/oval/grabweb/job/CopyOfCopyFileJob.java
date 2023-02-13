package com.oval.grabweb.job;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;


import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;



import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.oval.util.DateUtil;
import com.oval.util.FileUtil;
import com.oval.util.ReadXMLUtil;

public class CopyOfCopyFileJob implements Job {
	
	private static Logger logger = Logger.getLogger(CopyOfCopyFileJob.class);
	public static String standardFileDir="";
	public static String testFileDir="";
	public static String orgcode="";
	private static CopyOfCopyFileJob instance;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
 	    String rootSrc = dataMap.getString("rootSrc");
 	    String rootDest = dataMap.getString("rootDest");
 	    String rootTestDest=dataMap.getString("rootTestDest");
 	    doCopy(rootSrc, rootDest, rootTestDest);
 	    doDelete(rootSrc);
	}
	
	public void getFileDir(){//读取正式与测试文件路径
			SAXReader reader = new SAXReader();
//			System.out.println(this.getClass().getResource("/").getPath());
 			String xmlparse = this.getClass().getResource("/").getPath();//this.getClass().getResource("/").getPath();//.getClass().getResource("/").getPath();
 			Document doc = null;
			try {
				doc = reader.read(xmlparse + "350Web.xml");
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 			List<Element> customers = doc.selectNodes("//FileDir");
 			for (Element element : customers) {
 				standardFileDir = ReadXMLUtil.getChildValue(element, "standardFileDir");			
 				testFileDir = ReadXMLUtil.getChildValue(element, "testFileDir");
// 				System.out.println("testFileDir:"+testFileDir);
 			}
 			customers=doc.selectNodes("//customer[@test='Y']");
 			for(Element element:customers){
 				orgcode= orgcode+ReadXMLUtil.getChildValue(element, "orgcode")+","; 				
 			}
 			if(!orgcode.equals("")){
 				orgcode=orgcode.substring(0, orgcode.length()-1);
 			}
 			
 			
	}
 	    	
	private static void doCopy(String rootSrc, String rootDest, String rootTestDest) {
		try {
 	      List<File> srcSubDirs = FileUtil.getDirectSubDirs(rootSrc + "/auto/"); 
 		  if (instance == null) {
 				instance = new CopyOfCopyFileJob();
 				instance.getFileDir();
 		   }
 	      for (Iterator iterator = srcSubDirs.iterator(); iterator.hasNext();) {
 	    	  
			File srcSubDir = (File) iterator.next();
//	 	    File destDir = new File(rootDest + "/" + DateUtil.transform(srcSubDir.getName(), "yyyy-MM-dd", "yyyy/M/d") + "/"+standardFileDir);
//	 	    if(!destDir.exists()){
//	 	    	destDir.mkdirs();
//	 	    }
//	 	    
//	 	    File destTestDir=new File(rootTestDest + "/" + DateUtil.transform(srcSubDir.getName(), "yyyy-MM-dd", "yyyy/M/d") + "/"+testFileDir);
//	 	    if(!destTestDir.exists()){
//	 	    	destTestDir.mkdirs();
//	 	    }
	 	    
	 	    
//	 	    copyDir(srcSubDir,destDir,destTestDir,orgcode);
//	 	    logger.info("文件正在从" + srcSubDir.getAbsolutePath() + "复制到" + destDir.getAbsolutePath());
//	 	    logger.info("文件正在从" + srcSubDir.getAbsolutePath() + "复制到" + destTestDir.getAbsolutePath());
	 	    
	 	    
	 	    
//	 	    if(orgcode.indexOf(srcSubDir.getName())<0){
//		 	    copyDir(srcSubDir, destDir);	
//		        logger.info("文件正在从" + srcSubDir.getAbsolutePath() + "复制到" + destDir.getAbsolutePath());
//		        //上面是正式文件	 	    	
//	 	    }else{
//		        copyDir(srcSubDir,destTestDir);
//		        logger.info("文件正在从" + srcSubDir.getAbsolutePath() + "复制到" + destTestDir.getAbsolutePath());
//		        //上面是test文件	 	    	
//	 	    }
	        File bakDir = new File(rootDest+"\\"+srcSubDir.getName());
	 	    copyDir(srcSubDir, bakDir);	
	        logger.info("文件正在从" + srcSubDir.getAbsolutePath() + "复制到" + bakDir.getAbsolutePath() );
	        //备份文件
		}
		} catch (IOException e) {
			logger.error(e.getMessage());
			logger.error("文件复制过程中出错");
		}
		
	}
	
	private static void doDelete(String rootSrc){
		try {
 	      List<File> srcSubDirs = FileUtil.getDirectSubDirs(rootSrc + "/auto/");
 	      for (Iterator iterator = srcSubDirs.iterator(); iterator.hasNext();) {
			File srcSubDir = (File) iterator.next();
			FileUtils.deleteDirectory(srcSubDir);	
	        logger.info("删除目录" + srcSubDir);
		  }

		} catch (IOException e) {
			logger.error(e.getMessage());
			logger.error("删除目录过程中出错");
		}
		
	}

// copyDir(srcSubDir,destDir,destTestDir,orgcode);
	private static void  copyDir(File srcDir,File destDir,File destTestDir,String orgcode) throws IOException{
	        String[] ext = {"xls"};
			Collection files = FileUtils.listFiles(srcDir, ext, false);
			for (Iterator iterator = files.iterator(); iterator.hasNext();) {
				File file = (File) iterator.next();
				String s=file.getName().substring(1, file.getName().indexOf("_"));
				if(orgcode.indexOf(s)<0){
					FileUtils.copyFileToDirectory(file,destDir,true);
				}else{
					FileUtils.copyFileToDirectory(file,destTestDir,true);
				}
				
			}		
	}
	
	private static void  copyDir(File srcDir,File destDir) throws IOException{
        String[] ext = {"xls"};
		Collection files = FileUtils.listFiles(srcDir, ext, false);
		for (Iterator iterator = files.iterator(); iterator.hasNext();) {
			File file = (File) iterator.next();
			FileUtils.copyFileToDirectory(file,destDir,true);
		}		
}	
 	    
	public static void main(String[] args) throws IOException {
		CopyOfCopyFileJob.doCopy("d:/xjpfile","d:/dest","d:/dest");		
	}
}