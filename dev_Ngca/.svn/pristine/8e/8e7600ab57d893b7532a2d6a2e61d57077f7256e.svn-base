package com.bt.vosp.common.utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.SmilBeans;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;

public class ParseSMIL {

	@SuppressWarnings("unchecked")
	public List<SmilBeans> parseSMILString(String smilString) throws Exception {
		
		CommonGlobal.LOGGER.debug("parsing smil file is started ::");
		
		List<SmilBeans> smilBeanslist = new ArrayList<SmilBeans>();
		SmilBeans smilbeans =  new SmilBeans();
		ByteArrayInputStream xmlStream = new ByteArrayInputStream(smilString.getBytes());
		// FileInputStream fin = null;
		String tempValue = "";
		boolean mapFlag = false;
		boolean srcFlag = false;
		boolean titleFlag = false;
		boolean guidFlag = false;
		boolean securityFlag = false;
		boolean heightFlag = false;
		boolean widthFlag = false;
		boolean btGUIDFlag = false;
		@SuppressWarnings("unused")
		boolean typeFlag = false;
		XMLEventReader eventReader = null;
		long startTime = System.currentTimeMillis();
		boolean responseCodeFlag =false;
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		try {
			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			// fin = new FileInputStream(xmlStream);
			// InputSource is = new InputSource(new StringReader(smilString));
			eventReader = inputFactory.createXMLEventReader(xmlStream);
			while (eventReader.hasNext()) {
				XMLEvent xmlEvent = eventReader.nextEvent();
				if (xmlEvent.isStartElement()) {
					StartElement startElement = xmlEvent.asStartElement();

					if (startElement.getName().getLocalPart().toString().equalsIgnoreCase("video")) {
						mapFlag = true;
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							tempValue = attribute.getName().toString();
							if (tempValue.equalsIgnoreCase("src")) {
								smilbeans.setStreamingURL(attribute.getValue().toString());
								
								CommonGlobal.LOGGER.info("streaming url ::"+smilbeans.getStreamingURL());
								
								srcFlag = true;
							}
							if (tempValue.equalsIgnoreCase("title")) {
								smilbeans.setTitle(attribute.getValue().toString());
								titleFlag = true;
							}
							if (tempValue.equalsIgnoreCase("guid")) {
								smilbeans.setGuid(attribute.getValue().toString());
								
								CommonGlobal.LOGGER.info("guid ::"+smilbeans.getGuid());
								
								guidFlag = true;
							}
							if (tempValue.equalsIgnoreCase("dur")) {
								String duration=attribute.getValue().toString();
								if(!duration.trim().isEmpty()){
		    		            	String[] src1 = duration.split("ms");
		    		            	
		    		            	duration=src1[0];
		    		            	}    
								smilbeans.setDuration(duration);
								securityFlag = true;
							}
							if (tempValue.equalsIgnoreCase("type")) {
								smilbeans.setContentType(attribute.getValue().toString());
								String type=attribute.getValue().toString();
								if(!type.trim().isEmpty()){
									String[] src1 = type.split("/");
									type=src1[1];
								}    
								smilbeans.setContentType(type);
								
								CommonGlobal.LOGGER.info("Content Type ::"+smilbeans.getContentType());
								
								typeFlag = true;
							}
							if (tempValue.equalsIgnoreCase("height")) {
								smilbeans.setHeight(attribute.getValue().toString());
								heightFlag = true;
							}
							if (tempValue.equalsIgnoreCase("width")) {
								smilbeans.setWidth(attribute.getValue().toString());
								widthFlag = true;
							}
						}

					}
					if (startElement.getName().getLocalPart().toString().equalsIgnoreCase("ref")) {
						mapFlag=true;
						
						Iterator<Attribute> atributes = startElement.getAttributes();
						while (atributes.hasNext()) {
							Attribute attribute = atributes.next();
							tempValue = attribute.getName().toString();
						
							if (tempValue.equalsIgnoreCase("title")) {
								smilbeans.setTitle(attribute.getValue().toString());
								
							//	CommonGlobal.LOGGER.error("Error response received from selector ::"+smilbeans.getTitle());
								
								titleFlag = true;
							}
						}
					}
					if (startElement.getName().getLocalPart().toString().equalsIgnoreCase("param")) {
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							tempValue = attribute.getName().toString();
							if (tempValue.equalsIgnoreCase("name") && attribute.getValue().equalsIgnoreCase("clientAssetId")) {
								btGUIDFlag = true;
							}
							
							if (tempValue.equalsIgnoreCase("value") && btGUIDFlag) {
								smilbeans.setBtGuid(attribute.getValue().toString());
								
								CommonGlobal.LOGGER.info("Bt Guid / clientAssetId::"+smilbeans.getBtGuid());
								
								btGUIDFlag = false;
							}
							if (tempValue.equalsIgnoreCase("name") && attribute.getValue().equalsIgnoreCase("responseCode")) {
								responseCodeFlag =true;
							}
							if (tempValue.equalsIgnoreCase("value") && responseCodeFlag )
							{
								
								CommonGlobal.LOGGER.error("Error response received from selector ::"+attribute.getValue().toString()+smilbeans.getTitle());
								smilbeans.setResponseCode(attribute.getValue().toString());
								responseCodeFlag =false;
								mapFlag = true;

							}
							
						}

					}
				}
				if (smilbeans != null && mapFlag) {
					if(smilbeans.getDuration()==null)
					{
						smilbeans.setDuration("");
					}
					smilBeanslist.add(smilbeans);
					mapFlag = false;
				}
				}
			if (!srcFlag) {
				// throw new XMLParseException();
			}
			if (!titleFlag) {
				// throw new XMLParseException();
			}
			if (!guidFlag) {
				// throw new XMLParseException();
			}
			if (!securityFlag) {
				// throw new XMLParseException();
			}
			if (heightFlag) {
				// throw new XMLParseException();
			}
			if (widthFlag) {
				// throw new XMLParseException();
			}
			if (btGUIDFlag) {
				// throw new XMLParseException();
			}
			
			
			CommonGlobal.LOGGER.debug("parsing smil file is ended ::");
			
		} catch (Exception e) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("Exception in parsing smil file :: "+stackTrace.toString());
			throw e;
		} finally {
			eventReader.close();
			xmlStream.close();
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Parse SMIL File :" + endTime + ",");
				nftLoggingTime = null;
			}
		
		}
		return smilBeanslist;
	}

	
	
	
	public List<SmilBeans> parseSMILString1(String smilString) throws Exception {
		
		CommonGlobal.LOGGER.debug("parsing smil file is started ::");
		FileInputStream fin = null;

		List<SmilBeans> smilBeanslist = new ArrayList<SmilBeans>();
		SmilBeans smilbeans =  new SmilBeans();
		ByteArrayInputStream xmlStream = new ByteArrayInputStream(smilString.getBytes());
		// FileInputStream fin = null;
		String tempValue = "";
		boolean mapFlag = false;
		boolean srcFlag = false;
		boolean titleFlag = false;
		boolean guidFlag = false;
		boolean securityFlag = false;
		boolean heightFlag = false;
		boolean widthFlag = false;
		boolean btGUIDFlag = false;
		boolean absFlag=false;
	    boolean responseMsgFlag=false;
		boolean exceptionFlag =false;
		boolean categoriesFlag=false;
	    boolean secureFlag=false;
	    boolean updateLockIntervalFlag=false;
	    boolean concurrencyServiceUrlFlag=false;
	    boolean lockIdFlag=false;
	    boolean lockSequenceTokenFlag=false;
	    boolean lockFlag=false;
	    
		@SuppressWarnings("unused")
		boolean typeFlag = false;
		XMLEventReader eventReader = null;
		long startTime = System.currentTimeMillis();
		boolean responseCodeFlag =false;
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		try {
			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			//mock
			
			// Setup a new eventReader
			// fin = new FileInputStream(xmlStream);
			// InputSource is = new InputSource(new StringReader(smilString));
			//eventReader = inputFactory.createXMLEventReader(fin);
			eventReader = inputFactory.createXMLEventReader(xmlStream);

			while (eventReader.hasNext()) {
				XMLEvent xmlEvent = eventReader.nextEvent();
				if (xmlEvent.isStartElement()) {
					StartElement startElement = xmlEvent.asStartElement();
					if (startElement.getName().getLocalPart().toString().equalsIgnoreCase("meta"))
					{
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							tempValue = attribute.getName().toString();
							if (tempValue.equalsIgnoreCase("name") && attribute.getValue().equalsIgnoreCase("updateLockInterval")) {
								updateLockIntervalFlag = true;
							}
							
							if (tempValue.equalsIgnoreCase("content") && updateLockIntervalFlag) {
								smilbeans.setUpdateLockInterval(attribute.getValue().toString());
								
								
								btGUIDFlag = false;
							}
							if (tempValue.equalsIgnoreCase("name") && attribute.getValue().equalsIgnoreCase("concurrencyServiceUrl")) {
								concurrencyServiceUrlFlag = true;
							}
							if (tempValue.equalsIgnoreCase("content") && concurrencyServiceUrlFlag )
							{
								
								smilbeans.setConcurrencyServiceUrl(attribute.getValue().toString());
								exceptionFlag =false;
								mapFlag = true;

							}
							if (tempValue.equalsIgnoreCase("name") && attribute.getValue().equalsIgnoreCase("lockId")) {
								lockIdFlag = true;
								}
								if (tempValue.equalsIgnoreCase("content") && lockIdFlag )
								{
									
									smilbeans.setLockId(attribute.getValue().toString());
									responseMsgFlag =false;
									mapFlag = true;

								}


							if (tempValue.equalsIgnoreCase("name") && attribute.getValue().equalsIgnoreCase("lockSequenceToken")) {
								lockSequenceTokenFlag =true;
							}
							if (tempValue.equalsIgnoreCase("content") && lockSequenceTokenFlag )
							{
								
								smilbeans.setLockSequenceToken(attribute.getValue().toString());
								responseCodeFlag =false;
								mapFlag = true;

							}
							if (tempValue.equalsIgnoreCase("name") && attribute.getValue().equalsIgnoreCase("lock")) {
								lockFlag =true;
							}
							if (tempValue.equalsIgnoreCase("content") && lockFlag )
							{
								
								smilbeans.setLock(attribute.getValue().toString());
								responseCodeFlag =false;
								mapFlag = true;

							}
							
						}
					}
						
						
						
						
						
					if (startElement.getName().getLocalPart().toString().equalsIgnoreCase("video")) {
						mapFlag = true;
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							tempValue = attribute.getName().toString();
							if (tempValue.equalsIgnoreCase("src")) {
								smilbeans.setStreamingURL(attribute.getValue().toString());
								
								CommonGlobal.LOGGER.info("streaming url ::"+smilbeans.getStreamingURL());
								
								srcFlag = true;
							}
							if (tempValue.equalsIgnoreCase("title")) {
								smilbeans.setTitle(attribute.getValue().toString());
								titleFlag = true;
							}
							if (tempValue.equalsIgnoreCase("abstract")) {
								smilbeans.setAbs(attribute.getValue().toString());
								absFlag = true;
							}
							
							if (tempValue.equalsIgnoreCase("guid")) {
								smilbeans.setGuid(attribute.getValue().toString());
								
								CommonGlobal.LOGGER.info("guid ::"+smilbeans.getGuid());
								
								guidFlag = true;
							}
							
							if (tempValue.equalsIgnoreCase("categories")) {
								smilbeans.setCategories(attribute.getValue().toString());
								categoriesFlag = true;
							}
							if (tempValue.equalsIgnoreCase("dur")) {
								String duration=attribute.getValue().toString();
								if(!duration.trim().isEmpty()){
		    		            	String[] src1 = duration.split("ms");
		    		            	
		    		            	duration=src1[0];
		    		            	}    
								smilbeans.setDuration(duration);
								securityFlag = true;
							}
							if (tempValue.equalsIgnoreCase("type")) {
								smilbeans.setContentType(attribute.getValue().toString());
								String type=attribute.getValue().toString();
								if(!type.trim().isEmpty()){
									String[] src1 = type.split("/");
									type=src1[1];
								}    
								smilbeans.setContentType(type);
								
								CommonGlobal.LOGGER.info("Content Type ::"+smilbeans.getContentType());
								
								typeFlag = true;
							}
							if (tempValue.equalsIgnoreCase("security")) {
								smilbeans.setSecurity(attribute.getValue().toString());
								secureFlag = true;
							}
							if (tempValue.equalsIgnoreCase("height")) {
								smilbeans.setHeight(attribute.getValue().toString());
								heightFlag = true;
							}
							if (tempValue.equalsIgnoreCase("width")) {
								smilbeans.setWidth(attribute.getValue().toString());
								widthFlag = true;
							}
						}

					}
					if (startElement.getName().getLocalPart().toString().equalsIgnoreCase("ref")) {
						mapFlag=true;
						
						Iterator<Attribute> atributes = startElement.getAttributes();
						while (atributes.hasNext()) {
							Attribute attribute = atributes.next();
							tempValue = attribute.getName().toString();
						
							if (tempValue.equalsIgnoreCase("title")) {
								smilbeans.setTitle(attribute.getValue().toString());
								
							//	CommonGlobal.LOGGER.error("Error response received from selector ::"+smilbeans.getTitle());
								
								titleFlag = true;
							}
							if (tempValue.equalsIgnoreCase("src")) {
								smilbeans.setTitle(attribute.getValue().toString());
								
							//	CommonGlobal.LOGGER.error("Error response received from selector ::"+smilbeans.getTitle());
								smilbeans.setStreamingURL(attribute.getValue().toString());

								srcFlag = true;

							}
							if (tempValue.equalsIgnoreCase("abstract")) {
								smilbeans.setTitle(attribute.getValue().toString());
								
							//	CommonGlobal.LOGGER.error("Error response received from selector ::"+smilbeans.getTitle());
								smilbeans.setAbs(attribute.getValue().toString());

								

							}
						}
					}
					if (startElement.getName().getLocalPart().toString().equalsIgnoreCase("param")) {
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							tempValue = attribute.getName().toString();
							if (tempValue.equalsIgnoreCase("name") && attribute.getValue().equalsIgnoreCase("clientAssetId")) {
								btGUIDFlag = true;
							}
							
							if (tempValue.equalsIgnoreCase("value") && btGUIDFlag) {
								smilbeans.setBtGuid(attribute.getValue().toString());
								
								CommonGlobal.LOGGER.info("Bt Guid / clientAssetId::"+smilbeans.getBtGuid());
								
								btGUIDFlag = false;
							}
							if (tempValue.equalsIgnoreCase("name") && attribute.getValue().equalsIgnoreCase("isException")) {
							  exceptionFlag = true;
							}
							if (tempValue.equalsIgnoreCase("value") && exceptionFlag )
							{
								
								CommonGlobal.LOGGER.error("Exception received from selector ::"+attribute.getValue().toString());
								smilbeans.setException(Boolean.valueOf(attribute.getValue().toString()));
								exceptionFlag =false;
								mapFlag = true;

							}
							if (tempValue.equalsIgnoreCase("name") && attribute.getValue().equalsIgnoreCase("exception")) {
								   responseMsgFlag = true;
								}
								if (tempValue.equalsIgnoreCase("value") && responseMsgFlag )
								{
									
									CommonGlobal.LOGGER.error("Exception received from selector ::"+attribute.getValue().toString());
									smilbeans.setResponseMsg(attribute.getValue().toString());
									responseMsgFlag =false;
									mapFlag = true;

								}


							if (tempValue.equalsIgnoreCase("name") && attribute.getValue().equalsIgnoreCase("responseCode")) {
								responseCodeFlag =true;
							}
							if (tempValue.equalsIgnoreCase("value") && responseCodeFlag )
							{
								
								CommonGlobal.LOGGER.error("Error response received from selector ::"+attribute.getValue().toString()+smilbeans.getTitle());
								smilbeans.setResponseCode(attribute.getValue().toString());
								responseCodeFlag =false;
								mapFlag = true;

							}
							
						}

					}
				}
				if (smilbeans != null && mapFlag) {
					if(smilbeans.getDuration()==null)
					{
						smilbeans.setDuration("");
					}
					smilBeanslist.add(smilbeans);
					mapFlag = false;
				}
				}
			if (!srcFlag) {
				// throw new XMLParseException();
			}
			if(!absFlag){
				
			}
			if (!titleFlag) {
				// throw new XMLParseException();
			}
			if (!guidFlag) {
				// throw new XMLParseException();
			}
			if (!securityFlag) {
				// throw new XMLParseException();
			}
			if (heightFlag) {
				// throw new XMLParseException();
			}
			if (widthFlag) {
				// throw new XMLParseException();
			}
			if (btGUIDFlag) {
				// throw new XMLParseException();
			}
			if (!categoriesFlag) {
				// throw new XMLParseException();
			}
			if (!secureFlag) {
				// throw new XMLParseException();
			}
			
			
			CommonGlobal.LOGGER.debug("parsing smil file is ended ::");
			
		} catch (Exception e) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("Exception in parsing smil file :: "+stackTrace.toString());
			throw e;
		} finally {
			eventReader.close();
			xmlStream.close();
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Parse SMIL File :" + endTime + ",");
				nftLoggingTime = null;
			}
		
		}
		return smilBeanslist;
	}

}
