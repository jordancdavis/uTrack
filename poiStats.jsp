<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack - POI stats</title>
</head>
<h2>
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - POI Stats</FONT>
</h2>

<body>

	<%
		if (session.getAttribute("username") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
	%>



<div style="position: relative; width: 750px;">
		<div
			style="position: absolute; top: 0; right: 0; width: 350px; text-align: left;">

				<a href="welcomeUser.jsp">Return to Main Menu</a>
				<br><br>
				<br><br>
				<br><br>
			<% 
				if(session.getAttribute("statMessage") != null){
					out.println(session.getAttribute("statMessage"));
					%><br><br><a href="welcomeUser.jsp">Return to Main Menu</a>
					<% 
				}

			%>
			
	</div>

	<form action="sendPOIstats.jsp">
	Which POIs do you wish to search for each category? <br>
	
		<select name="type">
			<option value="1">Most Popular</option>
			<option value="2">Most Expensive</option>
			<option value="3">Highest Rated</option>
		</select> <br><br>
		How many POIs do you wish to view per category?<br>
		<select name="value">
			<option value="5">5</option>
			<option value="10">10</option>
			<option value="15">15</option>
			<option value="20">20</option>
			<option value="25">25</option>
			<option value="50">50</option>
			<option value="100">100</option>
			<option value="*">All</option>
		</select> <br><br><br>
		
		<input type="submit" value="View Stats">
	</form>


<br>
<br>
<br>
	
	</div>
</body>
</html>







