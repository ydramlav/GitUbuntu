package com.bt.vosp.daa.mpx.identityservice.impl.helper;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.proploader.OTGConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
public class EncryptUserInfo {
	
	final String ALGO = "RC4";	
	final byte[] keyValue="ABC1234".getBytes();
    String appkey="ABC1234";
    
	public String encryptUserInfoObject(UserInfoObject  resolveTokenRespObj) throws  VOSPGenericException, JSONException
    {
    	JSONObject dataobj=new JSONObject();
    	String responseMap=null;
    	JSONObject attributes=new JSONObject();
    	StringWriter sw=null;
    	PrintWriter pw=null;
    	try 
    	{	
    		sw=new StringWriter();
    		pw=new PrintWriter(sw);
    		if(null!=resolveTokenRespObj.getAccountStatus()){
    			attributes.put("accountStatus",resolveTokenRespObj.getAccountStatus());
    		}
    		if(null!=resolveTokenRespObj.getDeviceStatus()){
    		attributes.put("deviceStatus", resolveTokenRespObj.getDeviceStatus());
    		}
    		if(null!=resolveTokenRespObj.getNemoNodeId()){
    			attributes.put("nemoNodeId", resolveTokenRespObj.getNemoNodeId());
    		}
    		if(null!=resolveTokenRespObj.getPin()){
    			attributes.put("pin", resolveTokenRespObj.getPin());
    		}
    		if(null!=resolveTokenRespObj.getServiceType()){
    			attributes.put("serviceType", resolveTokenRespObj.getServiceType());
    		}
    		if(null!=resolveTokenRespObj.getSubscriptions()){
    			attributes.put("subscriptions", resolveTokenRespObj.getSubscriptions());
    		}
    		
    		if(null!=resolveTokenRespObj.getVsid()){
    			attributes.put("vsid", resolveTokenRespObj.getVsid());
    		}
    		
    		if(resolveTokenRespObj.getConcurrencySubject() != null)
    		{
    			attributes.put("concurrencySubject", resolveTokenRespObj.getConcurrencySubject());
    		}
    		if(resolveTokenRespObj.getDeviceClass() != null)
    		{
    			attributes.put("deviceClass", resolveTokenRespObj.getDeviceClass());
    		}
    		
    		if(resolveTokenRespObj.getSSID() != null)
    		{
    			attributes.put(OTGConstants.SSID, resolveTokenRespObj.getSSID());
    			
    		}
    		if(resolveTokenRespObj.getUUID() != null)
    		{
    			attributes.put(OTGConstants.UUID, resolveTokenRespObj.getUUID());
    			
    		}
    	
    		if(null!=resolveTokenRespObj.getSchema()){
    			attributes.put("schema", resolveTokenRespObj.getSchema());
    		}else{
    			attributes.put("schema", DAAGlobal.schema);
    		}
    		
    		attributes.put("deviceType", "physical");
    		
    		if(null!=resolveTokenRespObj.getEntitledUserId()){
    			attributes.put("entitledUserId", resolveTokenRespObj.getEntitledUserId());
    		}/*else{
    			attributes.put("entitledUserId", DAAGlobal.entitledUserId);
    		}*/
    		if(null!=resolveTokenRespObj.getUpdatedUserInfo()){
    			attributes.put("updatedUserInfo", resolveTokenRespObj.getUpdatedUserInfo());
    		}
    		
    		if(null!=resolveTokenRespObj.getId()){
    			dataobj.put("id",  resolveTokenRespObj.getId()); 
    		}
    		 
			dataobj.put("attributes", attributes);
			if(null!=resolveTokenRespObj.getPhysicalDeviceURL()){
				dataobj.put("userName",resolveTokenRespObj.getPhysicalDeviceURL());
			}
			
			if(null!=resolveTokenRespObj.getFullName()){
				dataobj.put("fullName",  resolveTokenRespObj.getFullName());  
			}
			DAAGlobal.LOGGER.info("UserInfo Object before encryption: " + dataobj.toString());
    	
			String filename=DAAGlobal.signInCertLoc+""+DAAGlobal.signInCertName;
			String algo="RSA";
			PrivateKey priv_key=getPemPrivateKey(filename,algo);
    	
			String userinfo=dataobj.toString();
			String encSessionKey=encryptSessionKey(appkey,priv_key);
			String respose=encrypt(userinfo);
			responseMap=(encSessionKey+"|"+respose);
			
    	}
    	catch(VOSPGenericException e){
    		throw e;
    	}
    	catch (JSONException e)
    	{
    		sw = new StringWriter();
    		e.printStackTrace(new PrintWriter(pw));
    		DAAGlobal.LOGGER.error("JSONException occurred during encryption of Userinfo object :: "+ sw.toString());
    		throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
    	
    	} 
    	catch (Exception e) 
    	{
    		sw = new StringWriter();
    		e.printStackTrace(pw);
    		DAAGlobal.LOGGER.error("Exception occurred during encryption of Userinfo object :: "+ sw.toString());
    		throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
    	}
    	finally
		{
			if(sw!=null)
			{
				sw=null;
			}
			if(pw!=null)
			{
				pw=null;
			}
		
		}
    	return responseMap;
    }
	
