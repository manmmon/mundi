<%@ page language="java" contentType="text/html; charset=utf-8" 
    import="java.util.*,java.sql.*,javax.servlet.http.HttpSession,
            java.io.IOException,org.apache.http.client.HttpClient,
            org.apache.log4j.Logger,org.dom4j.DocumentException,
            com.oval.util.ReadXMLUtil,com.oval.util.WebUtil,com.oval.grabweb.action.*" pageEncoding="utf-8"%>

<%@page import="servlet.VerifyManager,java.util.Collection"%>
              
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%
  String m="";
  boolean returnvalue=false;	  
  String orgcode=request.getParameter("orgcode");
  String orgName=new String(request.getParameter("OrgName").getBytes("iso-8859-1"),"gb2312").trim();
  try{
	 GetXMLInfo.taskmap.put(orgcode,orgName);
	 returnvalue=true;
  }catch(Exception e){
	  
  } 
  if(returnvalue){
		 m="1"; 
	  }else{
		 m="0";  
	  }
  response.getWriter().println(m);
  response.getWriter().close();    
%>
</body>
</html>