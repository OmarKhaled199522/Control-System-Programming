import java.util.LinkedList;
import java.util.List;

public class ZeroNegCoeffChecker {
	private List<Double> list;
	private List<Double> result = new LinkedList<Double>();
	private  double shiftFactor = 0;
	private String input = "";
	public ZeroNegCoeffChecker(List<Double> list , String input) {
		this.list=list;
		this.input = input;
	}
	public void start(){
		boolean flag = check(list);
		while (flag){
			shift();
			flag = check(list);
		}
	}
	public boolean check(List<Double> coeff){
		for (int i = 0;i<coeff.size()-1;i++){
			if (coeff.get(i) * coeff.get(i+1) <= 0){
				return true;
			}
				
		}
		return false;
	}
	public void shift( ){
		shiftFactor+= 0.05;
		int size = list.size();
		for (int i = 0; i < size - 1; i++) {
			for (int j = 1; j < list.size(); j++) {
				list.set(j, list.get(j - 1)
						* 0.05 + list.get(j));
				if(list.get(j) < 10e-4){
					list.set(j, 0.0);
				}
			}
			double x = list.remove(list.size() - 1);
			result.add(x);
		}
		result.add(list.get(0));
		list.clear();
		for (int i = result.size()-1;i>=0;i--){
			list.add(result.get(i));
		}
		result.clear();
		input = "";
		for (int i = 0; i < list.size() - 1; i++) {
			input += list.get(i).toString() + "S ^ "
					+ ((Integer) (list.size() - i - 1)).toString()
					+ " + ";
		}
		input += list.get(size-1).toString() + "S ^ "
				+ ((Integer) (0)).toString();
		
	}
	public List<Double> getList() {
		return list;
	}
	public double getShiftFactor() {
		return shiftFactor;
	}
	public String getInput() {
		return input;
	}

	
}
