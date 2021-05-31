package request_for_help.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import request_for_help.dao.RequestDaoException;
import request_for_help.service.RequestForHelp;
import request_for_help.service.RequestForHelpService;

/**
 * Servlet implementation class CreatingPhotoReportServlet
 */
public class CreatingPhotoReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BufferedReader reader;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreatingPhotoReportServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		reader = request.getReader();

		Gson gson = new Gson();
		RequestForHelp requestForHelpWithPhotoReports = new RequestForHelp();
		requestForHelpWithPhotoReports = gson.fromJson(reader, RequestForHelp.class);
		RequestForHelpService requestForHelpService = new RequestForHelpService();

		try {
			requestForHelpService.appendPhotoReport(requestForHelpWithPhotoReports);
			String StringResponseJson = gson
					.toJson(requestForHelpService.findRequestByherId(requestForHelpWithPhotoReports.getId()));
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
