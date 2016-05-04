<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack - POI Feedback</title>
</head>
<h2>
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - POI Feedback</FONT>
</h2>
<body>

	<%
		if (session.getAttribute("username") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
	%>


	<form action="sendPOIFeedback.jsp">
	Enter the POI you wish to give feedback: <br> 
	<input type="text" name="poiname" /> <br><br>
	Please provide a score: <br>
	
		<select name="item">
			<option value="0">0</option>
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
			<option value="7">7</option>	
			<option value="8">8</option>	
			<option value="9">9</option>					
			<option value="10">10</option>
		</select> <br><br>
		Share your thoughts about this POI: <br>
		<textarea maxlength='140' style="width:300px; height:50px;" name="opinion"></textarea>	<br>
		<br>
		<input type="submit" value="Submit Feedback">
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