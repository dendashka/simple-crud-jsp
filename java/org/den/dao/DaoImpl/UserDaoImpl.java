package org.den.dao.DaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.den.dao.UserDao;
import org.den.entity.User;
import org.den.util.ConnectionPool;

public class UserDaoImpl implements UserDao{
	
	private static Connection con = null;
	
	private static String addUser = "insert into user(firstname,lastname,dob,email) values (?, ?, ?, ? )";
	private static String deleteUser = "delete from user where userid=?";
	private static String updateUser = "update user set firstname=?, lastname=?, dob=?, email=? where userid=?";
	private static String getAllUser = "select * from user";
	private static String getUserById = "select * from user where userid = ?";
	

	public UserDaoImpl() {
		con = ConnectionPool.getInstance().getConnection();
	}

	public void addUser(User user) {
		try {
			PreparedStatement prst = con.prepareStatement(addUser);
			prst.setString(1, user.getFirstName());
			prst.setString(2, user.getLastName());
			prst.setDate(3, new java.sql.Date(user.getDate().getTime()));
			prst.setString(4, user.getEmail());
			prst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void deleteUser(int userid) {
		try {
			PreparedStatement prst = con.prepareStatement(deleteUser);
			prst.setInt(1, userid);
			prst.executeUpdate();
		} 
			
		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

	public void updateUser(User user) {
		try {
			PreparedStatement prst = con.prepareStatement(updateUser);
			prst.setString(1, user.getFirstName());
			prst.setString(2, user.getLastName());
			prst.setDate(3, user.getDate());
			prst.setString(4, user.getEmail());
			prst.setInt(5, user.getUserID());
			prst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public List<User> getAllUser() {
		List<User> userList = new ArrayList<User>();
		try {
			PreparedStatement prst = con.prepareStatement(getAllUser);
			ResultSet set = prst.executeQuery();
			while(set.next()) {
				User user = new User();
				user.setUserID(set.getInt("userid"));
				user.setFirstName(set.getString("firstname"));
				user.setLastName(set.getString("lastname"));
				user.setDate(set.getDate("date"));
				user.setEmail(set.getString("email"));
								
				userList.add(user);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userList;
	}

	public User getUserById(int userid) {
		User user = new User();
		try {
			PreparedStatement prst = con.prepareStatement(getUserById);
			prst.setInt(1, userid);
			ResultSet set = prst.executeQuery();
			if(set.next()) {
				user.setUserID(set.getInt("userid"));
				user.setFirstName(set.getString("firstname"));
				user.setLastName(set.getString("lastname"));
				user.setDate(set.getDate("date"));
				user.setEmail(set.getString("email"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	
}
