<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack - Record Visit</title>
</head>
<h2>
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - Visit</FONT>
</h2>
<body>
	<%
		if (session.getAttribute("username") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
	%>


	<%! boolean valid = false; %>
	<form method="post" action="addNewVisit.jsp">
		<center>
			<table border="1" width="50%" cellpadding="5">
				<thead>
					<tr>
						<th colspan="6"><FONT COLOR=RED FACE="Geneva, Arial" SIZE=4>Record
								POI Visit</FONT></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td>Name of POI visited:</td>
						<td><input type="text" name="vname"
							value="<%if(session.getAttribute("_vname")!=null){%><%=session.getAttribute("_vname")%><%}%>" /></td>
					</tr>
					<tr>
						<td>Party size:</td>
						<td><input type="text" name="vsize"
							value="<%if(session.getAttribute("_vsize")!=null){%><%=session.getAttribute("_vsize")%><%}%>" /></td>
					</tr>
					<tr>
						<td>Avg. cost per person:</td>
						<td><input type="text" name="vprice"
							value="<%if(session.getAttribute("_vprice")!=null){%><%=session.getAttribute("_vprice")%><%}%>" /></td>
					</tr>
					<tr>
						<td>Date of visit (mm/dd/yyyy):</td>
						<td><input type="text" name="vdate"
							value="<%if(session.getAttribute("_vdate")!=null){%><%=session.getAttribute("_vdate")%><%}%>" /></td>
					</tr>

					<tr>
						<td colspan="2">
							<center>
								<font color="red"> <%
								String e = (String) session.getAttribute("errmessg");
								if(e != null){
									out.println(e); 
									session.setAttribute("errmessg", null);
								}
							%>
								</font>
							</center>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<center>
								<input type="submit" value="Submit" />
							</center>
						</td>
					</tr>
				</tbody>
			</table>
			<br> <a href="welcomeUser.jsp">Return to Main Menu</a>
		</center>
	</form>

</body>

</html>