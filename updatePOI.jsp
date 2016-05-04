<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack - Update POI</title>
</head>
<h2>
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - Update POI</FONT>
</h2>
<body>
	<%
		if (session.getAttribute("username") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
	

	session.setAttribute("_pname", null);
	session.setAttribute("_pcategory", null);
	session.setAttribute("_pyear", null);
	session.setAttribute("_phours", null);
	session.setAttribute("_pprice", null);
	session.setAttribute("_purl", null);
	session.setAttribute("_pphone", null);
	session.setAttribute("_pstreet", null);
	session.setAttribute("_pcity", null);
	session.setAttribute("_pstate", null);
	session.setAttribute("_pzip", null);
	%>
	<br>
	<form method="post" action="displayPOIUpdate.jsp">
		Enter the POI name you want to update: <br> <input type="text" name="poiname" /> <br>
		<br> <input type="submit" value="Get POI Info">
	</form>
	<br>
	<FONT COLOR=RED>
		<%
		
			if (session.getAttribute("kmssg") == "false") {
				out.print(session.getAttribute("errormssg"));
				session.setAttribute("kmssg", null);
			}
		%>
	</FONT>
	<br>
	<br>
	<br>
	<a href="welcomeAdmin.jsp">Return to Admin Menu</a>
</body>
</html>