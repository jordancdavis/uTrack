<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	if (session.getAttribute("username") == null) {
		response.sendRedirect("index.jsp");
		return;
	}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Most Trusted Users</title>
</head>
<body>
	<%
		out.println(request.getParameter("item"));
	%>
	most trusted users:
	<br>
	<br>
	<%
		User usr = new User();
		Connector con = new Connector();
		String s = usr.getXMostTrustedUsers(request.getParameter("item"), con.stmt);
		out.print(s);

		con.closeConnection();
	%>
	<br>
	<a href="welcomeAdmin.jsp">Return to Admin Menu</a>
</body>

</html>
