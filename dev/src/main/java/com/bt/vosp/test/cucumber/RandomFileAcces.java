package com.bt.vosp.test.cucumber;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class RandomFileAcces {
	
	static long  intialFileLength=0;
	 static double  FinalFileLength=0;
		
	public static void getFileLength(String filename,int type)
	{
		File file =new File(filename);

		if(file.exists()){
			
			if (type==1)
			intialFileLength=file.length();
			else
				FinalFileLength=file.length();
		}
		else
		{
			System.out.println("File "+filename+" not available");
		}
			
	}
	

	
	
	public static void exctractLogs(String SourceFileName,String DestFileName) throws IOException
	
	{
		
		byte[] byteArray = new byte[1000];
		int k=0;
		try {
			RandomAccessFile raf = new RandomAccessFile(SourceFileName,"rw");
			RandomAccessFile DestinationFile =new RandomAccessFile(DestFileName,"rw");
			
			if(intialFileLength>FinalFileLength)
				intialFileLength=1;
			
			raf.seek(intialFileLength);
		
			 while((k=raf.read(byteArray))>0)
		       {
			//	 System.out.println(new String(byteArray));
				 DestinationFile.write(byteArray,0,k);
		       }
			 
			 raf.setLength(0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
}
