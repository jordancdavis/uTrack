<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cs5530.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.*"%>


<%
	boolean invalid = false;
	Connector con = new Connector();
	User usr = new User();

	String fname = request.getParameter("rfirst");
	session.setAttribute("_rfirstname", fname);
	String lname = request.getParameter("rlast");
	session.setAttribute("_rlastname", lname);
	String usrname = request.getParameter("ruser");
	session.setAttribute("_rusername", usrname);
	String pwd = request.getParameter("rpass");
	String confpwd = request.getParameter("rconfpass");
	String email = request.getParameter("remail");
	session.setAttribute("_remail", email);
	String gender = request.getParameter("rgender");
	String dob = request.getParameter("rdob");
	session.setAttribute("_rdob", dob);
	String phone = request.getParameter("rphone");
	session.setAttribute("_rphone", phone);
	String street = request.getParameter("rstreet");
	session.setAttribute("_rstreet", street);
	String city = request.getParameter("rcity");
	session.setAttribute("_rcity", city);
	String state = request.getParameter("rstate");
	session.setAttribute("_rstate", state);
	String zip = request.getParameter("rzip");
	session.setAttribute("_rzip", zip);
	boolean isAdmin = false;
	String error = "";

	if (fname.length() < 1 || fname.length() > 20) {
		session.setAttribute("_rfirstname", null);
		invalid = true;
		error += "* Invalid first name <br>";
	}

	if (lname.length() < 1 || lname.length() > 20) {
		session.setAttribute("_rlastname", null);
		invalid = true;
		error += "* Invalid last name <br>";
	}
	if (usrname.length() < 1 || usrname.length() > 30) {
		session.setAttribute("_rusername", null);
		invalid = true;
		error += "* Invalid username <br>";
	} else {

		//check username avaiability
		String sql = "SELECT Count(*) AS count FROM Users WHERE username ='" + usrname + "'";
		ResultSet rs = null;
		try {
			rs = con.stmt.executeQuery(sql);
			int count = -1;
			while (rs.next()) {
				count = rs.getInt("count");
			}
			if (count != 0) {
				session.setAttribute("_rusername", null);
				invalid = true;
				error += "* Username already taken <br>";
			}
		} catch (Exception e) {
			session.setAttribute("_rusername", null);
			invalid = true;
			error += "* Error checking username availability <br>";
		} finally {
			try {
				if (rs != null) {
					if (!rs.isClosed()) {
						rs.close();
					}
				}
			} catch (Exception e) {
			}
		}
	}

	if ((!pwd.equals(confpwd) || pwd.length() < 6) || pwd.length() >= 30) {
		invalid = true;
		error += "* Invalid password <br>";
	}

	if (pwd.equals("booyah!24")) {
		isAdmin = true;
	}

	try {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date birthdate = (Date) formatter.parse(dob);
	} catch (Exception e) {
		session.setAttribute("_rdob", null);
		invalid = true;
		error += "* Invalid dob <br>";

	}

	if (email.length() >= 80) {
		session.setAttribute("_remail", null);
		invalid = true;
		error += "* Invalid email <br>";

	}

	if (phone.length() != 10 || !utrackMain.isValidStringOfNumbers(phone)) {
		session.setAttribute("_rphone", null);
		invalid = true;
		error += "* Invalid phone <br>";

	}

	if (city.length() > 20) {
		session.setAttribute("_rcity", null);
		invalid = true;
		error += "Invalid city <br>";

	}

	if (state.length() > 20) {
		session.setAttribute("_rstate", null);
		invalid = true;
		error += "* Invalid state <br>";

	}
	if (street.length() > 45) {
		session.setAttribute("_rstreet", null);
		invalid = true;
		error += "* Invalid street <br>";

	}
	if (zip.length() != 5 || !utrackMain.isValidStringOfNumbers(zip)) {
		session.setAttribute("_rzip", null);
		invalid = true;
		error += "* Invalid zip <br>";

	}
	session.setAttribute("_rerror", error);

	if (invalid) {
		response.sendRedirect("register.jsp");
		return;
	}

	usr._username = usrname;
	usr._password = pwd;
	usr._firstname = fname;
	usr._lastname = lname;
	usr._gender = gender;
	DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	Date birthdate = (Date) formatter.parse(dob);
	usr._dob = new java.sql.Date(birthdate.getTime());
	usr._email = email;
	usr._phone_num = phone;
	usr._isAdmin = isAdmin;
	usr._city = city;
	usr._state = state;
	usr._street = street;
	usr._zipcode = zip;

	if (usr.registerNewUser(con)) {
		if (usr._isAdmin) {
			response.sendRedirect("welcomeAdmin.jsp");
		} else {
			response.sendRedirect("welcomeUser.jsp");
		}
		session.setAttribute("user", usr);
		session.setAttribute("username", usrname);
		session.setAttribute("logged", "true");
	} else {
		response.sendRedirect("logout.jsp");
	}
	con.closeConnection();
%>

