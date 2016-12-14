package com.bt.vosp.common.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.bt.vosp.common.model.BlockedDevicesInXML;
import com.bt.vosp.common.proploader.CommonGlobal;

public class BlockedDeviceParser extends DefaultHandler {

	BlockedDevicesInXML blockedDevicesInXML = null;

	/**
	 * Parses the document.
	 *
	 * @param fileName the file name
	 * @throws Exception the exception
	 */
	public void parseDocument(String fileName) throws Exception {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		//get a factory
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		try {
			//get a new instance of parser
			SAXParser saxParser = saxParserFactory.newSAXParser();
			//parse the file and also register this class for call backs
			saxParser.parse(fileName, this);

		} catch(SAXException se) {
			throw se;
		} catch(ParserConfigurationException pce) {
			throw pce;
		} catch (IOException ie) {
			//ie.printStackTrace();
			throw ie;
		} catch (Exception e) {
			CommonGlobal.LOGGER.error("Exception while parsing config xml");

			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("VOSPBusinessException occured. Reason:" + e.getMessage());
			CommonGlobal.LOGGER.info("Stack Trace :: " + stringWriter.toString());
		}
	}

	//Event Handlers
	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if(qName.equalsIgnoreCase("listOfBlockedDevices")) {
			blockedDevicesInXML = new BlockedDevicesInXML();
		} else if(qName.equalsIgnoreCase("deviceGroup")) {
			String makeValue = attributes.getValue("make");
			blockedDevicesInXML.setMakeValue(makeValue);
		} else if(qName.equalsIgnoreCase("model")) {
			String model = attributes.getValue("name");
			blockedDevicesInXML.saveModel(model);
		}
	}



	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if (qName.equalsIgnoreCase("deviceGroup")) {
			CommonGlobal.blockedDevices.addAll(blockedDevicesInXML);
		} else if (qName.equalsIgnoreCase("listOfBlockedDevices")) {
			CommonGlobal.blockedDevices.retainAll(blockedDevicesInXML);
		}
	}
}


