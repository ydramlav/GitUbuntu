package com.bt.vosp.daa.storefront.impl.helper;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.stream.XMLStreamException;

import org.codehaus.jettison.json.JSONException;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.ProductInfoRequestObject;
import com.bt.vosp.common.model.ProductInfoResponseObject;
import com.bt.vosp.common.model.ProductResponseBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.utils.ParseProductXML;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;


public class RetrieveReleasePID {


	/*public ProductInfoResponseObject getReleasePIDS(ProductInfoRequestObject productInfoRequestObject) throws VOSPGenericException, JSONException
	{
		ProductInfoResponseObject productInfoResponseObject = null;
		//StringWriter s = new StringWriter();
		File productfile =null;
		long startTime = System.currentTimeMillis();
		StringWriter stringWriter = null;
		try
		{
			DAAGlobal.LOGGER.debug("getReleasepids is started");
			ParseProductXML parseProductXML = new ParseProductXML();
			productInfoResponseObject = new ProductInfoResponseObject();
			if((productInfoRequestObject.getSlotType()).equalsIgnoreCase("Feature")){
				productfile = new File(DAAGlobal.Productxml+productInfoRequestObject.getProductId()+".xml");
				DAAGlobal.LOGGER.info("productfile ::"+productfile);
			}
			else{
				productfile = new File(DAAGlobal.trailerXml+productInfoRequestObject.getProductId()+".xml");
				DAAGlobal.LOGGER.info("AssetFile ::"+productfile);
			}
			File Adultproductfile = new File(DAAGlobal.AdultProductxml+productInfoRequestObject.getProductId()+".xml");
			DAAGlobal.LOGGER.info("DeviceServiceTypeMapping ::"+DAAGlobal.DeviceServiceTypeMapping);
			if(productfile.exists() && productfile.canRead())
			{

				String productInfoContent = getContents(productfile);
				DAAGlobal.LOGGER.info("product info content ::"+productInfoContent);
				if(!productInfoContent.isEmpty())
				{
					productInfoRequestObject.setProductInfoContent(productInfoContent);
					productInfoRequestObject.setDeviceServiceTypeFilePath(DAAGlobal.DeviceServiceTypeMapping);
					ProductResponseBean productResponseBean = null;

					productResponseBean= parseProductXML.parseProductXML(productInfoRequestObject);

					if(productResponseBean!=null)
					{
						productInfoResponseObject.setStatus("0");
						productInfoResponseObject.setProductResponseBean(productResponseBean);
					}

				}

			}
			else if(Adultproductfile.exists())
			{

				DAAGlobal.LOGGER.info("Adultproductfile ::"+Adultproductfile);

				String productInfoContent = getContents(Adultproductfile);
				DAAGlobal.LOGGER.info("product info content ::"+productInfoContent);
				if(!productInfoContent.isEmpty())
				{
					productInfoRequestObject.setProductInfoContent(productInfoContent);
					productInfoRequestObject.setDeviceServiceTypeFilePath(DAAGlobal.DeviceServiceTypeMapping);
					ProductResponseBean productResponseBean = null;
					productResponseBean = parseProductXML.parseProductXML(productInfoRequestObject);

					if(productResponseBean!=null)
					{
						productInfoResponseObject.setStatus("0");
						productInfoResponseObject.setProductResponseBean(productResponseBean);
					}
				}
			}
			else
			{
				if((productInfoRequestObject.getSlotType()).equalsIgnoreCase("Feature")){
					DAAGlobal.LOGGER.error("Product File does not exist :: "+DAAGlobal.Productxml+productInfoRequestObject.getProductId()+".xml");
				}else{
					DAAGlobal.LOGGER.error("Asset File does not exist :: "+DAAGlobal.trailerXml+productInfoRequestObject.getProductId()+".xml");

				}
			}
			DAAGlobal.LOGGER.debug("getRelease pids is ended");
		} catch (FileNotFoundException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			CommonGlobal.LOGGER.error("FileNotFoundException occurred while parsing ProductXML file : " +  stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		} catch (XMLStreamException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			CommonGlobal.LOGGER.error("XMLStreamException occurred while parsing ProductXML file : " + stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		} catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			CommonGlobal.LOGGER.error("Exception occurred while parsing ProductXML file : " + stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		}finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Parse Product xml:" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return productInfoResponseObject;
	}


	public String getContents(File aFile) {
		StringBuilder contents = new StringBuilder();
		StringWriter stringWriter = null;
		try {
			BufferedReader input =  new BufferedReader(new FileReader(aFile));
			try {
				String line = null; 

				while (( line = input.readLine()) != null){
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			}
			finally {
				input.close();
			}
		}
		catch (Exception ex){
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while getting content :: "+stringWriter.toString());
		}
		return contents.toString();
	}*/
}
