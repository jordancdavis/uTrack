<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack - Feedback</title>
</head>
<h2>
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - View & Rate Feedback</FONT>
</h2>
<body>

	<%
		if (session.getAttribute("username") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		session.setAttribute("feedPOI", null);
	%>


	<form action="selectFeedback.jsp">
	Enter the POI name to view & rate feedback: <br> 
	<input type="text" name="poiname" /> <br>
	<br>
	<input type="submit" value="Select POI">
	</form>

	<br>
	<FONT COLOR=RED >
	<%if(session.getAttribute("kmssg") != null){
		out.print("* " + session.getAttribute("kmssg") + " *");
		session.setAttribute("kmssg", null);
	}
	%>
	</FONT>
	<br>
	<br>
	<a href="welcomeUser.jsp">Return to Main Menu</a>
</body>

</html>