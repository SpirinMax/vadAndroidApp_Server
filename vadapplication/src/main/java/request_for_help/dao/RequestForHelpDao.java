package request_for_help.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.type.IntegerType;

import request_for_help.service.ImagePhotoReport;
import request_for_help.service.ParametersRequestForQuest;
import request_for_help.service.PhotoReport;
import request_for_help.service.RequestForHelp;
import user.profile.User;
import utils.hibernate.SessionFactoryDB;

public class RequestForHelpDao implements ActionRequsetForHelp {
	private static Logger log = LogManager.getLogger();
	private static final String NAME_DB = "vaddb";
	private static final String TABLE_PHOTO_REPORTS = "photo_reports";
	private static final String STRING_QUERY_RECIVING_REQUEST_WITHOUT_PHOTO_REPORTS = " SELECT * FROM requests WHERE requests.id NOT IN "
			+ " (SELECT DISTINCT r.id FROM requests AS r , photo_reports_in_request AS pr, photo_reports WHERE (r.id = pr.id_request))";

	public void createRequestForHelp(RequestForHelp requestForHelp, User author) {
		Session session = null;
		try {
			session = SessionFactoryDB.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			log.info("saving a request under a number" + String.valueOf(requestForHelp.getId()));

			requestForHelp.setAuthorUser(author);
			session.save(requestForHelp);
			transaction.commit();

		} catch (Exception e) {
			log.error("Error saved requestForHelp" + e);
		} finally {
			session.close();
		}

	}

	public List<PhotoReport> receiveAllPhotoReports() throws RequestDaoException {
		Session session = null;
		List<PhotoReport> listPhotoReports = new ArrayList<PhotoReport>();
		try {
			session = SessionFactoryDB.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			String queryString = "from PhotoReport";
			Query query = session.createQuery(queryString);
			listPhotoReports = query.list();
			transaction.commit();
		} catch (Exception e) {
			log.error("Error when selecting request" + e);
			throw new RequestDaoException("Error", 400);
		} finally {
			session.close();
		}
		return listPhotoReports;
	}

	public RequestForHelp findRequestByherId(int idRequest) {
		Session session = null;
		RequestForHelp foundRequestForHelp = new RequestForHelp();
		try {
			session = SessionFactoryDB.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			foundRequestForHelp = session.get(RequestForHelp.class, idRequest);
			transaction.commit();
		} catch (Exception e) {
			log.error("Error when searching for the request" + e);
		} finally {
			session.close();
		}
		return foundRequestForHelp;
	}

	public List<RequestForHelp> findRequestsByParameters(ParametersRequestForQuest parametersQuest)
			throws RequestDaoException {
		Session session = null;
		List<RequestForHelp> resultListRequest = new ArrayList<RequestForHelp>();
		try {
			session = SessionFactoryDB.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();

			String queryString = receiveQueryStringForQuest(parametersQuest);
			Query query = session.createQuery(queryString);
			defineParameterQueryForQuest(parametersQuest, query);
			List<RequestForHelp> listRequestsByParameters = query.list();

			String queryStringEmptyPhotoReport = " SELECT requests.id as id_r FROM requests WHERE requests.id NOT IN "
					+ " (SELECT DISTINCT r.id FROM requests AS r , photo_reports_in_request AS pr, photo_reports WHERE (r.id = pr.id_request))";
			Query queryRequestWithoutPhotoReport = session.createSQLQuery(queryStringEmptyPhotoReport).addScalar("id_r",
					IntegerType.INSTANCE);
			List<Integer> idRequestsWithoutPhotoReport = queryRequestWithoutPhotoReport.list();
			if (idRequestsWithoutPhotoReport.size() != 0) {
				for (int i = 0; i < listRequestsByParameters.size(); i++) {
					RequestForHelp currentRequest = listRequestsByParameters.get(i);
					for (int j = 0; j < idRequestsWithoutPhotoReport.size(); j++) {
						if (currentRequest.getId() == (int) idRequestsWithoutPhotoReport.get(j)) {
							resultListRequest.add(currentRequest);
						}
					}
				}
			}
			transaction.commit();
		} catch (Exception e) {
			log.error("Error when selecting request" + e);
			throw new RequestDaoException("Error", 400);
		} finally {
			session.close();
		}
		return resultListRequest;
	}

