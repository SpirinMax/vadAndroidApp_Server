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

import request_for_help.service.ParametersRequestForQuest;
import request_for_help.service.PhotoReport;
import request_for_help.service.RequestForHelp;
import user.profile.User;
import utils.hibernate.SessionFactoryDB;

public class RequestForHelpDao implements ActionRequsetForHelp {
	private static Logger log = LogManager.getLogger();

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
	
	public List<PhotoReport> receiveAllPhotoReports () throws RequestDaoException {
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
		List<RequestForHelp> listRequestUser = new ArrayList<RequestForHelp>();

		try {
			session = SessionFactoryDB.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();

			String queryString = receiveQueryStringForQuest(parametersQuest);
			Query query = session.createQuery(queryString);
			defineParameterQueryForQuest(parametersQuest, query);

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

	public List<RequestForHelp> findRequestByIdAuthor(int idUser) throws RequestDaoException {
		Session session = null;
		List<RequestForHelp> listRequestUser = new ArrayList<RequestForHelp>();
		try {
			session = SessionFactoryDB.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			String queryString = "from RequestForHelp where author_id=:id";
			Query query = session.createQuery(queryString);
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

	public List<RequestForHelp> receiveListAllRequests() throws RequestDaoException {
		Session session = null;
		List<RequestForHelp> listRequestUser = new ArrayList<RequestForHelp>();
		try {
			session = SessionFactoryDB.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			String queryString = "from RequestForHelp";
			Query query = session.createQuery(queryString);
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
