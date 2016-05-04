package cs5530;

/**
 * Jordan Davis
 * u0670513
 * March 21, 2016
 * cs5530 - uTrack
 */

import java.io.BufferedReader;
import java.sql.*;

public class User {

	public String _username, _password, _firstname, _lastname, _gender, _email, _phone_num, _city, _state, _street,
			_zipcode;
	public boolean _isAdmin;
	public Date _dob;
	public boolean isTaken = true;

	public void resetUser() {
		_username = null;
		_password = null;
		_firstname = null;
		_lastname = null;
		_gender = null;
		_dob = null;
		_email = null;
		_phone_num = null;
		_isAdmin = false;
		_city = null;
		_state = null;
		_street = null;
		_zipcode = null;
	}

	/**
	 * adds a new user to the given database connection
	 * 
	 * @param con
	 * @return true if success, false if error occured
	 */
	public boolean registerNewUser(Connector con) {
		String sql = "INSERT INTO Users(username, user_password, firstname, lastname, gender, "
				+ "email, phone_num, is_admin, city, state, street, zipcode, dob) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		System.out.println("Adding new user to database...");
		try {
			PreparedStatement ps = con.con.prepareStatement(sql);
			ps.setString(1, _username);
			ps.setString(2, _password);
			ps.setString(3, _firstname);
			ps.setString(4, _lastname);
			ps.setString(5, _gender);
			ps.setString(6, _email);
			ps.setString(7, _phone_num);
			ps.setBoolean(8, _isAdmin);
			ps.setString(9, _city);
			ps.setString(10, _state);
			ps.setString(11, _street);
			ps.setInt(12, Integer.parseInt(_zipcode));
			ps.setDate(13, _dob);
			ps.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Username Already Exists");
			return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean login(String username, String password, Statement stmt) {
		boolean success = false;
		String sql = "SELECT * FROM Users WHERE username='" + username + "' " + "AND user_password='" + password + "'";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				_username = rs.getString("username");
				_password = rs.getString("user_password");
				_firstname = rs.getString("firstname");
				_lastname = rs.getString("lastname");
				_gender = rs.getString("gender");
				_dob = rs.getDate("dob");
				_email = rs.getString("email");
				_phone_num = rs.getString("phone_num");
				_isAdmin = rs.getBoolean("is_admin");
				_city = rs.getString("city");
				_state = rs.getString("state");
				_street = rs.getString("street");
				_zipcode = "" + rs.getInt("zipcode");

			}
			if (_username.equals(username) && _password.equals(password)) {
				success = true;
			} else {
				resetUser();
			}

		} catch (Exception e) {
			System.out.println("Error occured durring loggin");
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

		return success;
	}

	public void checkUsernameAvailability(String username, BufferedReader in, Statement stmt) {
		String sql = "SELECT Count(*) AS count FROM Users WHERE username ='" + username + "'";
		System.out.println("Checking Username...");
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			int count = -1;
			while (rs.next()) {
				count = rs.getInt("count");
			}
			if (count == 0) {
				System.out.println("Username Is Available");
				isTaken = false;
			} else {
				System.out.println("Username Is Already Taken. Try Again.");
				isTaken = true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error Checking Usernmae");
			isTaken = true;
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

	public String addFavoritePOI(String pname, String pid, Connector con) {
		String sql = "INSERT INTO Favorites(username, pid, fvdate) " + "VALUES (?, ?, ?)";
		String response;
		try {
			PreparedStatement ps = con.con.prepareStatement(sql);
			ps.setString(1, _username);
			ps.setInt(2, Integer.parseInt(pid));
			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			ps.setTimestamp(3, date);

			response = ("Adding Favorite POI to database...");
			ps.executeUpdate();
			response = ("Favorite POI was sucessfully recorded");

		} catch (SQLIntegrityConstraintViolationException e) {
			response = ("You have already selected "+ pname +" as a favorite POI");
			return response;
		} catch (Exception e) {
			response = (e.getMessage());
			response += ("<br> Sorry, favorite POI was not recorded");
			return response;
		}
		return response;
	}

	public String addFeedbackToPOI(String pname, String _pid, int score, String opinion, Connector con ) {
		String sql = "INSERT INTO Feedback(pid, username, score, opinion, fbdate) " + "VALUES (?, ?, ?, ?,?)";
		String response;
		try {
			PreparedStatement ps = con.con.prepareStatement(sql);
			ps.setString(1, _pid);
			ps.setString(2, _username);
			ps.setInt(3, score);
			ps.setString(4, opinion);
			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			ps.setTimestamp(5, date);
			ps.executeUpdate();
			response = ("POI Feedback sucessfuly recorded");

		} catch (Exception e) {
			response = (e.getMessage()+ "<br>");
			if(response.contains("Duplicate entry")){
				response = ("You have already provided feedback for this POI");
			} else {
				response = ("Error: Sorry, POI Feedback was not recorded");
			}
			return response;
		}
		return response;
	}

	public boolean getUser(String username, Statement stmt) {
		boolean success = false;
		String sql = "SELECT * FROM Users WHERE username='" + username + "'";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				_username = rs.getString("username");
				_password = rs.getString("user_password");
				_firstname = rs.getString("firstname");
				_lastname = rs.getString("lastname");
				_gender = rs.getString("gender");
				_dob = rs.getDate("dob");
				_email = rs.getString("email");
				_phone_num = rs.getString("phone_num");
				_isAdmin = rs.getBoolean("is_admin");
				_city = rs.getString("city");
				_state = rs.getString("state");
				_street = rs.getString("street");
				_zipcode = "" + rs.getInt("zipcode");

			}
			if (_username.equals(username)) {
				success = true;
			} else {
				resetUser();
			}

		} catch (Exception e) {
			System.out.println("Error occured retreiving user from database");
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

		return success;
	}

	public String addUserRelationship(User otherUser, int score, Connector con) {
		String sql = "INSERT INTO Trust(username1, username2, is_trusted, tdate) " + "VALUES (?, ?, ?, ?)";
		String response;
		try {
			PreparedStatement ps = con.con.prepareStatement(sql);
			ps.setString(1, _username);
			ps.setString(2, otherUser._username);
			ps.setInt(3, score);
			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			ps.setTimestamp(4, date);

			response = ("Adding relationship to database...");
			ps.executeUpdate();
			response = ("Relationship was sucessfully recorded");

		} catch (Exception e) {
			response = (e.getMessage());
			if(response.contains("Duplicate")){
				response = "Relationship already exists";
			}
			response += ("<br> Sorry, relationship was not recorded");
			return response;
		}
		return response;
	}

	public String getXMostTrustedUsers(String xValue, Statement stmt) {
		String sss = "";
		if(xValue.equals("*")){
			xValue = "";
		} else {
			xValue =  " LIMIT "+ xValue;
		}
		String sql = "SELECT u.username, SUM(t.is_trusted) AS ttt FROM Trust t, Users u"
				+ " WHERE t.username2 = u.username GROUP BY t.username2 ORDER BY SUM(is_trusted) DESC " + xValue;
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			sss+=("Trusted Score<span style=\"margin-left:1em\"></span>| Username <br>");
			sss+=("-----------------------------------------<br>");

			while (rs.next()) {
				sss+=(rs.getInt("ttt")  + "<span style=\"margin-left:6em\"</span>|<span style=\"margin-left:1em\"</span> " + rs.getString("username") +"<br>");
			}
		} catch (Exception e) {
			sss+=(e.getMessage());
			sss+=("Error loading " + xValue + " most trusted users.");
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
		sss+=("<br>");
		return sss;
	}

	public String getXMostUsefulUsers(String xValue, Statement stmt) {
		String sss = "";
		if(xValue.equals("*")){
			xValue = "";
		} else {
			xValue =  " LIMIT "+ xValue;
		}
		String sql = "SELECT f.username, AVG(r.rating) avgr FROM Ratings r, Feedback f "
				+ "WHERE r.feedbackid = f.fid GROUP BY username ORDER BY avgr DESC " + xValue;
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			sss+=("Avg. Usefulness Score<span style=\"margin-left:1em\"></span>| Username <br>");
			sss+=("-----------------------------------------<br>");

			while (rs.next()) {
				sss+=(rs.getInt("avgr")  + "<span style=\"margin-left:6em\"</span>|<span style=\"margin-left:1em\"</span> " + rs.getString("username") +"<br>");

			}
		} catch (Exception e) {
			sss+=(e.getMessage());
			sss+=("Error loading " + xValue + " most useful users.");
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
		sss+=("<br>");
		return sss;
	}

	public boolean checkFirstDegreeOfSeparation(User otherUser, Statement stmt) {
		String sql = "SELECT count(*) AS countQ FROM Favorites F, Favorites F2 "
				+ "WHERE F.pid = F2.pid AND F.username ='" + _username + "' AND F2.username='" + otherUser._username
				+ "'";
		int count = 0;
		boolean result = false;
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				count = rs.getInt("countQ");
			}
			if (count > 0) {
				result = true;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error calculating degree of separation.\n");
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
		return result;
	}

	public boolean checkSecondDegreeOfSeparation(User otherUser, Statement stmt) {
		String sql = "SELECT count(*) AS countQ FROM " + "(SELECT F2.username FROM Favorites F, Favorites F2 "
				+ "Where F.username ='" + _username + "' AND F.pid = F2.pid " + "And F2.username != '" + _username
				+ "') AS T, " + "(SELECT U2.username FROM Favorites U, Favorites U2 " + "Where U.username ='"
				+ otherUser._username + "' AND U.pid = U2.pid " + "And U2.username != '" + otherUser._username
				+ "') AS T2 " + "WHERE T.username = T2.username;";

		int count = 0;
		boolean result = false;
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				count = rs.getInt("countQ");
			}
			if (count > 0) {
				result = true;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error calculating degree of separation.\n");
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
		return result;
	}

}
