package utils.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import request_for_help.service.ImagePhotoReport;
import request_for_help.service.PhotoReport;
import request_for_help.service.RequestForHelp;
import request_for_help.service.RequestForHelpService;
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
				configuration.addAnnotatedClass(RequestForHelp.class);
				configuration.addAnnotatedClass(PhotoReport.class);
				configuration.addAnnotatedClass(ImagePhotoReport.class);
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
