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
	String fid = request.getParameter("fid");
	String rating = request.getParameter("item");
	String opinion = request.getParameter("opinion");
	ArrayList<String> flist = (ArrayList<String>) session.getAttribute("flist");
	
	if(!utrackMain.isValidStringOfNumbers(fid) || fid.length() == 0){
		session.setAttribute("kmssg","Invalid Feedback ID");
		response.sendRedirect("selectFeedback.jsp");
		return;
	}
	
	if(!flist.contains(fid)){
		if(!poi.rateFeedback(currentUser, fid, rating, opinion, con)){
			session.setAttribute("kmssg","Error: Failed To Rate Feedback. Try Again");
		}
	} else {
		session.setAttribute("kmssg","You Cannot Rate Your Own Feedback.");
	}
	
	if(session.getAttribute("kmssg") == null){
		session.setAttribute("kmssg","Feedback rated successfully");
	}
	response.sendRedirect("selectFeedback.jsp");
	con.closeConnection();
%>