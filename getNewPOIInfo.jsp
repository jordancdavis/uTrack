<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack - Add POI</title>
</head>
<h2>
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - Register POI</FONT>
</h2>
<body>

<%if(session.getAttribute("username") == null){
	response.sendRedirect("index.jsp");
	return;
}
%>

	<%! boolean valid = false; %>
	<form method="post" action="addNewPOI.jsp">
		<center>
			<table border="1" width="33%" cellpadding="5">
				<thead>
					<tr>
						<th colspan="6"><FONT COLOR=RED FACE="Geneva, Arial" SIZE=4>uTrack
								Add New POI</FONT></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td>POI name:</td>
						<td><input type="text" name="pname"
							value="<%if(session.getAttribute("_pname")!=null){%><%=session.getAttribute("_pname")%><%}%>" /></td>
					</tr>
					<tr>
						<td>POI categories (separate by comma)</td>
						<td><input type="text" name="pcategory"
							value="<%if(session.getAttribute("_pcategory")!=null){%><%=session.getAttribute("_pcategory")%><%}%>" /></td>
					</tr>
					<tr>
						<td>POI year est. (2016)</td>
						<td><input type="text" name="pyear"
							value="<%if(session.getAttribute("_pyear")!=null){%><%=session.getAttribute("_pyear")%><%}%>" /></td>
					</tr>
					<tr>
						<td>POI avg. price</td>
						<td><input type="text" name="pprice"
							value="<%if(session.getAttribute("_pprice")!=null){%><%=session.getAttribute("_pprice")%><%}%>" /></td>
					</tr>
					<tr>
						<td>POI hours (9:00 - 15:00)</td>
						<td><input type="text" name="phours"
							value="<%if(session.getAttribute("_phours")!=null){%><%=session.getAttribute("_phours")%><%}%>" /></td>
					</tr>
					<tr>
						<td>POI url</td>
						<td><input type="text" name="purl"
							value="<%if(session.getAttribute("_purl")!=null){%><%=session.getAttribute("_purl")%><%}%>" /></td>
					</tr>
					<tr>
						<td>POI Phone Number (0123456789)</td>
						<td><input type="text" name="pphone"
							value="<%if(session.getAttribute("_pphone")!=null){%><%=session.getAttribute("_pphone")%><%}%>" /></td>
					</tr>
					<tr>
						<td>Address</td>
						<td><input type="text" name="pstreet"
							value="<%if(session.getAttribute("_pstreet")!=null){%><%=session.getAttribute("_pstreet")%><%}%>" /></td>
					</tr>
					<tr>
						<td>City</td>
						<td><input type="text" name="pcity"
							value="<%if(session.getAttribute("_pcity")!=null){%><%=session.getAttribute("_pcity")%><%}%>" /></td>
					</tr>
					<tr>
						<td>State</td>
						<td><input type="text" name="pstate"
							value="<%if(session.getAttribute("_pstate")!=null){%><%=session.getAttribute("_pstate")%><%}%>" /></td>
					</tr>
					<tr>
						<td>Zip</td>
						<td><input type="text" name="pzip"
							value="<%if(session.getAttribute("_pzip")!=null){%><%=session.getAttribute("_pzip")%><%}%>" /></td>
					</tr>
					<tr>
						<td colspan="2">
							<center>
								<font color="red"> <%
								String e = (String) session.getAttribute("_perror");
								if(e != null){
									out.println(e); 
									session.setAttribute("_perror", null);
								}
							%>
								</font>
							</center>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<center>
								<input type="submit" value="Add POI" />
							</center>
						</td>
					</tr>
				</tbody>
			</table>
			<br> <a href="welcomeAdmin.jsp">Cancel - Return to Admin
				Menu</a>
		</center>
	</form>

</body>

</html>