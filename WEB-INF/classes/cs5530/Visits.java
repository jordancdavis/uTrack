package cs5530;

/**
 * Jordan Davis
 * u0670513
 * March 21, 2016
 * cs5530 - uTrack
 */

import java.io.BufferedReader;
import java.sql.*;

public class Visits {

	public String _username, _pid, _cost, _partysize;

	public Date _visit_date;

	public String _pname;

	/**
	 * adds a new user to the given database connection
	 * 
	 * @param con
	 * @return true if success, false if error occured
	 */
	public boolean registerNewVisit(Connector con) {
		String sql = "INSERT INTO Visits(username, pid, visit_date, cost, partySize) " + "VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = con.con.prepareStatement(sql);
			ps.setString(1, _username);
			ps.setInt(2, Integer.parseInt(_pid));
			ps.setDate(3, _visit_date);
			ps.setInt(4, Integer.parseInt(_cost));
			ps.setInt(5, Integer.parseInt(_partysize));	
			ps.executeUpdate();


		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

		return true;
	}

	@SuppressWarnings("finally")
	public String getSuggestedVisits(User currentUser, String pid, Connector con) {
		String suggest = "";
		String sql = "SELECT *, SUM(num_visits) AS cxt FROM (SELECT username, pid, COUNT(*) num_visits "
				+ "FROM Visits GROUP BY username, pid) AS T NATURAL JOIN " + "(SELECT * FROM POI NATURAL JOIN "
				+ "(SELECT DISTINCT pid FROM " + "(SELECT username FROM Visits " + "WHERE pid = " + pid
				+ " AND username != '" + currentUser._username + "') " + "AS samePOIvisit NATURAL JOIN "
				+ "Visits WHERE pid != " + pid + ") " + "otherPOI) AS T2 " + "GROUP BY pid ORDER BY cxt DESC ";
		suggest += ("Here are some Suggested POI's based upon your recent visit: <br><br>");
		suggest += ("************************************"+ "<br>");
		ResultSet rs = null;
		try {
			rs = con.stmt.executeQuery(sql);
			int count = 0;
			while (rs.next()) {
				count++;
				if (rs.getString("pname") == null) {
					suggest += ("Sorry, No suggested POI's <br>");
				} else {
					count++;
					suggest += ("similar user visit count:  " + rs.getInt("cxt") + "<br>");
					suggest += ("POI id:               " + rs.getInt("pid")+ "<br>");
					suggest += ("POI name:             " + rs.getString("pname")+ "<br>");
					suggest += ("POI year est.:        " + rs.getInt("year_est")+ "<br>");
					suggest += ("POI phone number:     " + rs.getString("phone_num")+ "<br>");
					suggest += ("POI url:              " + rs.getString("url")+ "<br>");
					suggest += ("POI price listed:     " + rs.getInt("price")+ "<br>");
					suggest += ("POI hours:            " + rs.getString("hours")+ "<br>");
					suggest += ("POI Address:          <br>");
					suggest += ("   street:            " + rs.getString("street")+ "<br>");
					suggest += ("   city:              " + rs.getString("city")+ "<br>");
					suggest += ("   state:             " + rs.getString("state")+ "<br>");
					suggest += ("   zipcode:           " + rs.getInt("zipcode")+ "<br><br>");
				}
			}
			if(count == 0){
				suggest += ("<br>Sorry, No suggested POI's <br><br>");
			}
			suggest += ("************************************"+ "<br>");
			suggest += ("Be sure to give them a visit!" + "<br>");

		} catch (Exception e) {
			suggest = (e.getMessage() + "<br>");
			suggest += ("Error fetching suggested POI's <br>");
		} finally {
			try {
				if (rs != null) {
					if (!rs.isClosed()) {
						rs.close();
					}
				}
			} catch (Exception e) {
				return suggest;
			}
			return suggest;
		}
	}

}