	public List<RequestForHelp> findRequestByIdAuthor(int idUser) throws RequestDaoException {
		Session session = null;
		List<RequestForHelp> listRequestUser = new ArrayList<RequestForHelp>();
		try {
			session = SessionFactoryDB.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			String queryString = STRING_QUERY_RECIVING_REQUEST_WITHOUT_PHOTO_REPORTS + " AND author_id=:id";
			Query query = session.createSQLQuery(queryString).addEntity(RequestForHelp.class);
			query.setParameter("id", idUser);
			listRequestUser = query.list();
			transaction.commit();
		} catch (Exception e) {
			log.error("Error when searching for the author request" + e);
			throw new RequestDaoException("invlid idUser", 400);
		} finally {
			session.close();
		}
		return listRequestUser;
	}

	public List<RequestForHelp> findRequestByIdParticipant(int idParticipant) throws RequestDaoException {
		Session session = null;
		List<RequestForHelp> resultListRequest = new ArrayList<RequestForHelp>();
		try {
			session = SessionFactoryDB.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();

			String queryString = " SELECT requests.* FROM requests, participants as part WHERE requests.id NOT IN"
					+ " (SELECT DISTINCT r.id FROM requests AS r , photo_reports_in_request AS pr, participants as part WHERE (r.id = pr.id_request)) "
					+ " and part.id_user = :idParticipant and requests.id = part.id_request ";

			Query query = session.createSQLQuery(queryString).addEntity(RequestForHelp.class);
			query.setParameter("idParticipant", idParticipant);
			resultListRequest = query.list();

			transaction.commit();
		} catch (Exception e) {
			log.error("Error when selecting request" + e);
			throw new RequestDaoException("Error", 400);
		} finally {
			session.close();
		}
		return resultListRequest;
	}

	public void appendParticipant(int idNewParticipant, int idRequest) throws RequestDaoException {
		Session session = null;
		RequestForHelp foundRequestForHelp = new RequestForHelp();
		Set<User> participantsRequest = new HashSet<User>();
		User foundParticipant = new User();
		try {
			session = SessionFactoryDB.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			foundRequestForHelp = session.get(RequestForHelp.class, idRequest);
			foundParticipant = session.get(User.class, idNewParticipant);

			if (foundRequestForHelp == null || foundParticipant == null) {
				throw new RequestDaoException("ID participant or request invalid (not found)", 400);
			} else {
				int idAuthorRequest = foundRequestForHelp.getAuthorUser().getId();
				if (idNewParticipant != idAuthorRequest) {
					participantsRequest = foundRequestForHelp.getParticipants();
					participantsRequest.add(foundParticipant);
					RequestForHelp requestWithNewParticipant = foundRequestForHelp;
					requestWithNewParticipant.setParticipants(participantsRequest);
					session.update(requestWithNewParticipant);
					transaction.commit();
				} else if (idNewParticipant == idAuthorRequest) {
					throw new RequestDaoException("The author request cannot be a participant", 400);
				}
			}

		} catch (RequestDaoException daoException) {
			log.error("Error when updating list participants request:_  " + daoException.getMessage());
			throw new RequestDaoException(daoException.getMessage(), daoException.getErrorType());
		} finally {
			session.close();
		}
	}

