<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack Login</title>
</head>
<body>
	<%
		Connector con = new Connector();

		String name = request.getParameter("uname");
		String password = request.getParameter("password");
		User usr = new User();
		if ((!(name.equals(null) || name.equals("")) && !(password.equals(null) || password.equals("")))
				&& usr.login(name, password, con.stmt)) {
			if (usr._isAdmin) {
				response.sendRedirect("welcomeAdmin.jsp");
			} else {
				response.sendRedirect("welcomeUser.jsp");
			}
			session.setAttribute("user", usr);
			session.setAttribute("username", name);
			session.setAttribute("logged", "true");
		} else {
	%>
	<center>
		<p style="color: red">Error In Login</p>
	</center>
	<%
		getServletContext().getRequestDispatcher("/index.jsp").include(request, response);
		}
		con.closeConnection();
	%>
</body>
</html>