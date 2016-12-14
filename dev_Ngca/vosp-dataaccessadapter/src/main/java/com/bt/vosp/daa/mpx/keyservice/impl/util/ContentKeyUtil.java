package com.bt.vosp.daa.mpx.keyservice.impl.util;

import java.io.UnsupportedEncodingException;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.StringTokenizer;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import org.codehaus.jettison.json.JSONException;

import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;


public class ContentKeyUtil {

	public String getContentKey(String key, String secret) throws JSONException, VOSPMpxException{
		
		DAAConstants DAAConstants = new DAAConstants();
		
		try{
			Security.addProvider(new BouncyCastleProvider());
			//get private key
			PrivateKey privateKey = generatePrivate(key);
			
			StringTokenizer token = new StringTokenizer(secret, "|");
			String[] values = new String[2];
			int i = 0;
			while(token.hasMoreTokens()){
				values[i++] = token.nextToken();
			}
		    
		    //decrypt first part of secret with private to get AES-256 bit key
			byte[] retrievedFirstPart = decodeFirstPart(values[0].getBytes(), privateKey);
			
   		    //decrypt second part of secret with above AES-256 bit key to get AES-128 bit content key
			byte[] secondPart1 = decodeSecondPart(values[1].getBytes(), generateAESKey(retrievedFirstPart));
			
			return new String(secondPart1);
			
		}catch(NoSuchAlgorithmException nsae){
			DAAGlobal.LOGGER.error(nsae);
			DAAGlobal.LOGGER.error("DAA_Int_"+DAAConstants.CONTENTKEY_FAILURE_CODE+"|"+DAAConstants.CONTENTKEY_FAILURE_MESSAGE);
			throw new VOSPMpxException("DAA_1004","UserKeyException");
		}catch(InvalidKeySpecException ikse){
			DAAGlobal.LOGGER.error(ikse);
			DAAGlobal.LOGGER.error("DAA_Int_"+DAAConstants.CONTENTKEY_FAILURE_CODE+"|"+DAAConstants.CONTENTKEY_FAILURE_MESSAGE);
			throw new VOSPMpxException("DAA_1004","UserKeyException");
		}catch(NoSuchPaddingException nspe){
			DAAGlobal.LOGGER.error(nspe);
			DAAGlobal.LOGGER.error("DAA_Int_"+DAAConstants.CONTENTKEY_FAILURE_CODE+"|"+DAAConstants.CONTENTKEY_FAILURE_MESSAGE);
			throw new VOSPMpxException("DAA_1004","UserKeyException");
		}catch(InvalidKeyException ike){
			DAAGlobal.LOGGER.error(ike);
			DAAGlobal.LOGGER.error("DAA_Int_"+DAAConstants.CONTENTKEY_FAILURE_CODE+"|"+DAAConstants.CONTENTKEY_FAILURE_MESSAGE);
			throw new VOSPMpxException("DAA_1004","UserKeyException");
		}catch(BadPaddingException bpe){
			DAAGlobal.LOGGER.error(bpe);
			DAAGlobal.LOGGER.error("DAA_Int_"+DAAConstants.CONTENTKEY_FAILURE_CODE+"|"+DAAConstants.CONTENTKEY_FAILURE_MESSAGE);
			throw new VOSPMpxException("DAA_1004","UserKeyException");
		}catch(IllegalBlockSizeException ibse){
			DAAGlobal.LOGGER.error(ibse);
			DAAGlobal.LOGGER.error("DAA_Int_"+DAAConstants.CONTENTKEY_FAILURE_CODE+"|"+DAAConstants.CONTENTKEY_FAILURE_MESSAGE);
			throw new VOSPMpxException("DAA_1004","UserKeyException");
		}catch(IllegalArgumentException iae){
			DAAGlobal.LOGGER.error(iae);
			DAAGlobal.LOGGER.error("DAA_Int_"+DAAConstants.CONTENTKEY_FAILURE_CODE+"|"+DAAConstants.CONTENTKEY_FAILURE_MESSAGE);
			throw new VOSPMpxException("DAA_1004","UserKeyException");
		}
		catch(java.lang.StringIndexOutOfBoundsException iae){
			DAAGlobal.LOGGER.error(iae);
			DAAGlobal.LOGGER.error("DAA_Int_"+DAAConstants.CONTENTKEY_FAILURE_CODE+"|"+DAAConstants.CONTENTKEY_FAILURE_MESSAGE);
			throw new VOSPMpxException("DAA_1004","UserKeyException");
		}
		
		
	}
	private static PrivateKey generatePrivate(String hexEncodedPrivateKey) throws NoSuchAlgorithmException, InvalidKeySpecException{
		  
		  byte[] keys = Hex.decode(hexEncodedPrivateKey);
		  KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		  EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keys);
		  PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
		  return privateKey;
	}
	
	private static byte[] decodeFirstPart(byte[] firstPart, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException{
		Cipher prefixCipher = Cipher.getInstance("RSA/NONE/PKCS1Padding");
	    prefixCipher.init(Cipher.DECRYPT_MODE,privateKey);
	    return prefixCipher.doFinal(Hex.decode(firstPart));
	}
	
	private static byte[] decodeSecondPart(byte[] firstPart, SecretKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException{
		Cipher prefixCipher = Cipher.getInstance("RC4");
	    prefixCipher.init(Cipher.DECRYPT_MODE,privateKey);
	    return prefixCipher.doFinal(Hex.decode(firstPart));
	}
	
	private static SecretKey generateAESKey(byte[] hexEncodedPrivateKey) throws IllegalArgumentException {
		SecretKeySpec secretKey = new SecretKeySpec(hexEncodedPrivateKey, "AES");
		return secretKey;
	}
	public static String getSHA1(String input) {
		String output="";

		try{
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] mdbytes = md.digest(input.getBytes("UTF-8"));
		StringBuffer sb = new StringBuffer();
		//converting to byte to HEX
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		output = sb.toString() ;
		}catch(NoSuchAlgorithmException e){
			DAAGlobal.LOGGER.error("Exception while encoding contentey"+	e.getMessage());
			
		}catch(UnsupportedEncodingException e){
			DAAGlobal.LOGGER.error("Exception while encoding contentkey"+	e.getMessage());

		}catch (Exception e){
			DAAGlobal.LOGGER.error("Exception while encoding contentkey"+	e.getMessage());

		}
		
		return output ;
	}

}
