package com.oval.util;

import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ExcelUtil {
	private static final Logger logger = Logger.getLogger(ExcelUtil.class);

	public static boolean isExcel2003(String filePath){  
        return filePath.matches("^.+\\.(?i)(xls)$");  
    }  
	
	public static boolean isExcel2007(String filePath){  
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

	/**
	 * 获取生成好的excel文件数据
	 * @param urlPath
	 * @return
	 */
	public static List<List<String>> readFileFromUrl(String urlPath){
		try{
			URL url = new URL(urlPath);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(6000);
			urlConnection.setReadTimeout(6000);
			urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36");
			urlConnection.setRequestProperty("Content-type","application/vnd.ms-excel");

			urlConnection.connect();
			logger.info("Code100001864读取文件返回状态码="+urlConnection.getResponseCode());
			if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("文件读取失败");
			}
			InputStream inputStream = urlConnection.getInputStream();
			try {
				return ExcelUtil.readExcel(inputStream,false,0);
			} catch (Exception e) {
				logger.error("Code100001864读取excel解析文件异常",e);
			}finally {
				if(inputStream!=null) {
					inputStream.close();
				}
				urlConnection.disconnect();
			}

		}catch (Exception e){
			logger.error("Code100001864根据url读取文件异常",e);
		}
		return null;

	}

	/**
	 * 读取流文件数据
	 * @param fs
	 * @param isExcel2003
	 * @param fromRowNo
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
    public static List<List<String>> readExcel(InputStream fs, boolean isExcel2003, int fromRowNo) throws FileNotFoundException,IOException, IllegalAccessException, InvocationTargetException {
    	Workbook workBook = null;
		List<List<String>> resultList = new ArrayList<>();
		try {
			if(isExcel2003){
				workBook = new HSSFWorkbook(fs);
			}else{
				workBook = new XSSFWorkbook(fs);
			}
			for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
				// 创建工作表
				Sheet sheet = workBook.getSheetAt(i);
				int rows = sheet.getPhysicalNumberOfRows(); // 获得行数
				if (rows > fromRowNo) {
					sheet.getMargin(HSSFSheet.TopMargin);
					for (int r = fromRowNo; r < rows; r++) { // 行循环
						Row row = sheet.getRow(r);
						List<String> rowList = new ArrayList<>();
						if (row != null) {
							int cells = row.getLastCellNum();// 获得列数
							for (int c = 0; c < cells; c++) {
								Cell cell = row.getCell(c);
								if (cell != null) {
									String value = _getValue(cell);
									if(value.length()<=0) {
										rowList.add("");
									}else{
										rowList.add(value.trim());
									}
								}else{
									rowList.add("");
								}
							}
						}
						if(rowList.size()>0) {
							resultList.add(rowList);
						}
					}
				}
			}
		}catch (Exception e){
			logger.error("读取文件失败",e);
		}finally {

			if(fs!=null) {
				fs.close();
			}

		}
		return resultList;
	}
    
	private static String _getValue(Cell cell) {
		String value = "";
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数值型
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是date类型则 ，获取该cell的date值
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					value = format.format(cell.getDateCellValue());
				} else {// 纯数字
					NumberFormat nf = NumberFormat.getInstance();
					value = nf.format(cell.getNumericCellValue());
					Pattern p = Pattern.compile("[0-9]*\\.[0]+");
					if (p.matcher(value).find()) {
						value = value.substring(0, value.lastIndexOf("."));
					}
					if (value.indexOf(",") >= 0) {
						value = value.replace(",", "");
					}
				}
				break;
			/* 此行表示单元格的内容为string类型 */
			case HSSFCell.CELL_TYPE_STRING: // 字符串型
				value = cell.getRichStringCellValue().toString();
				break;
			case HSSFCell.CELL_TYPE_FORMULA:// 公式 型
				// 读公式计算值
				value = String.valueOf(cell.getNumericCellValue());
				if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
					value = cell.getRichStringCellValue().toString();
				}
				// cell.getCellFormula();读公式
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔
				value = " " + cell.getBooleanCellValue();
				break;
			/* 此行表示该单元格值为空 */
			case HSSFCell.CELL_TYPE_BLANK: // 空值
				value = "";
				break;
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				value = "";
				break;
			default:
				value = cell.getRichStringCellValue().toString();
		}

		return value.trim();

	}
	
	/**
	 * 下载文件后读取
	 * @param downUrl
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static List<List<String>> readDownExcelByPost(HttpClient client,String downUrl,String fileDir, String fileName,Map<String,String> header,String paramJson) throws Exception {
		String filePath = String.format("%s/%s", fileDir, fileName);
		File originalPath = new File(fileDir);
		if (!originalPath.exists()) {
			originalPath.mkdirs();
		}
		boolean isExcel2003 = true;
		if(StringUtils.isNotBlank(paramJson)){
			post(client,header,paramJson,downUrl,filePath);
		}else{
			isExcel2003 = false;
			post(client,header,null,downUrl,filePath);
		}
		FileInputStream fis = null;
		List<List<String>> excelData = new ArrayList<>();
		try{
			fis = new FileInputStream(filePath);
			excelData = readExcel(fis,isExcel2003,0);
		}finally {
			new File(filePath).delete();
		}
		return excelData;
	}

	public static List<List<String>> readDownExcelByGet(HttpClient client,String downUrl,String fileDir, String fileName) throws Exception{
		String filePath = String.format("%s/%s", fileDir, fileName);
		File originalPath = new File(fileDir);
		if (!originalPath.exists()) {
			originalPath.mkdirs();
		}
		WebUtil.repeatGetMN(client,downUrl, filePath);

		FileInputStream fis = null;
		List<List<String>> excelData = new ArrayList<>();
		try{
			fis = new FileInputStream(filePath);
			excelData = readExcel(fis,true,0);
		}finally {
			new File(filePath).delete();
		}
		return excelData;
	}


	private static void post(HttpClient client, Map<String, String> header,String paramJson,
							   String url, String filePath) throws Exception {
		HttpPost httppost = null;

		try {
			httppost = new HttpPost(buildURI(url));
			httppost.setHeader("Cookie", header.get("Cookie"));
			if(StringUtils.isNotBlank(paramJson)){
				StringEntity entity=null;
				entity = new StringEntity(paramJson,"UTF-8");
				httppost = new HttpPost(buildURI(url));
				httppost.setHeader("accept", "application/json, text/javascript, */*");
				httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
				httppost.setHeader("accessToken",header.get("accessToken"));
				httppost.setEntity(entity);
			}else{
				httppost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			}
			HttpResponse response = client.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();

			if (HttpStatus.SC_INTERNAL_SERVER_ERROR == statusCode)
				throw new Exception("服务器发生内部错误:"
						+ EntityUtils.toString(response.getEntity()));

			if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()){
				logger.error("请求url返回状态异常="+response.getStatusLine().getStatusCode());
				throw new Exception("请求url返回状态异常");
			}
			HttpEntity respEntity = response.getEntity();
			File storeFile = new File(filePath);
			FileOutputStream output = new FileOutputStream(storeFile);
			respEntity.writeTo(output);
			output.flush();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			httppost.abort();
		}

	}
	public static URI buildURI(String url) throws MalformedURLException,
			URISyntaxException {
		URL uri = new URL(url);
		return new URI(uri.getProtocol(), null, uri.getHost(), uri.getPort(),
				uri.getPath(), uri.getQuery(), null);
	}


}
