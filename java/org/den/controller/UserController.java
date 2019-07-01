package org.den.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.den.dao.UserDao;
import org.den.dao.DaoImpl.UserDaoImpl;
import org.den.entity.User;

/**
 * Servlet implementation class UserController
 */
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String INSERT_OR_EDIT = "/user.jsp";
	private static final String LIST_USER = "/listuser.jsp";
	
	private UserDao  dao;
	
    public UserController() {
    	dao = new UserDaoImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward = "";
		String action = request.getParameter("action");
		
		if(action.equalsIgnoreCase(request.getParameter("delete"))) {
			
			forward = LIST_USER;
			int userid = Integer.parseInt(request.getParameter("userid"));
			dao.deleteUser(userid);
			
			request.setAttribute("user", dao.getAllUser());
			
		} else if(action.equalsIgnoreCase(request.getParameter("edit"))) {
			
			forward = INSERT_OR_EDIT;
			int userid = Integer.parseInt(request.getParameter("userid"));
			User user = dao.getUserById(userid);
			
			request.setAttribute("user", user);
			
		} else if(action.equalsIgnoreCase(request.getParameter("listuser"))) {
			
			forward = LIST_USER;
			
			request.setAttribute("user", dao.getAllUser());
			
		} else {
			
			forward = INSERT_OR_EDIT;
			
		}
		
		request.getRequestDispatcher(forward).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		user.setFirstName(request.getParameter("firstname"));
		user.setLastName(request.getParameter("lastname"));
		try {
			Date date = (Date) new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("date"));
			user.setDate(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setEmail(request.getParameter("email"));
		String userid = request.getParameter("userid");
		if(userid ==null || userid.isEmpty()) {
			dao.addUser(user);
		} else {
			user.setUserID(Integer.parseInt(userid));
			dao.updateUser(user);
		}
		
		request.setAttribute("user", dao.getAllUser());
		request.getRequestDispatcher(LIST_USER).forward(request, response);
	}

}
