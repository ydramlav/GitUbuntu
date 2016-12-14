package com.bt.vosp.daa.didb.impl.helper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

public class DataBaseService {
	private static final StringWriter s = new StringWriter();
	public static Connection getConnection() throws SQLException {
		Connection connection = null;
		DataSource ds = null;
		long startTime = System.currentTimeMillis();
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, DAAGlobal.initialContextFactory);
			env.put(Context.PROVIDER_URL, DAAGlobal.providerURLProtocol+"://"+DAAGlobal.dataSourcehost+":"+DAAGlobal.dataSourceport);
			Context context = new InitialContext(env);
			ds = (DataSource) context.lookup(DAAGlobal.DIDBDataSource);
			connection = ds.getConnection();
			DAAGlobal.LOGGER.debug("Connection established successfully");
		} catch (NamingException e) {
			e.printStackTrace(new PrintWriter(s));
				if(connection!=null){
					connection.close();
				}
			DAAGlobal.LOGGER.error("Exception while Retrieving Data Base Connection:"+ s.toString());
		} catch (SQLException sqlex) {
			//sqlex.printStackTrace();
			sqlex.printStackTrace(new PrintWriter(s));
			if(connection!=null){
				connection.close();
			}
			DAAGlobal.LOGGER.error("Exception while Retrieving Data Base Connection:"	+ s.toString());
		} catch (Exception ex) {
			ex.printStackTrace(new PrintWriter(s));
			if(connection!=null){
				DAAGlobal.LOGGER.debug("Closing connection");
				connection.close();
			}
			DAAGlobal.LOGGER.error("Exception while Retrieving Data Base Connection:"+ s.toString());
			
		}finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for getDIDBConnection Call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return connection;
	}
}
