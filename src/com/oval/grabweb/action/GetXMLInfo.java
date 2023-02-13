package com.oval.grabweb.action;

import java.util.ArrayList;
import java.util.HashMap;

import org.dom4j.DocumentException;

import com.oval.util.ReadXMLUtil;

public class GetXMLInfo {

    public String[] z;
    public static StringBuffer xmlsb=new StringBuffer();
    public static ArrayList<String> List = new ArrayList<String>(); 
    public static HashMap<String, String> taskmap=new HashMap<String, String>();

    
    
    
	public static HashMap<String, String> getTaskmap() {
		return GetXMLInfo.taskmap;
	}

	public static void setTaskmap(HashMap<String, String> taskmap) {
		GetXMLInfo.taskmap = taskmap;
	}

	public static ArrayList<String> getList() {		
		return GetXMLInfo.List;
	}

	public static void setList(ArrayList<String> list) {
		GetXMLInfo.List = list;
	}

	public StringBuffer getXmlsb() {
		return xmlsb;
	}

	public void setXmlsb(StringBuffer xmlsb) {
		GetXMLInfo.xmlsb = xmlsb;
	}

	public StringBuffer GetXmlData() throws DocumentException{
	   ReadXMLUtil strxml=new ReadXMLUtil();
//	   xmlsb=strxml.AnalyzeXML();	
	   xmlsb=strxml.jobXML();	   
	   return xmlsb;
	}

	public String[] getZ() {
		return z;
	}

	public void setZ(String[] z) {
		this.z = z;
	}
	
	
}
