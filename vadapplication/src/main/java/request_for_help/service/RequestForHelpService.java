package request_for_help.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import request_for_help.dao.RequestDaoException;
import request_for_help.dao.RequestForHelpDao;
import user.profile.User;

public class RequestForHelpService {

	private RequestForHelpDao requestForHelpDao = new RequestForHelpDao();

	public void createRequestForHelp(RequestForHelp requestForHelp, User author) {
		requestForHelpDao.createRequestForHelp(requestForHelp, author);
	}

	public RequestForHelp findRequestByherId(int idRequest) {
		return requestForHelpDao.findRequestByherId(idRequest);
	}

	public List<RequestForHelp> receiveRequestThatAuthorCreated(User author) throws RequestDaoException {
		int idUser = author.getId();
		return requestForHelpDao.findRequestByIdAuthor(idUser);
	}

	public void appendParticipant(int idNewParticipant, int idRequest) throws RequestDaoException {
		requestForHelpDao.appendParticipant(idNewParticipant, idRequest);
	}

	public void deleteParticipant(int idParticipant, int idRequest) throws RequestDaoException {
		requestForHelpDao.deleteParticipantFrorRequest(idParticipant, idRequest);
	}

	public List<RequestForHelp> receiveAllRequests() throws RequestDaoException {
		return requestForHelpDao.receiveListAllRequests();
	}

	public void hideCredentialsDataInRequest(List<RequestForHelp> listRequestsForHelp) {
		for (int i = 0; i < listRequestsForHelp.size(); i++) {
			RequestForHelp requestForHelp = listRequestsForHelp.get(i);
			hideCredentialsDataInAuthorRequest(requestForHelp);
			hideCredentialsDataInListParticipants(requestForHelp);
			listRequestsForHelp.set(i, requestForHelp);
		}
	}

	public void hideCredentialsDataInListParticipants(RequestForHelp requestForHelp) {
		hideCredentialsDataInAuthorRequest(requestForHelp);
		Set<User> participants = requestForHelp.getParticipants();
		List<User> listPart = new ArrayList<User>(participants);
		for (int i = 0; i < listPart.size(); i++) {
			User participant = listPart.get(i);
			participant.setPassword("0");
			participant.setEmail("0");
			participant.setPhone("0");
		}
		Set<User> resultSetPart = new HashSet<User>(listPart);
		requestForHelp.setParticipants(resultSetPart);
	}

	private void hideCredentialsDataInAuthorRequest(RequestForHelp requestForHelp) {
		User author = requestForHelp.getAuthorUser();
		author.setPassword("0");
		author.setEmail("0");
		author.setPhone("0");
		requestForHelp.setAuthorUser(author);
	}

}
