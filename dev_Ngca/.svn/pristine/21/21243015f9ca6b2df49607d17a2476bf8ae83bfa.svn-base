package com.bt.vosp.daa.commons.impl.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

public class ScodeGrpParser extends DefaultHandler {


	/**
	 * Parses the document.
	 *
	 * @param FileName the file name
	 * @throws Exception the exception
	 */
	public void parseDocument(String FileName) throws Exception {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		//get a factory
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		try {
			//get a new instance of parser
			SAXParser saxParser = saxParserFactory.newSAXParser();
			//parse the file and also register this class for call backs
			saxParser.parse(FileName, this);

		} catch(SAXException se) {
			throw se;
		} catch(ParserConfigurationException pce) {
			throw pce;
		} catch (IOException ie) {
			//ie.printStackTrace();
			throw ie;
		} catch (Exception e) {
			DAAGlobal.LOGGER.error("Exception while parsing config xml");

			e.printStackTrace(printWriter);
			DAAGlobal.LOGGER.error("VOSPBusinessException occurred. Reason:" + e.getMessage());
			DAAGlobal.LOGGER.info("Stack Trace :: " + stringWriter.toString());

		}
	}

	//Event Handlers
	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if(qName.equalsIgnoreCase("ScodeGroup")) {
			refreshScodeGrpMapping(attributes);
		}

	}


	/**
	 * @param attributes
	 */
	public void refreshScodeGrpMapping(Attributes attributes) {
		String key;
		int valueFrmXML;
		int valueFrmMap;

		key = attributes.getValue("scode").toUpperCase();
		valueFrmXML = Integer.valueOf(attributes.getValue("maxDeviceLimit"));

		if (DAAGlobal.scodesToDeviceLimitMapping.containsKey(key)) {
			valueFrmMap = DAAGlobal.scodesToDeviceLimitMapping.get(key);
		} else {
			valueFrmMap = -1;
		}

		if (DAAGlobal.scodesToDeviceLimitMapping.isEmpty() || valueFrmMap == -1) {
			DAAGlobal.LOGGER.info("The value ' " + key + " : " + valueFrmXML + " ' is not present in the scodeGroupMapping");
			DAAGlobal.scodesToDeviceLimitMapping.put(key.toUpperCase(), valueFrmXML);
		} else if (valueFrmMap != valueFrmXML) {
			DAAGlobal.scodesToDeviceLimitMapping.put(key.toUpperCase(), valueFrmXML);
			DAAGlobal.LOGGER.info("Updating the value ' " + key + " : " + valueFrmMap + " ' with the latest value from XML : " + valueFrmXML);
		} 
	}
}
