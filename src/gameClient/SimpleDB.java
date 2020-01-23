	package gameClient;
	import utils.StdDraw;

	import javax.swing.*;
	import java.awt.*;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.Hashtable;

	/**
 * This class represents a simple example of using MySQL Data-Base.
 * Use this example for writing solution. 
 * @author boaz.benmoshe
 *
 */
public class SimpleDB {
		public static final String jdbcUrl = "jdbc:mysql://db-mysql-ams3-67328-do-user-4468260-0.db.ondigitalocean.com:25060/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
		public static final String jdbcUser = "student";
		public static final String jdbcUserPassword = "OOP2020student";
		public static StringBuilder myplays = new StringBuilder();
		public static int numofgames;
		public static Hashtable<Integer, Integer> bestscore = new Hashtable<Integer, Integer>();

		/**
		 * Simple main for demonstrating the use of the Data-base
		 *
		 * @param args
		 */
		public static void main(String[] args) {
			//		int id1 = 999;  // "dummy existing ID
			int level = 0;
			//System.out.println(allUsers());
			//printLog();
			printAlldata();
			//	String kml = getKML(316405505,1);
			//System.out.println(kml);
			//		System.out.println("***** KML file example: ******");
			//	System.out.println(kml);
		}

		/**
		 * This function prints in the gui the following data:
		 * Number of game you've played.
		 * Your current level.
		 * Your best score in each level.
		 */
		public static void printLog() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection =
						DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
				Statement statement = connection.createStatement();
				String allCustomersQuery = "SELECT * FROM Logs;";
				ResultSet resultSet = statement.executeQuery(allCustomersQuery);

