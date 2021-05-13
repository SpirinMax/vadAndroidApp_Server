package request_for_help.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import request_for_help.service.RequestForHelp;
import request_for_help.service.RequestForHelpService;

/**
 * Servlet implementation class ReceivingAllRequests
 */
public class ReceivingAllRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReceivingAllRequests() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		Gson gson = new Gson();
		List<RequestForHelp> listRequests = new ArrayList<RequestForHelp>();
		RequestForHelpService requestForHelpService = new RequestForHelpService();

		try {
			listRequests = requestForHelpService.receiveAllRequests();
			requestForHelpService.hideCredentialsDataInRequest(listRequests);
			String StringResponseJson = gson.toJson(listRequests);
			out = response.getWriter();
			out.print(StringResponseJson);
		} catch (Exception e) {
			response.setStatus(401);
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
