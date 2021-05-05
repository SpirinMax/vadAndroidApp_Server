package user.profile;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import request_for_help.service.RequestForHelp;
import request_for_help.service.RequestForHelpService;
import security.EncodedPassword;

/**
 * Servlet implementation class TestAuth
 */
public class TestAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BufferedReader reader;
	private static Logger log = LogManager.getLogger();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestAuth() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");

		Gson gson = new Gson();

		UserService userService = new UserService();

		int idAuthor = Integer.valueOf(request.getParameter("idAuthor"));
		String name = request.getParameter("name");
		String region = request.getParameter("region");
		String district = request.getParameter("district");
		String city = request.getParameter("city");
		String street = request.getParameter("street");
		String houseNumber = request.getParameter("houseNumber");
		String creationDate = request.getParameter("creationDate");
		String startDate = request.getParameter("startDate");
		String description = request.getParameter("description");

		RequestForHelp requestForHelp = new RequestForHelp();
		requestForHelp.setName(name);
		requestForHelp.setRegion(region);
		requestForHelp.setDistrict(district);
		requestForHelp.setCity(city);
		requestForHelp.setStreet(street);
		requestForHelp.setHouseNumber(houseNumber);
		requestForHelp.setCreationDate(LocalDateTime.now());
		requestForHelp.setStartDate(LocalDateTime.now());
		requestForHelp.setDescription(description);

		User authorUser = userService.receiveUserById(idAuthor);

		List<RequestForHelp> listRequest = new ArrayList<RequestForHelp>();

		RequestForHelpService requestForHelpService = new RequestForHelpService();

		try {
			requestForHelpService.createRequestForHelp(requestForHelp, authorUser);
			listRequest = requestForHelpService.receiveRequestThatAuthorCreated(authorUser);
			requestForHelpService.hideCredentialsDataInRequest(listRequest);
			String StringResponseJson = gson.toJson(listRequest);
			PrintWriter out = response.getWriter();
			out.print(StringResponseJson);
		} catch (Exception e) {
			response.setStatus(401);
		} finally {
			reader.close();
		}

	}

}
