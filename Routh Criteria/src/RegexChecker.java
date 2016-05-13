import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexChecker {

	java.util.List < String > divider; // to store the matched parts
	java.util.List < Double > coeffs;
	
	public List<Double> checker(String theRegex, String str2Check, boolean coeff) throws SQLException{
		 
		divider = new LinkedList<String>();
		coeffs = new LinkedList<Double>();
		
		// search for the pattern in the input string
        Pattern checkRegex = Pattern.compile(theRegex , Pattern.CASE_INSENSITIVE);
        
        // This matcher used to find any match for the pattern in the input string
	    Matcher regexMatcher = checkRegex.matcher( str2Check );
	         
	    while ( regexMatcher.find() ){
	        	
	        	String s = regexMatcher.group();   
	        	divider.add(s);
	    }
	   
	    if(coeff) convertToInteger();
	    else convertDegree();
	    return coeffs;
	 }
	
	private void convertToInteger(){
		
		for(String s : divider){
			
			coeffs.add(Double.parseDouble(s.substring(0 , s.length() - 1)));
		}
	}
	
	private void convertDegree(){
		
		for(String s : divider){
			
			String temp = "";
			
			for(int i = 1; i < s.length(); i++){
				
				if(s.charAt(i) != ' ') temp += s.charAt(i);
			}
			
			coeffs.add(Double.parseDouble(temp));
		}
	}
	
}
