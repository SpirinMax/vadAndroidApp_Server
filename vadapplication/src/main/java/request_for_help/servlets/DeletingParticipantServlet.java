package request_for_help.servlets;

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
 * Servlet implementation class DeletingParticipantServlet
 */
public class DeletingParticipantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	public DeletingParticipantServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");
		Gson gson = new Gson();
		RequestForHelpService requestForHelpService = new RequestForHelpService();
		PrintWriter out = null;

		int idParticipant = Integer.parseInt(request.getParameter("idPart"));
		int idRequest = Integer.parseInt(request.getParameter("idRequest"));
		try {
			requestForHelpService.deleteParticipant(idParticipant, idRequest);
			RequestForHelp editedRequestForHelp = requestForHelpService.findRequestByherId(idRequest);
			requestForHelpService.hideCredentialsDataInListParticipants(editedRequestForHelp);
			String StringResponseJson = gson.toJson(editedRequestForHelp);
			out = response.getWriter();
			out.print(StringResponseJson);
		} catch (RequestDaoException daoException) {
			response.setStatus(daoException.getErrorType());
			response.sendError(daoException.getErrorType(), daoException.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
