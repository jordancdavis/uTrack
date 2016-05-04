package cs5530;

/**
 * Jordan Davis
 * u0670513
 * March 21, 2016
 * cs5530 - uTrack
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;
import java.sql.*;

public class utrackMain {

	public static boolean _isLoggedIn = false;
	public static boolean _isAdmin = false;
	public static User _currentUser;

	/**
	 * utrack main program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Connector con = null;
		String choice;
		int c = 0;
		int status = 0;
		try {
			// remember to replace the password
			con = new Connector();
			System.out.println("Database connection established");
			System.out.println("----------  Welcome to the uTrack System  ----------");

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				displayMenu();
				while ((choice = in.readLine()) == null && choice.length() == 0)
					;
				try {
					c = Integer.parseInt(choice);
				} catch (Exception e) {
					continue;
				}

				// selects correct int operations
				status = 0;
				if (!_isLoggedIn) {
					// not signed in options
					// will run untill user selects valid option
					while (status == 0) {
						status = initialOperations(con, in, c);
					}
				} else if (_isAdmin) {
					// admin controls options
					// will run untill user selects valid option
					while (status == 0) {
						status = adminOperations(con, in, c);
					}
				} else {
					// user controls
					// will run untill user selects valid option
					while (status == 0) {
						status = userOperations(con, in, c);
					}
				}

				// end program was selected
				if (status == -1) {
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Either connection error or query execution error!");
		} finally {
			if (con != null) {
				try {
					con.closeConnection();
					System.out.println("Database connection terminated");
				}

				catch (Exception e) {
					/* ignore close errors */ }
			}
		}
	}

	/**
	 * Main Display Menu
	 * 
	 * @param args
	 */
	public static void displayMenu() {
		System.out.println("**********  uTrack Menu  **********");

		// user is not logged in
		if (!_isLoggedIn) {
			System.out.println("1. register");
			System.out.println("2. login");
			System.out.println("3. exit");
		} else if (_isAdmin) {
			// admin logged in
			System.out.println("1. add a new POI");
			System.out.println("2. update existing POI");
			System.out.println("3. most trusted users");
			System.out.println("4. most useful users");
		    System.out.println("5. add keyword to db");
		    System.out.println("6. add keyword to POI");
			System.out.println("7. logout");
		} else {
			// user logged in
			System.out.println("1. record a visit to a POI");
			System.out.println("2. declare a favorite POI to visit");
			System.out.println("3. record feedback for POI");
			System.out.println("4. record usefullness of another users feedback");
			System.out.println("5. get the top most usefull feedback for a POI");
			System.out.println("6. trust / dis-trust another user");
			System.out.println("7. check degrees of separation with another user");
			System.out.println("8. browse POI's");
			System.out.println("9. view POI statistics");
			System.out.println("10. logout");
		}
		System.out.println("***********************************");
		System.out.println("please enter your choice:");

	}

	/**
	 * User will be prompted to select one of the following options: 1. register
	 * (allows new users to register and gathers information) 2. login (allows
	 * existing users to access their account) 3. exit (terminates the program)
	 * User will input value into the consol to select an option
	 * 
	 * @param con
	 * @param in
	 * @param input
	 * @return -1 if end program was selected, 0 if not valid input, 1 if input
	 *         was valid.
	 * @throws SQLException
	 * @throws IOException
	 */
	public static int initialOperations(Connector con, BufferedReader in, int input) throws SQLException, IOException {
		if (input < 1 | input > 3) { // bounds check
			return 0;
		}

		if (input == 1) { // register new user

			_currentUser = getNewUserInformation(in, con);
			if (_currentUser.registerNewUser(con)) {
				System.out.println("Registration Sucessful. WELCOME!");
				_isLoggedIn = true;
				if (_currentUser._isAdmin) {
					_isAdmin = true;
				}
			} else {
				System.out.println("Sorry, registration failed. Please try again.");
			}
		} else if (input == 2) { // user login
			loginUser(in, con);
		} else { // exit
			System.out.println("Remeber to pay us!");
			con.stmt.close();
			return -1;
		}

		return 1;
	}

	/**
	 * User will be prompted to select one of the following options: 1. add a
	 * new POI (allows admin to add a new POI to db) 2. update existing POI
	 * (allows admin to update a POI) 3. most trusted users (allows admin to get
	 * 'x' most trusted users) 4. most usefull users (allows admin to get 'x'
	 * most usefull users) 5. logout (ends the users session. returns to main
	 * menue.) User will input value into the consol to select an option
	 * 
	 * @param con
	 * @param in
	 * @param input
	 * @return 0 if not valid input, 1 if input was valid.
	 * @throws SQLException
	 * @throws IOException
	 */
	public static int adminOperations(Connector con, BufferedReader in, int input) throws SQLException, IOException {
		if (input < 1 | input > 7) { // bounds check
			return 0;
		}

		if (input == 1) { // add new POI

			POI poi = getNewPOIInformation(in);
			if (poi.addNewPOI(con)) {
				System.out.println("POI successfully added to database");
			} else {
				System.out.println("Sorry, POI was not added to the database. Please try again.");
			}
		} else if (input == 2) { // modify existing POI
			POI poi = new POI();
			while (poi.isNotReal) {
				poi = getExistingPOI(in, con);
			}
			String oldpname = poi._pname;
			String pid = poi._pid;
			System.out.println(oldpname);
			int selected = -1;
			while (selected == -1) {
				selected = displayPOIUpdate(in, poi);
			}
			String value;
			switch (selected) {
			case 1: // name
				while (true) {
					System.out.println("enter new name of POI: ");
					while ((value = in.readLine()) == null)
						;
					if (value.length() < 1 || value.length() > 50) {
						System.out.println("Please enter a valid POI name");
						value = null;
					} else {
						break;
					}
				}
				poi._pname = value;
				break;
			case 2: // category
				while (true) {
					System.out.println("enter a new category for the POI (separate by comma): ");
					while ((value = in.readLine()) == null)
						;
					if (value.length() < 1 || value.length() > 50) {
						System.out.println("Please enter a valid last name");
						value = null;
					} else {
						break;
					}
				}
				poi._category = value;
				break;
			case 3: // phone number
				while (true) {
					System.out.println("enter the new POI's phone number (0123456789): ");
					while ((value = in.readLine()) == null)
						;

					if (value.length() == 10 && isValidStringOfNumbers(value)) {
						break;
					} else {
						System.out.println("Please enter 10 digits");
						value = null;
					}
				}
				poi._phone_num = value;
				break;
			case 4: // price
				while (true) {
					System.out.println("enter POI's new average price (whole numbers): ");
					while ((value = in.readLine()) == null)
						;

					if (isValidStringOfNumbers(value)) {
						break;
					} else {
						System.out.println("Please enter numbers only");
						value = null;
					}
				}
				poi._price = value;
				break;
			case 5: // year established
				while (true) {
					System.out.println("enter POI's new year established (2016): ");
					while ((value = in.readLine()) == null)
						;

					if (value.length() == 4 && isValidStringOfNumbers(value)) {
						break;
					} else {
						System.out.println("Please enter numbers only");
						value = null;
					}
				}
				poi._year_est = value;
				break;
			case 6: // hours
				System.out.println("enter the POI's new hours in miliatary time(9:00 - 15:00): ");
				while ((value = in.readLine()) == null)
					;
				if (value.length() < 21)
					poi._hours = value;
				break;
			case 7: // url
				System.out.println("enter the POI new url:");
				while ((value = in.readLine()) == null)
					;
				if (value.length() < 50)
					poi._url = value;
				break;
			case 8: // city
				System.out.println("enter the POI's new city: ");
				while ((value = in.readLine()) == null)
					;
				if (value.length() < 20)
					poi._city = value;

				break;
			case 9: // state
				System.out.println("enter the POI's new state: ");
				while ((value = in.readLine()) == null)
					;
				if (value.length() < 20)
					poi._state = value;
				break;
			case 10: // street
				System.out.println("enter the POI's new street address: ");
				while ((value = in.readLine()) == null)
					;
				if (value.length() < 45)
					poi._street = value;

				break;
			case 11: // zipcode
				while (true) {
					System.out.println("enter POI's new zip code (12345): ");
					while ((value = in.readLine()) == null)
						;

					if (value.length() == 5 && isValidStringOfNumbers(value)) {
						break;
					} else {
						System.out.println("Please enter 5 digits");
						value = null;
					}
				}
				poi._zipcode = value;
				break;
			case 12: // all
				poi = getNewPOIInformation(in);
				break;
			case 13:
				break;
			default:
				poi = getNewPOIInformation(in);
				break;
			}

			if (selected != 13) {
				poi._pid = pid;
				if (poi.updatePOI(oldpname, con, con.stmt)) {
					System.out.println("POI successfully updated");
				} else {
					System.out.println("Sorry, POI was not updated. Please try again.");
				}
			} else {
				System.out.println("Update Cancelled");
			}

		} else if (input == 3) { // get x most trusted users
			String xValue;
			while (true) {
				System.out.println("Enter the number of top 'trusted' users you want to view: ");
				while ((xValue = in.readLine()) == null)
					;
				try {
					int score = Integer.parseInt(xValue);
					if (score > 0) {
						break;
					} else {
						System.out.println("Please enter a positive number");
					}
				} catch (Exception e) {
					System.out.println("Please enter a valid number");
				}
			}
			System.out.println(_currentUser.getXMostTrustedUsers(xValue, con.stmt));

		} else if (input == 4) { // get x most usefull users
			String xValue;
			while (true) {
				System.out.println("Enter the number of top 'useful' users you want to view: ");
				while ((xValue = in.readLine()) == null)
					;
				try {
					int score = Integer.parseInt(xValue);
					if (score > 0) {
						break;
					} else {
						System.out.println("Please enter a positive number");
					}
				} catch (Exception e) {
					System.out.println("Please enter a valid number");
				}
			}
			System.out.println(_currentUser.getXMostUsefulUsers(xValue, con.stmt));
		}else if(input == 5){
			String kword;
			while (true) {
				System.out.println("Enter the keyword you want to add: ");
				while ((kword = in.readLine()) == null);
					if(kword.length() > 0)
						break;
				
			}
			
			boolean empty = addKeyword(kword, con);
			
		} else if(input == 6){
			POI poi = new POI();
			while (poi.isNotReal) {
				poi = getExistingPOI(in, con);
				if (poi.isNotReal) {
					System.out.println("POI does not exist. please try again.");
				}
			}
			
			String wid;
			while (true) {
				System.out.println("Enter the keyword id you want to add: ");
				while ((wid = in.readLine()) == null);
					if(wid.length() > 0 && isValidStringOfNumbers(wid)){
						break;
					}
				
			}
			
			poi.addKeywordToPOI(wid, con);
			
		}
		else {
			// logout
			System.out.println("\nGoodbye " + _currentUser._username);
			_isLoggedIn = false;
			_isAdmin = false;
			_currentUser.resetUser();
		}

		return 1;
	}



	// -1 break, 0 false, 1 true
	/**
	 * 1. add a visit to a POI (allows user to add a visit to an existing POI)
	 * 2. declare a favorite POI to visit (allows uer to add a favorite POI) 3.
	 * record feedback for POI (allow user to give a score and his opinion of a
	 * POI) 4. record usefullness of another users feedback (allow users to rate
	 * feedback) 5.get top most usefull feedback for a POI 6. trust / dis-trust
	 * another user 7. 8. 9. 10. logout (logs the user out and returns to main
	 * menu)
	 * 
	 * @param con
	 * @param in
	 * @param input
	 * @return -1 if end program was selected, 0 if not valid input, 1 if input
	 *         was valid.
	 * @throws SQLException
	 * @throws IOException
	 */
	public static int userOperations(Connector con, BufferedReader in, int input) throws SQLException, IOException {
		if (input < 1 | input > 10) { // bounds check
			return 0;
		}

		if (input == 1) { // Record visit
			String partysize, cost, dob;
			Visits visit = new Visits();
			POI poi = new POI();
			while (poi.isNotReal) {
				poi = getExistingPOI(in, con);
				if (poi.isNotReal) {
					System.out.println("POI does not exist. please try again.");
				}
			}

			System.out.println("enter the size of your party: ");
			while ((partysize = in.readLine()) == null)
				;

			System.out.println("enter the avg. cost per person (whole number): ");
			while ((cost = in.readLine()) == null)
				;

			while (true) {
				System.out.println("enter the date of your visit (mm/dd/yyyy):");
				while ((dob = in.readLine()) == null)
					;

				try {
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
					Date birthdate = (Date) formatter.parse(dob);
					visit._visit_date = new java.sql.Date(birthdate.getTime());
					;
					break;
				} catch (Exception e) {
					System.out.println("invalid date format. please try again");
				}
			}

			visit._pname = poi._pname;
			visit._cost = cost;
			visit._partysize = partysize;
			visit._pid = poi._pid;
			visit._username = _currentUser._username;

			visit.registerNewVisit(con);
			visit.getSuggestedVisits(_currentUser, poi._pid, con);

		} else if (input == 2) { // Add Favorite POI to visit
			POI poi = new POI();
			while (poi.isNotReal) {
				poi = getExistingPOI(in, con);
				if (poi.isNotReal) {
					System.out.println("POI does not exist. please try again.");
				}
			}
			System.out.println(_currentUser.addFavoritePOI(poi._pname, poi._pid, con));

		} else if (input == 3) { // record feedback\
			String opinion, tmpScore;
			int score;
			POI poi = new POI();
			while (poi.isNotReal) {
				poi = getExistingPOI(in, con);
				if (poi.isNotReal) {
					System.out.println("POI does not exist. please try again.");
				}
			}

			while (true) {
				System.out.println("Please enter a rating (0 = terrible, 10 = excellent): ");
				while ((tmpScore = in.readLine()) == null)
					;
				try {
					score = Integer.parseInt(tmpScore);
					if (score <= 10 && score >= 0) {
						break;
					} else {
						System.out.println("Please enter a single number from 0 to 10");
					}
				} catch (Exception e) {
					System.out.println("Please enter a single number from 0 to 10");
				}

			}

			System.out.println("Feel free to leave your opinion (max 140 characters): ");
			while ((opinion = in.readLine()) == null)
				;

			System.out.println(_currentUser.addFeedbackToPOI(poi._pname, poi._pid, score, opinion, con));

		} else if (input == 4) { // usefulness rating for a feedback
			POI poi = new POI();
			ArrayList<String> fList = new ArrayList<String>();
			String fid, rating, opinion;
			while (poi.isNotReal) {
				poi = getExistingPOI(in, con);
				if (poi.isNotReal) {
					System.out.println("POI does not exist. please try again.");
				}
			}
			fList = poi.getFeedbackForPOI(_currentUser, con.stmt);
			while (true) {
				System.out.println("enter the fid for the feedback you would like to rate (look above):");
				while ((fid = in.readLine()) == null)
					;

				if (!isValidStringOfNumbers(fid)) {
					System.out.println("please enter a valid fid. (digits only)");
					fid = null;
					continue;
				}
				if (!fList.contains(fid)) {
					while (true) {
						System.out.println("Please enter a rating: ");
						System.out.println("0. useless");
						System.out.println("1. usefull");
						System.out.println("2. very useful");

						while ((rating = in.readLine()) == null)
							;
						try {
							int rat = Integer.parseInt(rating);
							if (rat <= 2 && rat >= 0) {
								break;
							} else {
								System.out.println("Please enter 0, 1, or 2");
							}
						} catch (Exception e) {
							System.out.println("Please enter 0, 1, or 2");
						}
					}

					System.out.println("Feel free to leave your opinion (max 140 characters): ");
					while ((opinion = in.readLine()) == null)
						;

					if (poi.rateFeedback(_currentUser, fid, rating, opinion, con)) {
						break;
					} else {
						System.out.println("Error: please try again");
					}
				} else {
					System.out.println("You cannot rate your own feedback. please try again.");
				}

			}

		} else if (input == 5) {
			POI poi = new POI();
			String xValue;
			while (poi.isNotReal) {
				poi = getExistingPOI(in, con);
				if (poi.isNotReal) {
					System.out.println("POI does not exist. please try again.");
				}
			}

			while (true) {
				System.out.println("Enter the number of top 'useful' users you want to view: ");
				while ((xValue = in.readLine()) == null)
					;
				try {
					int score = Integer.parseInt(xValue);
					if (score > 0) {
						break;
					} else {
						System.out.println("Please enter a positive number");
					}
				} catch (Exception e) {
					System.out.println("Please enter a valid number");
				}
			}

			System.out.println(poi.getTopFeedbackForPOI(xValue, con.stmt));
		}

		else if (input == 6) { // ???
								// trust / distrust other users
			String trusted;
			String value;
			int score = -1;
			User otherUser = new User();
			while (true) {
				System.out.println("enter the username for whom you'd like to trust / dis-trust:");
				while ((trusted = in.readLine()) == null)
					;

				if (otherUser.getUser(trusted, con.stmt)) {
					System.out.println("user found");
					break;
				} else {
					System.out.println("user does not exist. please try again");
				}
			}

			while (true) {
				System.out.println("Do you wish to trust or dis-trust " + otherUser._username + " ?");
				System.out.println("0. Distrust");
				System.out.println("1. Trust");
				while ((value = in.readLine()) == null)
					;

				try {
					score = Integer.parseInt(value);
				} catch (Exception e) {
					System.out.println("Invalid input.");
				}

				if (score == 1 || score == 0) {
					System.out.println(_currentUser.addUserRelationship(otherUser, score, con));
					break;
				} else {
					System.out.println("Invalid input.");
				}
			}

		} else if (input == 7) { // two degrees of separation
			String user;
			User otherUser = new User();
			while (true) {
				System.out.println("enter the username for the user you wish to compare with: ");
				while ((user = in.readLine()) == null)
					;
				if (otherUser.getUser(user, con.stmt)) {
					break;
				} else {
					System.out.println("user does not exist. please try again");
				}
			}

			if (_currentUser.checkFirstDegreeOfSeparation(otherUser, con.stmt)) {
				System.out.println("You and " + otherUser._username + " are 1-degree away\n");
			} else if (_currentUser.checkSecondDegreeOfSeparation(otherUser, con.stmt)) {
				System.out.println("You and " + otherUser._username + " are 2-degrees away\n");
			} else {
				System.out.println("You and " + otherUser._username + " are completely separated\n");
			}

		} else if (input == 8) { // browse POI's

			Browse(in, con);

		} else if (input == 9) {
			// view POI statistics
			POI poi = new POI();
			while (true) {
				int selection = -1;
				while (selection == -1) {
					selection = displayPOIStatisticsMenu(in);
				}

				if (selection == 1) {
					// most popular POI's by category
					String xValue;
					while (true) {
						System.out.println("enter the number of most popular POI's you wish to view per category: ");
						while ((xValue = in.readLine()) == null)
							;
						try {
							int score = Integer.parseInt(xValue);
							if (score > 0) {
								break;
							} else {
								System.out.println("Please enter a positive number");
							}
						} catch (Exception e) {
							System.out.println("Please enter a valid number");
						}
					}
					System.out.println(poi.getXMostPopularPOIForEachCategory(xValue, con.stmt));
				} else if (selection == 2) {
					// most expensive POI's by category
					String xValue;
					while (true) {
						System.out.println("enter the number of most expensive POI's you wish to view per category: ");
						while ((xValue = in.readLine()) == null)
							;
						try {
							int score = Integer.parseInt(xValue);
							if (score > 0) {
								break;
							} else {
								System.out.println("Please enter a positive number");
							}
						} catch (Exception e) {
							System.out.println("Please enter a valid number");
						}
					}
					System.out.println(poi.getXMostExpensivePOIForEachCategory(xValue, con.stmt));
				} else if (selection == 3) {
					// highly rated POI's by category
					String xValue;
					while (true) {
						System.out.println("enter the number of highest rated POI's you wish to view per category: ");
						while ((xValue = in.readLine()) == null)
							;
						try {
							int score = Integer.parseInt(xValue);
							if (score > 0) {
								break;
							} else {
								System.out.println("Please enter a positive number");
							}
						} catch (Exception e) {
							System.out.println("Please enter a valid number");
						}
					}
					System.out.println(poi.getXHighestRatedPOIForEachCategory(xValue, con.stmt));
				} else {
					System.out.println("Returning to main menu..");
					break;
				}

			}
		} else { // logout
			System.out.println("\nGoodbye " + _currentUser._username);
			_isLoggedIn = false;
			_isAdmin = false;
			_currentUser.resetUser();
		}

		return 1;

	}

	/**
	 * Helper method to get New User information when registering
	 * 
	 * @param in
	 * @param con
	 * @return
	 * @throws IOException
	 */
	public static User getNewUserInformation(BufferedReader in, Connector con) throws IOException {
		// info needed to register
		String firstname, lastname, password, username, gender, dob, email, phone_num, city, state, street, vpassword,
				zipcode;
		boolean isAdmin = false;
		User user = new User();

		while (true) {
			System.out.println("enter your first name: ");
			while ((firstname = in.readLine()) == null)
				;
			if (firstname.length() < 1 || firstname.length() > 20) {
				System.out.println("Please enter a valid first name");
				firstname = null;
			} else {
				break;
			}
		}
		user._firstname = firstname;

		while (true) {
			System.out.println("enter your last name: ");
			while ((lastname = in.readLine()) == null)
				;
			if (lastname.length() < 1 || lastname.length() > 20) {
				System.out.println("Please enter a valid last name");
				lastname = null;
			} else {
				break;
			}
		}
		user._lastname = lastname;

		while (true) {
			System.out.println("create a username: ");
			while ((username = in.readLine()) == null)
				;

			user.checkUsernameAvailability(username, in, con.stmt);

			if (username.length() < 1 || username.length() > 30) {
				System.out.println("Sorry, username must be atleast 1 character and no more than 30");
				username = null;
			} else {
				if (!user.isTaken) {
					break;
				}
			}
		}
		user._username = username;

		// select and verrify password
		while (true) {
			System.out.println("enter a password (must contain atleast 6 characters):");
			while ((password = in.readLine()) == null)
				;

			System.out.println("verrify password:");
			while ((vpassword = in.readLine()) == null || vpassword.length() > 30)
				;

			if ((vpassword.equals(password) && vpassword.length() >= 6) && password.length() <= 30) {
				if (password.equals("booyah!99")) {
					isAdmin = true;
				}
				break;
			} else {
				System.out.println("varrification failed, try again.");
				vpassword = null;
				password = null;
			}
		}
		user._password = password;
		user._isAdmin = isAdmin;

		while (true) {
			System.out.println("enter your gender (M or F):");
			while ((gender = in.readLine()) == null)
				;

			if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F")) {
				break;
			} else {
				System.out.println("please enter M or F");
				gender = null;
			}
		}
		user._gender = gender;

		while (true) {
			System.out.println("enter your date of birth (mm/dd/yyyy):");
			while ((dob = in.readLine()) == null)
				;

			try {
				DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date birthdate = (Date) formatter.parse(dob);
				user._dob = new java.sql.Date(birthdate.getTime());
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("invalid date format. please try again.");
			}
		}

		System.out.println("enter your email address:");
		while ((email = in.readLine()) == null)
			;
		if (email.length() < 80) {
			user._email = email;
		} else {
			user._email = "";
		}

		while (true) {
			System.out.println("enter your phone number (0123456789): ");
			while ((phone_num = in.readLine()) == null)
				;

			if (phone_num.length() == 10 && isValidStringOfNumbers(phone_num)) {
				break;
			} else {
				System.out.println("Please enter 10 digits");
				phone_num = null;
			}
		}
		user._phone_num = phone_num;

		System.out.println("enter your city: ");
		while ((city = in.readLine()) == null)
			;
		if (city.length() <= 20)
			user._city = city;

		System.out.println("enter your state: ");
		while ((state = in.readLine()) == null)
			;
		if (state.length() <= 20)
			user._state = state;

		System.out.println("enter your street address: ");
		while ((street = in.readLine()) == null)
			;
		if (street.length() <= 45)
			user._street = street;

		while (true) {
			System.out.println("enter your zip code (12345): ");
			while ((zipcode = in.readLine()) == null)
				;

			if (zipcode.length() == 5 && isValidStringOfNumbers(zipcode)) {
				break;
			} else {
				System.out.println("Please enter 5 digits");
				zipcode = null;
			}
		}
		user._zipcode = zipcode;

		return user;
	}

	/**
	 * helper method prompt admit to enter new POI information
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static POI getNewPOIInformation(BufferedReader in) throws IOException {
		// info needed to register
		String pname, url, phone_num, price, year_est, hours, category, city, state, street, zipcode;
		POI poi = new POI();

		while (true) {
			System.out.println("enter name of POI: ");
			while ((pname = in.readLine()) == null)
				;
			if (pname.length() < 1 || pname.length() > 50) {
				System.out.println("Please enter a valid POI name");
				pname = null;
			} else {
				break;
			}
		}
		poi._pname = pname;

		while (true) {
			System.out.println("enter a category for the POI (separate by comma): ");
			while ((category = in.readLine()) == null)
				;
			if (category.length() < 1 || category.length() > 50) {
				System.out.println("Please enter a category");
				category = null;
			} else {
				break;
			}
		}
		poi._category = category;

		while (true) {
			System.out.println("enter POI's year established (2016): ");
			while ((year_est = in.readLine()) == null)
				;

			if (year_est.length() == 4 && isValidStringOfNumbers(year_est)) {
				break;
			} else {
				System.out.println("Please enter numbers only");
				year_est = null;
			}
		}
		poi._year_est = year_est;

		while (true) {
			System.out.println("enter POI's average price (whole numbers): ");
			while ((price = in.readLine()) == null)
				;

			if (isValidStringOfNumbers(price)) {
				break;
			} else {
				System.out.println("Please enter numbers only");
				price = null;
			}
		}
		poi._price = price;

		System.out.println("enter the POI's hours in miliatary time(9:00 - 15:00): ");
		while ((hours = in.readLine()) == null)
			;
		if (hours.length() < 21)
			poi._hours = hours;

		System.out.println("enter the POI url:");
		while ((url = in.readLine()) == null)
			;
		if (url.length() < 50)
			poi._url = url;

		while (true) {
			System.out.println("enter the POI's phone number (0123456789): ");
			while ((phone_num = in.readLine()) == null)
				;

			if (phone_num.length() == 10 && isValidStringOfNumbers(phone_num)) {
				break;
			} else {
				System.out.println("Please enter 10 digits");
				phone_num = null;
			}
		}
		poi._phone_num = phone_num;

		System.out.println("enter the POI's city: ");
		while ((city = in.readLine()) == null)
			;
		if (city.length() < 20)
			poi._city = city;

		System.out.println("enter the POI's state: ");
		while ((state = in.readLine()) == null)
			;
		if (state.length() < 20)
			poi._state = state;

		System.out.println("enter the POI's street address: ");
		while ((street = in.readLine()) == null)
			;
		if (street.length() < 45)
			poi._street = street;

		while (true) {
			System.out.println("enter POI's zip code (12345): ");
			while ((zipcode = in.readLine()) == null)
				;

			if (zipcode.length() == 5 && isValidStringOfNumbers(zipcode)) {
				break;
			} else {
				System.out.println("Please enter 5 digits");
				zipcode = null;
			}
		}
		poi._zipcode = zipcode;
				
		return poi;
	}

	/**
	 * Helper method to log in a user
	 * 
	 * @param in
	 * @param con
	 * @throws IOException
	 */
	public static void loginUser(BufferedReader in, Connector con) throws IOException {
		String username;
		String password;

		System.out.println("username:");
		while ((username = in.readLine()) == null)
			;
		System.out.println("password:");
		while ((password = in.readLine()) == null)
			;

		_currentUser = new User();
		if (_currentUser.login(username, password, con.stmt)) {
			_isLoggedIn = true;
			_isAdmin = _currentUser._isAdmin;
			System.out.println("\nWelcome " + username);
		} else {
			username = null;
			password = null;
			System.out.println("Invalid username or password");
		}
	}

	/**
	 * Checks and returns the existing POI
	 * 
	 * @param in
	 * @param con
	 * @return
	 * @throws IOException
	 */
	public static POI getExistingPOI(BufferedReader in, Connector con) throws IOException {
		String pname;
		while (true) {
			System.out.println("enter the name of the POI: ");
			while ((pname = in.readLine()) == null)
				;
			if (pname.length() < 1 || pname.length() > 50) {
				System.out.println("Please enter a valid POI name");
				pname = null;
			} else {
				break;
			}
		}
		POI poi = new POI();
		poi.selectExistingPOI(pname, con.stmt);
		return poi;

	}

	/**
	 * helper to verrify input strings are all valid ints
	 * 
	 * @param numbers
	 * @return t/f
	 */
	public static boolean isValidStringOfNumbers(String numbers) {
		for (int i = 0; i < numbers.length(); i++) {
			int c = numbers.charAt(i) - 48;
			if (c < 0 || c > 9) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Displays update POI options to the user
	 * 
	 * @param in
	 * @param poi
	 * @return
	 * @throws IOException
	 */
	public static int displayPOIUpdate(BufferedReader in, POI poi) throws IOException {
		System.out.println("Please select what you wish to update: ");
		System.out.println("1. Name:        " + poi._pname);
		System.out.println("2. Category:    " + poi._category);
		System.out.println("3. Phone #:     " + poi._phone_num);
		System.out.println("4. Price:       " + poi._price);
		System.out.println("5. Year Est.:   " + poi._year_est);
		System.out.println("6. Hours:       " + poi._hours);
		System.out.println("7. Url:         " + poi._url);
		System.out.println("8. City:        " + poi._city);
		System.out.println("9. State:       " + poi._state);
		System.out.println("10. Street:     " + poi._street);
		System.out.println("11. Zipcode:    " + poi._zipcode);
		System.out.println("12. Update All");
		System.out.println("13. Cancel");

		int c;
		String choice;
		while ((choice = in.readLine()) == null)
			;
		try {
			c = Integer.parseInt(choice);
		} catch (Exception e) {
			return -1;
		}
		if (c < 1 || c > 13) {
			return -1;
		}
		return c;
	}

	public static int displayPOIStatisticsMenu(BufferedReader in) throws IOException {
		System.out.println("Please select an option: ");
		System.out.println("1. most popular POI's for each category");
		System.out.println("2. most expensive POI's for each category");
		System.out.println("3. highest rated POI's for each category");
		System.out.println("4. Return to Main Menu");

		int c;
		String choice;
		while ((choice = in.readLine()) == null)
			;
		try {
			c = Integer.parseInt(choice);
		} catch (Exception e) {
			return -1;
		}
		if (c < 1 || c > 4) {
			return -1;
		}
		return c;
	}

	public static void Browse(BufferedReader in, Connector con) throws IOException {
		String entered = null, minprice = null, maxprice = null, city = null, state = null, category = null,
				keywords = null, order = null;
		int min = -1, max = -1;
		int enteredInput = -1;
		boolean dontExit = true;
		POI poi = new POI();
		System.out.println("Begin your search by adding one of the following:");
		while (dontExit) {
			min = -1;
			max = -1;
			System.out.println("1. add price range    2. add address    3. select keywords   4. select category");
			System.out.println("5. execute search     6. cancel/exit search");
			while ((entered = in.readLine()) == null)
				;
			try {
				enteredInput = Integer.parseInt(entered);
			} catch (Exception e) {
				System.out.println("Invalid input.");
			}
			if (enteredInput > 0 && enteredInput < 7) {
				switch (enteredInput) {
				case 1: { // add price range
					System.out.println("choose an option:");
					System.out.println("1. enter only a minimum price constraint");
					System.out.println("2. enter only maximum price constraint");
					System.out.println("3. enter both");
					System.out.println("4. remove price constrain");
					String sss;
					int option = -1;
					while (true) {
						while ((sss = in.readLine()) == null)
							;
						try {
							if (isValidStringOfNumbers(sss)) {
								option = Integer.parseInt(sss);
							} else {
								System.out.println("Invalid input.");
							}
						} catch (Exception e) {
							System.out.println("Invalid input.");
						}
						if (option > 0 && option < 5) {
							switch (option) {
							case 1: {
								min = -1;
								max = -1;
								while (true) {
									System.out.println("enter minimum price constraint: ");
									while ((minprice = in.readLine()) == null)
										;
									try {
										if (isValidStringOfNumbers(minprice)) {
											min = Integer.parseInt(minprice);
											if (min >= 0) {
												break;
											}
										} else {
											System.out.println("Invalid input. please enter a whole number.");
										}
									} catch (Exception e) {
										System.out.println("Invalid input. please enter a whole number.");
									}
								}
								break;
							}
							case 2: {
								min = -1;
								max = -1;
								while (true) {
									System.out.println("enter maximum price constraint: ");
									while ((maxprice = in.readLine()) == null)
										;
									try {
										if (isValidStringOfNumbers(maxprice)) {
											max = Integer.parseInt(maxprice);
											if (max >= 0) {
												break;
											}
										} else {
											System.out.println("Invalid input. please enter a whole number");
										}
									} catch (Exception e) {
										System.out.println("Invalid input. please enter a whole number");
									}
								}
								break;
							}
							case 3: {
								min = -1;
								max = -1;
								minprice = null;
								maxprice = null;
								while (true) {
									System.out.println("enter minimum price constraint: ");
									while ((minprice = in.readLine()) == null)
										;
									try {
										min = Integer.parseInt(minprice);
										if (min >= 0) {
											break;
										} else {
											System.out.println("Invalid input. please enter a whole number.");
										}
									} catch (Exception e) {
										System.out.println("Invalid input. plese enter a whole number.");
									}
								}

								while (true) {
									System.out.println("enter maximum price constraint: ");
									while ((maxprice = in.readLine()) == null)
										;
									try {
										max = Integer.parseInt(maxprice);
										if (max >= 0) {
											break;

										} else {
											System.out.println("Invalid input. please enter a whole number.");
										}
									} catch (Exception e) {
										System.out.println("Invalid input. please enter a whole number.");
									}
								}
								break;
							}
							case 4: {
								min = -1;
								max = -1;
								minprice = "";
								maxprice = "";
								break;
							}
							default: {
								break;
							}
							}// end inner switch
							break;
						} else {
							System.out.println("Invalid input. please try again.");
						}
					}
					if (min < 0 && max < 0) {
						System.out.println(
								"price range removed from search. narrow down your search more or press 5 to execute.\n");

					} else {
						System.out.println(
								"price range added to search. narrow down your search more or press 5 to execute.\n");

					}
					break;
				}
				case 2: { // add address
					System.out.println("choose an option:");
					System.out.println("1. search only for a city");
					System.out.println("2. search only for a state");
					System.out.println("3. search for both");
					System.out.println("4. remove both");

					String sss;
					int option = -1;
					while (true) {
						while ((sss = in.readLine()) == null)
							;
						try {
							option = Integer.parseInt(sss);

						} catch (Exception e) {
							System.out.println("Invalid input.");
						}
						if (option > 0 && option < 5) {
							switch (option) {
							case 1: {
								while (true) {
									System.out.println("enter a city: ");
									while ((city = in.readLine()) == null)
										;
									if (city.length() > 0) {
										break;
									} else {
										System.out.println("invalid input. try again. ");

									}
								}
								break;
							}
							case 2: {
								while (true) {
									System.out.println("enter a state: ");
									while ((state = in.readLine()) == null)
										;
									if (state.length() > 0) {
										break;
									} else {
										System.out.println("invalid input. try again. ");

									}
								}
								break;
							}
							case 3: {
								while (true) {
									System.out.println("enter a city: ");
									while ((city = in.readLine()) == null)
										;
									if (city.length() > 0) {
										break;
									} else {
										System.out.println("invalid input. try again. ");

									}
								}

								while (true) {
									System.out.println("enter a state: ");
									while ((state = in.readLine()) == null)
										;
									if (state.length() > 0) {
										break;
									} else {
										System.out.println("invalid input. try again. ");

									}
								}
								break;
							}
							case 4: {
								city = "";
								state = "";
								break;
							}
							default: {
								break;
							}
							}// end inner switch
							break;
						} else {
							System.out.println("Invalid input.");
						}
					}
					if (city == null && state == null) {
						System.out.println(
								"address removed from search. narrow down your search more or press 5 to execute.\n");
					} else {
						System.out.println(
								"address added to search. narrow down your search more or press 5 to execute.\n");
					}
					break;
				}
				case 3: { // keywords
					while (true) {
						System.out.println("enter keywords (separated by spaces): ");
						while ((keywords = in.readLine()) == null)
							;
						if (keywords.length() > 0) {
							break;
						} else {
							System.out.println("invalid input. try again. ");
						}
					}
					System.out
							.println("keywords added to search. narrow down your search more or press 5 to execute.\n");
					break;
				}
				case 4: { // categories
					while (true) {
						System.out.println("enter a category: ");
						while ((category = in.readLine()) == null)
							;
						if (category.length() > 0) {
							break;
						} else {
							System.out.println("invalid input. try again. ");
						}
					}
					System.out
							.println("category added to search. narrow down your search more or press 5 to execute.\n");
					break;
				}
				case 5: { // execute

					String sort = null;
					int sortInput = -1;
					System.out.println("Sort results by:");
					System.out.println("  1. price ascending");
					System.out.println("  2. price descending");
					System.out.println("  3. avg feedback score ascending");
					System.out.println("  4. avg feedback score descending");
					System.out.println("  5. avg score of trusted user feedbacks ascending");
					System.out.println("  6. avg score of trusted user feedbacks descending");
					System.out.println("  7. no specific order");

					while ((sort = in.readLine()) == null)
						;
					try {
						sortInput = Integer.parseInt(sort);
					} catch (Exception e) {
						System.out.println("Invalid input.");
					}
					if (sortInput > 0 && enteredInput < 8) {
						switch (sortInput) {
						case 1: {
							order = " ORDER BY price ASC ";
							break;
						}
						case 2: {
							order = " ORDER BY price DESC ";
							break;
						}
						case 3: {
							order = " ORDER BY fscore ASC ";
							break;
						}
						case 4: {
							order = " ORDER BY fscore DESC ";
							break;
						}
						case 5: {
							order = " ORDER BY fscore DESC ";
							break;
						}
						case 6: {
							order = " ORDER BY fscore DESC ";
							break;
						}
						case 7: {
							order = " ";
							break;
						}
						default: {
							order = "";
						}
							break;
						}
					}

					poi.executeSearch(_currentUser, min, max, city, state, keywords, category, order, sortInput,con.stmt);
					min = -1;
					max = -1;
					city = null;
					state = null;
					keywords = null;
					category = null;
					break;
				}
				case 6: { // exit
					dontExit = false;
					break;
				}
				default: {
					break;
				}
				}
			} else {
				entered = null;
			}
		}

	}
	
	public static boolean addKeyword(String kword, Connector con) {
		String sql = "INSERT INTO Keywords(kword) VALUES (?)";
		System.out.println("Adding Keyword to database...");
		try {
			PreparedStatement ps = con.con.prepareStatement(sql);
			ps.setString(1, kword);
			ps.executeUpdate();
			System.out.println("Keyword added to database");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}	
		return true;
	}

}
