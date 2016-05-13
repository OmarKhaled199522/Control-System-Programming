import java.util.ArrayList;
import java.util.List;

public class Shifter {
	private RouthImplementation routhStarter = new RouthImplementation();
	private double routhArray[][];
	private List<Double> equationCoeff;
	private List<Double> syntheticDivisionCoeff = new ArrayList<Double>();
	private List<Double> nextEquation = new ArrayList<Double>();
	private String next = "";
	private List<Double> roots = new ArrayList<Double>();
	private double shiftFactor = 0.05;
	private int positiveRoots = 0;
	private int maxPositiveRoots = Integer.MAX_VALUE;

	public Shifter(RouthImplementation routhStarter) {
		this.routhStarter = routhStarter;
		this.equationCoeff = routhStarter.getBuilder().getEquationCoeff();
		routhArray = routhStarter.getBuilder().getEquationSlover()
				.getEquationCoeff();
		for (int i = 0; i < equationCoeff.size() - 1; i++) {
			if (routhArray[i][0] * routhArray[i + 1][0] < 0)
				positiveRoots++;
		}
		maxPositiveRoots = positiveRoots;
		if (positiveRoots != 0)
			this.doSyntheticDivision();
	}

	public void doSyntheticDivision() {
		if (positiveRoots == 0) {
			this.printRoots();
			return;
		}

		copyCoeff();
		int size = syntheticDivisionCoeff.size();
		shift(syntheticDivisionCoeff, nextEquation);
		for (int i = 0; i < nextEquation.size() - 1; i++) {
			next += nextEquation.get(size - 1 - i).toString() + "S ^ "
					+ ((Integer) (nextEquation.size() - i - 1)).toString()
					+ " + ";
		}
		next += nextEquation.get(0).toString() + "S ^ "
				+ ((Integer) (0)).toString();
		// System.out.println(next);
		syntheticDivisionCoeff.clear();
		nextEquation.clear();
		checkStability();
	}

	private void printRoots() {
		System.out
				.println("------------------------------------------------------------------");
		System.out.println("#RHS roots = " + maxPositiveRoots);
		System.out.print("Real prats of Roots are : ");
		for (Double element : roots) {
			System.out.print("| " + element + " | ");
		}
		System.out
				.println("\n--------------------------------------------------------------------");

	}

	public void copyCoeff() {
		for (Double element : equationCoeff) {
			syntheticDivisionCoeff.add(element);
		}
	}

	public void checkStability() {
		int tempPositiveRoots = 0;
		routhStarter.inputString(next, false);
		next = "";
		routhArray = routhStarter.getBuilder().getEquationSlover()
				.getEquationCoeff();
		for (int i = 0; i < routhArray.length - 1; i++) {
			if (routhArray[i][0] * routhArray[i + 1][0] < 0) {
				tempPositiveRoots++;
			} else if (routhArray[i][0] * routhArray[i + 1][0] == 0) {
				tempPositiveRoots = positiveRoots - 1;
				break;
			}
		}
		if (tempPositiveRoots >= 0 && tempPositiveRoots < positiveRoots) {

			roots.add(shiftFactor);

		}
		shiftFactor += 0.05;
		positiveRoots = tempPositiveRoots;
		this.doSyntheticDivision();

	}

	public void shift(List<Double> syntheticDivisionCoeff,
			List<Double> nextEquation) {
		int size = syntheticDivisionCoeff.size();
		for (int i = 0; i < size - 1; i++) {
			for (int j = 1; j < syntheticDivisionCoeff.size(); j++) {
				syntheticDivisionCoeff.set(j, syntheticDivisionCoeff.get(j - 1)
						* shiftFactor + syntheticDivisionCoeff.get(j));
			}
			nextEquation.add(syntheticDivisionCoeff
					.remove(syntheticDivisionCoeff.size() - 1));
		}
		nextEquation.add(syntheticDivisionCoeff.get(0));

	}

	public List<Double> getRoots() {
		return roots;
	}

}
