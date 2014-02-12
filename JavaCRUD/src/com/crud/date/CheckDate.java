package com.crud.date;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CheckDate {
	
	 public boolean isThisDateWithinFourWeeks(Date dateInColumn){
		 
		 Calendar currentDateAfter4Weeks= Calendar.getInstance();
		 currentDateAfter4Weeks.add(Calendar.WEEK_OF_MONTH, 4);
		 Date d = currentDateAfter4Weeks.getTime();
			Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateInRightFormat = formatter.format(dateInColumn);
			Date d2 = null;
			try {
				d2 = (Date) formatter.parseObject(dateInRightFormat);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 if (d2.after(d)){ 
		return false;
		 }else return true;
		 
		 
	 }
	

}
