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
		session.setAttribute("kmssg", "true");
	} else {
		poi.selectExistingPOI(name, con.stmt);

		if (poi.isNotReal) {
			session.setAttribute("errormssg", "POI Name does not exist");
			session.setAttribute("kmssg", "true");
		} else {
			User user = (User) session.getAttribute("user");
			String rsp = user.addFavoritePOI(poi._pname, poi._pid, con);
			session.setAttribute("errormssg", rsp);
			session.setAttribute("kmssg", "true");
		}
	}
	response.sendRedirect("addFavPOI.jsp");
	con.closeConnection();
%>
