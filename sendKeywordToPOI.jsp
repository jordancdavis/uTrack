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

	if (name.length() < 1 || name.length() > 50) {
		session.setAttribute("errormssg", "Invalid POI Name");
	}

	poi.selectExistingPOI(name, con.stmt);
	String wid = request.getParameter("kid");
	
	if(poi.isNotReal){
		con.closeConnection();
		session.setAttribute("errormssg", "POI does not exist");
		response.sendRedirect("addKeywordToPOI.jsp");
		return;
	}

	if (poi.addKeywordToPOI(wid, con)) {
		session.setAttribute("kmssg", "true");

	} else {
		session.setAttribute("kmssg", "false");

	}
	response.sendRedirect("addKeywordToPOI.jsp");
	con.closeConnection();
%>
