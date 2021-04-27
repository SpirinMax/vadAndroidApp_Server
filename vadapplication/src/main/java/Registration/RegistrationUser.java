package Registration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import security.EncodedPassword;
import user.profile.User;
import user.profile.UserService;

/**
 * Servlet implementation class RegistrationUser
 */
public class RegistrationUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BufferedReader reader;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationUser() {
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
		
		User userFromJson = new User();
		userFromJson = gson.fromJson(reader, User.class);
		
		String password = userFromJson.getPassword();
		userFromJson.setPassword(EncodedPassword.sendHash(password));
		
		UserService user = new UserService();
		user.register(userFromJson);

		String responseJson = gson.toJson(userFromJson);
		PrintWriter out = response.getWriter();
		out.print(responseJson);
	}

}
