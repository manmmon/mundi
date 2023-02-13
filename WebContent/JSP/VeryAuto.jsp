<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="sevenphase.verifycode.VerifyAbstractAction"%>
<%@page import="servlet.VerifyManager,java.util.Collection"%>
<%@page import="com.oval.util.ReadXMLUtil,com.oval.grabweb.action.*" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>WEB无验证码网页抓取页面</title>		
		<link type="text/css" rel="stylesheet" href="css.css" />
		<script language="javascript">
		//使用AJAX
		  var xmlHttp=null;
		  function GetXmlHttpObject(){
			 try{
				xmlHttp=new XMLHttpRequest();
			 }catch(e){
				try{
				   xmlHttp=new ActiveXObject("Msxm12.XMLHTTP");
				}catch(e){
				   xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
				}
			 }
			 return xmlHttp;
		  }

		  function addtasklist(orgcode,OrgName){
			 xmlHttp=GetXmlHttpObject();
			 if(xmlHttp==null){
				alert("you browser don't support the ajax");
				return;
			  }//+ escape(new Date())			
			 var url="OperatorAutoDealer.jsp?"+escape(new Date())+"&OrgName="+OrgName+"&orgcode="+orgcode;			
			 xmlHttp.open("GET",url,true);
			 xmlHttp.onreadystatechange=stateChanged;
			 xmlHttp.send(null);				  
		  }

		  function stateChanged(){
				 if(xmlHttp.readyState==4){
				   if(xmlHttp.status==200){
					   var root = xmlHttp.responseText; 	   
					   if(root==0){
							  alert("加入任务失败!!!");
					   }else{
							  alert("加入任务成功!!!");
						}  
				   }
				 }		 
			  }		  
		  
		  function OperateAuto(){
			xmlHttp=GetXmlHttpObject();
			if(xmlHttp==null){
			  alert("you browser don't support the ajax");
			  return;
			}//+ escape(new Date())			
			var url="tastopeator.jsp?"+escape(new Date());			
			xmlHttp.open("GET",url,true);
			xmlHttp.onreadystatechange=stateChangedtask;
			alert("开始执行!!!");
			xmlHttp.send(null);			
		  }

		  function stateChangedtask(){
			 if(xmlHttp.readyState==4){
			   if(xmlHttp.status==200){
				   var root = xmlHttp.responseText; 	   
				   if(root==0){
						  alert("自动取数程序正在运行,请稍等几分钟执行人工取数!!!");
				   }else{
						  alert("执行完成!!!");
					}  
			   }
			 }		 
		  }
		</script>
	</head>
	
	<body style="text-align: center">
		<h2>无验证码网页抓取程序</h2>
		<hr>
		<div style=" position: fixed; _position: absolute;top: 10px; right: 1px; _bottom: auto; 
               _top: expression(eval(document.documentElement.scrollTop+50)); padding: 1px; 
               z-index: 9999999; width:100px; height:30px;" class="p-t_10">
		    <input type="button"  value="执行任务"  onclick="OperateAuto();" />
		</div>	
		<form name="cform" target="self" method="post">				
		<div align="center">
			<table border="0" cellspacing="0">
			 <tr>
					<td width="30" class="td_title" align="center">序号</td>
					<td width="125" class="td_title" align="center">经销商代码</td>
			    	<td width="225" class="td_title" align="center">经销商名称</td>
			    	<td width="200" class="td_title" align="center">网址</td>
			    	<td width="200" class="td_title" align="center">操作</td>
			  </tr>			  
		      <% 		        
		        String[] z;
		        StringBuffer xmlsb=new StringBuffer();
		        GetXMLInfo xinfo=new GetXMLInfo();
		        xmlsb=xinfo.GetXmlData();
		        xinfo.setXmlsb(xmlsb);
		        String[] m=xmlsb.toString().split("#");
			  %>
			  <% for (int i=1;i<m.length;i++) {
				      z=m[i].split(",");		  
			  %>
			   <tr>
				  <td class="td_con"  align="center"><%=i%></td> 
				  <td class="td_con" align="center"><%=z[1]%></td>
				  <td class="td_con" align="center"><%=z[2]%></td>
				  <td class="td_con" align="center">http://www.163.com</td>
				  <td class="td_con" align="center"><input type="button" onclick="addtasklist('<%=z[1]%>','<%=z[2]%>');" name="autoactor" id="autoactor" value="加入任务"></input></td>
               </tr>
			  <%}%>				
			</table>			
			</div>
		</form>
	</body>
</html>