package com.bt.vosp.test.cucumber;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public class DownloadCsv {
	
	static Date currenntTimeStamp = new Date();
	private static String csvSourcefilelocation="/wls_domains/wlsapp02/YouViewPlay/app-config/csv";
	private static String csvLocalFile="src/test/resources/YouviewPlayLog2.txt";
	public static String pattern;
	public static String getcsvFileName(String Lines) {

		String Temp = "", ResultFileName = "empty";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date sysDate = new Date();
		String currentYear = dateFormat.format(sysDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sysDate);
		cal.add(Calendar.MINUTE, -1);
		Date oneMinBack = cal.getTime();
		dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String oneMinBackS = dateFormat.format(oneMinBack);
		
		String[] Commandlines = Lines.split(System.getProperty("line.separator"));
		for(int i=0;i<Commandlines.length;i++)
		System.out.println("command line:"+Commandlines[i]);
		long StartTime, ControlefileTime;

		StartTime = Long.parseLong(oneMinBackS);
		//System.out.println("StartTime "+StartTime);
		for (String line : Commandlines) {
			
			if (line.contains(".csv") && line.contains("_" + currentYear)) {
				
//				
					Temp = line.substring(line.indexOf("_" + currentYear) + 1, line.lastIndexOf(".csv"));

				
				System.out.println(line);
				

				ControlefileTime = Long.parseLong(Temp);
				

				if (ControlefileTime >= StartTime) {
					ResultFileName = line;
					StartTime = ControlefileTime;
//					 System.out.println(ResultFileName);
				}
			} 
			

		}
		return ResultFileName;
}
	
	public static void downloadcsvFile()
	{
		

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Channel channel;
		try {
			channel = ApplogRetrival_YV.session.openChannel("shell");
		
		channel.setOutputStream(baos);
		PrintStream shellStream = new PrintStream(channel.getOutputStream()); // printStream
																				// for
												// convenience
		channel.connect();

		shellStream.println("cd " + csvSourcefilelocation);

		shellStream.println("ls -lrt");

		shellStream.flush();

		shellStream.close();

		Thread.sleep(5000);

		//System.out.println("Here: **** " + baos.toString() + " \n######### ");

		String Lines = baos.toString();
		
		String filename=getcsvFileName(Lines);
	    	
		ChannelSftp sftpChannel = (ChannelSftp) ApplogRetrival_YV.session.openChannel("sftp");
	    	sftpChannel.connect();
	    	sftpChannel.get(csvSourcefilelocation+filename,csvLocalFile);
	    	Thread.sleep(2000);
	    	System.out.println("Csv file downloaded");
	    	
	    	 sftpChannel.disconnect();
	    pattern="(?i)cid^eventTimeStamp^eventType^deviceId^mediaId^mediaAssetId^productId^releasePID^serviceType^mediaAssetType^assetSlotType^assetTitle^sc^dc^placementId^recommendationGuid^eventLogTime^errorCode^errorMessage^platform^ProductServiceTypes^ProductOfferingType^TargetBandwidth^ContentProviderID^Scodes^titleID^linkedTitleID^ParentGuid^SchedulerChannel^GUID^Genre^Subgenres^LMBTWSID^LineReliabilityIndex^LineEarliestTrustTime^LineLastVerificationTime^ISPType^LMDecision^Lock_id^Rn_id^Concurrency_Client_id^CDNtype^CDNDeliveryURL^URLSigningInfo^EntitlementType^LicenceEntitlementID^PlayType^Matched_Scodes^VSID^IP^UserAgentString^UUID^BTAppVersion^playlistType^AccountStatus^DeviceStatus^DeviceMake^DeviceModel^DeviceVariant^SoftwareVersion^AssetReleaseId^SeriesTitle^ContentStartTime^ContentEndTime^Duration^AssetType^ProductOfferPeriod.*";
	 	boolean readPattern = YouviewPlay.FramePattern(pattern,"src/test/resources/YouviewPlayLog2.txt");
	 	//System.out.println("readPattern : "+readPattern);
	    	
			} catch (JSchException e) {
				
				e.printStackTrace();
			} catch (SftpException e) {
				
				e.printStackTrace();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	
	}
	
}
