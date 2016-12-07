package com.bt.vosp.test.cucumber;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ApplogRetrival_YV {
	public static Session session;
	static String user="wlsapp02",host="10.101.47.56",password="wlsapp02";
//	static String user="wlsapp02",host="10.1.1.26",password="wlsapp02";
//	static String SourceFileName="/wls_domains/wlsapp01_logs/MPlayLog.txt";
//	static String DestFileName="C:/Systest/AutomationCucumber/vosp16/MPlayLogs.txt";
//	public static void main(String[] args) {
//		LoginSSH();
//	}
	public static void LoginSSH()
	{
		try {
		java.util.Properties config = new java.util.Properties(); 
    	config.put("StrictHostKeyChecking", "no");
    	JSch jsch = new JSch();
    	
		System.out.println(user);
			session = jsch.getSession(user, host, 22);
		
    	session.setPassword(password);
    	session.setConfig(config);
    	session.connect();
    	System.out.println("Connected to ssh Session");
    	
		} catch (JSchException e) {
			e.printStackTrace();
		}
    	
	}

	public static void GetLogsBeforeTestLog1(String SourceFileName,String DestFileName)
	{
	    
		try{
		    	
		    	ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
		    
		    	sftpChannel.connect();
		    	sftpChannel.get(SourceFileName,DestFileName);
		    	RandomFileAcces.getFileLength(DestFileName, 1);
		    	
		    	System.out.println("intialFileLength"+RandomFileAcces.intialFileLength);
		sftpChannel.disconnect();
	       
	       

	        
	    }
	    catch(Exception e)
	    {
	        System.err.println("Error: " + e);
	        e.printStackTrace();
	    }
	}
	public static void GetLogsAfterTestLog1(String SourceFileName,String DestFileName)
	{
	   		   
		    try{
		    	
		    	ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
		    	sftpChannel.connect();
		    	
		    	
		    	sftpChannel.get(SourceFileName,DestFileName);
		    	RandomFileAcces.getFileLength(DestFileName, 2);
		    	
//		    	RandomFileAcces.exctractLogs(DestFileName,"src/test/resources/MPlayLog1.txt");
		    	RandomFileAcces.exctractLogs(DestFileName,"src/test/resources/YouviewPlayLog1.txt");
		    	
		sftpChannel.disconnect();
	      
	    }
	    catch(Exception e)
	    {
	        System.err.println("Error: " + e);
	        e.printStackTrace();
	    }
	}
	public static void GetLogsAfterTestLog2(String SourceFileName,String DestFileName)
	{
	   		   
		    try{
		    	
		    	ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
		    	sftpChannel.connect();
		    	
		    	
		    	sftpChannel.get(SourceFileName,DestFileName);
		    	RandomFileAcces.getFileLength(DestFileName, 2);
		    	
//		    	RandomFileAcces.exctractLogs(DestFileName,"src//test//resources//MPlayLog2.txt");
		    	RandomFileAcces.exctractLogs(DestFileName,"src/test/resources/YouviewPlayLog2.txt");
		    	
		sftpChannel.disconnect();
	      
	    }
	    catch(Exception e)
	    {
	        System.err.println("Error: " + e);
	        e.printStackTrace();
	    }
	}

	public static void GetYouviewPlayPropsFile(String sourceyouviewplayfile, String destyvpropfile) {
		try{
	    	
	    	ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
	    
	    	sftpChannel.connect();
	    	sftpChannel.get(sourceyouviewplayfile,destyvpropfile);
	    	RandomFileAcces.getFileLength(destyvpropfile, 1);
	    	
	    	System.out.println("intialFileLength"+RandomFileAcces.intialFileLength);
	sftpChannel.disconnect();
    }
//		try{
//	    	
//	    	ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
//	    	sftpChannel.connect();
//	    	
//	    	
//	    	sftpChannel.get(sourceyouviewplayfile,destyvpropfile);
//	    	RandomFileAcces.getFileLength(destyvpropfile, 2);
//	    	
////	    	RandomFileAcces.exctractLogs(DestFileName,"src/test/resources/MPlayLog1.txt");
//	    	RandomFileAcces.exctractLogs(destyvpropfile,"src/test/resources/YouviewPlayProps2.txt");
//	    	
//	sftpChannel.disconnect();
//      
//    }
    catch(Exception e)
    {
        System.err.println("Error: " + e);
        e.printStackTrace();
    }
		
	}
}