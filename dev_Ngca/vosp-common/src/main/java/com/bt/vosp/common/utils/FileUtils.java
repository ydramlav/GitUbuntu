package com.bt.vosp.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.proploader.CommonGlobal;

public class FileUtils {

	//reading file
	public   JSONObject readJSONFile(String filePath) throws Exception{
		
		
		JSONObject jsonObject =  null;
		String fileContent = null;
		BufferedReader br = null;
		try {
	    	File fileName = new File(filePath);
	    	if(fileName != null && fileName.exists())
	    	{
	    		br = new BufferedReader(new FileReader(fileName));
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null) {
		            sb.append(line);
		            sb.append("\n");
		            line = br.readLine();
		        }
		        fileContent = sb.toString();
		        jsonObject = new JSONObject(fileContent);
	    	}
	    	else
	    	{
	    		CommonGlobal.LOGGER.error("The file "+filePath+" specified in the configuration is not exists");
	    	}
			
	    }
		catch(Exception e){
			CommonGlobal.LOGGER.error("Error occurred while reading configured json object.Reason : "+e.getMessage());
		}
		
		finally {
	        br.close();
	    }

		return jsonObject;
	}
//	@SuppressWarnings("unchecked")
//	public static void main(String[] args) throws Exception{
//		
//		FileUtils fileUtils = new FileUtils();
//		JSONObject mappinglocationJSON =null;
//		JSONArray serviceUrlArray =null;
//		JSONArray channelUrlArray =null;
//		String mappinglocation = null;
//		Map<String,JSONArray> otgchannelLocURLMap = new HashMap<String, JSONArray>();
//		Map<String,JSONArray> otgserviceURLMap = new HashMap<String, JSONArray>();
//		try{
//			
//			JSONObject jsonObject = fileUtils.readJSONFile("D:/MSR/TVE_SourceCode/ConfigurationMapping.json");
//
//			if(jsonObject.has("configuration_mapping")){
//
//				JSONObject configMappingJson  = jsonObject.getJSONObject("configuration_mapping");
//				Iterator<String> keys = configMappingJson.keys();
//				
//				if(configMappingJson!=null){
//					  while( keys.hasNext() ){
//						  String controlGroup = (String)keys.next();
//						  
//						mappinglocation =  (String) configMappingJson.getString(controlGroup);
//						mappinglocationJSON = fileUtils.readJSONFile(mappinglocation);
//						serviceUrlArray =  new JSONArray();
//						channelUrlArray =  new JSONArray();
//						if(mappinglocationJSON!=null && mappinglocationJSON.has("serviceInfo")){
//							serviceUrlArray =    mappinglocationJSON.getJSONObject("serviceInfo").getJSONArray("serviceUrl");
//							otgchannelLocURLMap.put(controlGroup, serviceUrlArray);
//						}
//						if(mappinglocationJSON!=null && mappinglocationJSON.has("channelInfo")){
//							channelUrlArray =   mappinglocationJSON.getJSONObject("channelInfo").getJSONArray("channelUrl");
//							otgserviceURLMap.put(controlGroup, channelUrlArray);
//
//						}
//					}
//				} else{
//					throw new  Exception();
//				}
//			}else if(mappinglocation==null){
//				throw new Exception();
//			}
//
//
//		}catch(Exception e){
//			throw new Exception();
//		}
//		System.out.println(otgchannelLocURLMap);
//		
//	}
//	
	
	
	
}