	public String encrypt(String Data)  
	{
        Key key;
        String encryptedValue = null;
        StringWriter sw=null;
        PrintWriter pw=null;
		try 
		{
			sw=new StringWriter();
			pw=new PrintWriter(sw);
			key = generateKey(keyValue,ALGO);
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);   
      
			byte[] encVal = c.doFinal(Data.getBytes());
			encryptedValue = new String(Base64.encodeBase64(encVal));
			return encryptedValue;
		}
		catch (Exception e)
		{
			sw = new StringWriter();
    		e.printStackTrace(pw);
    		DAAGlobal.LOGGER.error("Exception while encryption :: "+sw.toString());
		}
		finally
		{
			if(sw!=null)
			{
				sw=null;
			}
			if(pw!=null)
			{
				pw=null;
			}
		}
		return encryptedValue;
    }
	private Key generateKey(byte[] keyValue,String algo) throws Exception 
	{
        Key key = new SecretKeySpec(keyValue, algo);
        return key;
	}
	public String encryptSessionKey(String keyvalue,PrivateKey privkey) throws VOSPGenericException, JSONException  
	{
    	String encryptSessionKey = null;
    	StringWriter stringWriter = null;
    	try 
    	{
    		Cipher c = Cipher.getInstance("RSA/ECB/NoPadding");
			c.init(Cipher.ENCRYPT_MODE,privkey); 
			byte[] encVal = c.doFinal(keyvalue.getBytes());
			encryptSessionKey = new String(Base64.encodeBase64(encVal));
		} 
		catch (NoSuchPaddingException e)
		{
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("NoSuchPaddingException occurred during session key encryption :: "+ stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE, DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		}
		catch (NoSuchAlgorithmException e)
		{
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("NoSuchAlgorithmException occurred during session key encryption :: "+ stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE, DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		}
		catch(Exception e)
		{
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred during session key encryption :: "+ stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE, DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
    	}
		/*finally
		{
			if(sw!=null)
			{
				sw=null;
			}
			if(pw!=null)
			{
				pw=null;
			}
			
		}*/
		return encryptSessionKey;
	}
	
	public PrivateKey getPemPrivateKey(String filename, String algorithm) throws Exception
	{
		 FileInputStream fis = null;
		 DataInputStream dis = null;
		 PrivateKey privkey=null;
		 StringWriter stringWriter = null;
		try
		{
			File f = new File(filename);
		    fis = new FileInputStream(f);
		    dis = new DataInputStream(fis);
		    byte[] keyBytes = new byte[(int) f.length()];
		    dis.readFully(keyBytes);
		    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);	      
		    KeyFactory kf = KeyFactory.getInstance(algorithm);
		    privkey=kf.generatePrivate(spec);
		}
		/*catch(Exception e){
			DAAGlobal.LOGGER.error("Exception occured while retrieving private key : " + e.getMessage());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		}*/ catch (FileNotFoundException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("FileNotFoundException occurred because signIn certificate location is not proper " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		} catch (IOException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("IOException occurred during signIn token " + e.getMessage());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " +stringWriter.toString());
		} catch (NoSuchAlgorithmException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("NoSuchAlgorithmException occurred during signIn token " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		} catch (InvalidKeySpecException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("InvalidKeySpecException occurred during signIn token " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		}
		finally
		{
			try
			{
				if(fis!=null)
				{
					fis.close();
				}
				if(dis!=null)
				{
					dis.close();
				}
			}
			catch(Exception e){
				DAAGlobal.LOGGER.error("Exception occurred while retrieving private key : " + e.getMessage());
				throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
			}
		}
	    return privkey ;
	}
}
