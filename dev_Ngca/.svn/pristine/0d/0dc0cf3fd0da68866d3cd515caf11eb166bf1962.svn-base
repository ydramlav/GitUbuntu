package com.bt.vosp.daa.mpx.userprofile.impl.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.bt.vosp.common.proploader.CommonGlobal;




/**
 * Class DateUtil.
 * */

public class DateUtil {
	/** constant for database date format. */
	//    private static final String DB_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	/** constant for database date format. */
	//    private static final String DB_DATE_FORMAT_OFS = "yyyyMMddHHmmss";
	/** constant for UTC date format. */
	StringWriter s = new StringWriter();
	private static final String UTCS_MILLISEC_FORMAT_CSV = "yyyy-MM-dd HH:mm:ss.SSS";

	/** The Constant UTCS_DATE_FORMAT. */
	// private static final String UTCS_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	/** The Constant TS_DATE_FORMAT. */
	//private static final String TS_DATE_FORMAT = "ddMMyyyyHHmmss";

	/** constructor. */
	public DateUtil() {

	}

	/**
	 * Gets the current date string in utc format.
	 *
	 * @return the current date string in utc format
	 */
	public static String getCurrentDateStringInUTCFormat() {
		Calendar current = null;
		String utcDate = "";
		try {
			current = getCurrentDateInUTCFormat();
			utcDate = convertMillisecondsToUTCFormat("" + current.getTimeInMillis());
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			CommonGlobal.LOGGER.error(sw.toString());
		}
		return utcDate;
	}


	/**
	 * To convert the system date to UTC format.
	 * @return UTC date as Calendar
	 * @throws Exception - parse exception
	 */
	public static Calendar getCurrentDateInUTCFormat() throws Exception {
		Calendar cal = null;
		cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		return cal;
	}

	/**
	 * Convert utc stingto date.
	 *
	 * @param inputUTC the input utc
	 * @return the date
	 */
	public static Date convertUTCStingtoDate(String inputUTC) {
		Date convertedDate = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(UTCS_MILLISEC_FORMAT_CSV);
			if (inputUTC != null) {
				convertedDate = dateFormat.parse(inputUTC);
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			CommonGlobal.LOGGER.error(sw.toString());
		}
		return convertedDate;
	}


