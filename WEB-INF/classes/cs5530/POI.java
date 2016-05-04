package cs5530;

/**
 * Jordan Davis
 * u0670513
 * March 21, 2016
 * cs5530 - uTrack
 */

import java.io.BufferedReader;
import java.sql.*;
import java.util.ArrayList;

public class POI {
	public String _pid, _pname, _url, _phone_num, _price, _year_est, _hours, _category, _city, _state, _street, _zipcode;
	public boolean isNotReal = true;

	public boolean addNewPOI(Connector con) {
		String sql = "INSERT INTO POI(pname, url, phone_num, price, year_est, "
				+ "hours, category, city, state, street, zipcode) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		System.out.println("Adding POI to database...");
		try {
			PreparedStatement ps = con.con.prepareStatement(sql);
			ps.setString(1, _pname);
			ps.setString(2, _url);
			ps.setString(3, _phone_num);
			ps.setInt(4, Integer.parseInt(_price));
			ps.setInt(5, Integer.parseInt(_year_est));
			ps.setString(6, _hours);
			ps.setString(7, _category);
			ps.setString(8, _city);
			ps.setString(9, _state);
			ps.setString(10, _street);
			ps.setInt(11, Integer.parseInt(_zipcode));
			ps.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean updatePOI(String pname, Connector con, Statement stmt) {
		String sql = "UPDATE POI SET pname='" + _pname + "'," + "url='" + _url + "'," + "phone_num='" + _phone_num
				+ "'," + "price=" + _price + "," + "year_est=" + _year_est + "," + "hours='" + _hours + "',"
				+ "category='" + _category + "'," + "city='" + _city + "'," + "state='" + _state + "'," + "street='"
				+ _street + "'," + "zipcode=" + _zipcode + " WHERE pname = '" + pname + "' AND pid = " + _pid;
		System.out.println("Updating POI...");
		try {
			PreparedStatement ps = con.con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error occured retreiving POI");
			return false;
		}
		return true;
	}

	public void selectExistingPOI(String pname, Statement stmt) {
		String sql = "SELECT * FROM POI WHERE pname ='" + pname + "'";
		System.out.println("Retreiving POI...");
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				_pid = "" + rs.getInt("pid");
				_pname = rs.getString("pname");
				_url = rs.getString("url");
				_phone_num = rs.getString("phone_num");
				_price = "" + rs.getInt("price");
				_year_est = "" + rs.getInt("year_est");
				_hours = rs.getString("hours");
				_category = rs.getString("category");
				_city = rs.getString("city");
				_state = rs.getString("state");
				_street = rs.getString("street");
				_zipcode = "" + rs.getInt("zipcode");
			}

			if (_pid != null) {
				System.out.println("POI exists");
				isNotReal = false;
			} else {
				System.out.println("POI not found.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error occured retreiving POI");
			isNotReal = true;
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

	public ArrayList<String> getFeedbackForPOI(User currentUser, Statement stmt) {
		String username, opinion;
		ArrayList<String> fList = new ArrayList<String>();
		int fid, score;
		Date fbdate;
		String sql = "SELECT * FROM Feedback WHERE pid ='" + _pid + "'";
		System.out.println("Retreiving Feedback for POI...");
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);

			System.out.println("\n\nFeedback for: POI Id: " + _pid + "  POI Name: " + _pname);
			System.out.println("");

			while (rs.next()) {
				username = rs.getString("username");
				opinion = rs.getString("opinion");
				fid = rs.getInt("fid");
				score = rs.getInt("score");
				fbdate = rs.getDate("fbdate");
				if (username.equals(currentUser._username)) {
					fList.add("" + fid);
				}

				System.out.println("Feedback From: " + username);
				System.out.println("Feedback ID: " + fid);
				System.out.println("Feedback Score: " + score);
				System.out.println("Feedback Date: " + fbdate.toString());
				System.out.println("Feedback Opinion: \n" + opinion + "\n\n");

			}
			if (_pid != null) {
				isNotReal = false;
			} else {
				System.out.println("No feedback exists for this POI");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error occured retreiving POI Feedback");
			isNotReal = true;
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
		return fList;
	}

	public String getFeedbackForPOIString(User currentUser, Statement stmt) {
		String username, opinion;
		String response = "";
		ArrayList<String> fList = new ArrayList<String>();
		int fid, score;
		Date fbdate;
		String sql = "SELECT * FROM Feedback WHERE pid ='" + _pid + "'";
		System.out.println("Retreiving Feedback for POI...");
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);

			response = ("Feedback for: POI Id: " + _pid + "  POI Name: " + _pname + "<br><br>");
			while (rs.next()) {
				username = rs.getString("username");
				opinion = rs.getString("opinion");
				fid = rs.getInt("fid");
				score = rs.getInt("score");
				fbdate = rs.getDate("fbdate");
				if (username.equals(currentUser._username)) {
					fList.add("" + fid);
				}

				response += ("Feedback From: " + username+ "<br>");
				response += ("Feedback ID: " + fid+ "<br>");
				response += ("Feedback Score: " + score+ "<br>");
				response += ("Feedback Date: " + fbdate.toString()+ "<br>");
				response += ("Feedback Opinion: <br><span style=\"margin-left:2em\"></span>" + opinion + "<br><br><br>");

			}
			if (_pid != null) {
				isNotReal = false;
			} else {
				response = ("No feedback exists for this POI");
			}
		} catch (Exception e) {
			response = (e.getMessage());
			if(response.contains("Duplicate entry")){
				response = ("You have already rated this feedback");
			} else {
				response = ("Error occured retreiving POI Feedback");
			}
			isNotReal = true;
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
		return response;
	}
	
	public String getTopFeedbackForPOI(String xValue, Statement stmt) {

		if(xValue.equals("*")){
			xValue = "";
		} else {
			xValue =  " LIMIT "+ xValue;
		}
		
		String username, opinion;
		int fid, score;
		String result = "No Feedback Found";
		Date fbdate;
		String sql = "SELECT u.username AS username, f.opinion AS opinion, f.fid AS fid, f.score AS score, "
				+ "f.fbdate AS fbdate, AVG(r.rating) AS avg_rating FROM "
				+ "Feedback f, Ratings r, POI p, Users u WHERE f.fid = r.feedbackid AND "
				+ "f.pid = "+ _pid +" AND f.username = u.username GROUP BY r.feedbackid ORDER BY "
				+ "AVG(r.rating) DESC " + xValue;

		System.out.println("Retreiving Feedback for POI...");
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);

			result = ("pID: "+ _pid +"<br> <br>");
			int count = -1;
			while (rs.next()) {
				count++;
				username = rs.getString("username");
				opinion = rs.getString("opinion");
				fid = rs.getInt("fid");
				score = rs.getInt("score");
				fbdate = rs.getDate("fbdate");

				result += ("Feedback ID: " + fid+ "<br>");
				result += ("From: " + username+ "<br>");
				result += ("Score: " + score+ "<br>");
				result += ("Date: " + fbdate.toString()+ "<br>");
				result += ("Opinion: <br><span style=\"margin-left:3em\"></span>" + opinion + "<br><br><br>");

			}
			if(count == -1){
				result = "No Feedback Found";
			}
			if (_pid != null) {
				isNotReal = false;
			} else {
				result = "No Feedback Exists for POI";
			}
		} catch (Exception e) {
			result = (e.getMessage() + "<br>");
			result += ("Error occured retreiving POI Feedback");
			isNotReal = true;
			return result;
		} finally {
			try {
				if (rs != null) {
					if (!rs.isClosed()) {
						rs.close();
					}
				}
			} catch (Exception e) {
				return result;
			}
		}
		return result;
	}

