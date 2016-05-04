<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack User</title>
</head>
<h2>
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - Main Menu</FONT>
</h2>

<body>

	<%
		if (session.getAttribute("username") == null) {
			response.sendRedirect("index.jsp");
			return;
		}

		session.setAttribute("_vname", null);
		session.setAttribute("_vsize", null);
		session.setAttribute("_vprice", null);
		session.setAttribute("_vdate", null);
		session.setAttribute("statMessage", null);
		session.setAttribute("browseMessage", null);
		session.setAttribute("_bmin", null);
		session.setAttribute("_bmax", null);
		session.setAttribute("_bcity", null);
		session.setAttribute("_bstate", null);
		session.setAttribute("_bkeywords", null);
		session.setAttribute("_bcategory", null);
		session.setAttribute("_bsort", null);
	%>

	<p>
		Welcome,
		<%=session.getAttribute("username")%></p>
	<p>
	<center>
		<table border="1" width="66%" cellpadding="3" align="center">
			<tr>
				<td colspan="2">
					<center>
						<br>
						<form method="post" action="getVisit.jsp">
							<input type="submit" name="act" value="Record A Visit" />
						</form>
						<br>
					</center>
				</td>

				<td colspan="2">
					<center>
						<br>
						<form method="post" action="browsePOI.jsp">
						<input type="submit" name="act" value="Browse POI's" /> <br>
						</form>
						<br>
					</center>
				</td>

				<td colspan="2">
					<center>
						<br>
						<form method="post" action="poiStats.jsp">
						<input type="submit" name="act" value="View POI Statistics" /><br>
						</form>
						<br>
					</center>
				</td>
			</tr>

			<tr>
				<td colspan="2">
					<center>
						<br>
						<form method="post" action="recordFeedback.jsp">
						<input type="submit" name="act" value="Provide POI feedback" /><br>
						<br>
						</form>
					</center>
				</td>

				<td colspan="2">
					<center>
						<br>
						<form method="post" action="rateFeedback.jsp">
						<input type="submit" name="act" value="View & Rate Feedback" /> <br>
						</form>
						<br>
					</center>
				</td>

				<td colspan="2">
					<center>
						<br>
						<form method="post" action="getXMostUsefulFeedback.jsp">
						<input type="submit" name="act" value="Get Most Usefull Feedback for POI" /><br>
						</form>
						<br>
					</center>
				</td>
			</tr>

			<tr>
				<td colspan="2">
					<center>
						<br>
						<form method="post" action="addFavPOI.jsp">
						<input type="submit" name="act" value="Declare Favorite POI" />
						</form>
						<br>
					</center>
				</td>

				<td colspan="2">
					<center>
						<br>
						<form method="post" action="addUserTrust.jsp">
						<input type="submit" name="act" value="Trust / Dis-Trust Users" />
						</form>
						<br>
					</center>
				</td>

				<td colspan="2">
					<center>
						<br>
						<form method="post" action="getDegreeOfSeparation.jsp">
						<input type="submit" name="act" value="Degree of separation from a User" /><br>
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