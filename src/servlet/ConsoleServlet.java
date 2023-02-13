package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConsoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VerifyManager verifyManager;
       
    public ConsoleServlet() throws Exception {
        super();
        verifyManager = VerifyManager.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String comand = String.valueOf(request.getParameter("cmd"));
		
		String orgCode = request.getParameter("orgCode");
		
		if("execTask".equalsIgnoreCase(comand)){
			try {
				if (verifyManager.isBusy()){
					pw.println("busy");
				    pw.flush();
				    pw.close();
				}
				else{
//					pw.println("success");
//					pw.flush();
//					pw.close();	
				    verifyManager.execTasks();			    
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
				verifyManager.getVerifyCode(orgCode);
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
				verifyManager.addActor(orgCode, verifyCode,DataMerger);
				pw.println("success");
			}catch(Exception e){
				pw.println("fail");
			}finally{
				pw.flush();
				pw.close();
			}
		}else if("checkTodoList".equalsIgnoreCase(comand)){
			try {
				if (verifyManager.isTodoListFull())
					pw.println("true");
				else
					pw.println("false");
			} finally{
				pw.flush();
				pw.close();
			}
		}else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/VerifyCode.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
