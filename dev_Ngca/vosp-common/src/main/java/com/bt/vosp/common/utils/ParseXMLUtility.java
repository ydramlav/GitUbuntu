package com.bt.vosp.common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.lang3.StringUtils;

import com.bt.vosp.common.model.MediaInputObject;
import com.bt.vosp.common.proploader.CommonGlobal;


public class ParseXMLUtility {
	// XML parser method for serviceType.xml, serviceBaseCIDChannelMapping.xml
	// and DeviceServiceTypeMapping.xml
	public Map<String, String> genricXMLParser(String keyTag, String filePath) throws XMLStreamException, IOException {

		InputStream fin = null;
		XMLEventReader eventReader = null;
		Map<String, String> values = null;
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			fin = new FileInputStream(filePath);
			eventReader = inputFactory.createXMLEventReader(fin);

			while (eventReader.hasNext()) {
				XMLEvent xmlEvent = eventReader.nextEvent();
				if (xmlEvent.isStartElement()) {
					StartElement startElement = xmlEvent.asStartElement();
					values = parseServiceTypeXML(eventReader, keyTag);

				}
			}
			CommonGlobal.LOGGER.debug("XML parsing is done successfully");
		} catch (XMLStreamException e) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("XMLStreamException during XML parsing : " + stackTrace.toString());
			throw e;
		} catch (FileNotFoundException e) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("FilenotfoundException during XML parsing : " + stackTrace.toString());
			throw e;
		} finally {
			if (eventReader != null) {
				try {
					eventReader.close();
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}
			}
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return values;
	}

	// For parsing Asset Type XML
	public Map<String, String> parseAssetTypeXML(String serviceType, String AssetTypeName, String AssetTypeDelivery,
			String filePath) throws IOException, FileNotFoundException, XMLStreamException {
		// List<String> values = new ArrayList<String>();
		Map<String, String> tempMap = null;
		String name = null;
		String deliveryType = "";
		String assetType = "";
		String[] key = new String[2];
		String[] value = new String[2];
		boolean serviceTypeFlag = false;
		boolean assetTypeDeliveryFlag = false;
		boolean streamingFlag = false;
		boolean downloadFlag;
		InputStream fin = null;
		XMLEventReader eventReader = null;
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		// String contentCatalogServiceType=null;
		try {
			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			fin = new FileInputStream(filePath);
			eventReader = inputFactory.createXMLEventReader(fin);

			while (eventReader.hasNext()) {
				XMLEvent xmlEvent = eventReader.nextEvent();
				if (xmlEvent.isStartElement()) {
					StartElement startElement = xmlEvent.asStartElement();
					if (startElement.getName().getLocalPart().equalsIgnoreCase("AssetType")) {
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							name = attribute.getValue();
						}
					}
					if (AssetTypeName.equalsIgnoreCase(name) && xmlEvent.isStartElement()) {
						if (startElement.getName().getLocalPart().equalsIgnoreCase("Service")) {
							Iterator<Attribute> attributes = startElement.getAttributes();
							while (attributes.hasNext()) {
								Attribute attribute = attributes.next();
								String attvalue = attribute.getValue();// .toString();
								if (attribute.getValue().toString().equalsIgnoreCase(serviceType)) {
									serviceTypeFlag = true;
								}

							}
						}
						if (startElement.getName().getLocalPart().equalsIgnoreCase("DeliveryType") && serviceTypeFlag) {
							tempMap = new HashMap<String, String>();
							int i = 0;
							Iterator<Attribute> attributes = startElement.getAttributes();
							while (attributes.hasNext()) {
								Attribute attribute = attributes.next();
								String attvalue = attribute.getValue();// .toString();

								tempMap.put(attribute.getName().toString(), attribute.getValue().toString());

								if (attribute.getValue().toString().equalsIgnoreCase(AssetTypeDelivery)) {
									deliveryType = attribute.getValue().toString();

								}
							}
							if (deliveryType.equalsIgnoreCase(AssetTypeDelivery))
								return tempMap;

						}
					}
				}
			}

		} catch (Exception e) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("Exception while parsing Asset Type XML :: "+stackTrace.toString());
		} finally {
			if (eventReader != null)
				eventReader.close();
			if (fin != null)
				fin.close();
		}
		return tempMap;
	}

	private Map<String, String> parseServiceTypeXML(XMLEventReader eventReader, String keyTag) throws XMLStreamException, IOException
	{
		// List<String> serviceTypevalues=new ArrayList<String>();
		Map<String, String> parseMap = new HashMap<String, String>();
		String id = null;
		String tagValue = null, tagType = null;
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		try{
			while (eventReader.hasNext()) {
				// try {
				XMLEvent xmlEvent = eventReader.nextEvent();
				if (xmlEvent.isStartElement()) {
					StartElement startElement = xmlEvent.asStartElement();
					if (startElement.getName().getLocalPart().equalsIgnoreCase("ServiceType")) {
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							// if
							// (attribute.getValue().equalsIgnoreCase(keyTag))
							id = attribute.getValue();
						}
					}
					if (keyTag.equalsIgnoreCase(id) && xmlEvent.isStartElement()) {
						// if(startElement.getName().getLocalPart().equalsIgnoreCase("service"))
						if (startElement.getName().getLocalPart().equalsIgnoreCase("type")) {
							xmlEvent = eventReader.nextEvent();
							tagType = xmlEvent.asCharacters().getData();
							continue;
						}
						if (startElement.getName().getLocalPart().equalsIgnoreCase("value")) {
							xmlEvent = eventReader.nextEvent();
							tagValue = xmlEvent.asCharacters().getData();

						}
					}
					// For
					// VoSPContentCatalogueServiceTypeToDeviceServiceTypeMapping
					if (startElement.getName().getLocalPart().equalsIgnoreCase("DeviceServiceType")) {
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							// if
							// (attribute.getValue().equalsIgnoreCase(keyTag))
							id = attribute.getValue();
							if (id.equalsIgnoreCase(keyTag)) {
								tagType = keyTag;
							}
						}
						if (tagType != null)
							continue;

					}
					if (keyTag.equalsIgnoreCase(id) && xmlEvent.isStartElement()) {
						if (startElement.getName().getLocalPart().equalsIgnoreCase("ContentCatalogueServiceType")) {
							xmlEvent = eventReader.nextEvent();
							tagValue = xmlEvent.asCharacters().getData();
						}
					}

					// For ServiceBasedCIDChannelMapping xml
					if (startElement.getName().getLocalPart().equalsIgnoreCase("ServiceBaseCID")) {
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							// if
							// (attribute.getValue().equalsIgnoreCase(keyTag))
							id = attribute.getValue();
							// tagType = id;
						}
					}
					if (keyTag.equalsIgnoreCase(id) && xmlEvent.isStartElement()) {
						if (startElement.getName().getLocalPart().equalsIgnoreCase("name")) {
							xmlEvent = eventReader.nextEvent();
							tagValue = xmlEvent.asCharacters().getData();
							tagType = tagValue;
						}
					}

				}
				if (tagType != null && tagValue != null) {
					parseMap.put(tagType, tagValue);
					tagType = null;
					tagValue = null;
				}

			}
		} catch (XMLStreamException e) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("XMLStreamException occurred while parsing :" +stackTrace.toString());
			throw e;
		}
		return parseMap;
	}

	// For Parsing product XML this method return all the attribute define under
	// different tags defined in XML
	private static Map<String, Map<String, String>> parseProductXML(String productID, String filePath)
	throws IOException, XMLStreamException {
		String name = null;
		Map<String, String> prodAttr = new HashMap<String, String>();
		Map<String, String> assetAttr = new HashMap<String, String>();
		Map<String, String> servicetAttr = new HashMap<String, String>();
		Map<String, String> featuretAttr = new HashMap<String, String>();
		Map<String, Map<String, String>> productMap = new HashMap<String, Map<String, String>>();
		String attrValue = null;
		FileInputStream fin = null;
		XMLEventReader eventReader = null;
		// try {
		// First create a new XMLInputFactory
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		// Setup a new eventReader
		fin = new FileInputStream(filePath);
		eventReader = inputFactory.createXMLEventReader(fin);

		while (eventReader.hasNext()) {
			XMLEvent xmlEvent = eventReader.nextEvent();
			if (xmlEvent.isStartElement()) {
				StartElement startElement = xmlEvent.asStartElement();
				if (startElement.getName().getLocalPart().equalsIgnoreCase("product")) {
					Iterator<Attribute> attributes = startElement.getAttributes();
					while (attributes.hasNext()) {
						Attribute attribute = attributes.next();
						prodAttr.put(attribute.getName().toString(), attribute.getValue());
					}
					productMap.put(startElement.getName().toString(), prodAttr);
				}
				if (startElement.getName().getLocalPart().equalsIgnoreCase("asset")) {
					Iterator<Attribute> attributes = startElement.getAttributes();
					while (attributes.hasNext()) {
						Attribute attribute = attributes.next();
						assetAttr.put(attribute.getName().toString(), attribute.getValue());
					}
					productMap.put(startElement.getName().toString(), assetAttr);
				}
				if (startElement.getName().getLocalPart().equalsIgnoreCase("service")) {
					Iterator<Attribute> attributes = startElement.getAttributes();
					while (attributes.hasNext()) {
						Attribute attribute = attributes.next();
						attrValue = attribute.getValue();
						servicetAttr.put(attribute.getName().toString() + attrValue, attribute.getValue());
					}
					productMap.put(startElement.getName().toString(), servicetAttr);
				}
				if (startElement.getName().getLocalPart().equalsIgnoreCase("feature")) {
					Iterator<Attribute> attributes = startElement.getAttributes();
					while (attributes.hasNext()) {
						Attribute attribute = attributes.next();
						// attrValue=attribute.getValue();
						featuretAttr.put(attribute.getName().toString() + attrValue, attribute.getValue());
					}
					productMap.put(startElement.getName().toString() + attrValue, featuretAttr);
				}
			}
		}
		/*
		 * } catch (Exception e) { e.printStackTrace(); } finally {
		 * eventReader.close(); fin.close(); }
		 */
		eventReader.close();
		fin.close();
		return productMap;
	}

	// For Parsing SMIL xml
	public static List<Map<String, String>> parseSMILXML(String filePath) throws XMLStreamException, IOException,
	FileNotFoundException {
		List<Map<String, String>> listofMap = new ArrayList<Map<String, String>>();
		Map<String, String> parseMap = null;
		FileInputStream fin = null;
		String tempValue = "";
		boolean mapFlag = false;
		boolean srcFlag = false;
		boolean titleFlag = false;
		boolean guidFlag = false;
		boolean securityFlag = false;
		boolean heightFlag = false;
		boolean widthFlag = false;
		boolean btGUIDFlag = false;
		XMLEventReader eventReader = null;
		// try {
		// First create a new XMLInputFactory
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		// Setup a new eventReader
		fin = new FileInputStream(filePath);
		eventReader = inputFactory.createXMLEventReader(fin);
		while (eventReader.hasNext()) {
			XMLEvent xmlEvent = eventReader.nextEvent();
			if (xmlEvent.isStartElement()) {
				StartElement startElement = xmlEvent.asStartElement();

				if (startElement.getName().getLocalPart().toString().equalsIgnoreCase("video")) {
					mapFlag = true;
					parseMap = new HashMap<String, String>();
					Iterator<Attribute> attributes = startElement.getAttributes();
					while (attributes.hasNext()) {
						Attribute attribute = attributes.next();
						tempValue = attribute.getName().toString();
						if (tempValue.equalsIgnoreCase("src")) {
							parseMap.put(tempValue, attribute.getValue().toString());
							srcFlag = true;
						}
						if (tempValue.equalsIgnoreCase("title")) {
							parseMap.put(tempValue, attribute.getValue().toString());
							titleFlag = true;
						}
						if (tempValue.equalsIgnoreCase("guid")) {
							parseMap.put(tempValue, attribute.getValue().toString());
							guidFlag = true;
						}
						if (tempValue.equalsIgnoreCase("security")) {
							parseMap.put(tempValue, attribute.getValue().toString());
							securityFlag = true;
						}
						if (tempValue.equalsIgnoreCase("height")) {
							parseMap.put(tempValue, attribute.getValue().toString());
							heightFlag = true;
						}
						if (tempValue.equalsIgnoreCase("width")) {
							parseMap.put(tempValue, attribute.getValue().toString());
							widthFlag = true;
						}
					}

				}
				if (startElement.getName().getLocalPart().toString().equalsIgnoreCase("param")) {
					Iterator<Attribute> attributes = startElement.getAttributes();
					while (attributes.hasNext()) {
						Attribute attribute = attributes.next();
						tempValue = attribute.getName().toString();
						if (tempValue.equalsIgnoreCase("name") && attribute.getValue().equalsIgnoreCase("btGuid")) {
							parseMap.put(tempValue, attribute.getValue().toString());
							btGUIDFlag = true;
						}
					}

				}
			}
			if (parseMap != null && mapFlag) {
				listofMap.add(parseMap);
				mapFlag = false;
			}
		}
		if (!srcFlag) {
			// Src attribute is missing in xml
		}
		if (!titleFlag) {
			// title attribute is missing in xml
		}
		if (!guidFlag) {
			// guid attribute is missing in xml
		}
		if (!securityFlag) {
			// security attribute is missing in xml
		}
		if (heightFlag) {
			// Height
		}
		if (widthFlag) {
			// width
		}
		if (btGUIDFlag) {
			// btGUID
		}

		/*
		 * } catch (XMLStreamException e) { e.printStackTrace(); } catch
		 * (IOException e) { e.printStackTrace(); } finally {
		 * eventReader.close(); fin.close(); }
		 */
		eventReader.close();
		fin.close();
		return listofMap;
	}
	@SuppressWarnings("unchecked")
	public MediaInputObject parseDeviceMappingXML(String keyTag,String filePath, String ISP, String service) throws XMLStreamException, IOException
	{
		// List<String> serviceTypevalues=new ArrayList<String>();
		MediaInputObject mediaInputObject= new MediaInputObject();
		//Map<String, String> parseMap = new HashMap<String, String>();
		InputStream fin = null;
		XMLEventReader eventReader = null;

    	String type=null;
		String tagType = null;
		String isp1=null;
		String cdn=null;
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		boolean typeFlag=false;
		boolean ISPFlag=false;
		try{
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			fin = new FileInputStream(filePath);
			eventReader = inputFactory.createXMLEventReader(fin);
			while (eventReader.hasNext()) {
				// try {
				XMLEvent xmlEvent = eventReader.nextEvent();
				if (xmlEvent.isStartElement()) {
					StartElement startElement = xmlEvent.asStartElement();
				
					if (startElement.getName().getLocalPart().equalsIgnoreCase("DeviceType")) {
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							// if
							// (attribute.getValue().equalsIgnoreCase(keyTag))
							 type = attribute.getValue();
						}
					}
					
						// if(startElement.getName().getLocalPart().equalsIgnoreCase("service"))
						if (startElement.getName().getLocalPart().equalsIgnoreCase("UserAgent")) {
							
							xmlEvent = eventReader.nextEvent();
							tagType = xmlEvent.asCharacters().getData();
							
							if(StringUtils.containsIgnoreCase(keyTag, tagType))
							{
							 mediaInputObject.setUserAgent(tagType);
							 mediaInputObject.setDeviceType(type);
						     CommonGlobal.LOGGER.info("UserAgent: " +tagType);
						     CommonGlobal.LOGGER.info("DeviceType: " +type);


							 typeFlag=true;
						}
						
						}
						if (startElement.getName().getLocalPart().equalsIgnoreCase("ServiceProvider")) {
							Iterator<Attribute> attributes = startElement.getAttributes();
							
							while (attributes.hasNext()) {
								Attribute attribute = attributes.next();
								
								// if
								// (attribute.getValue().equalsIgnoreCase(keyTag))
								isp1 = attribute.getValue();
								if(isp1.equalsIgnoreCase(ISP))
								{
								 ISPFlag=true;
								}
								else{
									ISPFlag=false;
								}
						}
						}
						if (startElement.getName().getLocalPart().equalsIgnoreCase("CDN")&& typeFlag && ISPFlag) {
							
							xmlEvent = eventReader.nextEvent();
						    cdn = xmlEvent.asCharacters().getData();
							mediaInputObject.setCDN(cdn);
						    CommonGlobal.LOGGER.info("CDN: " +cdn);

						}
							if (startElement.getName().getLocalPart().equalsIgnoreCase("AssetType")&& typeFlag && ISPFlag) {
		                        
								
								xmlEvent = eventReader.nextEvent();
							    tagType = xmlEvent.asCharacters().getData();
								if(tagType.contains(service))
								  {
									mediaInputObject.setAssetType(tagType);
								    CommonGlobal.LOGGER.info("AssetType: " +tagType);

									break;
								  }
							
							}
						
				
					}
				}
		
		}catch (XMLStreamException e) {
		
		
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("XMLStreamException occurred while parsing :" +stackTrace.toString());
			throw e;
		}
		return mediaInputObject;
	}
	
	
	
