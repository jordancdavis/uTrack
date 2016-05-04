<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack - Useful Feedback</title>
</head>
<h2>
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - Most Useful Feedback</FONT>
</h2>
<body>

	<%
		if (session.getAttribute("username") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
	%>


	<form action="mostUsefulFeedback.jsp">
	Enter the POI you wish to view: <br> 
	<input type="text" name="poiname" /> <br><br>
	How many Useful Feedbacks do you want to view from this POI? <br>
	
		<select name="item">
			<option value="5">5</option>
			<option value="10">10</option>
			<option value="15">15</option>
			<option value="20">20</option>
			<option value="25">25</option>
			<option value="50">50</option>
			<option value="100">100</option>	
			<option value="*">All</option>
		</select> <br><br>
		<input type="submit" value="View Feedback">
	</form>

	<br>
	<FONT COLOR=RED >
	<%if(session.getAttribute("kmssg") != null){
		out.print("* Invalid POI");
		session.setAttribute("kmssg", null);
	}
	%>
	</FONT>
	<br>
	<br>
	<a href="welcomeUser.jsp">Return to Main Menu</a>
</body>

</html>