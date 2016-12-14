package com.bt.vosp.common.utils;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.bt.vosp.common.model.ProductInfoRequestObject;
import com.bt.vosp.common.model.ProductResponseBean;
import com.bt.vosp.common.proploader.CommonGlobal;


public class ParseProductXML {

	@SuppressWarnings("unchecked")
	public ProductResponseBean parseProductXML(ProductInfoRequestObject productInfoRequest) throws Exception  {
		//JSONObject jsonObj = new JSONObject();

		CommonGlobal.LOGGER.debug("parse product xml file is started");

		ProductResponseBean prodBeans=new ProductResponseBean();
		//InputStream fin = null;
		Map<String,String> map=new HashMap<String, String>();
		XMLEventReader eventReader = null;

		ParseXMLUtility parseXMLUtility = new ParseXMLUtility();

		// String contentCatalogServiceType=null;
		String deviceServiceFilePath=productInfoRequest.getDeviceServiceTypeFilePath();
		String deviceServiceType=productInfoRequest.getServiceType();

		CommonGlobal.LOGGER.info("device Service File Path ::"+deviceServiceFilePath);

		//	CommonGlobal.LOGGER.info("device Service type ::"+deviceServiceFilePath);
		ByteArrayInputStream xmlStream = null;
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		try{
			map=parseXMLUtility.genricXMLParser(deviceServiceType,deviceServiceFilePath);



			String serviceType=map.get(deviceServiceType);
			String slotType=productInfoRequest.getSlotType();

			CommonGlobal.LOGGER.info("slot type ::"+slotType);

			String fileContent=productInfoRequest.getProductInfoContent();
			xmlStream = new ByteArrayInputStream(fileContent.getBytes());

			String name = "";
			String hd="";
			String genre="";
			String rating="";
			
			boolean serviceTypeFlag = false;
			//boolean availableDateFlag = false;
			boolean serviceTypeFlag1 = false;
			boolean slotTyepeFlag = false;
			boolean releasePIDFlag = false;
			boolean releaseIDFlag = false;
			boolean filesizeFlag = false;
			boolean proctetedFlag = false;
			boolean schemeFlag = false;
			boolean hdFlag = false;
			boolean downloadPlayFlag= false;
			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			//fin = new FileInputStream(filePath);
			eventReader = inputFactory.createXMLEventReader(xmlStream);
			// eventReader = XMLFileLoaderUtility.getXmlEventReader(filePath);
			while (eventReader.hasNext()) {
				XMLEvent xmlEvent = eventReader.nextEvent();
				if (xmlEvent.isStartElement()) {
					StartElement startElement = xmlEvent.asStartElement();
					/*	if (startElement.getName().getLocalPart().toString().equalsIgnoreCase("product")) {
	    				Iterator<Attribute> attributes = startElement.getAttributes();
	    				while (attributes.hasNext()) {
	    					Attribute attribute = attributes.next();
	    					if (attribute.getName().toString().equalsIgnoreCase("availability")) {
	    						prodBeans.setAvaibilityDate(attribute.getValue().toString());
	    						availableDateFlag = true;
	    					}

	    				}
	    			}*/

					if (startElement.getName().getLocalPart().toString().equalsIgnoreCase("release")) {


						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							name = attribute.getName().toString();
							if (name.equalsIgnoreCase("service")) {
								if(attribute.getValue().toString().equalsIgnoreCase(serviceType)){
									slotTyepeFlag = true;
									prodBeans.setSlotType(slotType);
                                    prodBeans.setServiceType(serviceType);
									serviceTypeFlag = true;
									break;
								}
								else{
									serviceTypeFlag = false;
								}
							}
						}

						if ( serviceTypeFlag )	
						{
							String releasePID[]=null;
							String releaseid="";
							String filesize="";
							//String prtotectionScheme="";
							String scheme="";
							Iterator<Attribute> attributesRelease = startElement.getAttributes();
							while (attributesRelease.hasNext()) {

								Attribute attribute = attributesRelease.next();
								name = attribute.getName().toString();
								if (name.equalsIgnoreCase("PID")) 
								{
									releasePID=(attribute.getValue().toString()).split("/"); 


									prodBeans.setReleasePID(releasePID[releasePID.length-1]);
									releasePIDFlag = true;
									if(releasePID.length>1){
										prodBeans.setselectorURL(attribute.getValue().toString());

									}
							//		CommonGlobal.LOGGER.info("PID" +prodBeans.getReleasePID());
								}
								if (name.equalsIgnoreCase("releaseid")) 
								{
									releaseid=attribute.getValue(); 
									
									prodBeans.setReleaseID(releaseid);
									releaseIDFlag = true;
							//		CommonGlobal.LOGGER.info("releaseid" +prodBeans.getReleaseID());
								}
								if (name.equalsIgnoreCase("filesize")) 
								{
									filesize=attribute.getValue().toString(); 
									prodBeans.setFileSize(filesize);
									filesizeFlag = true;
									CommonGlobal.LOGGER.info("filesize" +prodBeans.getFileSize());
								}
								if (name.equalsIgnoreCase("protected")) 
								{
									if (attribute.getValue().equals("1")) {
										prodBeans.setProtected(true);
										proctetedFlag = true;
									} else {
										prodBeans.setProtected(false);
										proctetedFlag = true;
									}
								//	CommonGlobal.LOGGER.info("protected value ::"+prodBeans.isProtected());
								}
								if (name.equalsIgnoreCase("scheme")) 
								{
									scheme=attribute.getValue().toString(); 
									prodBeans.setProtectionSchema(scheme);
									schemeFlag = true;
							//	CommonGlobal.LOGGER.info("scheme" +prodBeans.getProtectionSchema());
								}

							}


						}



					}
					if (startElement.getName().getLocalPart().toString().equalsIgnoreCase("asset")) {
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equalsIgnoreCase("downloadplay")) {

								if (attribute.getValue().equals("1")) {
									prodBeans.setDownloadPlay("true");

								} else {
									prodBeans.setDownloadPlay("false");
								}

						//		CommonGlobal.LOGGER.info("Download Play value ::"+prodBeans.getDownloadPlay());

								downloadPlayFlag = true;
							}
							if (attribute.getName().toString().equalsIgnoreCase("hd")) {
								hd=attribute.getValue().toString();
								if(hd.equals("0")){
									prodBeans.setAssetType("SD");
								}else
									prodBeans.setAssetType("HD");
								hdFlag = true;
							}
							if (attribute.getName().toString().equalsIgnoreCase("genre"))
							{
								genre=attribute.getValue().toString();
								prodBeans.setGenre(genre);
							}
							if (attribute.getName().toString().equalsIgnoreCase("schedulerChannel"))
							{
								String schedulerChannel=attribute.getValue().toString();
								prodBeans.setSchedulerChannel(schedulerChannel);
							}
							
							if (attribute.getName().toString().equalsIgnoreCase("subGenre"))
							{
								
								prodBeans.setSubGenre(attribute.getValue().toString());
							}
							if (attribute.getName().toString().equalsIgnoreCase("ClientAssetId"))
							{
								
								prodBeans.setFeatureProgramID(attribute.getValue().toString());
							}
							if (attribute.getName().toString().equalsIgnoreCase("rating")) {
								prodBeans.setRating(attribute.getValue().toString());
							}
						}
					}
					if (startElement.getName().getLocalPart().toString().equalsIgnoreCase("category")) 
					{
					
					
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equalsIgnoreCase("id")) {
								prodBeans.setCategory(attribute.getValue().toString());

						//		CommonGlobal.LOGGER.info("serviceType value ::"+prodBeans.getServiceType());

							} else {
							//	serviceTypeFlag = false;
							}
						}
					
					
					}
					if (startElement.getName().getLocalPart().toString().equalsIgnoreCase("product")) 
					{
					
					
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equalsIgnoreCase("content_provider")) {
								prodBeans.setContentProviderId(attribute.getValue().toString());

					
							} else {
								//serviceTypeFlag = false;
							}
							if (attribute.getName().toString().equalsIgnoreCase("prices")) {
								prodBeans.setPrices(attribute.getValue().toString());
							}
							if (attribute.getName().toString().equalsIgnoreCase("sids")) {
								prodBeans.setSids(attribute.getValue().toString());
							}
							if (attribute.getName().toString().equalsIgnoreCase("rating")) {
								prodBeans.setRating(attribute.getValue().toString());
							}
						}
					
					
					}
					if (startElement.getName().getLocalPart().toString().equalsIgnoreCase("contentProvider")) 
					{
					
					
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equalsIgnoreCase("id")) {
								prodBeans.setContentProviderId(attribute.getValue().toString());

					
							} 
						}
					}
					if (startElement.getName().getLocalPart().toString().equalsIgnoreCase("service")) {
						// xmlEvent=eventReader.nextEvent();
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getValue().toString().equalsIgnoreCase(serviceType)) {
								name = serviceType;
								prodBeans.setServiceType(name);
								serviceTypeFlag = true;
								serviceTypeFlag1 = true;

						//		CommonGlobal.LOGGER.info("serviceType value ::"+prodBeans.getServiceType());

							} else {
								serviceTypeFlag = false;
							}
						}
					}

					if (startElement.getName().getLocalPart().toString().equalsIgnoreCase(slotType) && serviceTypeFlag) {
						// xmlEvent=eventReader.nextEvent();
						prodBeans.setSlotType(slotType);
						slotTyepeFlag = true;
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							String src="";
							String releasePID="";
							if (attribute.getName().toString().equalsIgnoreCase("src")) {
								src=attribute.getValue().toString();
								if(!src.trim().isEmpty()){
									String[] src1 = src.split("/");
									
									releasePID=src1[src1.length-1];
								}    
								prodBeans.setReleasePID(releasePID);
								prodBeans.setselectorURL(src);

						//		CommonGlobal.LOGGER.info("releasePID value ::"+src);

								releasePIDFlag = true;
							}
//							if (attribute.getName().toString().equalsIgnoreCase("src")) {
//								src=attribute.getValue().toString();
//								if(!src.trim().isEmpty()){
//									String[] src1 = src.split("=");
//									String src2=src1[src1.length-2];
//									String [] src3=src2.split("&");
//									releasePID=src3[0];
//								}    
//								prodBeans.setReleasePID(releasePID);
//								prodBeans.setselectorURL(src);
//
//						//		CommonGlobal.LOGGER.info("releasePID value ::"+src);
//
//								releasePIDFlag = true;
//							}

							if (attribute.getName().toString().equalsIgnoreCase("releaseid")) {
								prodBeans.setReleaseID(attribute.getValue());
							//	CommonGlobal.LOGGER.info("releaseid value ::"+prodBeans.getReleaseID());
								releaseIDFlag = true;
							}
							if (attribute.getName().toString().equalsIgnoreCase("filesize")) {
								prodBeans.setFileSize(attribute.getValue().toString());
								filesizeFlag = true;
							}

							if (attribute.getName().toString().equalsIgnoreCase("protected")) {
								if (attribute.getValue().equals("1")) {
									prodBeans.setProtected(true);
									proctetedFlag = true;
								} else {
									prodBeans.setProtected(false);
									proctetedFlag = true;
								}
							//	CommonGlobal.LOGGER.info("protected value ::"+prodBeans.isProtected());
							}

							if (attribute.getName().toString().equalsIgnoreCase("scheme")) {
								prodBeans.setProtectionSchema(attribute.getValue().toString());
								schemeFlag = true;
							}

						}
					}
				}
			}
			if (!serviceTypeFlag1)
				//prodBeans.setAvaibilityDate("");
				/*if (!availableDateFlag)
	    			prodBeans.setAvaibilityDate("");*/
			
				if (!slotTyepeFlag){
					throw new Exception();
				}
			if (!releasePIDFlag)
				prodBeans.setReleasePID("");
			if (!releaseIDFlag)
				prodBeans.setReleaseID("");
			if (!schemeFlag)
				prodBeans.setProtectionSchema("");
			if (!filesizeFlag)
				prodBeans.setFileSize("");
			if (!proctetedFlag)
				prodBeans.setProtected(false);
			if (!downloadPlayFlag)
				prodBeans.setDownloadPlay("false");
			if (!hdFlag)
				prodBeans.setAssetType("SD");


			CommonGlobal.LOGGER.debug("parse product xml file is ended");

		}/*catch (XMLParseException e) { 
			 throw new XMLParseException();
		 }catch(FileNotFoundException e){ 
			 throw new FileNotFoundException();
		 }catch(Exception e){
			 e.printStackTrace();
			 throw new Exception();
		 }*/ catch (FileNotFoundException e) {
			 stackTrace = new StringWriter();
			 printWriter = new PrintWriter(stackTrace);
			 e.printStackTrace(printWriter);
			 CommonGlobal.LOGGER.error("FileNotFoundException occurred while parsing ProductXML file : " + stackTrace.toString());
			 throw e;
		 } catch (XMLStreamException e) {
			 stackTrace = new StringWriter();
			 printWriter = new PrintWriter(stackTrace);
			 e.printStackTrace(printWriter);
			 CommonGlobal.LOGGER.error("XMLStreamException occurred while parsing ProductXML file : " + stackTrace.toString());
			 throw e;
		 } catch (Exception e) {
			 stackTrace = new StringWriter();
			 printWriter = new PrintWriter(stackTrace);
			 e.printStackTrace(printWriter);
			 CommonGlobal.LOGGER.error("Exception occurred while parsing ProductXML file : " + stackTrace.toString());
			 throw e;
		 }finally {
			 try{
				 if(eventReader!=null)
				 {
					 eventReader.close();
				 }
				 if(xmlStream!=null)
				 {
					 xmlStream.close();
				 }
			 }catch (XMLStreamException e) {
				 CommonGlobal.LOGGER.error("XMLStreamException occurred while parsing ProductXML file : " + e.getMessage());
				 throw e;
			 } catch (IOException e) {
				 CommonGlobal.LOGGER.error("IOException occurred while parsing ProductXML file : " + e.getMessage());
				 throw e;
			 }
		 }
		 return prodBeans;

	}

}
