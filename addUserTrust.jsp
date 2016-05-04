<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack - User Trust</title>
</head>
<h2>
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - Trust/Dis-Trust Users</FONT>
</h2>
<body>
	<%
		if (session.getAttribute("username") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
	%>
	<br> Enter the name of Username you'd like to trust / distrust:
	<br>
	<form method="post" action="sendUserTrust.jsp">

		<input type="text" name="otherUsrName" /> <br>
		<br> <input type="radio" name="trust" value="1" id="yes" />
		Trust <br> <input type="radio" name="trust" value="0" id="no" />
		Dis-Trust <br>
		<br> <input type="submit" value="Submit">
	</form>
	<br>
	<FONT COLOR=RED>
		<%
			if (session.getAttribute("kmssg") == "true") {
				out.print(session.getAttribute("errormssg"));
				session.setAttribute("kmssg", "false");
			}
		%>
	</FONT>
	<br>
	<br>
	<br>
	<a href="welcomeUser.jsp">Return to Main Menu</a>
</body>
</html>