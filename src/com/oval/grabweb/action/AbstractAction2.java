package com.oval.grabweb.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.alibaba.fastjson.JSONObject;
import com.oval.util.CodeUtil;
import com.oval.util.CompareList;
import com.oval.util.DateUtil;
import com.oval.util.FileUtil;
import com.oval.util.MD5Util;
import com.oval.util.Md5;
import com.oval.util.StringUtil;
import com.oval.util.Webserclient;

public abstract class AbstractAction2 {
	protected String file_prefix = Constant.DIR_PREFIX + DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
	protected String bak_prefix = Constant.BAK_PREFIX + DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
	protected Logger logger = Logger.getLogger(AbstractAction2.class);
	protected static String column_speractor = "!!";
	protected boolean ismerge = false;
	protected boolean isRemoveDuplicate = false; // 是否需要去重复
	protected boolean loginsuccess = true; // 判断登录是否成功，成功后才能查询数据
	public  boolean purFile =true;//默认采购执行代码
    public  boolean salesFile =true;//默认采购执行代码
    public  boolean invFile =true;//默认采购执行代码
    public  String distId ="";
    public  String callId ="";
    public  String baseUrl ="";

	/**
	 * @return isRemoveDuplicate
	 */
	public boolean isRemoveDuplicate() {
		return isRemoveDuplicate;
	}

	/**
	 * @param isRemoveDuplicate
	 */
	public void setRemoveDuplicate(boolean isRemoveDuplicate) {
		this.isRemoveDuplicate = isRemoveDuplicate;
	}

	public boolean isIsmerge() {
		return ismerge;
	}

	public void setIsmerge(boolean ismerge) {
		this.ismerge = ismerge;
	}

	protected List<String> createStock() {
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		sb.append("库存时间").append(column_speractor);
		sb.append("生产厂家").append(column_speractor);
		sb.append("供应商代码").append(column_speractor);
		sb.append("供应商名称").append(column_speractor);
		sb.append("产品代码").append(column_speractor);
		sb.append("产品名称").append(column_speractor);
		sb.append("产品规格").append(column_speractor);
		sb.append("产品剂型").append(column_speractor);
		sb.append("单位").append(column_speractor);
		sb.append("批号").append(column_speractor);
		sb.append("数量").append(column_speractor);
		sb.append("单价").append(column_speractor);
		sb.append("金额").append(column_speractor);
		sb.append("库存状态").append(column_speractor);
		sb.append("入库时间").append(column_speractor);
		sb.append("入库时间");
		list.add(sb.toString());
		return list;

	}

	protected List<String> createSale() {
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		sb.append("订单时间").append(column_speractor);
		sb.append("出库时间").append(column_speractor);
		sb.append("生产厂家").append(column_speractor);
		sb.append("客户代码").append(column_speractor);
		sb.append("客户名称").append(column_speractor);
		sb.append("产品代码").append(column_speractor);
		sb.append("产品名称").append(column_speractor);
		sb.append("产品规格").append(column_speractor);
		sb.append("剂型").append(column_speractor);
		sb.append("单位").append(column_speractor);
		sb.append("批号").append(column_speractor);
		sb.append("数量").append(column_speractor);
		sb.append("含税单价").append(column_speractor);
		sb.append("含税金额").append(column_speractor);
		sb.append("出货类型").append(column_speractor);
		sb.append("客户城市").append(column_speractor);
		sb.append("客户地址").append(column_speractor);
		sb.append("经销商发货单号").append(column_speractor);
		sb.append("开票时间");
		list.add(sb.toString());
		return list;
	}