	/*  public static String convertMillisecondsToUTCFormat(
            String inputDateinMilliseconds) throws Exception {
        final long dateinMilliseconds = Long.valueOf(inputDateinMilliseconds);
        Date currentLocalTime = new Date(dateinMilliseconds);
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");   
        df2.setTimeZone(TimeZone.getTimeZone("UTC")); 
        final String sDate = df2.format(currentLocalTime);
        return sDate;
    }*/
	public static String convertMillisecondsToUTCFormat(
			String inputDateinMilliseconds) throws Exception {
		/*final long inputDateinMilliseconds12 = Long
                .valueOf(inputDateinMilliseconds);
        final SimpleDateFormat sdf = new SimpleDateFormat(UTCS_DATE_FORMAT);
        final Date resultdate = new Date(inputDateinMilliseconds12);
        final String sDate = sdf.format(resultdate);
        return sDate;*/

		final long dateinMilliseconds = Long.valueOf(inputDateinMilliseconds);
		Date currentLocalTime = new Date(dateinMilliseconds);
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");   
		df2.setTimeZone(TimeZone.getTimeZone("UTC")); 

		final String sDate = df2.format(currentLocalTime);
		return sDate;
	}
	/*public static long convertUTCToMilliSec(String inputDateStr)
	throws Exception {

		final Date inputDate1 = InternetDateFormat.parseDate(inputDateStr);
		final long inMillSec = inputDate1.getTime();
		return inMillSec;
	}*/
	/**
	 * Gets the date from milli sec.
	 *
	 * @param millisec the millisec
	 * @return the date from milli sec
	 */
	public String getDateFromMilliSec(long millisec) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date resultdate = new Date(millisec);
		return sdf.format(resultdate);
	}
	
	public static long getUTCMilliseconds(Date inputDate) throws Exception{

		@SuppressWarnings("unused")
		String totalUTCMilliSeconds = null;

		Calendar cal = Calendar.getInstance();
		cal.setTime(inputDate);
		final long offsetMilliseconds= -cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET);
		final long totalUTC = inputDate.getTime()+offsetMilliseconds;
		totalUTCMilliSeconds = Long.toString(totalUTC);
		
		return totalUTC;
	}

	/**
	 * Gets the date in utc milli sec format for csv.
	 *
	 * @return the date in utc milli sec format for csv
	 * @throws Exception the exception
	 */
	public String getDateInUTCMilliSecFormatForCSV() throws Exception{       
		final SimpleDateFormat formatter = new SimpleDateFormat(UTCS_MILLISEC_FORMAT_CSV);
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		final String cdate = formatter.format(new Date());
		return cdate;
	}


	/**
	 * Convert hours to milliseconds.
	 *
	 * @param currentTimeinHours the current timein hours
	 * @return the long
	 */
	public static long convertHoursToMilliseconds(long currentTimeinHours) {
		long timeInMilliSeconds=0;
		try {
			timeInMilliSeconds = currentTimeinHours * 60 * 60 * 1000;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			CommonGlobal.LOGGER.error(sw.toString());
		}
		return timeInMilliSeconds;
	}


	/**
	 * To return the UTC milliseconds ro the given date.
	 * @param inputDate - format should be dd/MM/yyyy HH:mm:ss
	 * @return totalUTCMilliSeconds - totalUTCMilliSeconds
	 * @throws Exception - Exception
	 */
	/*   @SuppressWarnings("static-access")
    public static String getUTCMilliseconds(Date inputDate) throws Exception {
        String totalUTCMilliSeconds = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        final long offsetMilliseconds = -cal.get(cal.ZONE_OFFSET) + cal.get(cal.DST_OFFSET);
        final long totalUTC = inputDate.getTime() + offsetMilliseconds;
        totalUTCMilliSeconds = Long.toString(totalUTC);
        return totalUTCMilliSeconds;
    }*/

	/**
	 *  To get todays's date in DD-MM-YYYY HH24:MI:SS.
	 *  @return date values as String
	 *  @throws Exception exception
	 */
	/*  public static String getDateInUTCFormat() throws Exception {
        String dateinUTC = "";
        final SimpleDateFormat formatter = new SimpleDateFormat(UTCS_DATE_FORMAT);
        dateinUTC = formatter.format(new Date());
        return dateinUTC;
    }*/


	/** The Constant DATE_FORMAT. *//*
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	 *//**
	 * Test.
	 *
	 * @return the string
	 * @throws Exception the exception
	 *//*
    public static String test() throws Exception{
        String date = "";
        final SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        date = formatter.format(new Date());
        return date;
    }*/


	/**
	 *  To get todays's date in DD-MM-YYYY HH24:MI:SS.
	 *  @return date values as String
	 *  @throws Exception exception
	 */
	/*public static String getTimeStamp() throws Exception{
        String date = "";
        final SimpleDateFormat formatter = new SimpleDateFormat(TS_DATE_FORMAT);
        date = formatter.format(new Date());
        return date;
    }
	 */
	/**
	 *  To convert the input date to UTC format.
	 *  @param inputDateinMilliseconds - date in String format
	 *  @return sDate values as String
	 *  @throws Exception exception
	 */
	/*    public static String convertMillisecondsToUTCFormat(String inputDateinMilliseconds) throws Exception {
        String date = "";
        final long dateinMilliseconds = Long.valueOf(inputDateinMilliseconds);
        final SimpleDateFormat sdf = new SimpleDateFormat(UTCS_DATE_FORMAT);
        final Date resultdate = new Date(dateinMilliseconds);
        date = sdf.format(resultdate);
        return date;
    }
	 */
	/**
	 * To check whether date is in UTC format.
	 * @param inputDateString - date in String format
	 * @return utcDateStr - utcDateStr
	 * @throws Exception - Exception
	 */
	/* public static boolean isUTCFormat(String inputDateString) throws Exception {
        String dbDateStr = null;
        boolean utcFormat = false;
        try {
            final SimpleDateFormat utcDateFormat = new SimpleDateFormat(DateUtil.UTCS_DATE_FORMAT);
            final Date inputDate = utcDateFormat.parse(inputDateString);
            final SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.UTCS_DATE_FORMAT);
            dbDateStr = dateFormat.format(inputDate);
            if (dbDateStr != null && dbDateStr.equalsIgnoreCase(inputDateString)) {
                utcFormat = true;
            }
        } catch (Exception e) {
            utcFormat = false;
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            Global.LOGGER.error(sw.toString());
        }
        return utcFormat;
    }*/

	/**
	 * To convert UTC to milliseconds.
	 * @param inputDateStr - date in String format
	 * @return inMillSec - inMillSec
	 * @throws Exception - Exception
	 */
	/*  public static long convertUTCToMilliSec(String inputDateStr) throws Exception {
        long inMillSec = 0;
        final SimpleDateFormat utcDateFormat = new SimpleDateFormat(DateUtil.UTCS_DATE_FORMAT);
        final Date inputDate = utcDateFormat.parse(inputDateStr);
        inMillSec = inputDate.getTime();
        return inMillSec;
    }
	 */

	/*public static long convertUTCToMilliSec(String inputDateStr)
    throws Exception {
        final Date inputDate1 = InternetDateFormat.parseDate(inputDateStr);

        final long inMillSec = inputDate1.getTime();
        return inMillSec;
    }*/

	/**
	 * Convert stingto date.
	 *
	 * @param str the str
	 * @return the date
	 */
	/* public static Date convertStingtoDate(String str) {
        Date convertedDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (str != null) {
                convertedDate = dateFormat.parse(str);
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            Global.LOGGER.error(sw.toString());
        }
        return convertedDate;
    }*/


	/**
	 * Convert date to utc.
	 *
	 * @param object the object
	 * @return the string
	 */
	/* public static String convertDateToUTC(Date object) {
        String reportDate = "";
        try {
            DateFormat df = new SimpleDateFormat(UTCS_DATE_FORMAT);
            reportDate = df.format(object);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            Global.LOGGER.error(sw.toString());
        }
        return reportDate;
    }*/

	/**
	 * Convert dateto utc.
	 *
	 * @param date the date
	 * @return the string
	 */
	/* public static String convertDatetoUTC(Date date) {
        String dateStr = null;
        if (date != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            dateStr = df.format(date);
        }
        return dateStr;
    }*/


	/**
	 * Convert date to string.
	 *
	 * @param object the object
	 * @return the string
	 */
	/* public static String convertDateToString(Date object) {
        String reportDate = "";
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            reportDate = df.format(object);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            Global.LOGGER.error(sw.toString());
        }
        return reportDate;
    }
	 */



	/**
	 * Convert utc to date.
	 *
	 * @param object the object
	 * @return the date
	 */
	/*  public static Date convertUTCToDate(String object) {
        Date reportDate = null;
        try {
            DateFormat df = new SimpleDateFormat(UTCS_DATE_FORMAT);
            reportDate = df.parse(object);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            Global.LOGGER.error(sw.toString());
        }
        return reportDate;
    }*/




	/**
	 * Convert milli seconds to hours.
	 *
	 * @param currentDateInMilliSeconds the current date in milli seconds
	 * @return the long
	 */
	/* public static long convertMilliSecondsToHours(long currentDateInMilliSeconds) {
        long totalHours = 0;
        try {

            long totalSeconds = currentDateInMilliSeconds / 1000L;
            long totalMinutes = totalSeconds / 60L;
            totalHours = totalMinutes / 60L;
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            Global.LOGGER.error(sw.toString());
        }
        return totalHours;
    }*/


}