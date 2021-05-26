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

import request_for_help.dao.RequestDaoException;
import request_for_help.service.RequestForHelp;
import request_for_help.service.RequestForHelpService;

/**
 * Servlet implementation class ReceivingRequestsForAuthorServlet
 */
public class ReceivingRequestsForAuthorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReceivingRequestsForAuthorServlet() {
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
		int idAuthor = Integer.parseInt(request.getParameter("idAuthor"));
		List<RequestForHelp> listRequests = new ArrayList<RequestForHelp>();
		RequestForHelpService requestForHelpService = new RequestForHelpService();

		try {
			listRequests = requestForHelpService.receiveRequestThatAuthorCreated(idAuthor);
			//requestForHelpService.hideCredentialsData(listRequests);
			String StringResponseJson = gson.toJson(listRequests);
			out = response.getWriter();
			out.print(StringResponseJson);
		} catch (RequestDaoException e) {
			response.setStatus(e.getErrorType());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
