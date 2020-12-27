package DBContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Models.DailyActivity;

public class DailyActivityContext 
{
	//query example INSERT INTO `javaprojectdb`.`dailyactivity` (`id`, `idConnectedUser`, `title`, `text`, `date`) VALUES ('5', '123', 'test', 'text', '03.03.2020.');
	//query example DELETE FROM `javaprojectdb`.`dailyactivity` WHERE (`id` = '1');
	//query example UPDATE `javaprojectdb`.`dailyactivity` SET `idConnectedUser` = '123', `title` = 'Vule', `text` = 'Vule', `date` = '03.10.2020.' WHERE (`id` = '4');
	//query example SELECT * FROM dailyactivity WHERE idConnectedUser='1904994800086'";
	private static String tableName = "dailyactivity";
	
	//ADD---------------------------------------------------
	public static Boolean add(DailyActivity inDailyActivity)
	{
		String outMsg = ""; //message if there is any problem
		if(inDailyActivity != null)
		{
			String id = inDailyActivity.getId().toString(); //Query preparation
			String connectedUserId = inDailyActivity.getConnectedUserId().toString();
			String title = inDailyActivity.getTitle();
			String text = inDailyActivity.getText();
			String date = inDailyActivity.getDate();
			
			String query = "INSERT INTO `"+tableName+"` (`id`, `idConnectedUser`, `title`, `text`, `date`) VALUES ('"+id+"', '"+connectedUserId+"', '"+title+"', '"+text+"', '"+date+"')";
			
			Connection con = ConnectionHelper.getConnection();
			if(con != null)
			{
				try 
				{
					Statement st = con.createStatement();
					st.execute(query);
					st.close();
					con.close();
					return true;
				}
				catch (SQLException ex)
				{
					//e1.printStackTrace();
					outMsg = ex.getMessage();
				}	
			}
		}
		
		return false;
	}
	
	//GET--------------------------------------------------------
	public static DailyActivity get(Long inId)
	{
		String outMsg="";
		DailyActivity toRetObject = null;
		String query = "SELECT * FROM "+tableName+" WHERE id='"+inId+"'";	
		Connection con = ConnectionHelper.getConnection();
		if(con != null)
		{
			try 
			{
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);
				while(rs.next())
				{
					Long id = Long.parseLong(rs.getString("id")) ;
					Long connecteUserId = Long.parseLong(rs.getString("idConnectedUser")) ;
					String text = rs.getString("text");
					String title = rs.getString("title");
					String date = rs.getString("date");
					toRetObject = new DailyActivity(id,connecteUserId,title,text,date);	
				}
				st.close();
				con.close();
			}
			catch (SQLException ex)
			{
				//e1.printStackTrace();
				outMsg = ex.getMessage();
			}
		}
		return toRetObject;
	}
	
	//GET ALL----------------------------------------------------
	public static ArrayList<DailyActivity> getAll()
	{
		String outMsg="";
		ArrayList<DailyActivity> toRetList = new ArrayList<DailyActivity>();
		
		String query = "SELECT * FROM "+tableName;
		
		Connection con = ConnectionHelper.getConnection();
		if(con != null)
		{
			try 
			{
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);
				while(rs.next())
				{
					Long id = Long.parseLong(rs.getString("id")) ;
					Long connecteUserId = Long.parseLong(rs.getString("idConnectedUser")) ;
					String text = rs.getString("text");
					String title = rs.getString("title");
					String date = rs.getString("date");
					DailyActivity tmpDa = new DailyActivity(id,connecteUserId,title,text,date);
					toRetList.add(tmpDa);		
				}
				st.close();
				con.close();
			}
			catch (SQLException ex)
			{
				//e1.printStackTrace();
				outMsg = ex.getMessage();
			}
		}
		return toRetList;
	}
	
	//GET FOR USER----------------------------------------------
	public static ArrayList<DailyActivity> getForUser(Long inConnecteUserId)
	{
		String outMsg="";
		ArrayList<DailyActivity> toRetList = new ArrayList<DailyActivity>();
		
		String query = "SELECT * FROM "+tableName+" WHERE idConnectedUser='"+inConnecteUserId+"'";
		
		Connection con = ConnectionHelper.getConnection();
		if(con != null)
		{
			try 
			{
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);
				while(rs.next())
				{
					Long id = Long.parseLong(rs.getString("id")) ;
					String text = rs.getString("text");
					String title = rs.getString("title");
					String date = rs.getString("date");
					DailyActivity tmpDa = new DailyActivity(id,inConnecteUserId,title,text,date);
					toRetList.add(tmpDa);		
				}
				st.close();
				con.close();
			}
			catch (SQLException ex)
			{
				//e1.printStackTrace();
				outMsg = ex.getMessage();
			}
		}
		return toRetList;
	}
	
	//UPDATE-----------------------------------------------------
	public static Boolean update(DailyActivity inDailyActivity)
	{
		String outMsg = ""; //message if there is any problem
		if(inDailyActivity != null)
		{
			String id = inDailyActivity.getId().toString(); //Query preparation
			String connectedUserId = inDailyActivity.getConnectedUserId().toString();
			String title = inDailyActivity.getTitle();
			String text = inDailyActivity.getText();
			String date = inDailyActivity.getDate();
			
			String query = "UPDATE `"+tableName+"` SET `idConnectedUser` = '"+connectedUserId+"', `title` = '"+title+"', `text` = '"+text+"', `date` = '"+date+"' WHERE (`id` = '"+id+"')";
			
			Connection con = ConnectionHelper.getConnection();
			if(con != null)
			{
				try 
				{
					Statement st = con.createStatement();
					st.execute(query);
					st.close();
					con.close();
					return true;
				}
				catch (SQLException ex)
				{
					//e1.printStackTrace();
					outMsg = ex.getMessage();
				}	
			}
		}
		
		return false;
	}
	
	//DELETE----------------------------------------------------
	public static Boolean delete(Long inId)
	{
		String outMsg = "";
		String query = "DELETE FROM `"+tableName+"` WHERE (`id` = '"+inId+"');";
		Connection con = ConnectionHelper.getConnection();
		if(con != null)
		{
			try 
			{
				Statement st = con.createStatement();
				st.execute(query);
				st.close();
				con.close();
				return true;
			}
			catch (SQLException ex)
			{
				//e1.printStackTrace();
				outMsg = ex.getMessage();
			}	
		}
		return null;
	}

}
