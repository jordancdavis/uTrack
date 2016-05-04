<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.Date"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%if(session.getAttribute("username") == null){
	response.sendRedirect("index.jsp");
	return;
}%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Confirm Visit</title>
</head>
<body>

	<%
    POI poi = new POI();
    Connector con = new Connector();
    Visits visit = new Visits();
    
    boolean isValid = true;
    String name = request.getParameter("vname");
    String size = request.getParameter("vsize");
    String price = request.getParameter("vprice");
    String date = request.getParameter("vdate");
    
    session.setAttribute("_vname", name);
    session.setAttribute("_vsize", size);
    session.setAttribute("_vprice", price);
    session.setAttribute("_vdate", date);

    
	session.setAttribute("errmessg", null);
    String mssg = "";
    
    if(name.length() <1 || name.length() >50){
    	isValid = false;
    	mssg += "* Invalid POI name <br>";
    } else {
		poi.selectExistingPOI(name, con.stmt);
		if(poi.isNotReal){
	    	isValid = false;
	    	mssg += "* POI does not exist <br>";
		}
    }
    try {
        int x = Integer.parseInt(size);
    } catch(Exception e) {
    	isValid = false;
    	mssg += "* Invalid Party Size (must be whole number) <br>";
    }
    
    try {
        int p = Integer.parseInt(price);
    } catch(Exception e) {
    	isValid = false;
    	mssg += "* Invalid Price (must be whole number) <br>";
    }
 
    
    try {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date birthdate = (Date) formatter.parse(date);
		visit._visit_date = new java.sql.Date(birthdate.getTime());
		session.setAttribute("_vdate",visit._visit_date);
	} catch (Exception e) {
		mssg += "* Invalid date format. <br>";
		isValid = false;
	}
    
    if(!isValid){
    	//send back with message
    	session.setAttribute("errmessg", mssg);
    	response.sendRedirect("getVisit.jsp");
    	return;
    }
    
    %>

	You are about to record this Visit.
	<br> Please confirm the information below:
	<br>
	<br>

	<table border="1" width="50%" cellpadding="5">
		<thead>
			<tr>
				<th colspan="6"><FONT COLOR=RED FACE="Geneva, Arial" SIZE=4>Confirm
						POI Visit</FONT></th>
			</tr>
		</thead>
		<tbody>

			<tr>
				<td>Name of POI visited:</td>
				<td>
					<% out.print(name); %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td>Party size:</td>
				<td>
					<% out.print(size); %>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td>Avg. cost per person:</td>
				<td>
					<% out.print(price); %>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td>Date of visit (mm/dd/yyyy):</td>
				<td>
					<% out.print(date); %>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>

			<tr>
				<td colspan="2">
					<center>
						<font color="red"> <%
								String e = (String) session.getAttribute("_perror");
								if(e != null){
									out.println(e); 
								}
							%>
						</font>
					</center>
			</tr>
		</tbody>
	</table>
	<br> Are you sure you want to record this visit?
	<br>
	<br>
	<form method="post" action="sendVisit.jsp">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp; <input type="submit" value="Confirm" />
	</form>
	<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="getVisit.jsp">Cancle</a>

</body>
</html>