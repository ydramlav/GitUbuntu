/*import java.text.Normalizer;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class Test2 {
	public static void main(String args[]){
		//String abc="["S0219268","S0219269"]";
		String subjectString = "öäü";
		subjectString = Normalizer.normalize(subjectString, Normalizer.Form.NFD);
		String resultString = subjectString.replaceAll("[^\\x00-\\x7F]", "?");
		System.out.println("res "+resultString);
		
		Test2 test2= new Test2();
		test2.replaceSpecialCharacter("friendly$name¥ª");
		

		
	}
	public void replaceSpecialCharacter(String friendlyname) 
    { 
        
        
        String resultString = friendlyname.replaceAll("[^\\x00-\\x7F]", "?");
      //  String withReplacedchar=k.replaceAll("\\s", "").replaceAll("\\W", "?"); 
       System.out.println(resultString); 
    } 
}

*/