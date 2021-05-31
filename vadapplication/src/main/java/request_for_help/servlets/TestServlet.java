package request_for_help.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import request_for_help.dao.RequestDaoException;
import request_for_help.service.ImagePhotoReport;
import request_for_help.service.PhotoReport;
import request_for_help.service.RequestForHelp;
import request_for_help.service.RequestForHelpService;
import user.profile.UserService;

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
		RequestForHelp requestForHelp = new RequestForHelp();
		RequestForHelpService requestForHelpService = new RequestForHelpService();
		UserService userService = new UserService();

		requestForHelp = requestForHelpService.findRequestByherId(113);
		PhotoReport photoReport = new PhotoReport();
		photoReport.setAuthorReport(userService.receiveUserById(45));
		photoReport.setCreationDate(Calendar.getInstance());
		photoReport.setName("Тестовый фотоотчет");
		photoReport.setDescription("Тестовое описание");

		Set<ImagePhotoReport> images = new HashSet<ImagePhotoReport>();
		ImagePhotoReport imagePhotoReport = new ImagePhotoReport();
		imagePhotoReport.setImage(null);
		images.add(imagePhotoReport);
		photoReport.setImages(images);

		Set<PhotoReport> photoReports = new HashSet<PhotoReport>();
		photoReports.add(photoReport);
		requestForHelp.setPhotoReports(photoReports);

		try {
			requestForHelpService.appendPhotoReport(requestForHelp);
			String StringResponseJson = gson.toJson(requestForHelpService.findRequestByherId(113));
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
