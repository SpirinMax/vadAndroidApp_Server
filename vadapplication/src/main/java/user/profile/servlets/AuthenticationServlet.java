package user.profile.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import request_for_help.service.RequestForHelp;
import user.profile.User;
import user.profile.UserService;

/**
 * Servlet implementation class AuthenticationServlet
 */
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BufferedReader reader;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthenticationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");

		reader = request.getReader();
		
		Gson gson = new Gson();
		User userPasswordJson = new User();
		userPasswordJson = gson.fromJson(reader, User.class);
		String email = userPasswordJson.getEmail();
		String password = userPasswordJson.getPassword();
		UserService user = new UserService();
		
		try {
			User responseUserJson = user.authentication(email, password);
			String StringResponseUserJson = gson.toJson(responseUserJson);
			PrintWriter out = response.getWriter();
			out.print(StringResponseUserJson);
		} catch (Exception e) {
			response.setStatus(401);
		}

	}

}