/*	public Map<String, String> parseServiceCDNXML(String filepath) throws XMLStreamException, IOException
	{
		// List<String> serviceTypevalues=new ArrayList<String>();
		Map<String, String> parseMap = new HashMap<String, String>();
		String ISP = null;
		
		
		XMLEventReader eventReader = null;
		InputStream fin = null;
		String  tagType = null;
		String tempValue = "";

		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		try{
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			fin = new FileInputStream(filepath);
			eventReader = inputFactory.createXMLEventReader(fin);
			while (eventReader.hasNext()) {
				// try {
				XMLEvent xmlEvent = eventReader.nextEvent();
				if (xmlEvent.isStartElement()) {
					StartElement startElement = xmlEvent.asStartElement();
					if (startElement.getName().getLocalPart().equalsIgnoreCase("ServiceProvider")) {
						Iterator<Attribute> attributes = startElement.getAttributes();
						
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							
							// if
							// (attribute.getValue().equalsIgnoreCase(keyTag))
							ISP = attribute.getValue();
							parseMap.put( attribute.getName().toString(),ISP);
					
						if (startElement.getName().getLocalPart().equalsIgnoreCase("CDN")) {
							xmlEvent = eventReader.nextEvent();
							tagType = xmlEvent.asCharacters().getData();
							parseMap.put(startElement.getName().getLocalPart(),tagType);
						}
					}
					}
						// if(startElement.getName().getLocalPart().equalsIgnoreCase("service"))
						
						
					
				}
		} 
		}catch (XMLStreamException e) {
		
		
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("XMLStreamException occurred while parsing :" +stackTrace.toString());
			throw e;
		}
		return parseMap;
	}*/
	

}
