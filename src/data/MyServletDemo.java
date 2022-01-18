package data;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServletDemo extends HttpServlet {
	
	private String msg;
	
	 public void init() {
		 msg = "hello World!";
	 }
	
	 
	 public void doGet(HttpServletRequest req, HttpServletResponse res) {
		 
		 res.setContentType("text/html");
		 
		 try {
			PrintWriter out = res.getWriter();
			out.println("<h1>"+msg+"</h1>");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		 
		 
	 }
	 
	 public void destory() {
		 
	 }
	

}
