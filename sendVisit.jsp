<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack - Suggested POI</title>
</head>
<h2>
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - Suggested POI's</FONT>
</h2>

<%if(session.getAttribute("username") == null){
	response.sendRedirect("index.jsp");
	return;
}
%>

<body>
	<div style="position: relative; width: 500px;">
		<div
			style="position: absolute; top: 0; right: 0; width: 350px; text-align: right;">
			<br>
			<br> <a href="getVisit.jsp">Record Another Visit</a> <br>
			<br>
			<a href="welcomeUser.jsp">Return to Main Menu</a>
		</div>
		<%
Connector con = new Connector();
Visits visit = new Visits();
User user = (User) session.getAttribute("user");

visit._pname = (String) session.getAttribute("_vname");
visit._cost = (String) session.getAttribute("_vprice");
visit._partysize = (String) session.getAttribute("_vsize");
visit._visit_date = (java.sql.Date) session.getAttribute("_vdate");
visit._username = user._username;

POI poi = new POI();
poi.selectExistingPOI(visit._pname, con.stmt);
visit._pid = poi._pid;
if(visit.registerNewVisit(con)){
	out.print(visit.getSuggestedVisits(user, poi._pid, con));
} else {
	out.print("A visit to this POI already exists on this date. <br> Please come back another day!");
}
con.closeConnection();
session.setAttribute("_vname", null);
session.setAttribute("_vsize", null);
session.setAttribute("_vprice", null);
session.setAttribute("_vdate", null);
%>

	</div>
</body>
</html>

