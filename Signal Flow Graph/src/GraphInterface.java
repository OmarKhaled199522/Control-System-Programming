import java.util.ArrayList;


public interface GraphInterface {

	public GraphBuilder input(int size, ArrayList <ArrayList <GraphConnection> > verticiesEdges,
			int sourceNode, int sinkNode, boolean directOperation);
	public double output();
	
}
