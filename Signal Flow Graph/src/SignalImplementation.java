import java.util.ArrayList;


public class SignalImplementation implements GraphInterface{

	GraphBuilder builder;
	
	@Override
	public GraphBuilder input(int size, ArrayList<ArrayList<GraphConnection> > verticiesEdges,
			int sourceNode, int sinkNode, boolean directOperation) {
		
		builder = new GraphBuilder();
		builder.initializeEdges(size , verticiesEdges, sourceNode, sinkNode, directOperation);
		
		return builder;
	}

	@Override
	public double output() {
		

		return builder.getTransferResult();
	}

}
