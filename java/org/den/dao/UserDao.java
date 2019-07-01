package org.den.dao;

import java.util.List;

import org.den.entity.User;

public interface UserDao {
	
	void addUser(User user);
	void deleteUser(int userid);
	void updateUser(User user);
	List<User> getAllUser();
	User getUserById(int userid);
	
	

}
