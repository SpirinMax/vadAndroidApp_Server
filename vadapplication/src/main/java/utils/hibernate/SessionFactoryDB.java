package utils.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import user.profile.User;

public class SessionFactoryDB {
	private static SessionFactory sessionFactory;

	private SessionFactoryDB() {
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration().configure();
				configuration.addAnnotatedClass(User.class);
				StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties());
				sessionFactory = configuration.buildSessionFactory(builder.build());
				return sessionFactory;
			} catch (Exception e) {
				System.out.println("Исключение!" + e);
			}
		}
		return sessionFactory;
	}
}
