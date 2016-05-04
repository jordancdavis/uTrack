<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack - Most Useful Users</title>
</head>
<h2>
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - Most Useful Users</FONT>
</h2>
<body>

	<%
		if (session.getAttribute("username") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
	%>


	How many useful users do you want to view?

	<form action="mostUsefulUsers.jsp">
		<select name="item">
			<option value="5">5</option>
			<option value="10">10</option>
			<option value="15">15</option>
			<option value="20">20</option>
			<option value="25">25</option>
			<option value="50">50</option>
			<option value="100">100</option>			
			<option value="*">All</option>
		</select> <input type="submit" value="View Most Useful Users">
	</form>

	<br>
	<br>
	<br>
	<a href="welcomeAdmin.jsp">Return to Admin Menu</a>
</body>
</html>