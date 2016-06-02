package com.BhavCopyDownloader.util;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;



public class BhavDownloader
{

	/**
	 * Get Equity and Derivative BhavCopys
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException, IOException
	{
		/*String equityURL="https://www.nseindia.com/content/historical/EQUITIES/2016/MAY/cm13MAY2016bhav.csv.zip";
		String foURL="https://www.nseindia.com/content/historical/DERIVATIVES/2016/JUN/fo01JUN2016bhav.csv.zip";*/
		

		String marketChoice="CM";		
		
		int choice=0;
		while(true){
		//Display Menu
		choice=displayMenu(marketChoice);
		
		System.out.println("Selected Choice:"+choice);
		
		if(choice==1)
		{
			Date date=Calendar.getInstance().getTime();
			System.out.println("Today is :"+date);
			
			String bhavURL=Utils.createBhavURL(date,marketChoice);
			System.out.println("Downloading ....");
			System.out.println(bhavURL);
			downloadBhavCopy(bhavURL,marketChoice);
		}
		else if(choice==2){
			
			String date=null;
			Date specificDate=null;
			while(true)
			{
			System.out.println("Enter the date in format DD-MMM-YYYY  like 02-MAY-2016");
			Scanner in=new Scanner(System.in);
			
			try{
				 date=in.nextLine();
					DateFormat format=new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH);
					specificDate=format.parse(date);

			}
			catch(InputMismatchException e)
			{
				System.out.println("Oops Invalid Date format. Retry !!");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println("Oops Invalid Date format. Retry !!");
			}
			SimpleDateFormat df=new SimpleDateFormat("EEEE");
			String today=df.format(specificDate);
			if(today.equalsIgnoreCase("Sunday") || today.equalsIgnoreCase("Saturday"))
			{
				System.out.println("Oops today is a trading holiday its -"+today+"  ! Enter a valid day !");
				continue;
			}
			else{
				break;

			}
			}
			System.out.println("Downloading for ...."+date+ " from");
			String bhavURL=Utils.createBhavURL(specificDate,marketChoice);
			System.out.println(bhavURL);
			downloadBhavCopy(bhavURL,marketChoice);

		}
		else if(choice==3){
			DateFormat formatter=new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH);
			Calendar sDate=Calendar.getInstance();
			Calendar eDate=Calendar.getInstance();
			
			while(true)
			{
			System.out.println("Enter the start date in format DD-MMM-YYYY  like 02-MAY-2016");
			Scanner in =new Scanner(System.in);
			String startDate=in.nextLine();
			System.out.println("Enter the end date in format DD-MMM-YYYY  like 22-JUL-2016");
			String endDate=in.nextLine();
			
			
			
			try {
				sDate.setTime(formatter.parse(startDate));
				eDate.setTime(formatter.parse(endDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(sDate.after(eDate))
			{
				System.out.println("Invalid start Date, start Date cannot be less than end date. Retry !!");
				
			}else
			{
				break;
			}
			}
			
			int countTradingDays=0;
			while(!sDate.after(eDate)){
				
				int day=sDate.get(Calendar.DAY_OF_WEEK);
				
				if(!(day== 7) && !(day==1) )
				{
				System.out.println("Downloading for ... "+formatter.format(sDate.getTime()) + " from");
				String bhavURL=Utils.createBhavURL(sDate.getTime(),marketChoice);
				System.out.println(bhavURL);
				downloadBhavCopy(bhavURL,marketChoice);
				countTradingDays++;
				}
				else{
					String []dayNames=new DateFormatSymbols().getWeekdays();
					System.out.println("Skipping today "+formatter.format(sDate.getTime())+"  since its "+dayNames[sDate.get(Calendar.DAY_OF_WEEK)]);
				}
				
				sDate.add(Calendar.DATE, 1);
				

			}
			

				System.out.println("Successfully Downloaded bhavCopy for "+countTradingDays +"  trading days.");
		}
		else if(choice==4){
			if(marketChoice.equals("CM")){
				System.out.println("Market type changed to F&O");
				marketChoice="FO";
			}else{
				System.out.println("Market type changed to Cash");
				marketChoice="CM";
			}
		}
		else if (choice==5){
			System.out.println("Exiting application. Bye !!");
			break;
		}
		else{
			System.out.println("Invalid Choice !! Try Again !!");
		}
		}
		


	}
	private static int displayMenu(String marketChoice){
		
		String displayMarketName=null;
		if(marketChoice.equals("CM")){
			displayMarketName="Cash Market";
		}else
		{
			displayMarketName="F&O";

		}
		
		System.out.println("What do you want to do ?");
		System.out.println("=============================================");
		System.out.println("1 - To Download today's "+displayMarketName+" BhavCopy");
		System.out.println("2 - To Download a "+displayMarketName+" BhavCopy for a specific date");
		System.out.println("3 - To Download "+displayMarketName+" BhavCopy between two dates");
		System.out.println("4 - To Toggle market type");
		System.out.println("5 - To Exit");

		System.out.println("=============================================");

		System.out.println("Enter Choice :");
		Scanner in =new Scanner(System.in);
		int choice=in.nextInt();
		//in.close();
		return choice;
	}

	private static void downloadBhavCopy(String bhavURL,String marketChoice){
		
		File targetDir=null;
		try
		{
			if(marketChoice.equals("CM"))
			{
				File cmTargetDir=new File("C://BhavDownloads//Cash");
				targetDir=cmTargetDir;
			}else{
				File foTargetDir=new File("C://BhavDownloads//F&O");
				targetDir=foTargetDir;
			}
			Utils.unpackArchive(new URL(bhavURL), targetDir);

		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
