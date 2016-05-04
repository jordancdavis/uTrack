<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>uTrack signup</title>
</head>
<body>
	<%! boolean valid = false; %>
	<form method="post" action="registration.jsp">
		<center>
			<table border="1" width="33%" cellpadding="5">
				<thead>
					<tr>
						<th colspan="6"><FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack
								Registration</FONT></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td>First Name</td>
						<td><input type="text" name="rfirst"
							value="<%if(session.getAttribute("_rfirstname")!=null){%><%=session.getAttribute("_rfirstname")%><%}%>" /></td>
					</tr>
					<tr>
						<td>Last Name</td>
						<td><input type="text" name="rlast"
							value="<%if(session.getAttribute("_rlastname")!=null){%><%=session.getAttribute("_rlastname")%><%}%>" /></td>
					</tr>
					<tr>
						<td>Username</td>
						<td><input type="text" name="ruser"
							value="<%if(session.getAttribute("_rusername")!=null){%><%=session.getAttribute("_rusername")%><%}%>" /></td>
					</tr>
					<tr>
						<td>Password</td>
						<td><input type="password" name="rpass" value="" /></td>
					</tr>
					<tr>
						<td>Confirm Password</td>
						<td><input type="password" name="rconfpass" value="" /></td>
					</tr>
					<tr>
						<td>Email</td>
						<td><input type="text" name="remail"
							value="<%if(session.getAttribute("_remail")!=null){%><%=session.getAttribute("_remail")%><%}%>" /></td>
					</tr>
					<tr>
						<td>Gender</td>
						<td><center>
								M<input type="radio" name="rgender" value="M" id="yes" /> F<input
									type="radio" name="rgender" value="F" id="no" />
							</center></td>

					</tr>
					<tr>
						<td>D.O.B (mm/dd/yyyy)</td>
						<td><input type="text" name="rdob"
							value="<%if(session.getAttribute("_rdob")!=null){%><%=session.getAttribute("_rdob")%><%}%>" /></td>
					</tr>
					<tr>
						<td>Phone Number (0123456789)</td>
						<td><input type="text" name="rphone"
							value="<%if(session.getAttribute("_rphone")!=null){%><%=session.getAttribute("_rphone")%><%}%>" /></td>
					</tr>
					<tr>
						<td>Address</td>
						<td><input type="text" name="rstreet"
							value="<%if(session.getAttribute("_rstreet")!=null){%><%=session.getAttribute("_rstreet")%><%}%>" /></td>
					</tr>
					<tr>
						<td>City</td>
						<td><input type="text" name="rcity"
							value="<%if(session.getAttribute("_rcity")!=null){%><%=session.getAttribute("_rcity")%><%}%>" /></td>
					</tr>
					<tr>
						<td>State</td>
						<td><input type="text" name="rstate"
							value="<%if(session.getAttribute("_rstate")!=null){%><%=session.getAttribute("_rstate")%><%}%>" /></td>
					</tr>
					<tr>
						<td>Zip</td>
						<td><input type="text" name="rzip"
							value="<%if(session.getAttribute("_rzip")!=null){%><%=session.getAttribute("_rzip")%><%}%>" /></td>
					</tr>
					<tr>
						<td colspan="2">
							<center>
								<font color="red"> <%
								String e = (String) session.getAttribute("_rerror");
								if(e != null){
									out.println(e); 
								}
							%>
								</font>
							</center>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<center>
								<input type="submit" value="Register" />
							</center>
						</td>

					</tr>
					<tr>
						<td colspan="2">Already registered? <a href="index.jsp">Login
								Here</a></td>
					</tr>
				</tbody>
			</table>
		</center>
	</form>

</body>

</html>