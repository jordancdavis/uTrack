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
	String type = request.getParameter("type");
	String xValue = request.getParameter("value");
	String statMessage = "";
	
	if(type.equals("1")){
		statMessage = "" + xValue + " Most Popular POI's For Each Category <br><br>";
		statMessage += poi.getXMostPopularPOIForEachCategory(xValue, con.stmt);
	} else if (type.equals("2")){
		statMessage = "" + xValue + " Most Expensive POI's For Each Category <br><br>";
		statMessage += poi.getXMostExpensivePOIForEachCategory(xValue, con.stmt);
	} else {
		statMessage = "" + xValue + " Hightest Rated POI's For Each Category <br><br>";
		statMessage += poi.getXHighestRatedPOIForEachCategory(xValue, con.stmt);
	}

	session.setAttribute("statMessage", statMessage);	
	
	response.sendRedirect("poiStats.jsp");
	con.closeConnection();
%>
