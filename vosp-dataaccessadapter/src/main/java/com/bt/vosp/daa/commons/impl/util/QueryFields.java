package com.bt.vosp.daa.commons.impl.util;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * The Class QueryFields.
 */
public class QueryFields {
	
	/**
	 * Instantiates a new query fields.
	 */
	public QueryFields() {
	}

	/**
	 * Form request parameter.
	 *
	 * @param inputString the input string
	 * @param urlqueryParams the urlquery params
	 */
	public void formRequestParameter(String inputString,
			List<NameValuePair> urlqueryParams) {
		String[] Arr = inputString.split(",");
		String[] SubArr;
		for (int i = 0; i < Arr.length; i++) {
			SubArr = Arr[i].split("\\|");
			urlqueryParams.add(new BasicNameValuePair(SubArr[0], SubArr[1]));
		}
	}

}