	protected List<String> createPurchas() {
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();

		sb.append("入库时间").append(column_speractor);// 入库时间
		sb.append("生产厂家").append(column_speractor);
		sb.append("供应商代码").append(column_speractor);
		sb.append("供应商名称").append(column_speractor);// 供应商名称
		sb.append("产品代码").append(column_speractor);// 产品编码
		sb.append("产品名称").append(column_speractor);// 产品名称
		sb.append("产品规格").append(column_speractor);// 产品规格
		sb.append("剂型").append(column_speractor);
		sb.append("单位").append(column_speractor);
		sb.append("批号").append(column_speractor);
		sb.append("数量").append(column_speractor);
		sb.append("含税单价").append(column_speractor);
		sb.append("含税金额").append(column_speractor);
		sb.append("进货类型").append(column_speractor);
		sb.append("供应商入库单号").append(column_speractor);
		sb.append("有效期").append(column_speractor);
		sb.append("开票时间");
		list.add(sb.toString());
		
		return list;
	}

	protected abstract String[] createPurchas(int paramInt);

	protected abstract String[] createStock(int paramInt);

	protected abstract String[] createSale(int paramInt);

	protected abstract void login(CloseableHttpClient paramHttpClient, Map<String, String> paramMap) throws Exception;

	protected abstract String[] getStock(CloseableHttpClient paramHttpClient, String paramString) throws Exception;

	protected abstract String[] getPurchas(CloseableHttpClient paramHttpClient, String paramString) throws Exception;

	protected abstract String[] getSale(CloseableHttpClient paramHttpClient, String paramString) throws Exception;

	protected void addColumn(String row, StringBuilder sb, int columnCount) {
		sb.append(StringUtil.getColumnValue(row, columnCount)).append(column_speractor);
	}

	protected void addColumn(String row, StringBuffer sb, int columnCount) {
		sb.append(StringUtil.getColumnValue(row, columnCount)).append(column_speractor);
	}

	public void HasCreateDir(String FileDir) {
		File Fdir = new File(FileDir);
		if (!(Fdir.exists())) {
			String Cdir = Fdir.getParent();
			new File(Cdir).mkdirs();
		}
	}

	public boolean HasExistsFile(String orgCode, String orgName) {

		boolean IsExistStockFile = !invFile || checkFile(orgCode, orgName, Constant.STOCK);

		boolean IsExistSaleFile =!salesFile || checkFile(orgCode, orgName, Constant.SALE);

		boolean IsExistPurchaseFile =!purFile || checkFile(orgCode, orgName, Constant.PURCHASE);

		boolean IsWebFile;
		if ((IsExistStockFile) && (IsExistSaleFile) && (IsExistPurchaseFile))
			IsWebFile = true;
		else {
			IsWebFile = false;
		}
		return IsWebFile;
		
	}

	/**
	 * @param orgCode
	 * @param orgName
	 * @param type
	 * @return
	 */
	private boolean checkFile(String orgCode, String orgName, String type) {

		String filename;
		String bak_filename;
		String file_suffix = orgCode + "_" + orgName + "_" + DateUtil.getLastDay("yyyyMMdd") +Constant.FILE_LASTFIX+ ".xls";
		switch (type) {
		case Constant.STOCK:
			filename = file_prefix + "/" + "ID_" + file_suffix;
			bak_filename = bak_prefix + "/" + "ID_" + file_suffix;
			break;
		case Constant.SALE:
			filename = file_prefix + "/" + "SD_" + file_suffix;
			bak_filename = bak_prefix + "/" + "SD_" + file_suffix;
			break;
		default:
			filename = file_prefix + "/" + "PD_" + file_suffix;
			bak_filename = bak_prefix + "/" + "PD_" + file_suffix;
			break;
		}

		boolean IsExistFile;
		if ((FileUtil.checkFile(filename)) || (FileUtil.checkFile(bak_filename)))
			IsExistFile = true;
		else {
			IsExistFile = false;
		}
		return IsExistFile;
	}

	public List<Boolean> HasExistsFileList(String orgCode, String orgName) {
		List<Boolean> list = new ArrayList<Boolean>();
		boolean IsExistStockFile = checkFile(orgCode, orgName, Constant.STOCK);

		boolean IsExistSaleFile = checkFile(orgCode, orgName, Constant.SALE);

		boolean IsExistPurchaseFile = checkFile(orgCode, orgName, Constant.PURCHASE);

		list.add(IsExistStockFile);
		list.add(IsExistSaleFile);
		list.add(IsExistPurchaseFile);
		return list;
	}

