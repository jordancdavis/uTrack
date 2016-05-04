<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack - POI Browse</title>
</head>
<h2>
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - POI Browsing</FONT>
</h2>

<body>

	<%
		if (session.getAttribute("username") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
	%>



<div style="position: relative; width: 750px;">
		<div
			style="position: absolute; top: 0; right: 0; width: 350px; text-align: left;">

				<a href="welcomeUser.jsp">Return to Main Menu</a>
				<br><br>
				<br>

			<% 
				if(session.getAttribute("browseMessage") != null){
					out.println(session.getAttribute("browseMessage"));
					%><br><br><a href="welcomeUser.jsp">Return to Main Menu</a>
					<% 
				}

			%>		
	</div>

	<form action="executeSearch.jsp">
	Narrow Search by: <br><br>
	Price Range: <br>
	<span style="margin-left:2em"></span> Min &nbsp; <input type="text" value="<%if(session.getAttribute("_bmin")!=null){%><%=session.getAttribute("_bmin")%><%}%>" name="min">
	 
	<br>
	<span style="margin-left:2em"></span> Max &nbsp; <input type="text" value="<%if(session.getAttribute("_bmax")!=null){%><%=session.getAttribute("_bmax")%><%}%>" name="max">
	<br><br>
	
	Address: <br>
	<span style="margin-left:2em"></span> City &nbsp; <input type="text" value="<%if(session.getAttribute("_bcity")!=null){%><%=session.getAttribute("_bcity")%><%}%>" name="city">
	 
	<br>
	<span style="margin-left:2em"></span> State &nbsp; <input type="text" value="<%if(session.getAttribute("_bstate")!=null){%><%=session.getAttribute("_bstate")%><%}%>" name="state">
	<br><br>

	Keywords (separated by spaces): <br>
	<span style="margin-left:2em"></span> <input type="text" value="<%if(session.getAttribute("_bkeywords")!=null){%><%=session.getAttribute("_bkeywords")%><%}%>" name="keywords">
	<br><br>
	
	Category: <br>
	<span style="margin-left:2em"></span> <input type="text" value="<%if(session.getAttribute("_bcategory")!=null){%><%=session.getAttribute("_bcategory")%><%}%>" name="category">
	<br><br><br>
	
	Sort results by:<br><br>
	
	<input type="radio" name="sort" value=7 checked="checked"/> No Specific Order <br> 
	<input type="radio" name="sort" value=1 /> Price Ascending <br> 
	<input type="radio" name="sort" value=2 /> Price Descending <br>
	<input type="radio" name="sort" value=3 /> Avg. Feedback Score Ascending <br> 
	<input type="radio" name="sort" value=4 /> Avg. Feedback Score Descending <br>
	<input type="radio" name="sort" value=5 /> Avg. Trusted User Feedback Score Ascending <br> 
	<input type="radio" name="sort" value=6 /> Avg. Trusted User Feedback Score Descending <br>

	<br><br>	
		<input type="submit" value="Search POI's">
	</form>




	</div>
</body>
<br>
<br>
<br> 
</html>