	public void appendPhotoReport(RequestForHelp requestForHelp) throws RequestDaoException {
		// приходит заявка с одним фотоотчетом, который является новым
		// надо извлечь этот фотооотчет, найти сохраненную в БД заявку и добавить к ней
		// этот новый фотоотчет
		// для этого надо сохранить новую заявку со списком фото
		Session session = null;
		List<PhotoReport> listPhotoReportsInAcceptingRequest = new ArrayList<PhotoReport>(
				requestForHelp.getPhotoReports());
		int idRequest = requestForHelp.getId();
		try {
			session = SessionFactoryDB.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			RequestForHelp foundRequestForHelp = session.get(RequestForHelp.class, idRequest);
			int countPhotoReportsInFoundedRequest = foundRequestForHelp.getPhotoReports().size();
			int countPhotoReportsInAcceptingRequest = listPhotoReportsInAcceptingRequest.size();

			if (foundRequestForHelp != null) {
				if (countPhotoReportsInFoundedRequest == 0 && countPhotoReportsInAcceptingRequest == 1) {
					PhotoReport newPhotoReport = listPhotoReportsInAcceptingRequest.get(0);
					session.save(newPhotoReport);

					savePhotosInPhotoReport(session, newPhotoReport);
					listPhotoReportsInAcceptingRequest.set(0, newPhotoReport);

					foundRequestForHelp.setPhotoReports(new HashSet<PhotoReport>(listPhotoReportsInAcceptingRequest));
					session.update(foundRequestForHelp);
					transaction.commit();
				} else if (countPhotoReportsInFoundedRequest > 0) {
					PhotoReport newPhotoReport = listPhotoReportsInAcceptingRequest.get(0);
					session.save(newPhotoReport);

					savePhotosInPhotoReport(session, newPhotoReport);
					listPhotoReportsInAcceptingRequest.set(0, newPhotoReport);

					Set<PhotoReport> newHashSetPhotoReports = new HashSet<PhotoReport>(
							foundRequestForHelp.getPhotoReports());
					newHashSetPhotoReports.add(newPhotoReport);
					foundRequestForHelp.setPhotoReports(newHashSetPhotoReports);
					session.update(foundRequestForHelp);
					transaction.commit();
				}
			} else {
				throw new RequestDaoException("ID request invalid (not found)", 400);
			}

		} catch (

		RequestDaoException daoException) {
			log.error("Error when update requestForHelp" + daoException);
			throw new RequestDaoException(daoException.getMessage(), daoException.getErrorType());
		} finally {
			session.close();
		}
	}

	public void deleteParticipantFrorRequest(int idParticipant, int idRequest) throws RequestDaoException {
		Session session = null;
		RequestForHelp foundRequestForHelp = new RequestForHelp();
		Set<User> participantsRequest = new HashSet<User>();
		User foundParticipant = new User();
		try {
			session = SessionFactoryDB.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			foundRequestForHelp = session.get(RequestForHelp.class, idRequest);
			foundParticipant = session.get(User.class, idParticipant);

			if (foundRequestForHelp == null || foundParticipant == null) {
				throw new RequestDaoException("ID participant or request invalid (not found)", 400);
			} else {
				int idAuthorRequest = foundRequestForHelp.getAuthorUser().getId();
				if (idParticipant != idAuthorRequest) {
					participantsRequest = foundRequestForHelp.getParticipants();

					participantsRequest.remove(foundParticipant);
					RequestForHelp requestWithNewParticipant = foundRequestForHelp;
					requestWithNewParticipant.setParticipants(participantsRequest);
					session.update(requestWithNewParticipant);
					transaction.commit();
				} else if (idParticipant == idAuthorRequest) {
					throw new RequestDaoException("The author request cannot be a participant", 400);
				}
			}

		} catch (RequestDaoException daoException) {
			log.error("Error when updating list participants request:_  " + daoException.getMessage());
			throw new RequestDaoException(daoException.getMessage(), daoException.getErrorType());
		} finally {
			session.close();
		}
	}

	public List<RequestForHelp> receiveRequestsWherePhotoReportsEmpty() throws RequestDaoException {
		Session session = null;
		List<RequestForHelp> listRequestUser = new ArrayList<RequestForHelp>();
		try {
			session = SessionFactoryDB.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			String queryString = STRING_QUERY_RECIVING_REQUEST_WITHOUT_PHOTO_REPORTS;
			Query query = session.createSQLQuery(queryString).addEntity(RequestForHelp.class);
			listRequestUser = query.list();
			transaction.commit();
		} catch (Exception e) {
			log.error("Error when selecting request" + e);
			throw new RequestDaoException("Error", 400);
		} finally {
			session.close();
		}
		return listRequestUser;
	}

