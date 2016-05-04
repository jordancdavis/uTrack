<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	if (session.getAttribute("username") == null) {
		response.sendRedirect("index.jsp");
		return;
	}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add POI</title>
</head>
<body>
	<%
		boolean invalid = false;
		Connector con = new Connector();
		POI poi = new POI();

		session.setAttribute("_perror", null);

		String name = request.getParameter("pname");
		session.setAttribute("_pname", name);
		String category = request.getParameter("pcategory");
		session.setAttribute("_pcategory", category);
		String year = request.getParameter("pyear");
		session.setAttribute("_pyear", year);
		String hours = request.getParameter("phours");
		session.setAttribute("_phours", hours);
		String price = request.getParameter("pprice");
		session.setAttribute("_pprice", price);
		String url = request.getParameter("purl");
		session.setAttribute("_purl", url);
		String phone = request.getParameter("pphone");
		session.setAttribute("_pphone", phone);
		String street = request.getParameter("pstreet");
		session.setAttribute("_pstreet", street);
		String city = request.getParameter("pcity");
		session.setAttribute("_pcity", city);
		String state = request.getParameter("pstate");
		session.setAttribute("_pstate", state);
		String zip = request.getParameter("pzip");
		session.setAttribute("_pzip", zip);
		String error = "";

		if (name.length() < 1 || name.length() > 50) {
			session.setAttribute("_pname", null);
			invalid = true;
			error += "* Invalid POI name <br>";
		}

		if (category.length() < 1 || category.length() > 50) {
			session.setAttribute("_pcategory", null);
			invalid = true;
			error += "* Too many categories<br>";
		}

		if (year.length() != 4 || !utrackMain.isValidStringOfNumbers(year)) {
			session.setAttribute("_pyear", null);
			invalid = true;
			error += "* Invalid year <br>";
		}

		if (!utrackMain.isValidStringOfNumbers(price)) {
			session.setAttribute("_pprice", null);
			invalid = true;
			error += "* Invalid price. (whole numbers only) <br>";
		}

		if (url.length() >= 50) {
			session.setAttribute("_purl", null);
			invalid = true;
			error += "* Invalid url <br>";
		}

		if (hours.length() >= 20) {
			session.setAttribute("_phours", null);
			invalid = true;
			error += "* Invalid hours <br>";
		}

		if (phone.length() != 10 || !utrackMain.isValidStringOfNumbers(phone)) {
			session.setAttribute("_pphone", null);
			invalid = true;
			error += "* Invalid phone <br>";
		}

		if (city.length() > 20) {
			session.setAttribute("_pcity", null);
			invalid = true;
			error += "Invalid city <br>";
		}

		if (state.length() > 20) {
			session.setAttribute("_pstate", null);
			invalid = true;
			error += "* Invalid state <br>";

		}
		if (street.length() > 45) {
			session.setAttribute("_pstreet", null);
			invalid = true;
			error += "* Invalid street <br>";

		}
		if (zip.length() != 5 || !utrackMain.isValidStringOfNumbers(zip)) {
			session.setAttribute("_pzip", null);
			invalid = true;
			error += "* Invalid zip <br>";

		}
		session.setAttribute("_perror", error);

		if (invalid) {
			response.sendRedirect("getNewPOIInfo.jsp");
			return;
		}

		poi._pname = name;
		poi._url = url;
		poi._phone_num = phone;
		poi._category = category;
		poi._year_est = year;
		poi._hours = hours;
		poi._price = price;
		poi._city = city;
		poi._state = state;
		poi._street = street;
		poi._zipcode = zip;

		if (poi.addNewPOI(con)) {
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
			out.println("POI successfully added to database");
	%><a href="welcomeAdmin.jsp">Return to Admin Home</a>
	<%
		} else {
			session.invalidate();
			out.println("Sorry, POI was not added to the database. Please try again.");
	%><a href="welcomeAdmin.jsp">Return to Admin Home</a>
	<%
		}
		con.closeConnection();
	%>
</body>
</html>