	public boolean rateFeedback(User user, String fid, String rating, String opinion, Connector con) {
		String sql = "INSERT INTO Ratings(username, feedbackid, rating, rdate, opinion) " + "VALUES (?, ?, ?, ?, ?)";
		System.out.println("Adding Feedback Rating to database...");
		try {
			PreparedStatement ps = con.con.prepareStatement(sql);
			ps.setString(1, user._username);
			ps.setInt(2, Integer.parseInt(fid));
			ps.setInt(3, Integer.parseInt(rating));
			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			ps.setTimestamp(4, date);
			ps.setString(5, opinion);

			ps.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error adding rating to database...");
			System.out.println(e.getMessage());
			return false;
		}
		System.out.println("Feedback Rating successfully added to database...");
		return true;
	}

	public ArrayList<String> getAllPOICategories(Statement stmt) {
		ArrayList<String> list = new ArrayList<String>();
		String sql = "SELECT category FROM POI GROUP BY category";
		System.out.println("Finding each category...");
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("category"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error occured retreiving categories");
			isNotReal = true;
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
		return list;
	}

	public String getXMostPopularPOIForEachCategory(String xValue, Statement stmt) {

		ArrayList<String> poiCategories = getAllPOICategories(stmt);
		String message = "";
		String limit= "";
		
		if(!xValue.equals("*")){
			limit = " LIMIT " + xValue;
		}

		System.out.println("Retreiving " + xValue + " most popular POI's for each category...");
		for (String s : poiCategories) {
			message += ("<br>Category: " + s + "<br>");
			message += ("------------------------<br>");

			String sql = "SELECT *, COUNT(*) AS viscount FROM POI NATURAL JOIN Visits " + "WHERE category ='" + s
					+ "' GROUP BY pid ORDER BY viscount DESC  " + limit;
			ResultSet rs = null;
			try {
				rs = stmt.executeQuery(sql);
				while (rs.next()) {

					if (rs.getString("pname") == null) {
						message += ("No feedback exists for this category<br><br>");
					} else {
						message += ("POI name:          " + rs.getString("pname") + "<br>");
						message += ("POI visit count:   " + rs.getInt("viscount")+ "<br>");
						message += ("POI id:            " + rs.getInt("pid")+ "<br>");
						message += ("POI year est.:     " + rs.getInt("year_est")+ "<br>");
						message += ("POI phone number:  " + rs.getString("phone_num")+ "<br>");
						message += ("POI url:           " + rs.getString("url")+ "<br>");
						message += ("POI price:         " + rs.getInt("price")+ "<br>");
						message += ("POI hours:         " + rs.getString("hours")+ "<br>");
						message += ("POI Address:       "+ "<br>");
						message += ("<span style=\"margin-left:2em\"></span>   street:         " + rs.getString("street")+ "<br>");
						message += ("<span style=\"margin-left:2em\"></span>   city:           " + rs.getString("city")+ "<br>");
						message += ("<span style=\"margin-left:2em\"></span>   state:          " + rs.getString("state")+ "<br>");
						message += ("<span style=\"margin-left:2em\"></span>   zipcode:        " + rs.getInt("zipcode") + "<br><br>");

					}
				}
				if (_pid != null) {
					isNotReal = false;
				}
			} catch (Exception e) {
				message = (e.getMessage());
					if(message.contains("Duplicate entry")){
						message = "Entry Already Exists";
					} else {
						message = ("Error occured retreiving visits for this category\n");
					}
				isNotReal = true;
				return message;
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
		return message;

	}

	public String getXMostExpensivePOIForEachCategory(String xValue, Statement stmt) {

		ArrayList<String> poiCategories = getAllPOICategories(stmt);
		String message = "";
		String limit= "";
		
		if(!xValue.equals("*")){
			limit = " LIMIT " + xValue;
		}

		System.out.println("Retreiving " + xValue + " most expensive POI's for each category...");
		for (String s : poiCategories) {
			message += ("<br>Category: " + s + "<br>");
			message += ("------------------------<br>");

			String sql = "Select *, AVG(totalcost/totalparty) AS avgcost "
					+ "FROM POI NATURAL JOIN (SELECT pid, SUM(cost) AS totalcost, "
					+ "SUM(partysize) AS totalparty FROM Visits) AS T " + "WHERE category ='" + s
					+ "' ORDER BY avgcost DESC " + limit;
			ResultSet rs = null;
			try {
				rs = stmt.executeQuery(sql);
				while (rs.next()) {

					if (rs.getString("pname") == null) {
						message += ("No feedback exists for this category<br><br>");
					} else {
						message += ("POI name:          " + rs.getString("pname") + "<br>");
						message += ("avg cost per person: $" + String.format("%.2f", rs.getFloat("avgcost"))+ "<br>");
						message += ("POI id:            " + rs.getInt("pid")+ "<br>");
						message += ("POI year est.:     " + rs.getInt("year_est")+ "<br>");
						message += ("POI phone number:  " + rs.getString("phone_num")+ "<br>");
						message += ("POI url:           " + rs.getString("url")+ "<br>");
						message += ("POI price listed:         " + rs.getInt("price")+ "<br>");
						message += ("POI hours:         " + rs.getString("hours")+ "<br>");
						message += ("POI Address:       "+ "<br>");
						message += ("<span style=\"margin-left:2em\"></span>   street:         " + rs.getString("street")+ "<br>");
						message += ("<span style=\"margin-left:2em\"></span>   city:           " + rs.getString("city")+ "<br>");
						message += ("<span style=\"margin-left:2em\"></span>   state:          " + rs.getString("state")+ "<br>");
						message += ("<span style=\"margin-left:2em\"></span>   zipcode:        " + rs.getInt("zipcode") + "<br><br>");
					}
				}
				if (_pid != null) {
					isNotReal = false;
				}
			} catch (Exception e) {
				message = (e.getMessage());
				if(message.contains("Duplicate entry")){
					message = "Entry Already Exists";
				} else {
					message = ("Error occured retreiving visits for this category\n");
				}	
				isNotReal = true;
				return message;
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
		return message;
	}

	public String getXHighestRatedPOIForEachCategory(String xValue, Statement stmt) {
		ArrayList<String> poiCategories = getAllPOICategories(stmt);
		String message = "";
		String limit= "";
		
		if(!xValue.equals("*")){
			limit = " LIMIT " + xValue;
		}
		
		System.out.println("Retreiving " + xValue + " highest Rated POI's for each category...");
		for (String s : poiCategories) {
			message += ("<br>Category: " + s + "<br>");
			message += ("------------------------<br>");
		
			String sql = "SELECT * FROM POI NATURAL JOIN (SELECT pid, AVG(score) AS avgscore "
					+ "FROM Feedback GROUP BY pid) As T WHERE category='" + s + "' " + "ORDER BY avgscore DESC "
					+ limit;
			ResultSet rs = null;
			try {
				rs = stmt.executeQuery(sql);
				while (rs.next()) {

					if (rs.getString("pname") == null) {
						message += ("No ratings exists for this category<br><br>");
					} else {
						message += ("POI name:          " + rs.getString("pname") + "<br>");
						message += ("avg rating:        " + String.format("%.2f", rs.getFloat("avgscore"))+ "<br>");
						message += ("POI id:            " + rs.getInt("pid")+ "<br>");
						message += ("POI year est.:     " + rs.getInt("year_est")+ "<br>");
						message += ("POI phone number:  " + rs.getString("phone_num")+ "<br>");
						message += ("POI url:           " + rs.getString("url")+ "<br>");
						message += ("POI price listed:         " + rs.getInt("price")+ "<br>");
						message += ("POI hours:         " + rs.getString("hours")+ "<br>");
						message += ("POI Address:       "+ "<br>");
						message += ("<span style=\"margin-left:2em\"></span>   street:         " + rs.getString("street")+ "<br>");
						message += ("<span style=\"margin-left:2em\"></span>   city:           " + rs.getString("city")+ "<br>");
						message += ("<span style=\"margin-left:2em\"></span>   state:          " + rs.getString("state")+ "<br>");
						message += ("<span style=\"margin-left:2em\"></span>   zipcode:        " + rs.getInt("zipcode") + "<br><br>");
					}
				}
				if (_pid != null) {
					isNotReal = false;
				}
			} catch (Exception e) {
				message = (e.getMessage());
				if(message.contains("Duplicate entry")){
					message = "Entry Already Exists";
				} else {
					message = ("Error occured retreiving visits for this category\n");
				}	
				isNotReal = true;
				return message;
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
		return message;
	}

	public String executeSearch(User user, int min, int max, String city, String state, String keywords, String category,
			String order, int sortType, Statement stmt) {
		String message = "";
		String sql = "";
		if (sortType == 1 || sortType == 2 || sortType == 7) {
			sql = "SELECT * FROM POI NATURAL JOIN Keywords NATURAL JOIN HasKeywords ";
		} else if (sortType == 3 || sortType == 4) {
			sql = "SELECT * FROM POI NATURAL JOIN Keywords NATURAL JOIN HasKeywords NATURAL JOIN "
					+ " (SELECT pid, AVG(score) AS fscore, fid FROM Feedback GROUP BY pid) AS T ";
		} else if (sortType == 5 || sortType == 6) {
			sql = " SELECT * FROM POI NATURAL JOIN Keywords NATURAL JOIN HasKeywords NATURAL JOIN "
					+ " (SELECT F.pid, AVG(F.score) AS fscore, F.fid, T.is_trusted FROM Feedback F NATURAL JOIN Trust T "
					+ " Where T.username1 = '" + user._username
					+ "' AND F.username = T.username2 AND is_trusted = 1 GROUP BY F.pid) AS T ";
		}

		String minSearch = "", maxSearch = "", citySearch = "", stateSearch = "", keywordSearch = "", categorySearch = "";
		if (min > -1) {
			minSearch = " price >= " + min + " AND";
		}
		if (max > -1) {
			maxSearch += " price <= " + max + " AND";
		}
		if (city != null && city.length() > 0) {
			citySearch += " city = '" + city + "' AND";
		} else {
			city = "";
		}
		if (state != null && state.length() > 0) {
			stateSearch += " state = '" + state + "' AND";
		} else {
			state = "";
		}
		if (category != null && category.length() > 0) {
			categorySearch += " category = '" + category + "' AND";
		} else {
			category = "";
		}
		
		if (keywords != null && keywords.length() > 0) {
			String[] list = keywords.split(" ");
			if (list.length > 1) {
				keywordSearch += " ( ";
				for (String s : list) {
					keywordSearch += " kword ='" + s + "' OR";
				}
				if (keywordSearch.endsWith("OR")) {
					keywordSearch = keywordSearch.substring(0, keywordSearch.length() - 2);
				}
				keywordSearch += " ) AND";

			} else {
				keywordSearch += "( kword ='" + list[0] + "' ) AND";

			}
		} else {
			keywords = "";
		}


		if (order == null || order.length() == 0) {
			order = "";
		}

		if (minSearch.equals("") && maxSearch.equals("") && citySearch.equals("") && stateSearch.equals("")
				&& keywords.equals("") && categorySearch.equals("")) {
			sql += "";
		} else {

			sql += " WHERE " + minSearch + maxSearch + citySearch + stateSearch + categorySearch + keywordSearch;
		}
		if (sql.endsWith("AND")) {
			sql = sql.substring(0, sql.length() - 3);
		}

		sql += " GROUP BY pid " + order + " ;";

		System.out.println("Searching for POI's that match your specifications.....");
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);

			message += ("<br>Results found for following search constraints:"+"<br><br>");
			if (min < 0) {
				message += ("  No min constraint<br>");

			} else {
				message += ("  Min price: <span style=\"margin-left:2em\"></span>" + min +"<br>");
			}
			if (max < 0) {
				message += ("  No max constraint"+"<br>");

			} else {
				message += ("  Max price: <span style=\"margin-left:2em\"></span>" + max+"<br>");
			}
			message += ("<span style=\"margin-left:2em\"></span>  City:<span style=\"margin-left:2em\"></span>" + city+"<br>");
			message += ("<span style=\"margin-left:2em\"></span>  State:<span style=\"margin-left:2em\"></span>" + state+"<br>");
			message += ("<span style=\"margin-left:2em\"></span>  Keywords:<span style=\"margin-left:2em\"></span>" + keywords+"<br>");
			message += ("<span style=\"margin-left:2em\"></span>  Category:<span style=\"margin-left:2em\"></span>" + category+"<br>");

			message += ("<br><br><br>");

			while (rs.next()) {
				if (sortType <= 6 && sortType >= 3) {
					message += ("avg feedback score:   " + String.format("%.2f", rs.getFloat("fscore")) +"<br>");
				}
				message += ("POI name:             " + rs.getString("pname")+"<br>");
				message += ("POI id:               " + rs.getInt("pid")+"<br>");
				message += ("POI year est.:        " + rs.getInt("year_est")+"<br>");
				message += ("POI phone number:     " + rs.getString("phone_num")+"<br>");
				message += ("POI url:              " + rs.getString("url")+"<br>");
				message += ("POI price listed:     " + rs.getInt("price")+"<br>");
				message += ("POI hours:            " + rs.getString("hours")+"<br>");
				message += ("POI Address:          "+"<br>");
				message += ("<span style=\"margin-left:2em\"></span>   street:            " + rs.getString("street")+"<br>");
				message += ("<span style=\"margin-left:2em\"></span>   city:              " + rs.getString("city")+"<br>");
				message += ("<span style=\"margin-left:2em\"></span>   state:             " + rs.getString("state")+"<br>");
				message += ("<span style=\"margin-left:2em\"></span>   zipcode:           " + rs.getInt("zipcode") + "<br><br><br>");

			}
			message += ("<br>");
		} catch (Exception e) {
			System.out.println(sql);
			System.out.println(e.getMessage());
			message = ("Error occured executing search");
			isNotReal = true;
			return message;
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
		return message;
	}

	public boolean addKeywordToPOI(String wid, Connector con) {
		String sql = "INSERT INTO HasKeywords(pid, wid) " + "VALUES (?, ?)";
		System.out.println("Adding Keyword to POI...");
		try {
			PreparedStatement ps = con.con.prepareStatement(sql);
			ps.setString(1, _pid);
			ps.setString(2, wid);
			ps.executeUpdate();
			System.out.println("Keyword added to POI");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

		return true;
	}

}
