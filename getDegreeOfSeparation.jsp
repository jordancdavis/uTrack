<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack - Degrees of Separation</title>
</head>
<h2>
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - Degrees of Separation</FONT>
</h2>

<body>

	<%
		if (session.getAttribute("username") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
	%>
<br>
	<form method="post" action="checkDegreeOfSeparation.jsp">
		Enter the Username for the user you wish to compare with:
<br> <input type="text" name="othername" /> 
<br>
<br> <input type="submit" value="Submit">
	</form>
	<br> <br> Results: <br>
	<FONT COLOR=BLUE>
		<%
		
			if (session.getAttribute("emessage") != null) {
				out.print(session.getAttribute("emessage"));
				session.setAttribute("emessage", null);
			}
		%>
	</FONT>
	<br>
	<br>
	<br>
	<a href="welcomeUser.jsp">Return to Main Menu</a>
</body>
</html>