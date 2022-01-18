package data;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import com.google.gson.Gson;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig(
		  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		  maxFileSize = 1024 * 1024 * 10,      // 10 MB
		  maxRequestSize = 1024 * 1024 * 100   // 100 MB
		)
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

	}
	


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Part filePart = request.getPart("file");
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//		String GUID = fileName.substring(0, fileName.lastIndexOf('.'));
//		
//		response.getWriter().print(GUID);

		
		File uploads = new File("C:\\Users\\Chandru\\Documents\\uploadedFiles\\");
		File file = new File(uploads,fileName);
		
		try (InputStream input = filePart.getInputStream()) {
			Files.copy(input,file.toPath(), StandardCopyOption.REPLACE_EXISTING);	
		}
		response.getWriter().println("The file uploaded successfully \n");
		
		List<String> majorSkills = new LinkedList<String>();
		List<String> minorSkills = new LinkedList<String>();
		
		String dbURL = "jdbc:sqlserver://localhost\\sqlexpress;databaseName=Sampledb";
		String usrname = "sa";
		String pwd = "sqlexpresssa";
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rset = null;
		ResultSet rset1 = null;
		
		try {
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			con = DriverManager.getConnection(dbURL, usrname, pwd);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
		
			e.printStackTrace();
		}
		
		if(con!= null) {
			response.getWriter().println("DB Connected \n");
			
			String majorSql = "select TechSkillName from mastertechskill";
			String minorSql = "select OtherSkillName from masterotherskill";
			
			try {
				stmt = con.createStatement();
				rset = stmt.executeQuery(majorSql);
				
				while(rset.next()) {
					majorSkills.add(rset.getString(1));
				}
				
				rset1 =stmt.executeQuery(minorSql);
				while(rset1.next()) {
					minorSkills.add(rset1.getString(1));
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
		} 
		
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		majorSkills.add("Python");
//		majorSkills.add("Django");
//		majorSkills.add("MySQL");
//		majorSkills.add("AWS");
//		majorSkills.add("Flask");
//		majorSkills.add("Linux");
//		
//
//		
//		minorSkills.add("Agile");
//		minorSkills.add("Scrum");
//		minorSkills.add("Database");
//		minorSkills.add("REST");
//		minorSkills.add("Restful");
//		minorSkills.add("API");
//		minorSkills.add("git");
		
		Set<String> majorSkillsFound = new TreeSet<String>();
		
		Set<String> minorSkillsFound = new TreeSet<String>();
		
		String filename = "C:\\Users\\Chandru\\Documents\\uploadedFiles\\"+fileName;
		
//		String reqName = "Python Developer";
		
//		String filename = System.getProperty("user.dir")+"\\data\\"+reqName+" JD.docx";
		
		try {
			XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(filename)));
			
			XWPFWordExtractor xwpfword = new XWPFWordExtractor(doc);
			
			String docText = xwpfword.getText();
			
			
			List<XWPFParagraph> list = doc.getParagraphs();
			for(XWPFParagraph p:list) {
				
				String test = p.getText();
				
				test = test.replaceAll("[\\/\\.\\(\\)\\,\\'\\-]", "");
				
				String[] skills = test.split("[\\s\u00A0]+");
				
				for(String s:skills) {
			
				
					if(majorSkills.stream().anyMatch(s::equalsIgnoreCase)) {
						majorSkillsFound.add(s);
					}
					
					if(minorSkills.stream().anyMatch(s::equalsIgnoreCase)) {
						minorSkillsFound.add(s);
					}
					
				
				}

			}
		

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Gson gson = new Gson();
		
		String major = gson.toJson(majorSkillsFound);
		
		String minor = gson.toJson(minorSkillsFound);
		
		response.getWriter().println("Major: "+major);
		
		response.getWriter().println("Minor: "+minor);
		

		
	}	

}
