package request_for_help.service;

import java.util.ArrayList;
import java.util.Calendar;
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
		RequestForHelp requestForHelp = requestForHelpDao.findRequestByherId(idRequest);
		incrementMonthInDatesRequest(requestForHelp);
		return requestForHelp;
	}

	public List<RequestForHelp> findRequestsByParameters(ParametersRequestForQuest parametersQuest)
			throws RequestDaoException {
		List<RequestForHelp> listRequestForHelp = requestForHelpDao.findRequestsByParameters(parametersQuest);
		incrementMonthInDatesRequest(listRequestForHelp);
		return listRequestForHelp;
	}

	public List<RequestForHelp> receiveRequestThatAuthorCreated(int idUser) throws RequestDaoException {
		List<RequestForHelp> listRequestThatAuthorCreated = requestForHelpDao.findRequestByIdAuthor(idUser);
		incrementMonthInDatesRequest(listRequestThatAuthorCreated);
		return listRequestThatAuthorCreated;
	}

	public void appendPhotoReport(RequestForHelp requestForHelp) throws RequestDaoException {
		requestForHelpDao.appendPhotoReport(requestForHelp);
	}

	public void appendParticipant(int idNewParticipant, int idRequest) throws RequestDaoException {
		requestForHelpDao.appendParticipant(idNewParticipant, idRequest);
	}

	public void deleteParticipant(int idParticipant, int idRequest) throws RequestDaoException {
		requestForHelpDao.deleteParticipantFrorRequest(idParticipant, idRequest);
	}

	public List<RequestForHelp> receiveAllRequests() throws RequestDaoException {
		List<RequestForHelp> listRequestForHelp = requestForHelpDao.receiveRequestsWherePhotoReportsEmpty();
		incrementMonthInDatesRequest(listRequestForHelp);
		return listRequestForHelp;
	}

	public List<PhotoReport> receiveAllPhotoReports() throws RequestDaoException {
		Set<PhotoReport> listPhotoReports = new HashSet<PhotoReport>(requestForHelpDao.receiveAllPhotoReports());
		incrementMonthInDatesRequest(listPhotoReports);
		return new ArrayList<PhotoReport>(listPhotoReports);
	}

	public List<RequestForHelp> receiveRequestsByIdParticipant(int idParticipant) throws RequestDaoException {
		List<RequestForHelp> listWherePresentRequiredParticipant = requestForHelpDao
				.findRequestByIdParticipant(idParticipant);
		incrementMonthInDatesRequest(listWherePresentRequiredParticipant);
		return listWherePresentRequiredParticipant;
	}

	private void incrementMonthInDatesRequest(List<RequestForHelp> listRequestForHelp) {
		for (int i = 0; i < listRequestForHelp.size(); i++) {
			incrementMonthInDatesRequest(listRequestForHelp.get(i));
		}
	}

	private void incrementMonthInDatesRequest(RequestForHelp requestForHelp) {
		Calendar correctCreationDate = Calendar.getInstance();
		Calendar correctStartDate = Calendar.getInstance();

		Calendar incorrectCreationDate = requestForHelp.getCreationDate();
		Calendar incorrectStartDate = requestForHelp.getStartDate();

		int correctMonthCreationDate = incorrectCreationDate.get(Calendar.MONTH) + 1;
		int correctMonthStartDate = incorrectStartDate.get(Calendar.MONTH) + 1;

		correctCreationDate.set(incorrectCreationDate.get(Calendar.YEAR), correctMonthCreationDate,
				incorrectCreationDate.get(Calendar.DAY_OF_MONTH), incorrectCreationDate.get(Calendar.HOUR_OF_DAY),
				incorrectCreationDate.get(Calendar.MINUTE));

		correctStartDate.set(incorrectStartDate.get(Calendar.YEAR), correctMonthStartDate,
				incorrectStartDate.get(Calendar.DAY_OF_MONTH), incorrectStartDate.get(Calendar.HOUR_OF_DAY),
				incorrectStartDate.get(Calendar.MINUTE));

		requestForHelp.setCreationDate(correctCreationDate);
		requestForHelp.setStartDate(correctStartDate);

		Set<PhotoReport> photoReports = requestForHelp.getPhotoReports();
		incrementMonthInDatesRequest(photoReports);
		requestForHelp.setPhotoReports(new HashSet<PhotoReport>(photoReports));

	}

	private void incrementMonthInDatesRequest(Set<PhotoReport> photoReports) {
		List<PhotoReport> listPhotoReports = new ArrayList<PhotoReport>(photoReports);
		if (listPhotoReports.size() > 0) {
			for (int j = 0; j < listPhotoReports.size(); j++) {
				Calendar correctCreationDateInPhotoReport = Calendar.getInstance();
				Calendar incorrectCreationDateInPhotoReport = listPhotoReports.get(j).getCreationDate();
				int correctMonthCreationDateInPhotoReport = incorrectCreationDateInPhotoReport.get(Calendar.MONTH) + 1;

				correctCreationDateInPhotoReport.set(incorrectCreationDateInPhotoReport.get(Calendar.YEAR),
						correctMonthCreationDateInPhotoReport,
						incorrectCreationDateInPhotoReport.get(Calendar.DAY_OF_MONTH),
						incorrectCreationDateInPhotoReport.get(Calendar.HOUR_OF_DAY),
						incorrectCreationDateInPhotoReport.get(Calendar.MINUTE));

				listPhotoReports.get(j).setCreationDate(correctCreationDateInPhotoReport);
			}
		}
	}

//	public void hideCredentialsData(RequestForHelp requestForHelp) {
//		User author = requestForHelp.getAuthorUser();
//		author.setPassword("0");
//		author.setEmail("0");
//		author.setPhone("0");
//		requestForHelp.setAuthorUser(author);
//
//		Set<User> participants = requestForHelp.getParticipants();
//		List<User> listPart = new ArrayList<User>(participants);
//		for (int i = 0; i < listPart.size(); i++) {
//			User participant = listPart.get(i);
//			participant.setPassword("0");
//			participant.setEmail("0");
//			participant.setPhone("0");
//		}
//		Set<User> resultSetPart = new HashSet<User>(listPart);
//		requestForHelp.setParticipants(resultSetPart);
//	}
//
//	public void hideCredentialsData(List<RequestForHelp> listRequestsForHelp) {
//		for (int i = 0; i < listRequestsForHelp.size(); i++) {
//			RequestForHelp requestForHelp = listRequestsForHelp.get(i);
//			hideCredentialsData(requestForHelp);
//			listRequestsForHelp.set(i, requestForHelp);
//		}
//	}

//	public void hideCredentialsData(PhotoReport photoReport) {
//		User author = photoReport.getAuthorReport();
//		author.setPassword("0");
//		author.setEmail("0");
//		author.setPhone("0");
//		photoReport.setAuthorReport(author);
//	}
}
