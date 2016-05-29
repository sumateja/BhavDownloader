package com.BhavCopyDownloader.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BhavCopyCashMarketURLBean {
	
	private String todayBhavCopyURL="NSE";
	
	//Sample URL http://www.nseindia.com/content/historical/EQUITIES/2015/OCT/cm21OCT2015bhav.csv.zip
	private static String BASE_URL="https://www.nseindia.com/content/historical/EQUITIES/";
	
	BhavCopyCashMarketURLBean()
	{
		this.todayBhavCopyURL=buildBhavURL();
	}
	
	
	
	
	private String buildBhavURL() {
		
		StringBuffer bhavURLBuffer=new StringBuffer(BASE_URL);
		Calendar instance=Calendar.getInstance();

		String year= String.valueOf(instance.get(Calendar.YEAR));
		String month= new SimpleDateFormat("MMM").format(instance.getTime()).toUpperCase();
		String date=new SimpleDateFormat("dd").format(instance.getTime());
		
		bhavURLBuffer.append(year);
		bhavURLBuffer.append('/');
		bhavURLBuffer.append(month);
		bhavURLBuffer.append('/');


		bhavURLBuffer.append("cm");
		bhavURLBuffer.append(date);
		bhavURLBuffer.append(month);
		bhavURLBuffer.append(year);
		bhavURLBuffer.append("bhav.csv.zip");
		System.out.println(bhavURLBuffer.toString());
		return bhavURLBuffer.toString();
	}




	public String getBhavCopyURL()
	{
		return this.todayBhavCopyURL;
	}

}
