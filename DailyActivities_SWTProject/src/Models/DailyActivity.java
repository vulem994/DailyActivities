package Models;

import java.text.SimpleDateFormat;

import com.ibm.icu.util.Calendar;

import Shared.HelperModel;
import UserInterfaces.AddEditDailyActivity_Dialog;

public class DailyActivity 
{
	//Properties
	private Long id;
	private Long connectedUserId;
	private String title = "";
	private String text = "";
	private String date = "";
	
	//Constructor
	public DailyActivity(Long inUserId)
	{
		this.id = HelperModel.getTicks();
		this.connectedUserId = inUserId;
		this.title = "Enter a title";
		this.text = "Enter a text";
		Calendar instance = Calendar.getInstance();
		this.date = new SimpleDateFormat(HelperModel.getDateFormat()).format(instance.getTime());
	}	
	public DailyActivity(Long inUserId, String inTitle, String inText ,String inDate)
	{
		this.id = HelperModel.getTicks();
		this.connectedUserId = inUserId;
		this.title = inTitle;
		this.text= inText;
		this.date = inDate;
	}	
	
	public DailyActivity(Long inId, Long inUserId, String inTitle, String inText ,String inDate)
	{
		this.id = inId;
		this.connectedUserId = inUserId;
		this.title = inTitle;
		this.text= inText;
		this.date = inDate;
	}	
	
	
	//Return values {get;set;}
	public Long getId()
	{
		return id;
	}
	
	public Long getConnectedUserId()
	{
		return connectedUserId;
	}
	
	public String getTitle() //Title
	{
		return title;
	}
	public void setTitle(String inTitle)
	{
		this.title = inTitle;
	}
	
	
	public String getText() //Text
	{
		return text;
	}
	public void setText(String inText)
	{
		this.text = inText;
	}
	
	
	public String getDate() //Date
	{
		return date;
	}
	public void setDate(String inDate)
	{
		this.date = inDate;
	}
}//[Class]
