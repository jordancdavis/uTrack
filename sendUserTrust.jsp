<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<%
	if (session.getAttribute("username") == null) {
		response.sendRedirect("index.jsp");
		return;
	}
%>
<%
	Connector con = new Connector();
	POI poi = new POI();
	User currentUser = (User) session.getAttribute("user");
	String trusted = request.getParameter("otherUsrName");
	String type = request.getParameter("trust");
	int score = -1;
	User otherUser = new User();

	if (type == null) {
		score = -1;
		session.setAttribute("errormssg", "You must select trust / distrust");
		session.setAttribute("kmssg", "true");
	} else if (type.equals("1")) {
		score = 1;
	} else {
		score = 0;
	}

	if (trusted.length() < 1 || trusted.length() > 50) {
		session.setAttribute("errormssg", "Invalid username");
		session.setAttribute("kmssg", "true");
	} else {
		if (otherUser.getUser(trusted, con.stmt) && (score != -1)) {
			session.setAttribute("errormssg", currentUser.addUserRelationship(otherUser, score, con));
			session.setAttribute("kmssg", "true");
		} else {
			if (score != -1) {
				session.setAttribute("errormssg", "User does not exist. Please try again.");
				session.setAttribute("kmssg", "true");
			}
		}
	}
	response.sendRedirect("addUserTrust.jsp");
	con.closeConnection();
%>
