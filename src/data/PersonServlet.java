package data;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.json.JSONObject;

/**
 * Servlet implementation class PersonServlet
 */
@WebServlet("/PersonServlet")
public class PersonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public PersonServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestUrl = request.getRequestURI();
		String name = requestUrl.substring(requestUrl.lastIndexOf("/")+1);
		
		String person = DataStore.getInstance().getPerson(name);
		
//		if(person!=null) {
//			String json = "{\n";
//			json += "\"name\": "+JSONObject.quote(person.getName()) + ",\n";
//			json +="\"Profession\": "+JSONObject.quote(person.getProfession()) + ",\n";
//			json +="\"Year\": "+ person.getYear() + ",\n";
//			json += "}";
//			try {
//				response.getOutputStream().println(json);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			if(person!=null) {
				String json = "{\n";
//				json += "\"name\": "+JSONObject.quote(name) + ",\n";
//				json +="\"Profession\": "+JSONObject.quote(person) + ",\n";
				json += "}";
				try {
					response.getOutputStream().println(json);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} else {
			try {
				response.getOutputStream().println("{}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String prof = request.getParameter("Profession");
		int year = Integer.parseInt(request.getParameter("Year"));
		
//		DataStore.getInstance().setPerson(new Person(name,prof,year));
	}

}
