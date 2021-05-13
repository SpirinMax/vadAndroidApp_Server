package request_for_help.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
}
