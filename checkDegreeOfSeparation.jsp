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
	String othername = request.getParameter("othername");
	session.setAttribute("emessage", null);
	User currentUser = (User) session.getAttribute("user");
	User otherUser = new User();
	
	
	try {
		if(!otherUser.getUser(othername, con.stmt)){
			session.setAttribute("emessage", "User does not exist. Please try again.");
			return;
		}
		
		if (currentUser.checkFirstDegreeOfSeparation(otherUser, con.stmt)) {
			session.setAttribute("emessage","You and " + otherUser._username + " are 1-degree away");
		} else if (currentUser.checkSecondDegreeOfSeparation(otherUser, con.stmt)) {
			session.setAttribute("emessage","You and " + otherUser._username + " are 2-degrees away\n");
		} else {
			session.setAttribute("emessage","You and " + otherUser._username + " are completely separated\n");
		}
	
	} catch(Exception e) {
		session.setAttribute("emessage","Error: could not perform request. Please try again.");
	} finally {
		con.closeConnection();
		response.sendRedirect("getDegreeOfSeparation.jsp");
	}
		
%>