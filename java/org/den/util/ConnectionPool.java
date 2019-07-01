package org.den.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {
	
private static ConnectionPool entity = null;
	
	private ConnectionPool () {
		
	}
	
	public static ConnectionPool getInstance () {
		if (entity==null) {
			entity = new ConnectionPool();
		}
		return entity;
	}
	
	public Connection getConnection() {
		Context context;
		Connection con = null; 
		try {
			context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/querypool");
			con = ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
		
	}
	
}
