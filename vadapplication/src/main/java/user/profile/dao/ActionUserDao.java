package user.profile.dao;

import user.profile.User;

public interface ActionUserDao {
	 User findById(int id) ;
	 void register (User user);
	 void update(User user);
	 void delete(int id);
	 
}
