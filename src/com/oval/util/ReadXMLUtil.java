package com.oval.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ReadXMLUtil {

	private List<Leaf> elemList = new ArrayList<Leaf>();
	public StringBuffer strb = new StringBuffer();
	
	public static  String getChildValue(Element element,String childName){
		return element.selectSingleNode(childName).getText().trim();
	}
	
	public Element getRootElement() throws DocumentException {
		SAXReader reader = new SAXReader();
		String xmlparse = this.getClass().getResource("/").getPath();
		Document srcdoc = reader.read(xmlparse + "350Web.xml");
		Element element = srcdoc.getRootElement(); 
		return element;
	}

	public StringBuffer AnalyzeXML() throws DocumentException {// 开始
		ReadXMLUtil analyzexml = new ReadXMLUtil();
		Element root = analyzexml.getRootElement();
		StringBuffer strsf = new StringBuffer();
		strsf = analyzexml.getElementList(root);
		return strsf;
	}

	public StringBuffer jobXML() throws DocumentException {
		// 读取文件路径及内容
		SAXReader reader = new SAXReader();
		String xmlparse = this.getClass().getResource("/").getPath();
		Document srcdoc = reader.read(xmlparse + "timedTask.xml");
		Element root = srcdoc.getRootElement(); 
		
		//解析内容
		ReadXMLUtil analyzexml = new ReadXMLUtil();
		StringBuffer strsf = new StringBuffer();
		strsf = analyzexml.getJobElementList(root);
		return strsf;
	}
	
	/**
	 * 事务配置文件读取
	* @Title: getJobElementList 
	* @Description: TODO
	* @param element
	* @return 
	* @return StringBuffer
	 */
	public StringBuffer getJobElementList(Element element) {
		List elements = element.elements();
		if (elements.size() == 0) {
			String xpath = element.getPath();
			String value = element.getTextTrim();
			String attributeName = element.attributeValue("name");
			elemList.add(new Leaf(xpath, value, attributeName));
		} else {
			for (Iterator it = elements.iterator(); it.hasNext();) {
				Element elem = (Element) it.next();
				if (elem.getName() == "customer") {
					strb.append("#");
					strb.append(elem.elementText("classname")).append(",");
					strb.append(elem.elementText("orgcode")).append(",");
					strb.append(elem.elementText("orgname")).append(",");
					strb.append(elem.elementText("dailyExpr")).append(",");
					strb.append(elem.elementText("ismerge")).append(",");

				} else if (elem.getName() == "pram") {
					strb.append(elem.attributeValue("name")).append(",");
					strb.append(elem.getStringValue()).append(",");
				} 
				getJobElementList(elem);
			}
		}		
		return strb;
	}
	
	public StringBuffer getElementList(Element element) {
		List elements = element.elements();
		if (elements.size() == 0) {
			String xpath = element.getPath();
			String value = element.getTextTrim();
			String attributeName = element.attributeValue("name");
			elemList.add(new Leaf(xpath, value, attributeName));
		} else {
			for (Iterator it = elements.iterator(); it.hasNext();) {
				Element elem = (Element) it.next();
				if (elem.getName() == "customer") {
			       if (elem.attributeValue("verify").trim().equals("Y")) {
			           continue;
			        }					
					strb.append("#");
					strb.append(elem.elementText("classname")).append(",");
					strb.append(elem.elementText("orgcode")).append(",");
					strb.append(elem.elementText("orgname")).append(",");
				} else if (elem.getName() == "login") {	
					//getElementList(elem);
					//strb.append(elem.elementText("pram")).append(",");
					//strb.append(elem.elementText("pram")).append(",");
				} else if (elem.getName() == "pram") {
					strb.append(elem.getStringValue()).append(",");
					strb.append(elem.attributeValue("name")).append(",");
				}
				getElementList(elem);
			}
		}		
		return strb;
	}
}

class Leaf {
	private String xpath;
	private String value;
	private String attributeName;
	private StringBuffer strbf;

	public StringBuffer getStrbf() {
		return strbf;
	}

	public void setStrbf(StringBuffer strbf) {
		this.strbf = strbf;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public Leaf(String xpath, String value, String attributeName) {
		this.xpath = xpath;
		this.value = value;
		this.attributeName = attributeName;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
