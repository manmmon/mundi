package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConsoleServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VerifyManager2 VerifyManager2;
       
    public ConsoleServlet2() throws Exception {
        super();
        VerifyManager2 = VerifyManager2.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String comand = String.valueOf(request.getParameter("cmd"));
		
		String orgCode = request.getParameter("orgCode");
		
		if("execTask".equalsIgnoreCase(comand)){
			try {
				if (VerifyManager2.isBusy()){
					pw.println("busy");
				    pw.flush();
				    pw.close();
				}
				else{
//					pw.println("success");
//					pw.flush();
//					pw.close();	
				    VerifyManager2.execTasks();			    
				    pw.println("over");
				    pw.flush();
				    pw.close();
				} 
			} 
			catch (Exception e) {
				pw.println("over");
			}
			finally{
//			    pw.println("over");
				pw.flush();
				pw.close();
			}
		}else if("getCode".equalsIgnoreCase(comand)){
			try {
				VerifyManager2.getVerifyCode(orgCode);
				pw.println("success");
			} catch (Exception e) {
				pw.println("fail");
			}finally{
				pw.flush();
				pw.close();
			}
		}else if("addTask".equalsIgnoreCase(comand)){
			try {
				String verifyCode = request.getParameter("verifyCode");
				String DataMerger=request.getParameter("DataMerge");
				//System.out.println(DataMerger);
//				System.out.println("orgCode" + ":"+ orgCode + " --- verifyCode:" + verifyCode);
				VerifyManager2.addActor(orgCode, verifyCode,DataMerger);
				pw.println("success");
			}catch(Exception e){
				pw.println("fail");
			}finally{
				pw.flush();
				pw.close();
			}
		}else if("checkTodoList".equalsIgnoreCase(comand)){
			try {
				if (VerifyManager2.isTodoListFull())
					pw.println("true");
				else
					pw.println("false");
			} finally{
				pw.flush();
				pw.close();
			}
		}else if("restart".equalsIgnoreCase(comand)){
			try {
				Runtime.getRuntime().exec("cmd.exe /c start d:/script/cahweb-Restart.bat");
			} finally{
				pw.flush();
				pw.close();
			}
		}
		else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/VerifyCode.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
