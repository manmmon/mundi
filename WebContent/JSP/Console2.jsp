<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="sevenphase.verifycode.VerifyAbstractAction2"%>
<%@page import="servlet.VerifyManager2,java.util.Collection,java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WEB验证码网页抓取页面2</title>
<script language="javascript" type="text/javascript" src="../js/jquery-1.4.4.min.js">
		</script>
		
		<link type="text/css" rel="stylesheet" href="../JSP/css.css" />
<link type="text/css" rel="stylesheet" href="../asserts/css/bootstrap.min.css" />
</head>
<body style="text-align: center">
		<h2>WEB验证码网页抓取页面2</h2>
		<hr>
		<form action="ConsoleServlet2" name="cform" target="self" method="post">
		
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
				<thead>
					<tr>
						<td  class="td_title" align="center">序号</td>
						<td  class="td_title" align="center">经销商代码</td>
						<td  class="td_title" align="center">经销商名称</td>
						<td  class="td_title" align="center">网址</td>
						<td  class="td_title" align="center">验证码</td>
						<td  class="td_title" align="center">验证码</td>
						<!-- <td width="130" class="td_title" align="center">数据合并</td>-->
						<td  class="td_title" align="center">刷新</td>
						<td  class="td_title" align="center">执行</td>
						<td  class="td_title" align="center">状态</td>
					</tr>
				</thead>
				<tbody>
					<% Collection<VerifyAbstractAction2> actors = VerifyManager2.getInstance().getVerifyActors().values();
					int i=0;
				%>
					<% for (VerifyAbstractAction2 actor: actors) {%>
					<tr>
						<td class="td_con" align="center"><%=i + 1%></td>
						<td class="td_con" align="center"><%=actor.getOrgCode()%><input
							type="hidden" id="orgCode<%=i %>" name="orgCode<%=i %>"
							value="<%=actor.getOrgCode()%>" /></td>
						<td class="td_con" align="center"><%=actor.getOrgName()%></td>
						<td class="td_con" align="center">http://www.163.com</td>

						<td align="center"><img id="image-<%=actor.getOrgCode()%>"
							src="../verifycode/refresh.jpg" style="background-color: blue"
							onclick="getVerifyCode('<%=actor.getOrgCode()%>')" align=middle>
						</td>
						<td class="td_con" align="center"><input
							id="c-<%=actor.getOrgCode()%>" name="c-<%=actor.getOrgCode()%>"
							size="8" type="text"></input></td>
						<!-- <td class="td_con" align="center"><input id="s-<%=actor.getOrgCode()%>" name="s-<%=actor.getOrgCode()%>" type="checkbox"/></td> -->
						<td class="td_con" align="center"><input type="button"
							value="刷新验证码" onclick="refresh('<%=actor.getOrgCode()%>')" /></td>
						<td class="td_con" align="center"><input type="button"
							value="加入任务列表" onclick="addTask('<%=actor.getOrgCode()%>')" /></td>
						<td id="z-<%=actor.getOrgCode()%>" class="td_con" align="center">库存:<%=actor.getStatus().get(0)%><br />销售:<%=actor.getStatus().get(1)%><br />采购:<%=actor.getStatus().get(2)%></td>
					</tr>
					<%
						i = i + 1;
						}
					%>
				</tbody>
			</table>
			<input type="hidden" name="rownum" value="<%=actors.size()%>" /> <input
				type="hidden" name="currIndex" /> <input type="hidden" name="cmd" />

		</div>
		</form>
		</div>
	</div>
	<script src="../asserts/js/jquery.min.js"></script>
	<script src="../js/fancyTable.js"></script>
	<script src="../asserts/js/bootstrap.min.js"></script>
	<script src="../asserts/js/popper.min.js"></script>
	<script type="text/javascript">
	
	$(document).ready(function () {
  	  $("#sampleTableA").fancyTable({
        pagination: true,
     	   perPage: 120,
        globalSearch: true
   	 });
	});
	
		function CheckTodoList(){
		         var data = "cmd=checkTodoList"; 
		         $.ajax({
			       url: "../ConsoleServlet2",	
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
				
				function refreshStatus(){
					<% Collection<VerifyAbstractAction2> m = VerifyManager2.getInstance().getVerifyActors().values();
					for(VerifyAbstractAction2 n:m){
						n.setStatus(n.HasExistsFileList(n.getOrgCode(), n.getOrgName()));
 						%>$('#z-<%=n.getOrgCode() %>').innerText="库存:<%=n.getStatus().get(0)%><br />销售:<%=n.getStatus().get(1)%><br />采购:<%=n.getStatus().get(2)%>"; 
						<%
					}
 					%> 
					window.location.reload()
				}

			function executeTask(){
				alert("开始执行任务!");
		         var data = "cmd=execTask"; 
		         $.ajax({
			       url: "../ConsoleServlet2",	
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
				var data = "cmd=addTask&orgCode=" + orgCode + "&verifyCode=" + $('#c-'+orgCode).val()+"&DataMerge="+$('#s-'+orgCode).attr("checked");
        		$.ajax({
        			url: "../ConsoleServlet2",	
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
 	        		  $('#c-'+orgCode).val("");   
        			}		
        		});
			}

			function getVerifyCode(orgCode){
				var data = "cmd=getCode&orgCode=" + orgCode; 
        		$.ajax({
        			url: "../ConsoleServlet2",	
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
			
			function restart(){
				var data = "cmd=restart"; 
        		$.ajax({
        			url: "../ConsoleServlet2",	
        			type: "POST",	
        			data: data,		
        			cache: false,
        			success: function (html) {
        				alert('后台10s后启动success，请刷新页面,卡的话返回上级页面再进');
        			}		
        		});
			}
		</script>
	
</body>
</html>