<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<%
	if (session.getAttribute("username") == null) {
		response.sendRedirect("index.jsp");
		return;
	}

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

	poi.selectExistingPOI(name, con.stmt);
	if (poi.isNotReal) {
		con.closeConnection();
		session.setAttribute("errormssg", "POI Does not exist");
		session.setAttribute("kmssg", "false");
		response.sendRedirect("displayPOIUpdate.jsp");
		return;
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
		session.setAttribute("kmssg", "true");
		response.sendRedirect("displayPOIUPdate.jsp");
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

	if (poi.updatePOI((String) session.getAttribute("_oldpname"), con, con.stmt)) {
		session.setAttribute("kmssg", "false");
		session.setAttribute("_pname", name);
		session.setAttribute("_pcategory", category);
		session.setAttribute("_pyear", year);
		session.setAttribute("_phours", hours);
		session.setAttribute("_pprice", price);
		session.setAttribute("_purl", url);
		session.setAttribute("_pphone", phone);
		session.setAttribute("_pstreet", street);
		session.setAttribute("_pcity", city);
		session.setAttribute("_pstate", state);
		session.setAttribute("_pzip", zip);

	} else {
		session.setAttribute("_perror", "Sorry, POI was not added to the database. Please try again.");
		session.setAttribute("kmssg", "true");
	}
	con.closeConnection();
	response.sendRedirect("displayPOIUpdate.jsp");
	return;
%>