<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="sevenphase.verifycode.VerifyAbstractAction"%>
<%@page import="servlet.VerifyManager,java.util.Collection"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>WEB验证码网页抓取页面</title>
		<script language="javascript" type="text/javascript" src="../js/jquery-1.4.4.min.js">
		</script>
		
		<link type="text/css" rel="stylesheet" href="../JSP/css.css" />
		<script type="text/javascript">

			function CheckTodoList(){
		         var data = "cmd=checkTodoList"; 
		         $.ajax({
			       url: "../ConsoleServlet",	
			       type: "POST",	
			       data: data,		
			       cache: false,
			       success: function (html) {
   	    	          if ("true" == html){
       	    	         alert("任务列表已满!"); 
       	    	         return true;
   	    	          }else
   	   	    	          return false;
			       }		
		      });
			}

			function executeTask(){
				alert("开始执行任务!");
		         var data = "cmd=execTask"; 
		         $.ajax({
			       url: "../ConsoleServlet",	
			       type: "POST",	
			       data: data,		
			       cache: false,
			       success: function (html) {				   
       			   temp = html.replace( /^\s+|\s+$/g, "" );
	    	          if ("success" == temp)
    	    	        alert("开始执行任务!"); 
	    	          else if("over"==temp){
		    	          alert("执行完成!");
	    	          } else{		    	          
					    alert("服务器正在执行任务,请稍候再执行新的任务");	
	    	          }				  					    
    			   }	 	    	          
		      });
			      	      
			}
			
	    
			function addTask(orgCode){
				if (CheckTodoList())
					return;
				var data = "cmd=addTask&orgCode=" + orgCode + "&verifyCode=" + $('#c-'+orgCode).attr("value")+"&DataMerge="+$('#s-'+orgCode).attr("checked");
        		$.ajax({
        			url: "../ConsoleServlet",	
        			type: "POST",	
        			data: data,		
        			cache: false,
        			success: function (html) {
        			  temp = html.replace( /^\s+|\s+$/g, "" );
 	    	          if ("success" == temp)
        	    	     alert("加入任务列表成功!"); 
    	    	      else
     	    	         alert("加入任务列表失败!"); 

 	        		  $('#image-'+orgCode).attr('src','../verifycode/refresh.jpg?time=' + Math.random());   	
 	        		  $('#c-'+orgCode).attr('value','');    	         
        			}		
        		});
			}

			function getVerifyCode(orgCode){
				var data = "cmd=getCode&orgCode=" + orgCode; 
        		$.ajax({
        			url: "../ConsoleServlet",	
        			type: "POST",	
        			data: data,		
        			cache: false,
 		            async: false, 
        			success: function (html) {
        		    $('#image-'+orgCode).attr('src','../verifycode/' + orgCode + '.jpg?time=' + Math.random());
        			}		
        		});
			}

			function refresh(orgCode){
        	  $('#image-'+orgCode).attr('src','../verifycode/' + orgCode + '.jpg?time=' + Math.random());
			}
		</script>
	</head>
	
	<body style="text-align: center">
		<h2>WEB验证码网页抓取页面</h2>
		<hr>
		<form action="ConsoleServlet" name="cform" target="self" method="post">
		
		<div style="width:935px; height:30px; margin-bottom:10px;" class="p-t_10">
			<div class="name_1">经销商代码：</div>
     		<div class="input_box"><input type="text" /></div>
		    <div class="name_1 p-l_10">经销商名称：</div>
		    <div class="input_box"><input type="text" /></div>
		    <div class="name_1 p-l_10">状态：</div>
		    <div class="input_box"><input type="text" /></div>
		    <div class="btn">
		    <input type="button" name="查看" value="查看"  />
		    </div>
		</div>

		<div style=" position: fixed; _position: absolute;top: 10px; right: 1px; _bottom: auto; 
               _top: expression(eval(document.documentElement.scrollTop+50)); padding: 1px; 
               z-index: 9999999; width:100px; height:30px;" class="p-t_10">
		    <input type="button"  value="执行任务"  onclick="executeTask()" />
		</div>

				
		<div align="center">
			<table border="0" cellspacing="0" class="table_line">
				<tr>
					<td width="30" class="td_title" align="center">序号</td>
					<td width="125" class="td_title" align="center">经销商代码</td>
			    	<td width="225" class="td_title" align="center">经销商名称</td>
			    	<td width="200" class="td_title" align="center">网址</td>
			    	<td width="200" class="td_title" align="center">验证码</td>
			    	<td width="130" class="td_title" align="center">验证码</td>
			    	<!-- <td width="130" class="td_title" align="center">数据合并</td>-->
			    	<td width="80" class="td_title"  align="center">刷新</td>
			    	<td width="100" class="td_title" align="center">执行</td>
			    	<td width="85" class="td_title" align="center">状态</td>
			  </tr>
			  
				<% Collection<VerifyAbstractAction> actors = VerifyManager.getInstance().getVerifyActors().values();
					int i=0;
				%>
				<% for (VerifyAbstractAction actor: actors) {%>
					<tr>
						<td class="td_con"  align="center"><%=i + 1%></td> 
						<td class="td_con" align="center"><%=actor.getOrgCode()%><input type="hidden" id="orgCode<%=i %>" name="orgCode<%=i %>" value="<%=actor.getOrgCode()%>"/></td>
						<td class="td_con" align="center"><%=actor.getOrgName()%></td>
						<td class="td_con" align="center">http://www.163.com</td>
						
						<td  align="center">
							<img id="image-<%=actor.getOrgCode()%>" src="../verifycode/refresh.jpg" style="background-color: blue"  onclick="getVerifyCode('<%=actor.getOrgCode()%>')" align=middle>
						</td>
						<td class="td_con" align="center"><input id="c-<%=actor.getOrgCode()%>" name="c-<%=actor.getOrgCode()%>" size="8" type="text"></input></td>
						<!-- <td class="td_con" align="center"><input id="s-<%=actor.getOrgCode()%>" name="s-<%=actor.getOrgCode()%>" type="checkbox"/></td> -->
						<td class="td_con" align="center"><input type="button" value="刷新验证码" onclick="refresh('<%=actor.getOrgCode()%>')"/></td>
						<td class="td_con" align="center"><input type="button" value="加入任务列表" onclick="addTask('<%=actor.getOrgCode()%>')"/></td>
						<td class="td_con" align="center">未执行</td>
					</tr>
				<%i=i+1;}%>
			</table>
			<input type="hidden" name="rownum" value="<%=actors.size()%>"/>
			<input type="hidden" name="currIndex"/>
			<input type="hidden" name="cmd"/>
			
			</div>
		</form>
	</body>
</html>