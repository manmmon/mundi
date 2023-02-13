package sevenphase.verifycode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;

import servlet.VerifyManager;

import com.oval.grabweb.action.AbstractAction;
import com.oval.util.DateUtil;
import com.oval.util.FileUtil;
import com.oval.util.GlobalInfo;
import com.oval.util.PropertiesUtil;
import com.oval.util.WebUtil;

public abstract class VerifyAbstractAction extends AbstractAction{
	public VerifyAbstractAction(){
		
	}
	
	protected String account;
	protected HttpClient client;
	protected String orgCode;
    protected String orgName;
    protected Map<String, String> loginParams = new HashMap<String, String>();
    
	protected void login() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.putAll(loginParams);
		WebUtil.repeatPost(client, params, getLoginUrl());
	}

	public void getVerifyCode() throws Exception{
		client = WebUtil.getDefaultHttpClient();
		FileUtils.deleteQuietly(new File(GlobalInfo.verifyStorePath + "/" + orgCode + ".jpg"));
		WebUtil.repeatGet(client, getLoginUrl());
		WebUtil.requestFile(client, getPictureUrl(), GlobalInfo.verifyStorePath + "/" + orgCode + ".jpg");
	}
	

	public  void exec() throws Exception{
		boolean refresh = false;
		String orgcode_temp=orgCode;
		String orgname_temp=orgName;
		try {
			
			if(!ismerge && !(orgCode.indexOf("_") == -1)){//100004496_KW  100004496_BY 针对这两家展示验证码
				orgCode = orgCode.substring(0, orgCode.indexOf("_"));
				orgName = orgName.substring(0, orgName.indexOf("_"));
				refresh = true;
			}
			
			if (HasExistsFile(orgCode, orgName)) {
				return;
			}
			doExec();
		}finally {
			if(client == null) {
				return;
			}
			if(refresh) {
				orgCode=orgcode_temp;
				orgName=orgname_temp;
				VerifyManager.getInstance();
			}
			client.getConnectionManager().shutdown();
			client = null;
		}
	}
	
	public  void doExec() throws Exception{
//		file_prefix = Constant.DIR_PREFIX + DateUtil.getLastDay("yyyy-MM-dd");
//		bak_prefix = Constant.BAK_PREFIX + DateUtil.getLastDay("yyyy-MM-dd");
		String orgCode1 =null;
		if(ismerge){
			orgCode1 = orgCode.substring(0, orgCode.indexOf("_"));
		} else {
			orgCode1 = orgCode;

		}
		String lastday = DateUtil.getBeforeDayAgainstToday(1,"yyyy-MM-dd");
		if(!file_prefix.contains(lastday)) {
			file_prefix = file_prefix.substring(0, file_prefix.lastIndexOf("/")+1)+DateUtil.getBeforeDayAgainstToday(1,"yyyy-MM-dd");
		}
		if(!bak_prefix.contains(lastday)) {
			bak_prefix = bak_prefix.substring(0, bak_prefix.lastIndexOf("/")+1)+DateUtil.getBeforeDayAgainstToday(1,"yyyy-MM-dd");
		}
		
		String suffix =  orgCode + "_" + orgName + "_" + DateUtil.getLastDay("yyyyMMdd") +file_lastfix +".xls";
		System.out.println(orgCode+"====VerifyAbstractAction file_prefix========="+file_prefix);
		logger.info(orgCode+"====VerifyAbstractAction file_prefix========="+file_prefix);
		System.out.println(orgCode+"====VerifyAbstractAction bak_prefix========="+bak_prefix);
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
		String childDir = dir+"/"+orgCode1+"_"+orgName;
		try{
			String stockFile = file_prefix  + "/ID_" + suffix;
			String bakFileV = bak_prefix + "/ID_" + suffix;
			//System.out.println("bakFileV"+bakFileV);
			if(!ismerge){
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
			//多账号合并
			} else {
				String stockChildFile = childDir +"/ID_"+orgCode1+"_"+orgName+"_"+DateUtil.getLastDay("yyyyMMdd")+"_"+account+".xls";
				File dirFile = new File(childDir);
				if (!dirFile.exists())
					dirFile.mkdirs();
				if ((FileUtil.checkFile(stockChildFile))){
					 logger.info("库存已生成 ");	
				} else {
					String[] stockInfo=getStock(client,orgName); 							
					if (stockInfo!=null){
					  //若果某个账号的数据为空时	
					  if (stockInfo.length !=1){
						  if(FileUtil.checkFile(stockFile))  
							  new File(stockFile).delete();
						  FileUtil.createChildFile(stockInfo,stockChildFile.trim(),column_speractor,account);
						  logger.info("生成库存 " + stockChildFile);
//						  synchronized (stockInfo) {
							  String[] sumStockInfo = getSumInfo(childDir,"I");  
							  FileUtil.createFile(sumStockInfo,stockFile.trim(),column_speractor);
							  logger.info("生成库存合并文件 " + stockFile);
//						}
					  }else {
						  logger.info(orgName+"库存为空");
					  }
					}
				}	
			}
		}catch (Exception e){
			logger.error("获取"+orgName+"库存数据过程中发生错误！");	
			VerifyManager.getInstance();
		}
			
		try{//ActionFacade.DirPath	
		    //String saleFile = "E:"+ "/S" + orgCode + "_" + DateUtil.transform(GlobalInfo.endDate, "yyyy-MM-dd", "yyyyMMdd") + "_" + orgName + ".xls";
			String saleFile = file_prefix  + "/SD_" + suffix;
			String bakFileS = bak_prefix  + "/SD_" + suffix;
			String saleFile1 = file_prefix  + "/SD_" + suffix.replace(".xls", ".xlsx");
			String bakFileS1 = bak_prefix  + "/SD_" + suffix.replace(".xls", ".xlsx");
			//System.out.println("bakFileS"+bakFileS);
			//String sale
			if (!ismerge) {

				if ((FileUtil.checkFile(saleFile) || FileUtil.checkFile(bakFileS) 
						|| FileUtil.checkFile(saleFile1) || FileUtil.checkFile(bakFileS1))
						/*&& (VerifyManager.MultiVerCode.equals("false"))*/) {
					logger.info("流向已生成");
				} else {
					String[] saleInfo = getSale(client, orgName);
					if (saleInfo != null) {
						if (saleInfo.length == 1) {
							logger.info(orgName + "流向数据为空！");
						}

						if(saleInfo.length>65536) {
							//有改动111111111111111111111111111111111111111111111111111111
							saleFile=saleFile.replace("xls", "xlsx");
							//1111111111111111111111111111111111111111111111111111111111
							FileUtil.createLargeFile(saleInfo, saleFile, column_speractor);
							logger.info("生成流向" + saleFile);
							return;
						}
						// dbBeginLog("S", orgCode);
						FileUtil.createFile(saleInfo,
								saleFile.replaceAll("TWO", "").trim(),
								column_speractor);
						// dbEndLog("S", orgCode);
						logger.info("生成流向" + saleFile);
					}
				}
				//多账号
			} else {
				//创建子文件
				String saleChildFile = childDir +"/SD_"+orgCode1+"_"+orgName+"_"+DateUtil.getLastDay("yyyyMMdd")+"_"+account+".xls";
				File dirFile = new File(childDir);
				if (!dirFile.exists())
					dirFile.mkdirs();
				if ((FileUtil.checkFile(saleChildFile))){
					 logger.info("流向已生成 ");	
				} else {
					String[] saleInfo=getSale(client,orgName); 							
					if (saleInfo!=null){
					  //若果某个账号的数据为空时	
					  if (saleInfo.length !=1){
						  if(FileUtil.checkFile(saleFile))  
							  new File(saleFile).delete();
						  FileUtil.createChildFile(saleInfo,saleChildFile.trim(),column_speractor,account);
						  logger.info("生成流向" + saleChildFile);
						  String[] sumSaleInfo = getSumInfo(childDir,"S");  
						  FileUtil.createFile(sumSaleInfo,saleFile.trim(),column_speractor);
						  logger.info("生成流向合并文件" + saleFile);
					  }else {
						  logger.info(orgName+"流向为空");
					  }
					}
				}	
			}

		} catch (Exception e) {
			logger.error("获取" + orgName + "流向数据过程中发生错误！");
			throw new Exception(e.getMessage());
		}
		
		try{//ActionFacade.DirPath
			String purchasFile = file_prefix  + "/PD_" + suffix;
			String bakFileP = bak_prefix  + "/PD_" +suffix;
			if(!ismerge) {
			//System.out.println("bakFileV"+bakFileV);
			if ((FileUtil.checkFile(purchasFile)||FileUtil.checkFile(bakFileP))/*&&(VerifyManager.MultiVerCode.equals("false"))*/){
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
			}else {
				//创建子文件
				String purchaseChildFile = childDir +"/PD_"+orgCode1+"_"+orgName+"_"+DateUtil.getLastDay("yyyyMMdd")+"_"+account+".xls";
				File dirFile = new File(childDir);
				if (!dirFile.exists())
					dirFile.mkdirs();
				if ((FileUtil.checkFile(purchaseChildFile))){
					 logger.info("采购已生成 ");	
				} else {
					String[] purchaseInfo=getPurchas(client,orgName); 							
					if (purchaseInfo!=null){
					  //若果某个账号的数据为空时	
					  if (purchaseInfo.length !=1){
						  if(FileUtil.checkFile(purchasFile))  
							  new File(purchasFile).delete();
						  FileUtil.createChildFile(purchaseInfo,purchaseChildFile.trim(),column_speractor,account);
						  logger.info("生成采购 " + purchaseChildFile);
						  String[] sumPurchaseInfo = getSumInfo(childDir,"P");  
						  FileUtil.createFile(sumPurchaseInfo,purchasFile.trim(),column_speractor);
						  logger.info("生成采购合并文件 " + purchasFile);
					  }else {
						  logger.info(orgName+"采购为空");
					  }
					  
					}
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
    public void setClient(HttpClient client) {
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

    public static void main(String[] args) {
    	String lastday = DateUtil.getBeforeDayAgainstToday(1,"yyyy-MM-dd");
    	String file_prefix = PropertiesUtil.getString("BAK_PREFIX","paras")+"2022-10-12";
    	System.out.println(file_prefix);
    	System.out.println(lastday);
    	if(!file_prefix.contains(lastday)) {
			file_prefix = file_prefix.substring(0, file_prefix.lastIndexOf("\\")+1)+DateUtil.getBeforeDayAgainstToday(1,"yyyy-MM-dd");
		}
    	System.out.println(file_prefix);
		
	}
}
