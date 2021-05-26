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

	public List<RequestForHelp> findRequestsByParameters(ParametersRequestForQuest parametersQuest)
			throws RequestDaoException {
		return requestForHelpDao.findRequestsByParameters(parametersQuest);
	}

	public List<RequestForHelp> receiveRequestThatAuthorCreated(int idUser) throws RequestDaoException {
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

	public List<PhotoReport> receiveAllPhotoReports() throws RequestDaoException {
		return requestForHelpDao.receiveAllPhotoReports();
	}

	public List<RequestForHelp> receiveRequestsByIdParticipant(int idParticipant) throws RequestDaoException {
		List<RequestForHelp> listAllRequests = requestForHelpDao.receiveListAllRequests();
		List<RequestForHelp> listWherePresentRequiredParticipant = new ArrayList<RequestForHelp>();
		for (int i = 0; i < listAllRequests.size(); i++) {
			RequestForHelp currentRequest = listAllRequests.get(i);
			Set<User> hashSetParticipantsRequest = currentRequest.getParticipants();
			if (hashSetParticipantsRequest.size() != 0) {
				List<User> listParticipantsRequest = new ArrayList<User>(hashSetParticipantsRequest);
				for (int j = 0; j < listParticipantsRequest.size(); j++) {
					if (idParticipant == listParticipantsRequest.get(j).getId()) {
						listWherePresentRequiredParticipant.add(currentRequest);
					}
				}
			}
		}
		return listWherePresentRequiredParticipant;
	}

	public void hideCredentialsData(RequestForHelp requestForHelp) {
		User author = requestForHelp.getAuthorUser();
		author.setPassword("0");
		author.setEmail("0");
		author.setPhone("0");
		requestForHelp.setAuthorUser(author);

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

	public void hideCredentialsData(List<RequestForHelp> listRequestsForHelp) {
		for (int i = 0; i < listRequestsForHelp.size(); i++) {
			RequestForHelp requestForHelp = listRequestsForHelp.get(i);
			hideCredentialsData(requestForHelp);
			listRequestsForHelp.set(i, requestForHelp);
		}
	}

//	public void hideCredentialsData(PhotoReport photoReport) {
//		User author = photoReport.getAuthorReport();
//		author.setPassword("0");
//		author.setEmail("0");
//		author.setPhone("0");
//		photoReport.setAuthorReport(author);
//	}
}
