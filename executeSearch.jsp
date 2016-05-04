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
	
	String min = request.getParameter("min");
	String max = request.getParameter("max");
	String city = request.getParameter("city");
	String state = request.getParameter("state");
	String keywords = request.getParameter("keywords");
	String category = request.getParameter("category");
	String sort = request.getParameter("sort");
	
	session.setAttribute("_bmin", min);
	session.setAttribute("_bmax", max);
	session.setAttribute("_bcity", city);
	session.setAttribute("_bstate", state);
	session.setAttribute("_bkeywords", keywords);
	session.setAttribute("_bcategory", category);
	session.setAttribute("_bsort", sort);

	
	int mini = -1;
	int maxum = -1;
	String order = "";
	String browseMessage = "";
	
	if(!utrackMain.isValidStringOfNumbers(min)){
		session.setAttribute("browseMessage", "Invalid min. must be a whole number");
		response.sendRedirect("browsePOI.jsp");
		return;
	} else if (!min.equals("")) {
		mini = Integer.parseInt(min);
	} 
	
	if(!utrackMain.isValidStringOfNumbers(max)){
		session.setAttribute("browseMessage", "Invalid max. must be a whole number");
		response.sendRedirect("browsePOI.jsp");
		return;
	} else if (!max.equals("")){
		maxum = Integer.parseInt(max);
	} 
	
	
	switch (Integer.parseInt(sort)) {
		case 1: {
			order = " ORDER BY price ASC ";
			break;
		}
		case 2: {
			order = " ORDER BY price DESC ";
			break;
		}
		case 3: {
			order = " ORDER BY fscore ASC ";
			break;
		}
		case 4: {
			order = " ORDER BY fscore DESC ";
			break;
		}
		case 5: {
			order = " ORDER BY fscore DESC ";
			break;
		}
		case 6: {
			order = " ORDER BY fscore DESC ";
			break;
		}
		case 7: {
			order = " ";
			break;
		}
		default: {
			order = " ";
		}
			break;
		}
	
		
	browseMessage = poi.executeSearch(currentUser, mini, maxum, city, state, keywords, category, order, Integer.parseInt(sort), con.stmt);


	session.setAttribute("browseMessage", browseMessage);	
	
	response.sendRedirect("browsePOI.jsp");
	con.closeConnection();
%>
