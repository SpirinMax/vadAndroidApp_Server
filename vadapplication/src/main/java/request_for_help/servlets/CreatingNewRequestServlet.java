package request_for_help.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import request_for_help.service.RequestForHelp;
import request_for_help.service.RequestForHelpService;
import user.profile.User;
import user.profile.UserService;

/**
 * Servlet implementation class CreatingNewRequestServlet
 */
public class CreatingNewRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BufferedReader reader;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreatingNewRequestServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		reader = request.getReader();

		Gson gson = new Gson();
		RequestForHelp requestForHelp = new RequestForHelp();
		requestForHelp = gson.fromJson(reader, RequestForHelp.class);
		RequestForHelpService requestForHelpService = new RequestForHelpService();
		UserService userService = new UserService();

		int idAuthor = requestForHelp.getAuthorUser().getId();
		User authorUser = userService.receiveUserById(idAuthor);

		try {
			requestForHelpService.createRequestForHelp(requestForHelp, authorUser);
			String StringResponseJson = gson.toJson(requestForHelp);
			out = response.getWriter();
			out.print(StringResponseJson);
		} catch (Exception e) {
			response.setStatus(401);
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
}