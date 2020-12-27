package DBContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Models.User;

public class UserContext 
{


	private static String tableName = "user";

	// ADD---------------------------------------------------
	public static Boolean add(User inUser) {
		String outMsg = ""; 
		if (inUser != null) {
			String id = inUser.getId().toString(); // Query preparation
			String userName = inUser.getUserName();
			String password = inUser.getPassword();
			String fullName= inUser.getFullName();

			String query = "INSERT INTO `" + tableName
					+ "` (`id`, `username`, `password`, `fullname`) VALUES ('" + id + "', '" + userName
					+ "', '" + password + "', '"+fullName+"')";

			Connection con = ConnectionHelper.getConnection();
			if (con != null) {
				try {
					Statement st = con.createStatement();
					st.execute(query);
					st.close();
					con.close();
					return true;
				} catch (SQLException ex) {
					// e1.printStackTrace();
					outMsg = ex.getMessage();
				}
			}
		}

		return false;
	}

	// GET--------------------------------------------------------
	public static User get(Long inId) {
		String outMsg = "";
		User toRetObject = null;
		String query = "SELECT * FROM " + tableName + " WHERE id='" + inId + "'";
		Connection con = ConnectionHelper.getConnection();
		if (con != null) {
			try {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					Long id = Long.parseLong(rs.getString("id"));
					String userName = rs.getString("username");
					String password = rs.getString("password");
					String fullName = rs.getString("fullname");

					toRetObject = new User(id, userName, password,fullName);
				}
				st.close();
				con.close();
			} catch (SQLException ex) {
				// e1.printStackTrace();
				outMsg = ex.getMessage();
			}
		}
		return toRetObject;
	}
	
	public static User get(String inUserName) {
		String outMsg = "";
		User toRetObject = null;
		String query = "SELECT * FROM " + tableName + " WHERE username='" + inUserName + "'";
		Connection con = ConnectionHelper.getConnection();
		if (con != null) {
			try {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					Long id = Long.parseLong(rs.getString("id"));
					String userName = rs.getString("username");
					String password = rs.getString("password");
					String fullName = rs.getString("fullname");

					toRetObject = new User(id, userName, password,fullName);
				}
				st.close();
				con.close();
			} catch (SQLException ex) {
				// e1.printStackTrace();
				outMsg = ex.getMessage();
			}
		}
		return toRetObject;
	}

	// GET ALL----------------------------------------------------
	public static ArrayList<User> getAll() {
		String outMsg = "";
		ArrayList<User> toRetList = new ArrayList<User>();

		String query = "SELECT * FROM " + tableName;

		Connection con = ConnectionHelper.getConnection();
		if (con != null) {
			try {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					Long id = Long.parseLong(rs.getString("id"));
					String userName = rs.getString("username");
					String password = rs.getString("password");
					String fullName = rs.getString("fullname");
		
					User tmpUser = new User(id, userName, password,fullName);
					toRetList.add(tmpUser);
				}
				st.close();
				con.close();
			} catch (SQLException ex) {
				// e1.printStackTrace();
				outMsg = ex.getMessage();
			}
		}
		
		return toRetList;
	}
	

	// UPDATE-----------------------------------------------------
	public static Boolean update(User inDailyActivity) {
		String outMsg = ""; // message if there is any problem
		if (inDailyActivity != null) {
			String id = inDailyActivity.getId().toString(); // Query preparation
			String userName = inDailyActivity.getUserName();
			String password = inDailyActivity.getPassword();
			String fullName = inDailyActivity.getFullName();

			String query = "UPDATE `" + tableName + "` SET `username` = '" + userName + "', `password` = '"
					+ password + "', `fullname` = '"+fullName+"' WHERE (`id` = '" + id + "')";

			Connection con = ConnectionHelper.getConnection();
			if (con != null) {
				try {
					Statement st = con.createStatement();
					st.execute(query);
					st.close();
					con.close();
					return true;
				} catch (SQLException ex) {
					// e1.printStackTrace();
					outMsg = ex.getMessage();
				}
			}
		}

		return false;
	}

	// DELETE----------------------------------------------------
	public static Boolean delete(Long inId) {
		String outMsg = "";
		String query = "DELETE FROM `" + tableName + "` WHERE (`id` = '" + inId + "');";
		Connection con = ConnectionHelper.getConnection();
		if (con != null) {
			try {
				Statement st = con.createStatement();
				st.execute(query);
				st.close();
				con.close();
				return true;
			} catch (SQLException ex) {
				// e1.printStackTrace();
				outMsg = ex.getMessage();
			}
		}
		return null;
	}

}
