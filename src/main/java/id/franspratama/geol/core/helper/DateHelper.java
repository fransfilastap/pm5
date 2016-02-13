package id.franspratama.geol.core.helper;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {

	public static Date getZeroTimeDate(Date date){
	    Date res = date;
	    Calendar calendar = Calendar.getInstance();

	    calendar.setTime( date );
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);

	    res = calendar.getTime();
	    
	    return res;
	}
	
	public static int compareDate(Date date1,Date date2){
		return ( getZeroTimeDate(date1).compareTo( getZeroTimeDate(date2) ) );
	}
	
	public static boolean isInSameMonth(Date date1, Date date2){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		int year1 = calendar.get(Calendar.YEAR);
		int month1 = calendar.get(Calendar.MONTH);
		calendar.setTime(date2);
		int year2 = calendar.get(Calendar.YEAR);
		int month2 = calendar.get(Calendar.MONTH);
		
		return (year1 < year2 || (year1 == year2 && month1 <= month2));
	}
	
	public static  boolean isBeginingOfMonth(Date date){;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		System.out.println(cal.get(Calendar.DAY_OF_MONTH));
		return ( cal.get(Calendar.DAY_OF_MONTH)  == 1 );
	}
	
}
