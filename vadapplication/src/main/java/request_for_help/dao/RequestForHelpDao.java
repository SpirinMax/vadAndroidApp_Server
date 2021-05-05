package request_for_help.dao;

import java.util.ArrayList;
import java.util.List;

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

	public List<RequestForHelp> receiveRequestsUserById(int idUser) {
		Session session = null;
		List<RequestForHelp> listRequestUser = new ArrayList<RequestForHelp>();
		try {
			session = SessionFactoryDB.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			User user = session.get(User.class, idUser);

			String queryString = "from RequestForHelp where author_id=:id";
			Query query = session.createQuery(queryString);
			query.setParameter("id", idUser);
			listRequestUser = query.list();
			transaction.commit();
		} catch (Exception e) {
			log.error("Error saved requestForHelp" + e);
		} finally {
			session.close();
		}
		return listRequestUser;
	}
}
