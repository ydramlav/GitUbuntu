package com.bt.vosp.daa.didb.impl.processor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;



import oracle.jdbc.OracleTypes;

import com.bt.vosp.common.model.DIDBRequestObject;
import com.bt.vosp.common.model.DIDBresponseObject;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.service.GetMACAddress;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.didb.impl.helper.DataBaseService;


public class GetMACAddressImpl implements GetMACAddress{

	Connection con = null;
	CallableStatement cs = null;
	/** The Constant s. */
	//private static final StringWriter s = new StringWriter();
	public  DIDBresponseObject getMACAddressDetails(DIDBRequestObject didbRequestObject) {
		String macAddress="";
		String errCode="";
		String internal_deviceid="";
		String errorMsg="";
		DIDBresponseObject didBresponseObject = null;
		long startTime = System.currentTimeMillis();
		StringWriter stringWriter = null;
		try {
			didBresponseObject = new DIDBresponseObject();
			DAAGlobal.LOGGER.debug("Retrieving MAC Address Details Started from DIDB");
		//	final String mpxPhysicalNameURL = DAAGlobal.mpxIdenProtocol+"://"+CommonGlobal.entitlementDataService+DAAGlobal.mpxPhysicalDeviceURI+"/";
			//String mpxPhysicalNameURL =didbRequestObject.getDeviceID().lastIndexOf("/");
			//final int length = mpxPhysicalNameURL.length();
			String internal_id=didbRequestObject.getDeviceID();
			internal_deviceid  = internal_id.substring(internal_id.lastIndexOf("/")+1);
			con = DataBaseService.getConnection();
			if (con != null) {
				DAAGlobal.LOGGER.info("DIDB Connection found");
				DAAGlobal.LOGGER.info("Calling The Stored Procedure :"+DAAGlobal.DIDBProcName  +", Nemo_Node_Id : "
						+ didbRequestObject.getNemoNode() +",VSID: "+didbRequestObject.getVsid()+",InternalDeviceID :"+internal_deviceid+",CNCertification: "+didbRequestObject.getCnCertification());
				cs = con.prepareCall("{CALL "+DAAGlobal.DIDBProcName+"(?,?,?,?,?,?,?)}");
				cs.setString(1, (String) didbRequestObject.getNemoNode());				
				cs.setString(2, (String) didbRequestObject.getVsid());
				cs.setString(3, internal_deviceid);
				cs.setString(4, (String) didbRequestObject.getCnCertification());
				cs.registerOutParameter(5, OracleTypes.VARCHAR);
				cs.registerOutParameter(6, OracleTypes.VARCHAR);
				cs.registerOutParameter(7, OracleTypes.VARCHAR);
				cs.executeQuery();
				errCode = cs.getString(6);
				errorMsg=cs.getString(7);
				didBresponseObject.setErrorcode(cs.getString(6));
				if(cs.getString(5)!=null && !cs.getString(5).isEmpty()){
				didBresponseObject.setMacAddress(cs.getString(5));
				}
				didBresponseObject.setErrorMessage(cs.getString(7));
				if(errCode.equalsIgnoreCase(DAAGlobal.didb_MAC_Unavailable)){
					DAAGlobal.LOGGER.info("MAC Address does not Exist in DIDB  for Nemo_NodeID:"+didbRequestObject.getNemoNode());
					didBresponseObject.setExErrorCode(DAAConstants.DAA_1025_CODE);
					didBresponseObject.setExErrorMsg(DAAConstants.DAA_1025_MESSAGE);
					didBresponseObject.setStatus("1");
				}
				else if(errCode.equalsIgnoreCase("0"))
				{			
					didBresponseObject.setMacAddress(cs.getString(5));

					macAddress=cs.getString(5);
					didBresponseObject.setStatus("0");
					DAAGlobal.LOGGER.info("Successfully Retrieved MAC Address: "+macAddress+" and updated VSID and InternalDeviceID in DIDB");
				}
			}
			else{
				DAAGlobal.LOGGER.error("Error occurred while retrieving MAC Address" + errorMsg);
				didBresponseObject.setExErrorCode(DAAConstants.DAA_1026_CODE);
				didBresponseObject.setExErrorMsg(DAAConstants.DAA_1026_MESSAGE);
				didBresponseObject.setStatus("1");
				//throw new VOSPDIDBException(DAAConstants.DAA_1026_CODE,DAAConstants.DAA_1026_MESSAGE);
			}
		} catch (SQLException e) {
			DAAGlobal.LOGGER.error("SQLException occurred in DIDB :" + errorMsg);
			didBresponseObject.setExErrorCode(DAAConstants.DAA_1024_CODE);
			didBresponseObject.setExErrorMsg(DAAConstants.DAA_1024_MESSAGE);
			didBresponseObject.setStatus("1");
			//////CSV file Creation
		}/*catch(VOSPGenericException e){
			DAAGlobal.LOGGER.error("Exception occurred in DIDB :" + e.getMessage());
			didBresponseObject.setExErrorCode(e.getReturnCode());
			didBresponseObject.setExErrorMsg(e.getReturnText());
			didBresponseObject.setStatus("1");
		}*/
		catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred in DIDB :" + stringWriter.toString());
			didBresponseObject.setExErrorCode(DAAConstants.DAA_1006_CODE);
			didBresponseObject.setExErrorMsg(DAAConstants.DAA_1006_MESSAGE);
			didBresponseObject.setStatus("1");
		}
		finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
					DAAGlobal.LOGGER.error("SQLException occurred in DIDB :" + errorMsg);
					didBresponseObject.setExErrorCode(DAAConstants.DAA_1024_CODE);
					didBresponseObject.setExErrorMsg(DAAConstants.DAA_1024_MESSAGE);
					didBresponseObject.setStatus("1");
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					DAAGlobal.LOGGER.error("SQLException occurred in DIDB :" + errorMsg);
					didBresponseObject.setExErrorCode(DAAConstants.DAA_1024_CODE);
					didBresponseObject.setExErrorMsg(DAAConstants.DAA_1024_MESSAGE);
					didBresponseObject.setStatus("1");
				}
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for getMACAddressFromDIDB Call :" + endTime + ",");
				nftLoggingTime = null;
			}
			
		}
		}	
		return didBresponseObject;
	}
}
