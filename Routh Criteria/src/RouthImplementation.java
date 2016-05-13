
public class RouthImplementation implements RouthInterface{

	RouthBuilder builder;
	
	@Override
	public void inputString(String inputEquation , boolean firstTime) {
		builder = new RouthBuilder();
		builder.setFirstTime(firstTime);
		builder.buildTable(inputEquation) ;
		
	}

	@Override
	public String outputString() {
		
		return null;
	}

	public RouthBuilder getBuilder() {
		return builder;
	}


	

}
