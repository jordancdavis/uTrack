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
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - Useful Feedback</FONT>
</h2>
<body>
	<%
		if (session.getAttribute("username") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
	%>

	<%
	if(request.getParameter("poiname").length() < 1 || request.getParameter("poiname").length() > 50){
		session.setAttribute("kmssg", "true");
		response.sendRedirect("getXMostUsefulFeedback.jsp");
	}
	%>
	

<div style="position: relative; width: 500px;">
		<div
			style="position: absolute; top: 0; right: 0; width: 350px; text-align: right;">

			<a href="getXMostUsefulFeedback.jsp">Back - Change Search</a>
			<br><br>
			<a href="welcomeUser.jsp">Return to Main Menu</a>
		</div>
	<%
		out.println(request.getParameter("item"));
	%>
	most Useful Feedbacks for <%
		out.println(request.getParameter("poiname"));
	%>
<br>
	<%
		String poiname = request.getParameter("poiname");
		POI poi = new POI();
		Connector con = new Connector();
		poi.selectExistingPOI(poiname, con.stmt);
		
		if(poi.isNotReal){
			// error
		}
		out.print(poi.getTopFeedbackForPOI(request.getParameter("item"), con.stmt));

		con.closeConnection();
	%>
	<br><br>
		</div>
</body>

</html>
