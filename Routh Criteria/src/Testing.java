import org.junit.Test;


public class Testing {

	@Test
	public void test() {
		
		RouthImplementation implement = new RouthImplementation();
		
		String inputEquation = "1S ^ 5 + 1S ^ 4 + 10S ^ 3 + 72S ^ 2 + 152S ^ 1 + 240S ^ 0";
		implement.inputString(inputEquation , true);
		new Shifter(implement);
		
		inputEquation = "1S ^ 5 + -1S ^ 4 + 10S ^ 3 + 72S ^ 2 + 152S ^ 1 + 240S ^ 0";
		implement.inputString(inputEquation, true);
		new Shifter(implement);
		
		inputEquation = "1S ^ 5 + 10S ^ 3 + 72S ^ 2 + 152S ^ 1 + 240S ^ 0";
		implement.inputString(inputEquation, true);
		new Shifter(implement);
		
		inputEquation = "1S ^ 5 + 10S ^ 3 + 72S ^ 2 + 152S ^ 1 + 240S ^ 0";
		implement.inputString(inputEquation, true);
		new Shifter(implement);
		
		inputEquation = "1S ^ 3 + -9S ^ 2 + 26S ^ 1 + -24S ^ 0";
		implement.inputString(inputEquation, true);
		new Shifter(implement);
		
		inputEquation = "1S ^ 2 + -5S ^ 1 + 6S ^ 0";
		implement.inputString(inputEquation, true);
		new Shifter(implement);
	}

}
