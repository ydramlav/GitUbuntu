/*import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Test {

	
	public static void main(String args[]){
		
		StringBuffer str = new StringBuffer();
		String s="%%aaaasas**!@#$ da122)(*A";
		
		String ab=s.substring(s.length()-1,s.length());
		String bc=s.substring(0, s.length()-1);
		
		String result = bc.replaceAll("[^\\dA-Za-z ]", "?").replaceAll("\\s", "");
		String result2=ab.replaceAll("[^\\dA-Za-z ]", "?♦");

		str.append(result).append(result2);
		
	
		System.out.println("FinalResult" +str);
		
		String s="%%aaaasda";
		 Pattern p = Pattern.compile("[A-Za-z]");
	     Matcher m = p.matcher(s);
	     boolean b = m.find();
	     if (b == true){
	    	 System.out.println("hello");
	    	// m.replaceAll(replacement)
	     }
	     else{
	    	 System.out.println("fail");
	     }
	   
	}
}
	
	


}*/