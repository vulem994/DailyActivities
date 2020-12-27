package Shared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.widgets.DateTime;

public class HelperModel 
{
	
	//Calendar & Date time  helper functions *************************************
	private static String dateFormat = "dd.MM.yyyy.";
	public static String getDateFormat() // Get date format
	{
		return dateFormat;
	}
	
	public static Long getTicks()// Get ticks (mills)
	{
		Calendar calendar = Calendar.getInstance();    
		
		Date date = calendar.getTime();
		return date.getTime();
	}
		
	public static String getDateStringFromDateTimeWidget(DateTime inDateTimeWidget) // Get from DateTime widget
	{
		if (inDateTimeWidget != null) 
		{
			Calendar instance = Calendar.getInstance();
			instance.set(Calendar.DAY_OF_MONTH, inDateTimeWidget.getDay());
			instance.set(Calendar.MONTH, inDateTimeWidget.getMonth());
			instance.set(Calendar.YEAR, inDateTimeWidget.getYear());
			String dateString = new SimpleDateFormat(dateFormat).format(instance.getTime());
			return dateString;
		}
		return null;
	}

	public static void setDateStringToDateTimeWidget(DateTime inDateTimeWidget,String inDateTime) // Set to DateTime widget
	{
		if (inDateTimeWidget != null) 
		{
			Calendar calendarInstance = Calendar.getInstance();
			
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			try 
			{
				calendarInstance.setTime(sdf.parse(inDateTime));
				Date testTIme =calendarInstance.getTime();
				//calendarInstance.setTime(tmpDate);
				//this.dateTime_date.setDate(calendarInstance.DAY_OF_MONTH, calendarInstance.MONTH, calendarInstance.YEAR);
				inDateTimeWidget.setDay(calendarInstance.get(Calendar.DAY_OF_MONTH));
				inDateTimeWidget.setMonth(calendarInstance.get(Calendar.MONTH));
				inDateTimeWidget.setYear(calendarInstance.get(Calendar.YEAR));
			} 
			catch (ParseException e) 
			{
				calendarInstance.clear();
				calendarInstance = Calendar.getInstance();
				inDateTimeWidget.setDate(calendarInstance.DAY_OF_MONTH, calendarInstance.MONTH,
						calendarInstance.YEAR);
			}
		}
	}

}
