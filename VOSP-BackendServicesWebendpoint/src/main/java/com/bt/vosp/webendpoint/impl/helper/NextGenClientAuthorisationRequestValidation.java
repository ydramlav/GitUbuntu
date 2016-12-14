package com.bt.vosp.webendpoint.impl.helper;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NextGenClientAuthorisationRequestValidation 
{
    /*
     * request parameter validation
     */
    public String validate(Map<String,String> requestParamsMap)
    {
        String errorMessage = "";
        boolean multipleParams = false;
        if(requestParamsMap != null)
        {
            Set<String> set = requestParamsMap.keySet();
            for(Iterator<String> iterator = set.iterator(); iterator.hasNext();)
            {
                String key = iterator.next();
                String value = requestParamsMap.get(key);
                if((key != null && !key.trim().isEmpty()) && (value == null || value.trim().isEmpty()))
                {
                    if(errorMessage.trim().isEmpty())
                    {
                        errorMessage = key;
                    }
                    else
                    {
                        errorMessage = errorMessage + key;
                        multipleParams = true;
                    }
                }
            }
            
            if(!errorMessage.trim().isEmpty())
            {
                if(multipleParams)
                {
                    errorMessage = errorMessage + " are mandatory parameters. They should not be null or empty";
                }
                else
                {
                    errorMessage = errorMessage + " is a mandatory parameter. It should not be null or empty";
                }
            }
        }
        return errorMessage;
    }
}
