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
	String name = request.getParameter("poiname");
	String score = request.getParameter("item");
	String opinion = request.getParameter("opinion");

	if (name.length() < 1 || name.length() > 50) {
		session.setAttribute("kmssg", "Invalid POI Name");
	} else {
		poi.selectExistingPOI(name, con.stmt);

		if (poi.isNotReal) {
			session.setAttribute("kmssg", "POI does not exist");
		} else {
			User user = (User) session.getAttribute("user");
			session.setAttribute("kmssg", user.addFeedbackToPOI(name, poi._pid, Integer.parseInt(score), opinion, con));
		}
	}
	response.sendRedirect("recordFeedback.jsp");
	con.closeConnection();
%>