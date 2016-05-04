<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack - Add Keyword to DB</title>
</head>
<h2>
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - Add Keyword to Database</FONT>
</h2>
<body>
	<%
		if (session.getAttribute("username") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
	%>
	<br> Enter the keyword you would like to add to the DB
	<br>
	<form method="post" action="sendKeyword.jsp">

		<input type="text" name="item" /> <br>
		<br> <input type="submit" value="Add Keyword">
	</form>
	<br>
	<FONT COLOR=RED>
		<%
			if (session.getAttribute("kmssg") == "true") {
				out.print("Keyword successfuly added to DB");
			} else if (session.getAttribute("kmssg") == "false") {
				out.print("Keyword already exists.");
			}
		session.setAttribute("kmssg", null);

		%>
	</FONT>
	<br>
	<br>
	<br>
	<a href="welcomeAdmin.jsp">Return to Admin Menu</a>
</body>
</html>