package request_for_help.servlets;

import java.io.BufferedReader;
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
import request_for_help.service.ParametersRequestForQuest;
import request_for_help.service.RequestForHelp;
import request_for_help.service.RequestForHelpService;
import user.profile.User;
import user.profile.UserService;

/**
 * Servlet implementation class QuestRequestsForParametersServlet
 */
public class QuestRequestsForParametersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BufferedReader reader;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuestRequestsForParametersServlet() {
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
		PrintWriter out = null;
		reader = request.getReader();

		Gson gson = new Gson();
		ParametersRequestForQuest parametersQuest = new ParametersRequestForQuest();
		parametersQuest = gson.fromJson(reader, ParametersRequestForQuest.class);
		RequestForHelpService requestForHelpService = new RequestForHelpService();
		List<RequestForHelp> listRequests = new ArrayList<RequestForHelp>();

		try {
			listRequests = requestForHelpService.findRequestsByParameters(parametersQuest);
			String StringResponseJson = gson.toJson(listRequests);
			out = response.getWriter();
			out.print(StringResponseJson);
		} catch (RequestDaoException e) {
			response.setStatus(e.getErrorType());
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