	public void exec(CloseableHttpClient client, Map<String, String> loginParams, String orgCode, String orgName,
			StringBuffer sb) throws Exception {
//		file_prefix = Constant.DIR_PREFIX + DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
//		bak_prefix = Constant.BAK_PREFIX + DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
		String account = sb.toString();
		if (HasExistsFile(orgCode, orgName)) {
			return;
		}
		logger.info(orgName + "  " + DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd") + "  日报");
		try {
			if(loginParams.get("uuid")!=null && !loginParams.get("uuid").equals("")){
				if(!getUrlParamMap(loginParams)){//获取远程数据失败
					return;
				}
				baseUrl=loginParams.get("url");
			}
			
			login(client, loginParams);
			//登录失败
			if(!loginsuccess){
				return;
			}
		} catch (Exception e) {
			logger.error(orgName + "的网站不可访问！",e);
			throw new Exception(e.getMessage());
		}

		try {
//			createFile(client, orgCode, orgName, Constant.STOCK, account);

			createFile(client, orgCode, orgName, Constant.SALE, account);

			createFile(client, orgCode, orgName, Constant.PURCHASE, account);

			logger.info("记录抓取日志");
		} catch (Exception e) {
			logger.error("记录" + orgName + "抓取日志过程中发生错误！",e);
			throw new Exception(e.getMessage());
		}
		logger.info(orgName + "  " + DateUtil.getLastDay("yyyyMMdd") + "  日报完成");
		logger.info(
				"------------------------------------------------------------------------------------------------------");
	}

	/**
	 * @param client
	 * @param orgCode
	 * @param orgName
	 * @param sb
	 * @param account
	 * @param dir
	 */
	private void createFile(CloseableHttpClient client, String orgCode, String orgName, String type, String account) {
		String log_type = "";
		String appid="";
		try {
			String filename = null;
			String bak_filename = null;
			String file_suffix = orgCode + "_" + orgName + "_" + DateUtil.getLastDay("yyyyMMdd")
					+Constant.FILE_LASTFIX+ ".xls";

			switch (type) {
			case Constant.STOCK:
				filename = file_prefix + "/" + "ID_" + file_suffix;
				bak_filename = bak_prefix + "/" + "ID_" + file_suffix;
				log_type = "库存";
				break;
			case Constant.SALE:
				filename = file_prefix + "/" + "SD_" + file_suffix;
				bak_filename = bak_prefix + "/" + "SD_" + file_suffix;
				log_type = "销售";
				break;
			default:
				filename = file_prefix + "/" + "PD_" + file_suffix;
				bak_filename = bak_prefix + "/" + "PD_" + file_suffix;
				log_type = "采购";
				break;
			}

			if (!ismerge) {

				if ((FileUtil.checkFile(filename)) || (FileUtil.checkFile(bak_filename))) {
					logger.info(filename);
					logger.info(bak_filename);
					logger.info(log_type + "已生成 ");
				} else {
					String[] data = null;

					switch (type) {
					case Constant.STOCK:
						data = getStock(client, orgName);
						appid=Constant.STOCKAPPID;
						break;
					case Constant.SALE:
						data = getSale(client, orgName);
						appid=Constant.SALEAPPID;
						break;
					default:
						data = getPurchas(client, orgName);
						appid=Constant.PURCHASEAPPID;
						break;
					}

					if (data != null) {
						if (data.length == 1) {
							logger.info(orgName + log_type + "数据为空！");
						}
						HasCreateDir(filename);
						FileUtil.createFile(data, filename, column_speractor);
						logger.info("生成 " + log_type + filename);
						if(!appid.equals("") && !distId.equals("")){
							//生成结束将对应的数据写入的服务器上
							filename=filename.substring(filename.lastIndexOf("/")+1);
							writeLogToUrl(appid, "SUCCESS", filename+"【"+(data.length-1)+"】");
						}
					}
				}
				// 多账号合并
			} else {
				// 创建子文件
				String childDir = file_prefix + "/" + orgCode.concat("_").concat(orgName);
				// 创建子文件名称
				String stockChildFile = childDir + "/" + type + orgCode + "_" + DateUtil.getLastDay("yyyyMMdd") + "_"
						+ orgName + "_" + account + ".xls";
				File dirFile = new File(childDir);
				// 判断子文件路径是否存在
				if (!dirFile.exists())
					dirFile.mkdirs();
				// 若子文件已生成时不作处理
				if ((FileUtil.checkFile(stockChildFile))) {
					logger.info(log_type + "已生成 ");
				} else {
					String[] data = null;
					// 获取库存信息
					switch (type) {
					case Constant.STOCK:
						data = getStock(client, orgName);
						appid=Constant.STOCKAPPID;
						break;
					case Constant.SALE:
						data = getSale(client, orgName);
						appid=Constant.SALEAPPID;
						break;
					default:
						data = getPurchas(client, orgName);
						appid=Constant.PURCHASEAPPID;
						break;
					}

					if (data != null) {
						// 若果某个账号的数据不为空时
						if (data.length != 1) {
							FileUtil.createChildFile(data, stockChildFile.trim(), column_speractor, account);
							logger.info("生成 " + log_type + stockChildFile);
							// 删除现有库存文件
							if (FileUtil.checkFile(filename))
								new File(filename).delete();
							String[] sumStockInfo = null;

							switch (type) {
							case Constant.STOCK:
								sumStockInfo = getSumInfo(childDir, Constant.STOCK);
								break;
							case Constant.SALE:
								sumStockInfo = getSumInfo(childDir, Constant.SALE);
								break;
							default:
								sumStockInfo = getSumInfo(childDir, Constant.PURCHASE);
								break;
							}
							FileUtil.createFile(sumStockInfo, filename, column_speractor);
							logger.info("生成" + log_type + "合并文件 " + sumStockInfo);
							if(!appid.equals("") && !distId.equals("")){
								//生成结束将对应的数据写入的服务器上
								filename=filename.substring(filename.lastIndexOf("/")+1);
								writeLogToUrl( appid, "SUCCESS", filename+"【"+(data.length-1)+"】");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取" + orgName + log_type + "数据过程中发生错误！",e);
		}
	}

	protected String[] getSumInfo(String filepath, String character) throws Exception {
		List<String> list = new ArrayList<String>(); // 定义数据存储集合
		File file = new File(filepath); // 获取文件路下的所有文件
		String[] filelist = file.list();

		// 循环下列文件
		for (int i = 0; i < filelist.length; i++) {
			File readfile = new File(filepath + "\\" + filelist[i]);

			// 找出已V开头的excel文件character： V代表库存文件 S代表销售文件
			if (readfile.getName().startsWith(character)) {
				FileInputStream fileInputStream;
				HSSFWorkbook wb = null;
				try {
					// 获取excel第一个页签的内容
					fileInputStream = new FileInputStream(readfile);
					wb = new HSSFWorkbook(fileInputStream);
					HSSFSheet sheet = wb.getSheetAt(0);
					// 第一个excel的表头需要获取， 其他的去掉不需要
					for (int j = list.size() > 0 ? 1 : 0; j <= sheet.getLastRowNum(); j++) {
						StringBuffer o = new StringBuffer();
						for (int p = 0; p < sheet.getRow(0).getLastCellNum(); p++) {// 以表头的列为准
							o.append(sheet.getRow(j).getCell(p)).append("!!");
						}

						// 账号不一致 内容一致的去重复 isRemoveDuplicate为true代表需要不重复 反之不需要
						if (isRemoveDuplicate() && CompareList.contains(o.toString(), list)) {
							continue;
						}
						list.add(o.toString().replace("null", ""));
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}
		}
		return list.toArray(new String[1]);
	}

	/**
	 * 处理xlsx
	 * 
	 * @param filepath
	 * @param character
	 * @return
	 * @throws Exception
	 */
	protected String[] getSumLargeInfo(String filepath, String character) throws Exception {
		List<String> list = new ArrayList<String>(); // 定义数据存储集合
		File file = new File(filepath); // 获取文件路下的所有文件
		String[] filelist = file.list();

		// 循环下列文件
		for (int i = 0; i < filelist.length; i++) {
			File readfile = new File(filepath + "\\" + filelist[i]);

			// 找出已V开头的excel文件character： V代表库存文件 S代表销售文件
			if (readfile.getName().startsWith(character)) {
				FileInputStream fileInputStream;
				XSSFWorkbook wb = null;
				try {
					// 获取excel第一个页签的内容
					fileInputStream = new FileInputStream(readfile);
					wb = new XSSFWorkbook(fileInputStream);
					XSSFSheet sheet = wb.getSheetAt(0);
					// 第一个excel的表头需要获取， 其他的去掉不需要
					for (int j = list.size() > 0 ? 1 : 0; j <= sheet.getLastRowNum(); j++) {
						StringBuffer o = new StringBuffer();
						for (int p = 0; p < sheet.getRow(0).getLastCellNum(); p++) {// 以表头的列为准
							o.append(sheet.getRow(j).getCell(p)).append("!!");
						}

						// 账号不一致 内容一致的去重复 isRemoveDuplicate为true代表需要不重复 反之不需要
						if (isRemoveDuplicate() && CompareList.contains(o.toString(), list)) {
							continue;
						}
						list.add(o.toString().replace("null", ""));
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (wb != null) {
						wb.close();
					}
				}
			}
		}
		return list.toArray(new String[1]);
	}
	
	
	
	/**
	 * 获取远程地址并得到对应的参数
	 */
	public boolean getUrlParamMap(Map<String, String> params) {
		String baseUrl = "fnmfddwTaZvyIEr65so2Sp8AopiwpSW1Bj7dyl+NgQWhjKwOsIpgdOZqwVBLabNzREHjpOw/zMI=#";
		String queryXml="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.com\"><soapenv:Header/><soapenv:Body><web:query><web:param>#param#</web:param></web:query></soapenv:Body></soapenv:Envelope>";
		// MD5解密并连接对应的具体地址
		baseUrl = Md5.decrypt(baseUrl);
		baseUrl = baseUrl.concat("GetCallWs2nd");
		distId=params.get("distId");
		callId=params.get("callId");
		/**
		 * 提交的参数
		 */
		JSONObject json = new JSONObject();
		json.put("apiId", "50");
		json.put("type", "1");
		json.put("distId", params.get("distId"));
		json.put("callId", params.get("callId"));
		json.put("uuid", params.get("uuid"));
		String strParamAgain = CodeUtil.encodeString(json.toJSONString()); // 加密参数
		queryXml = queryXml.replace("#param#", strParamAgain);
		String strResult = invoke(baseUrl,"query",queryXml);
		String strResultAgain = CodeUtil.decodeString(strResult); // 解密返回
		System.out.println(strResultAgain);
		JSONObject object = JSONObject.parseObject(strResultAgain);
		String strSuccess = object.getString("success");
		if (strSuccess.equals("true")) {
			JSONObject resultData = JSONObject.parseObject(object
					.getString("resultData"));
			params.put("apiId", resultData.getString("apiId"));
			params.put("username", resultData.getString("account"));
			params.put("password", StringEscapeUtils.escapeXml(resultData
					.getString("password")));
			params.put("privilegekey", resultData.getString("privilegekey"));// 私钥
			params.put("specialstring", resultData.getString("specialstring"));// 特殊字符串
			params.put("bak1", resultData.getString("bak1"));// 备用字段1
			params.put("bak2", resultData.getString("bak2"));// 备用字段2
			params.put("bak3", resultData.getString("bak3"));// 备用字段3
			params.put("url", resultData.getString("url"));// 备用字段3
			return true;
		} else {
			System.out.println("执行失败，请确认参数正确与否");
			return false;
		}
	}
	
	/**
	 * 写日志
	 */
	public void writeLogToUrl(String apiId,String status,String msg) {
		String logxml="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.com\"><soapenv:Header/><soapenv:Body><web:log><web:param>#param#</web:param></web:log></soapenv:Body></soapenv:Envelope>";
		String baseUrl = "fnmfddwTaZvyIEr65so2Sp8AopiwpSW1Bj7dyl+NgQWhjKwOsIpgdOZqwVBLabNzREHjpOw/zMI=#";
		// MD5解密并连接对应的具体地址
		baseUrl = Md5.decrypt(baseUrl);
		baseUrl = baseUrl.concat("RunCallWs2nd");
		/**
		 * 提交的参数
		 */
		JSONObject json = new JSONObject();
		json.put("apiId", apiId);
		json.put("type", "1");
		json.put("distId", distId);
		json.put("callId", callId);
		json.put("status", status);
		json.put("msg", msg);
		String strParamAgain = CodeUtil.encodeString(json.toJSONString()); // 加密参数
		logxml = logxml.replace("#param#", strParamAgain);
		String strResult = invoke(baseUrl, "log",logxml);
		String strResultAgain = CodeUtil.decodeString(strResult); // 解密返回
	}
	
	/**
	 * post调用webservice接口
	 * 
	 * @param address
	 * @param params
	 * @param method
	 * @return
	 */
	public static String invoke(String address, String method,String xml) {
		String result = StringEscapeUtils.unescapeXml(Webserclient
				.sendSoapPost(address, xml,
						"http://webservice.com" + method));
		Document doc;
		try {
			doc = DocumentHelper.parseText(result);
			result = doc.getRootElement().element("Body")
					.element(method+"Response").element("return").getText();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public static void main(String[] args) {
		AbstractAction2 a=new AbstractAction2() {
			
			@Override
			protected void login(CloseableHttpClient paramHttpClient,
					Map<String, String> paramMap) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected String[] getStock(CloseableHttpClient paramHttpClient, String paramString)
					throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			protected String[] getSale(CloseableHttpClient paramHttpClient, String paramString)
					throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			protected String[] getPurchas(CloseableHttpClient paramHttpClient, String paramString)
					throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			protected String[] createStock(int paramInt) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			protected String[] createSale(int paramInt) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			protected String[] createPurchas(int paramInt) {
				// TODO Auto-generated method stub
				return null;
			}
		};
//		Map<String, String> params=new HashMap<String, String>();
//		params.put("distId", "3443");
//		params.put("callId", "1605");
//		params.put("uuid", "5C33E7DA-1074-449B-9A46-97D3DCD143EB");
//		a.getUrlParamMap(params);
//		String apiId="54";
//		String status="success";
//		String msg="测试";
//		a.writeLogToUrl(apiId,status,msg);
		/*String filename="D:/XJPFile/auto/2021-02-28/ID_002551_江苏省润天生化医药有限公司_20210228_DD_PFM_ALL_00.xls";
		filename=filename.substring(filename.lastIndexOf("/")+1);
		System.out.println(filename);*/
		String str="lalx123456";
		MD5Util md5=new MD5Util();
		System.out.println(md5.MD5(str));
//		eval("(" + $.Base64.decode(xmlDoc.substr(3)) + ")");
//		str="89|eyJwYXJhIjoiMDAiLCJtc2ciOiLlr4bnoIHkuI3mraPnoa4ifQ==";
//		BASE64Decoder decoder = new BASE64Decoder();
//		try {
//			byte[] b=decoder.decodeBuffer(str);
////			byte[] b=decoder.decodeBuffer(str.substring(3));
//			
//			System.out.println(new String(b));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}


}