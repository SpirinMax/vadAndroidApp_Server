package request_for_help.service;

import java.util.List;

import request_for_help.dao.RequestForHelpDao;
import user.profile.User;

public class RequestForHelpService {

	private RequestForHelpDao requestForHelpDao = new RequestForHelpDao();

	public void createRequestForHelp(RequestForHelp requestForHelp, User author) {
		requestForHelpDao.createRequestForHelp(requestForHelp, author);
	}

	public List<RequestForHelp> receiveRequestThatAuthorCreated(User author) {
		int idUser = author.getId();
		return requestForHelpDao.receiveRequestsUserById(idUser);
	}

	public void hideCredentialsDataInRequest(List<RequestForHelp> requestsForHelp) {
		for (int i = 0; i < requestsForHelp.size(); i++) {
			User author = requestsForHelp.get(i).getAuthorUser();
			author.setPassword("0");
			author.setEmail("0");
			author.setPhone("0");
			requestsForHelp.get(i).setAuthorUser(author);
		}
	}

}
