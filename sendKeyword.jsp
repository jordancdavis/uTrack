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
	String word = request.getParameter("item");

	if (utrackMain.addKeyword(word, con)) {
		session.setAttribute("kmssg", "true");

	} else {
		session.setAttribute("kmssg", "false");

	}
	response.sendRedirect("addKeywordToDB.jsp");
	con.closeConnection();
%>
