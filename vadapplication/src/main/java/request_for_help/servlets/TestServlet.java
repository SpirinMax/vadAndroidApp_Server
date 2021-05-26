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
import request_for_help.service.PhotoReport;
import request_for_help.service.RequestForHelpService;

/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestServlet() {
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
		List<PhotoReport> listPhotoReports = new ArrayList<PhotoReport>();
		RequestForHelpService requestForHelpService = new RequestForHelpService();

		try {
			listPhotoReports = requestForHelpService.receiveAllPhotoReports();
			String StringResponseJson = gson.toJson(listPhotoReports);
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
