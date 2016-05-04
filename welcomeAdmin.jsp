<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack Admin</title>
</head>
<h2>
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - Admin Menu</FONT>
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

	<p>
		Welcome Admin,
		<%=session.getAttribute("username")%></p>
	<p>
	<center>
		<table border="1" width="66%" cellpadding="3" align="center">
			<tr>
				<td colspan="2">
					<center>
						<br>
						<form method="post" action="getNewPOIInfo.jsp">
							<input type="submit" name="act" value="Add New POI" />
						</form>
						<br>
					</center>
				</td>

				<td colspan="2">
					<center>
						<br>
						<form method="post" action="updatePOI.jsp">
							<input type="submit" name="act" value="Update POI" />
						</form>
						<br>
					</center>
				</td>
			</tr>

			<tr>
				<td colspan="2">
					<center>
						<br>
						<form method="post" action="addKeywordToPOI.jsp">
							<input type="submit" name="act" value="Add Keyword to POI" />
						</form>
						<br>
					</center>
				</td>

				<td colspan="2">
					<center>
						<br>
						<form method="post" action="addKeywordToDB.jsp">
							<input type="submit" name="act" value="Add Keyword to DB" />
						</form>
						<br>
					</center>
				</td>
			</tr>

			<tr>
				<td colspan="2">
					<center>
						<br>
						<form method="post" action="getXMostTrustedUsers.jsp">
							<input type="submit" name="act" value="View Most Trusted Users" />
						</form>
						<br>
					</center>
				</td>

				<td colspan="2">
					<center>
						<br>
						<form method="post" action="getXMostUsefulUsers.jsp">
							<input type="submit" name="act" value="View Most Useful Users" />
						</form>
						<br>
					</center>
				</td>
			</tr>
			<tr></tr>

		</table>
	</center>
	<br>
	<a href="logout.jsp">Logout</a>

</body>
</html>