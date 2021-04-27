package user.profile.dao;

import java.util.List;

import org.hibernate.query.Query;

import security.EncodedPassword;

import org.hibernate.Session;
import org.hibernate.Transaction;

import user.profile.User;
import utils.hibernate.SessionFactoryDB;

public class UserDao implements ActionUserDao {

	@Override
	public User findById(int id) {
		Session session = SessionFactoryDB.getSessionFactory().getCurrentSession();
		return session.get(User.class, id);
	}

	@Override
	public void register(User user) {
		Session session = SessionFactoryDB.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.save(user);
		transaction.commit();
		session.close();
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub

	}

	public User authentication(String email, String password) throws Exception {
		Session session = SessionFactoryDB.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		String queryString = "FROM User WHERE email = :email AND password = :password";
		Query queryAuth = session.createQuery(queryString);
		queryAuth.setParameter("email", email);
		queryAuth.setParameter("password", password);
		List<User> userList = queryAuth.list();

		if (userList.size() == 0)
			throw new Exception("401");
		User userAuthSuccess = userList.get(0);
		transaction.commit();
		session.close();
		return userAuthSuccess;
	}

	public User authenticationAfterHash(String email, String password) throws Exception {
		String hash = EncodedPassword.sendHash(password);
		return authentication(email, hash);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
	}

}