	private void savePhotosInPhotoReport(Session session, PhotoReport photoReport) throws RequestDaoException {
		List<ImagePhotoReport> listImagesInPhotoReport = new ArrayList<ImagePhotoReport>(photoReport.getImages());
		if (listImagesInPhotoReport.size() != 0) {
			int idAuthorPhotoReport = photoReport.getAuthorReport().getId();
			int idPhotoReport = (int) session
					.createSQLQuery("SELECT MAX(id) as max_id FROM " + NAME_DB + "." + TABLE_PHOTO_REPORTS
							+ " WHERE author_id =:idAuthorPhotoReport")
					.setParameter("idAuthorPhotoReport", idAuthorPhotoReport).addScalar("max_id", IntegerType.INSTANCE)
					.list().get(0);
			for (byte i = 0; i < listImagesInPhotoReport.size(); i++) {
				ImagePhotoReport currentImage = listImagesInPhotoReport.get(i);
				currentImage.setIdPhotoReport(idPhotoReport);
				listImagesInPhotoReport.set(i, currentImage);
				session.save(currentImage);
			}
		} else {
			throw new RequestDaoException("List photos in photo report is zero", 400);
		}

		photoReport.setImages(new HashSet<ImagePhotoReport>(listImagesInPhotoReport));
	}

	private String receiveDateStringFromQuest(Calendar startDate) {
		String startDateString = "";
		if (startDate != null) {
			int year = startDate.get(Calendar.YEAR);
			int month = startDate.get(Calendar.MONTH);
			int day = startDate.get(Calendar.DAY_OF_MONTH);
			String yearInStartDate = String.valueOf(year);
			String monthInStartDate = String.valueOf(month);
			String dayInStartDate = String.valueOf(day);

			if (month < 10) {
				monthInStartDate = "0" + monthInStartDate;
			}

			if (day < 10) {
				dayInStartDate = "0" + dayInStartDate;
			}

			startDateString = dayInStartDate + "." + monthInStartDate + "." + yearInStartDate;
		}

		return startDateString;
	}

	private void defineParameterQueryForQuest(ParametersRequestForQuest parametersQuest, Query query) {
		String typeRequest = parametersQuest.getTypeRequest();
		String region = parametersQuest.getRegion();
		String district = parametersQuest.getDistrict();
		String city = parametersQuest.getCity();
		String startDate = receiveDateStringFromQuest(parametersQuest.getStartDate());

		if (!typeRequest.equals("")) {
			query.setParameter("typeRequest", typeRequest);
		}

		if (!region.equals("")) {
			query.setParameter("region", region);
		}

		if (!district.equals("")) {
			query.setParameter("district", district);
		}

		if (!city.equals("")) {
			query.setParameter("city", city);
		}

		if (!startDate.equals("")) {
			query.setParameter("startDate", startDate);
		}
	}

	private String receiveQueryStringForQuest(ParametersRequestForQuest parametersQuest) {
		String typeRequest = parametersQuest.getTypeRequest();
		String region = parametersQuest.getRegion();
		String district = parametersQuest.getDistrict();
		String city = parametersQuest.getCity();
		String startDate = receiveDateStringFromQuest(parametersQuest.getStartDate());

		String typeRequestString = "";
		String regionString = "";
		String districtString = "";
		String cityString = "";
		String startDateString = "";

		if (!typeRequest.equals("")) {
			typeRequestString = " type=:typeRequest and";
		}

		if (!region.equals("")) {
			regionString = " region=:region and";
		}

		if (!district.equals("")) {
			districtString = " district=:district and";
		}

		if (!city.equals("")) {
			cityString = " city=:city and";
		}

		if (!startDate.equals("")) {
			startDateString = " date_format(start_date, '%d.%m.%Y')=:startDate and";
		}

		String queryString = "from RequestForHelp where" + typeRequestString + regionString + districtString
				+ cityString + startDateString;
		// последние три буквы удаляются, которые "and"
		queryString = queryString.substring(0, queryString.length() - 3);
		return queryString;
	}

}
