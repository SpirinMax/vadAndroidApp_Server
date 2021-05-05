package user.profile.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;

import request_for_help.service.RequestForHelp;
import security.EncodedPassword;

import org.hibernate.Session;
import org.hibernate.Transaction;

import user.profile.User;
import utils.hibernate.SessionFactoryDB;

public class UserDao implements ActionUserDao {
	private static final int LENGHT_PHONE_NUMBER = 12;

	@Override
	public User findById(int id) {
		Session session = SessionFactoryDB.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		User user = session.get(User.class, id);
		transaction.commit();
		session.close();
		return user;
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
		String phone = user.getPhone();
		if (phone.length() > LENGHT_PHONE_NUMBER) {
			phone = phone.substring(0, LENGHT_PHONE_NUMBER);
			user.setPhone(phone);
		}
		Session session = SessionFactoryDB.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.update(user);
		transaction.commit();
		session.close();
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
