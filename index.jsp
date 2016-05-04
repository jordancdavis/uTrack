<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
{ margin:0; padding :0;}
html {
	background: url('ohtheplaces.jpg') no-repeat center center fixed;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	background-size: cover;
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack Home</title>
</head>
<body>
	<%
		utrackMain main = new utrackMain();
		session.setAttribute("main", main);
		session.setAttribute("logged", "false");
	%>

	<form method="post" action="login.jsp">
		<center>
			<h2>
				<FONT COLOR=RED FACE="Geneva, Arial" SIZE=7> Welcome to
					uTrack</FONT>
			</h2>
			<h4>
				<FONT COLOR=RED FACE="Geneva, Arial" SIZE=3> oh the places
					you'll go</FONT>
			</h4>
			<br>
			<br>
			<br>

		</center>
		<table bgcolor="#FFFFFF" border="2" align="center">
			<tr>
				<td>Username :</td>
				<td><input type="text" name="uname" /></td>
			</tr>

			<tr>
				<td>Password :</td>
				<td><input type="password" name="password" /></td>
			</tr>

			<tr></tr>

			<tr>
				<td colspan="2">
					<center>
						<input type="submit" value="Login" />
					</center>
				</td>
			</tr>

			<tr>
				<td colspan="2">
					<center>
						<a href="register.jsp">Register Here</a>
					</center>
				</td>
		</table>

	</form>
</body>
</html>