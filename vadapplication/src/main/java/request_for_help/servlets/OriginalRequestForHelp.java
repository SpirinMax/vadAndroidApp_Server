package request_for_help.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import request_for_help.service.RequestForHelp;
import user.profile.User;

/**
 * Servlet implementation class RequstsForHelpServlet
 */
public class OriginalRequestForHelp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OriginalRequestForHelp() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.setContentType("application/json; charset=UTF-8");
//
//		Gson gson = new Gson();
//		User author = new User();
//		
//		RequestForHelp requestForHelp = new RequestForHelp(author);
//		
//		try {
//			String StringResponseUserJson = gson.toJson(requestForHelp);
//			PrintWriter out = response.getWriter();
//			out.print(StringResponseUserJson);
//		} catch (Exception e) {
//			response.setStatus(401);
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
