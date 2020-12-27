package DBContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper 
{
	private static String url = "jdbc:mysql://localhost:3306/javaprojectdb?useSSL=false";
	private static String user= "root";
	private static String pass = "root";
	
	
	public static Connection getConnection()
	{
		try 
		{
			Connection con = DriverManager.getConnection(url,user,pass);
			return con;
		}
		catch (SQLException e) 
		{
			String msg = e.getMessage();			
		}
		return null;
	}

}
