package user.profile.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import user.profile.User;
import user.profile.UserService;

/**
 * Servlet implementation class DataUserEditedServlet
 */
public class DataUserEditedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BufferedReader reader;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataUserEditedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");
		
		reader = request.getReader();
		Gson gson = new Gson();
		User editedUserData = new User();
		editedUserData = gson.fromJson(reader, User.class);
		UserService userService = new UserService();
		
		try {
			userService.updateUserData(editedUserData); 
			String StringResponseUserJson = gson.toJson(editedUserData);
			PrintWriter out = response.getWriter();
			out.print(StringResponseUserJson);
		} catch (Exception e) {
			response.setStatus(404);
		}
	}

}
