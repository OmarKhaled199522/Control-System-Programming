
public class RouthSolver {
	private double equationCoeff [][];
	private boolean firstTime = true;
	public void solveEquation(double equationCoeff [] []){
		this.equationCoeff = equationCoeff;
		if(equationCoeff == null){
			System.out.println("System is Unstable(null array)");
			
		} else {
			
			boolean stable = true;
			for (int i = 0 ;i <equationCoeff.length - 1;i++){
				if (equationCoeff[i][0] * equationCoeff[i+1][0] < 0){
					stable = false;
					break;
				}
					
			}
			if (!stable && firstTime)
			{
				System.out.println("System is Unstable");
			}
			else if (stable && firstTime)
				System.out.println("System is Stable");
			
		
		}
	}
	public double[][] getEquationCoeff() {
		return equationCoeff;
	}
	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}
	
	
}
