package user.profile;

import security.EncodedPassword;
import user.profile.dao.UserDao;

public class UserService {

	private UserDao userDao = new UserDao();

	public void register(User user) {
		userDao.register(user);
	}

	public User authentication(String email, String password) throws Exception {
		if (password.length() == EncodedPassword.LEN) {
			return userDao.authentication(email, password);
		} else {
			return userDao.authenticationAfterHash(email, password);
		}
	}

}
