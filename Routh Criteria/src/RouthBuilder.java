import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class RouthBuilder {

	private List < Double > equationCoeff;
	private List < Double > degreeCoeff;
	private List < Double > equationCoeffWithZeros;
	private boolean degreeExist [];
	private boolean systemStability;
	private RegexChecker checkEquation;
	private double [][] routhTable;
	private int storeCoeff1, storeCoeff2;
	private RouthSolver equationSlover =  new RouthSolver();
	private boolean firstTime;
	
	public void buildTable(String inputEquation ){
		
		parseEquation(inputEquation);
		checkStability(inputEquation);
		
		formCoeff();
		equationSlover.setFirstTime(firstTime);
		equationSlover.solveEquation(routhTable ) ;
		
		
	}
	
	private void formCoeff(){
		
		int coeffNumber = equationCoeff.size();
		int halfSize = coeffNumber / 2 + (coeffNumber % 2);
		
		routhTable = new double [coeffNumber][coeffNumber];
		int i = 0;
		storeCoeff1 = storeCoeff2 = 0;
		
		for(double coeff: equationCoeff){
			
			if(coeff == 0) coeff = 1e-5;
			if(i % 2 == 0) routhTable[0][storeCoeff1++] = coeff;
			else routhTable[1][storeCoeff2++] = coeff;
			i++;
		}
		
		for(i = 2; i < coeffNumber; i++){
			for(int j = 0; j < halfSize - 1; j++){
				
				routhTable[i][j] = routhTable[i - 1][0] * routhTable[i - 2][j + 1] - routhTable[i - 1][j + 1] * routhTable[i - 2][0];
				routhTable[i][j] /= routhTable[i - 1][0];
				if(routhTable[i][j] == 0) routhTable[i][j] = 1e-5;
			}
		}
	}
	
	private void parseEquation(String inputEquation){
		
		String search = "(-?)\\d+\\.*\\d*S";
		try {
			
			checkEquation = new RegexChecker();
			equationCoeff = checkEquation.checker(search, inputEquation, true);
			
		} catch (SQLException e) {
			
			System.out.println("Invalid Input Equation!!!!");
		}
		
	}
	
	private void checkStability(String inputEquation){
		
		systemStability = true;
		boolean positiveSign = false, negativeSign = false;
		
		int startIndex = inputEquation.indexOf('^');
		int endIndex = inputEquation.indexOf('+');
		String tempInput = inputEquation.substring(1, inputEquation.length());
		int endIndex2 = tempInput.indexOf('-');
		if(endIndex2 != -1 && endIndex2 < endIndex) endIndex = endIndex2;
		
		String powerNumber = "";
		
		for(int i = startIndex + 1; i < endIndex; i++){
			
			if(inputEquation.charAt(i) != ' ') powerNumber += inputEquation.charAt(i);
		}
		
		int largestPower = Integer.parseInt(powerNumber);
		
		if(largestPower + 1 != equationCoeff.size()){ 
			systemStability = false;
			fillZeroCoeff(inputEquation);
			
		}
		
		for(int i = 0; i < inputEquation.length() && (!positiveSign || !negativeSign) && systemStability ; i++){
			
			if(inputEquation.charAt(i) == '+') positiveSign = true;
			else if (inputEquation.charAt(i) == '-') negativeSign = true;
		}
		
		if(positiveSign == true && negativeSign == true) systemStability = false;
	}
	
	public void fillZeroCoeff(String inputEquation){
		
		
		equationCoeffWithZeros = new LinkedList<Double>();
		String search = "\\^\\s+\\d+";
		try {
			
			degreeCoeff = checkEquation.checker(search, inputEquation, false);
		
		} catch (SQLException e) {
			
		}
		
		double largestPower = degreeCoeff.get(0);
		degreeExist = new boolean [(int) largestPower + 1];
		
		for(double degree: degreeCoeff) 
			degreeExist[(int) degree] = true;
		
		for(int i = (int) largestPower, j = 0; i >= 0; i--){
			
			if(degreeExist[i] == true) equationCoeffWithZeros.add(equationCoeff.get(j++));
			else equationCoeffWithZeros.add(new Double (0));
		}
		
		equationCoeff = equationCoeffWithZeros;
	}

	public List<Double> getEquationCoeff() {
		return equationCoeff;
	}

	public RouthSolver getEquationSlover() {
		return equationSlover;
	}

	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}

	
	

}
