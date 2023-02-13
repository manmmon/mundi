package com.oval.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;




import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import servlet.VerifyManager;

public class FileUtil {

	public static boolean checkFile(String fileName){
		File file = new File(fileName);
		return file.exists()? true: false;
	}
	
	
	/**
	 * 处理海量数据
	 * @param content
	 * @param fileName
	 * @param seperator
	 */
	public static void createLargeFile(String[] content, String fileName, String seperator) {
		int colNum =0;
		if (content == null)
			return;
		if(content.length==1){
			return;
		}

		if(content.length==2 && content[1]==null){
		  return;
		}
		try{
			if(content[1]==null){
				return;
			}			
		}catch(Exception e){
			
		}		
		org.apache.poi.ss.usermodel.Workbook workbook = null;
		try {
		workbook = 	new SXSSFWorkbook();
		
		Font font = workbook.createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short)10);
		CellStyle style = workbook.createCellStyle();
	    style.setFont(font);
	    		
		Sheet sheet = workbook.createSheet("page1");
		for (int i = 0; i < content.length; i++) {
			org.apache.poi.ss.usermodel.Row row = sheet.createRow(i);
			String[] cells = content[i].split(seperator);		
			if(i==0){ //表头的情况
				colNum = cells.length-1;
			}
			for (int j = 0; j < cells.length; j++) {
				if(j==colNum){
					continue;
				}
				Cell cell = row.createCell(j);
				cell.setCellValue(cells[j]);
				cell.setCellStyle(style);
			}
			if(VerifyManager.MultiVerCode.equals("false")){
				content[i] = null;
			}
		}
		FileOutputStream fos = new FileOutputStream(new File(fileName));
		workbook.write(fos);
		fos.close();
		content=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 创建海量数据的子excel
	* @Title: createFile 
	* @Description: TODO
	* @param content
	* @param fileName
	* @param seperator 
	* @return void
	 */
	public static void createLargeChildFile(String[] content, String fileName, String seperator, String account) {
		int colNum =0;
		if (content == null)
			return;
		if(content.length==1){
			return;
		}

		if(content.length==2 && content[1]==null){
		  return;
		}
		try{
			if(content[1]==null){
				return;
			}			
		}catch(Exception e){
			
		}
		org.apache.poi.ss.usermodel.Workbook workbook = null;
		try {
			workbook = 	new SXSSFWorkbook();
			
			Font font = workbook.createFont();
			font.setFontName("Arial");
			font.setFontHeightInPoints((short)10);
			CellStyle style = workbook.createCellStyle();
		    style.setFont(font);

		    Sheet sheet = workbook.createSheet("page1");
			for (int i = 0; i < content.length; i++) {
				org.apache.poi.ss.usermodel.Row row = sheet.createRow(i);
				String[] cells = content[i].split(seperator);		
				for (int j = 0; j < cells.length; j++) {
					Cell cell = row.createCell(j);
					cell.setCellValue(cells[j]);
					cell.setCellStyle(style);
				}
				if(i!=0){ //非表头的情况
					Cell cell = row.createCell(colNum);
					cell.setCellValue(account);
					cell.setCellStyle(style);
				} else {
					colNum = cells.length-1;
				}
				//释放引用,以免内存溢出
				if(VerifyManager.MultiVerCode.equals("false")){
					content[i] = null;
				}
							
			}
			FileOutputStream fos = new FileOutputStream(new File(fileName));
			workbook.write(fos);
			fos.close();
			content=null;
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void deleteDirectory(File file){
		if (file ==null || !file.exists())
			return;
		
		File[] files =file.listFiles();
		for (File f : files) {
			if(f.isDirectory()) {
				deleteDirectory(f);
			}else {
				f.delete();
			}
		}
	}
	
	public static void createFile(String[] content, String fileName,String seperator) {
		if (content == null)
			return;
		if(content.length==0){
			return;
		}

		WritableWorkbook wrb = null;
		try {
			wrb = Workbook.createWorkbook(new File(fileName));
			WritableSheet wrs = wrb.createSheet("page1", 0);

			for (int i = 0; i < content.length; i++) {
				if (content[i]==null)
					continue;			

				String[] cells = content[i].split(seperator);
				if(i==0){ //表头的情况
					
				}
				for (int j = 0; j < cells.length; j++) {
					/*if(j==colNum){
						continue;
					}*/
					wrs.addCell(new Label(j, i, cells[j]));
				}
				//释放引用,以免内存溢出
				if(VerifyManager.MultiVerCode.equals("false")){
					content[i] = null;
				}
			}
			content= null;
			wrb.write();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				wrb.close();
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 创建子excel
	* @Title: createFile 
	* @Description: TODO
	* @param content
	* @param fileName
	* @param seperator 
	* @return void
	 */
	public static void createChildFile(String[] content, String fileName,String seperator,String account) {
		int colNum =0;
		if (content == null)
			return;
		if(content.length==1){
			return;
		}

		if(content.length==2 && content[1]==null){
		  return;
		}
		try{
			if(content[1]==null){
				return;
			}			
		}catch(Exception e){
			
		}
		WritableWorkbook wrb = null;
		try {
			wrb = Workbook.createWorkbook(new File(fileName));
			WritableSheet wrs = wrb.createSheet("page1", 0);

			for (int i = 0; i < content.length; i++) {
				if (content[i]==null)
					continue;			
				String[] cells = content[i].split(seperator);
				for (int j = 0; j < cells.length; j++) {
					wrs.addCell(new Label(j, i, cells[j]));
				}
				if(i!=0){ //非表头的情况
//					wrs.addCell(new Label(colNum, i, account));
					wrs.addCell(new Label(colNum, i, ""));
				} else {
					colNum = cells.length-1;
				}
				
				//释放引用,以免内存溢出
				if(VerifyManager.MultiVerCode.equals("false")){
					content[i] = null;
				}
							
			}
			content= null;
			wrb.write();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				wrb.close();
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveToFile(String content, String fileName){
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(fileName));
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void checkAndCreateDir(String dir) {
		File dirFile = new File(dir);
		if (!dirFile.exists())
			dirFile.mkdirs();
	}
	
	public static void delete(String dir){
		File d= new File(dir);
		if (d.isFile())
			return;
		
		File[] files =d.listFiles();
		for (int i = 0; i < files.length; i++) {
			files[i].delete();
		}
	}
	
	public static List<File> getDirectSubDirs(String rootDir){
		List<File> subDirs = new LinkedList<File>();	
		
	   if (StringUtil.isEmpty(rootDir))	
		   return subDirs;
	   
	   File dir = new File(rootDir);
	   if (!dir.exists())
		   return subDirs;
	   
	   File[] files = dir.listFiles();
	   for (int i = 0; i < files.length; i++) {
		 if (files[i].isDirectory())
			 subDirs.add(files[i]);
	   }
	   
	   return subDirs;
	}
	
	public static String readFile(String fileName,String charSet) throws IOException{
		 File file = new File(fileName);
		 return FileUtils.readFileToString(file, charSet);
	}
}
