package Models;

import Shared.HelperModel;

public class User 
{
	Long id;
	String userName ="";
	String password ="";
	String fullName ="";
	
	public User()
	{
		this.id =HelperModel.getTicks();
	}
	
	public User(String inUserName)
	{
		this.id =HelperModel.getTicks();
		this.userName = inUserName;
	}
	
	public User(String inUserName,String inPassword)
	{
		this.id =HelperModel.getTicks();
		this.userName = inUserName;
		this.password = inPassword;
	}
	
	public User(Long inId,String inUserName,String inPassword)
	{
		this.id =inId;
		this.userName = inUserName;
		this.password = inPassword;
	}
	
	public User(Long inId,String inUserName,String inPassword,String inFullName)
	{
		this.id =inId;
		this.userName = inUserName;
		this.password = inPassword;
		this.fullName = inFullName;
	}
	
	
	
	//get set 
	public String getUserName()
	{
		return this.userName;
	}
	public void setUserName(String inUserName)
	{
		this.userName = inUserName;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public void setPassword(String inPassword)
	{
		this.password = inPassword;
	}
	
	public String getFullName()
	{
		return this.fullName;
	}
	
	public void setFullName(String inFullName)
	{
		this.fullName = inFullName;
	}
	
	public Long getId()
	{
		return this.id;
	}
}//[Class]
