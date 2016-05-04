<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>uTrack - Feedback</title>
</head>
<h2>
	<FONT COLOR=RED FACE="Geneva, Arial" SIZE=6>uTrack - View & Rate Feedback</FONT>
</h2>
<body>

	<%
		if (session.getAttribute("username") == null) {
			response.sendRedirect("index.jsp");
			return;
		}
	%>
<div style="position: relative; width: 700px;">
		<div
			style="position: absolute; top: 0; right: 0; width: 350px; text-align: left;">
			<% 
				ArrayList<String> flist = new ArrayList<String>();
				String name = request.getParameter("poiname");
				String feed = "";
				POI poi = new POI();
				User currentUser = (User) session.getAttribute("user");
				Connector con = new Connector();
				if(name != null){
					if (name.length() < 1 || name.length() > 50) {
						session.setAttribute("kmssg", "Invalid POI Name");
						response.sendRedirect("rateFeedback.jsp");
						return;

					} else {
						poi.selectExistingPOI(name, con.stmt);

						if (poi.isNotReal) {
							session.setAttribute("kmssg", "POI does not exist");
							response.sendRedirect("rateFeedback.jsp");
							return;
						} else {
							flist = poi.getFeedbackForPOI(currentUser, con.stmt);
							feed = poi.getFeedbackForPOIString(currentUser, con.stmt);
							session.setAttribute("feedPOI", feed);
							session.setAttribute("flist", flist);
						}
					}
				}
				if(session.getAttribute("feedPOI") != null){
					out.println(session.getAttribute("feedPOI"));
				}
				con.closeConnection();
			
			%>
			
</div>

	<form action="sendFeedbackRating.jsp">
	Enter the Feedback ID you wish to rate: <br> 
	<input type="text" name="fid" /> <br>
	<br>
	Provide a rating for this feedback: <br>
	
		<select name="item">
			<option value="0">Useless</option>
			<option value="1">Useful</option>
			<option value="2">Very Useful</option>
		</select> <br><br>
		Share your thoughts about this feedback: <br>
		<textarea maxlength='140' style="width:300px; height:50px;" name="opinion"></textarea>	<br>
		<br>
		<input type="submit" value="Submit Rating">
	</form>

	<br>
	
	<FONT COLOR=RED >
	<%
	if(session.getAttribute("kmssg") != null){
		out.print("* " + session.getAttribute("kmssg") + " *");
		session.setAttribute("kmssg", null);
	}
	%>
	</FONT>
	<br>
	<br>
	<a href="rateFeedback.jsp">Back - Select another POI</a>
	<br><br>
	<a href="welcomeUser.jsp">Return to Main Menu</a>
	</div>
</body>

</html>