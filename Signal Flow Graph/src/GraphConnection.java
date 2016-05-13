
public class GraphConnection {

	private int fromVertex;
	private int toVertex;
	private double edgeGain;
	
	public GraphConnection(int fromVertex, int toVertex, double edgeGain){
		
		this.fromVertex = fromVertex;
		this.toVertex = toVertex;
		this.edgeGain = edgeGain;
	}
	
	public int getToVertex() {
		return toVertex;
	}
	
	public void setToVertex(int toVetex) {
		this.toVertex = toVetex;
	}
	
	public double getEdgeGain() {
		return edgeGain;
	}
	
	public void setEdgeGain(double edgeGain) {
		this.edgeGain = edgeGain;
	}

	public int getFromVertex() {
		return fromVertex;
	}

	public void setFromVertex(int fromVertex) {
		this.fromVertex = fromVertex;
	}
	
}
