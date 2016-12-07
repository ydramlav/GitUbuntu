package com.bt.vosp.test.cucumber;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouviewPlay {
	static String Response="";
//	public static String FramePattern(String pattern,String LogfileName)
//	{
//		FileReader fileReader;
//		BufferedReader bufferedReader;
//		String line = new String();
//		String content="";
//		String result="";
//		try
//		{
//			fileReader = new FileReader(LogfileName);
//			bufferedReader =  new BufferedReader(fileReader);
//			 while( (line = bufferedReader.readLine())!=null)
//	          {
//	        	  content=content+line;
//	          }
//			 bufferedReader.close();
//	         result=ExecuteRegex(pattern, content);
//	                  
//		}
//		catch (Exception e)
//		{			
//			e.printStackTrace();
//		}
//		return result;
//	}
//	
//	public static String ExecuteRegex(String pattern,String content)
//	{
//		String	result="";
//		Pattern r = Pattern.compile(pattern);
//		Matcher m = r.matcher(content);
//		int ik=0;
//		while(m.find())
//  	  	{
//			Response+="\nURI From  Logs : " + m.group();
//			result=m.group();
//			ik++;
//  	  	}
//		if(ik>0)
//		{		
//			
//			System.out.println("Pattern Matched"+pattern);   
//		}
//		else
//		{
//			System.out.println("No match");          
//		}
//		return result;
//	}

	
	public static boolean FramePattern(String pattern,String LogfileName)
	{
		FileReader fileReader;
		BufferedReader bufferedReader;
		String line = new String();
		String content="";
		boolean result=false;
		try
		{
			fileReader = new FileReader(LogfileName);
			bufferedReader =  new BufferedReader(fileReader);
			 while( (line = bufferedReader.readLine())!=null)
	          {
	        	  content=content+line;
	          }
			 bufferedReader.close();
	         result=ExecuteRegex(pattern, content);
	         System.out.println("Regex Result : "+result);
	           
		}
		catch (Exception e)
		{			
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean ExecuteRegex(String pattern,String content)
	{
		boolean	result=false;
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(content);
		int ik=0;
		String temp="";
		while(m.find())
  	  	{
			//Response+="\nURI From  Logs : " + m.group();
			ik++;
  	  	}
		if(ik>0)
		{		
			result=true;
			System.out.println("Pattern Matched"+pattern);   
		}
		else
		{
			System.out.println("No match");          
		}
		return result;
	}
	
	public static boolean FramePattern1(String pattern,String LogfileName)
	{
		FileReader fileReader;
		BufferedReader bufferedReader;
		String line = new String();
		String content="";
		boolean result=false;
		try
		{
			fileReader = new FileReader(LogfileName);
			bufferedReader =  new BufferedReader(fileReader);
			 while( (line = bufferedReader.readLine())!=null)
	          {
	        	  content=content+line;
	          }
			 bufferedReader.close();
			 
	         result=ExecuteRegex1(pattern, content);
	         System.out.println("Regex Result : "+result);
	           
		}
		catch (Exception e)
		{			
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean ExecuteRegex1(String pattern,String content)
	{
		boolean	result=false;
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(content);
		int ik=0;
		String temp="";
		while(m.find()) 
  	  	{
			temp= m.group();
			if(temp.contains("Etag is not available in the request"))
   		  {
         System.out.println("Etag not present : " + m.group() );
         ik++;
         }
  	  	}
		if(ik>0)
		{	
			
			result=true;
		}
		else
		{
			System.out.println("No match");          
		}
		return result;
	}
	
			
}
