import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		System.out.println("Enter the equation : ");
		Scanner scanner = new Scanner(System.in);
		String inputEquation = scanner.nextLine();
		RouthImplementation implementation = new RouthImplementation();
		implementation.inputString(inputEquation , true);
		new Shifter(implementation);
		scanner.close();
	}

}
