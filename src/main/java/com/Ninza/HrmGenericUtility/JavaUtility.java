
package com.Ninza.HrmGenericUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class JavaUtility {
  public int getRandonNum()
  {
	  Random random=new Random();
	  int randomnumber=random.nextInt(5000);
	return randomnumber;
  }
  public String getSystemDateYYYYDDMM() {
	  Date dateobj=new Date();
	  SimpleDateFormat sim=new SimpleDateFormat("YYYY-MM-dd");
	  String actdate=sim.format(dateobj);
	  return actdate;
  }
  public String getRequiredDateYYYYDDMM(int days) {
	
	  SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
	  Calendar cal=sim.getCalendar();
	  cal.add(Calendar.DAY_OF_MONTH,days);
	  String daterequires=sim.format(cal.getTime());
	  return daterequires;  }
}