				while (resultSet.next()) {
					if (resultSet.getInt("UserID") == 316405505) {
						numofgames++;
						myplays.append("level:" + resultSet.getString("levelID") + "score:" + resultSet.getString("score") + "moves:" + resultSet.getString("moves"));
					}
					//System.out.println("Id: " + resultSet.getInt("UserID")+","+resultSet.getInt("levelID")+","+resultSet.getInt("score")+","+resultSet.getInt("moves")+","+resultSet.getDate("time"));
				}
				resultSet.close();
				statement.close();
				connection.close();

			} catch (SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
				System.out.println("Vendor Error: " + sqle.getErrorCode());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			int numofgames = SimpleDB.numofgames;
			System.out.println(SimpleDB.myplays.toString());
			System.out.println(numofgames);
			StdDraw.clear();
			StdDraw.setPenColor(Color.BLACK);
			StdDraw.setPenRadius(0.005);
			String gameans = "You played " + Integer.toString(numofgames) + " games";
			int levelindex = SimpleDB.myplays.lastIndexOf("level:");
			String urlevel = SimpleDB.myplays.substring(levelindex + 6, levelindex + 8);
			String lvlans = "You are at level " + urlevel;
			StdDraw.text((StdDraw.getGui().maxXPos() + StdDraw.getGui().minXPos()) / 2, ((StdDraw.getGui().maxYPos() + StdDraw.getGui().minYPos()) / 2) + 0.003, gameans);
			StdDraw.text((StdDraw.getGui().maxXPos() + StdDraw.getGui().minXPos()) / 2, ((StdDraw.getGui().maxYPos() + StdDraw.getGui().minYPos()) / 2) + 0.0027, lvlans);
			String s = SimpleDB.myplays.toString();
			int score = 0, maxscore = 0;
			double place = 0.0024;
			int[] levels = {0, 1, 3, 5, 9, 11, 13, 16, 19, 20, 23};
			int[] moves = {290, 580, 580, 500, 580, 580, 580, 290, 580, 290, 1140};
			int move = 0;
			for (int i = 0; i < levels.length; i++) {
				int maxmove = moves[i];
				String[] parts = s.split("level:" + Integer.toString(levels[i]));
				for (int j = 1; j < parts.length; j++) {
					if (parts[j].charAt(0) == 's') {
						try {
							score = Integer.parseInt(parts[j].substring(6, 10));
						} catch (Exception ex) {
							try {
								score = Integer.parseInt(parts[j].substring(6, 9));
							} catch (Exception ex1) {
								try {
									score = Integer.parseInt(parts[j].substring(7, 10));
								} catch (Exception ex2) {
									score = Integer.parseInt(parts[j].substring(7, 11));
								}
							}
						}
						try {
							move = Integer.parseInt(parts[j].substring(15, 18));
						} catch (Exception exc) {
							try {
								move = Integer.parseInt(parts[j].substring(16, 19));
							} catch (Exception exc1) {
								try {
									move = Integer.parseInt(parts[j].substring(15, 19));
								} catch (Exception exc2) {
									try {
										move = Integer.parseInt(parts[j].substring(16, 20));
									} catch (Exception exc3) {
										move = 0;
									}
								}
							}
						}
					}
					if (score > maxscore && move < maxmove)
						maxscore = score;
				}
				bestscore.put(i, maxscore);
				String bestforlevel = "Your best score at level " + Integer.toString(levels[i]) + " is: " + Integer.toString(maxscore);
				StdDraw.text((StdDraw.getGui().maxXPos() + StdDraw.getGui().minXPos()) / 2, ((StdDraw.getGui().maxYPos() + StdDraw.getGui().minYPos()) / 2) + place, bestforlevel);
				place -= 0.0003;
				maxscore = 0;
				score = 0;
			}


		}

		public static String printAlldata() {
			String s = "";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection =
						DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
				Statement statement = connection.createStatement();
				String allCustomersQuery = "SELECT * FROM Logs;";
				ResultSet resultSet = statement.executeQuery(allCustomersQuery);

				while (resultSet.next()) {
					s += "Id: " + resultSet.getInt("UserID") + "," + resultSet.getInt("levelID") + "," + resultSet.getInt("score") + "," + resultSet.getInt("moves") + "\n";
				}
				resultSet.close();
				statement.close();
				connection.close();
				System.out.println(s.toString());
			} catch (SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
				System.out.println("Vendor Error: " + sqle.getErrorCode());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return s;
		}

		/**
		 * this function returns the KML string as stored in the database (userID, level);
		 *
		 * @param id
		 * @param level
		 * @return
		 */
		public static String getKML(int id, int level) {
			String ans = null;
			String allCustomersQuery = "SELECT * FROM Users where userID=" + id + ";";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection =
						DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(allCustomersQuery);
				if (resultSet != null && resultSet.next()) {
					ans = resultSet.getString("kml_" + level);
				}
			} catch (SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
				System.out.println("Vendor Error: " + sqle.getErrorCode());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return ans;
		}

		public static int allUsers() {
			int ans = 0;
			String allCustomersQuery = "SELECT * FROM Users;";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection =
						DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(allCustomersQuery);
				while (resultSet.next()) {
					System.out.println("Id: " + resultSet.getInt("UserID"));
					ans++;
				}
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
				System.out.println("Vendor Error: " + sqle.getErrorCode());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return ans;
		}

		/**
		 * This function is taking all the infromation from the database in the sql server,
		 * and printing your rank in the server of each level.
		 */

		public static void printMyRank() {
			int[] levels = {0, 1, 3, 5, 9, 11, 13, 16, 19, 20, 23};
			int[] moves = {290, 580, 580, 500, 580, 580, 580, 290, 580, 290, 1140};
			int place = 0;
			double mover=0.003;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection =
						DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
				Statement statement = connection.createStatement();
				StdDraw.clear();
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.setPenRadius(0.005);
				for (int i=0; i<levels.length;i++) {
					place=0;
					int nextScore = Integer.MAX_VALUE;
					String allCustomersQuery = "SELECT userID,levelID,score,moves FROM Logs WHERE levelID ="+ levels[i]+" && moves <="+ moves[i]+" ORDER BY score DESC;";
					ResultSet resultSet = statement.executeQuery(allCustomersQuery);

					while (resultSet.next()) {
						if (resultSet.getInt("score")< nextScore)
						{
							place++;
							nextScore=resultSet.getInt("score");
						}
						if (resultSet.getInt("UserID") == 316405505) {
							String res = "My level "+levels[i]+" rank is "+place+" top score is "+nextScore;
							System.out.println(res);
							StdDraw.text((StdDraw.getGui().maxXPos() + StdDraw.getGui().minXPos()) / 2, ((StdDraw.getGui().maxYPos() + StdDraw.getGui().minYPos()) / 2) + mover, res);
							mover -= 0.0003;
							break;
						}

					}
					resultSet.close();

				}
				statement.close();
				connection.close();

			} catch (SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
				System.out.println("Vendor Error: " + sqle.getErrorCode());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
