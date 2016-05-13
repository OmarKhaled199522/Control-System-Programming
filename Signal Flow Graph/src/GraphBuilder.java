
import java.awt.Point;
import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;


public class GraphBuilder {

	private int numOfVerticies;
	private ArrayList< ArrayList <Integer> > forwardPaths;
	private ArrayList< ArrayList <Integer> > graphcycles;
	private ArrayList< ArrayList < ArrayList<Integer> > > nonTouchingLoops;
	private ArrayList <Integer> tempForwardPath; 
	private int numOfForwardPaths;
	private boolean visitedVertex [];
	private ArrayList<Integer> tempCycle;
	private int numOfCycles;
	private HashMap <Point, Double> connectionEdges;
	private Point edgeDetails;
	private double gainSum;
	private boolean cyclesNotInForwardPaths [];
	private double mios[];
	private double deltas[];
	private double bigDelta;
	private int sourceNode;
	private int sinkNode;
	private ArrayList< Integer > repeatedCycles;
	private double transferResult;
	private ArrayList<ArrayList<Integer>> tempNonTouchingLoops;
	private int tempSource;
	private int tempSink;
	
	public void initializeEdges(int size, ArrayList<ArrayList<GraphConnection> > verticiesEdges, 
			int startNode, int endNode, boolean directOperation){
		
		sourceNode = startNode;
		tempSink = size - 1;
		tempSource = 0;
		sinkNode = endNode;
		connectionEdges = new HashMap<Point, Double>();
		nonTouchingLoops = new ArrayList <ArrayList<ArrayList<Integer>>>();
		
		for(int i = 0; i < verticiesEdges.size(); i++){
			for(int j = 0; j < verticiesEdges.get(i).size(); j++){
				
				int fromVertex = verticiesEdges.get(i).get(j).getFromVertex();
				int toVertex = verticiesEdges.get(i).get(j).getToVertex();
				double edgeGain = verticiesEdges.get(i).get(j).getEdgeGain();
				edgeDetails = new Point(fromVertex, toVertex);
				connectionEdges.put(edgeDetails, edgeGain);
			}
		}
		
		findForwardPaths(size, verticiesEdges, directOperation);
		mios = new double [numOfForwardPaths];
		deltas = new double [numOfForwardPaths];
		cyclesNotInForwardPaths = new boolean [numOfCycles];
		
		for(int i = 0; i < numOfForwardPaths; i++) 
			mios[i] = calculateGain(forwardPaths.get(i));
		
		gainSum = 1;
		
		for(int i = 0; i < numOfCycles; i++) cyclesNotInForwardPaths[i] = true;
		
		boolean printLoops = true;
		
		bigDelta = findNonTouchingLoops(printLoops);
		System.out.println(bigDelta);
		printLoops = false;
		
		for(int i = 0; i < numOfForwardPaths; i++){
			
			findAvailableCycles(i);
			deltas[i] = findNonTouchingLoops(printLoops);
			System.out.println(deltas[i]);
		}
		
		transferResult = getTransferFunction();
		System.out.println(transferResult);
		
		for(int i = 0; i < nonTouchingLoops.size(); i++){
			
			for(int j = 0; j < nonTouchingLoops.get(i).size(); j++){
				
				for(int k = 0; k < nonTouchingLoops.get(i).get(j).size(); k++){
					
					System.out.print(nonTouchingLoops.get(i).get(j).get(k) + " ");
					
				}
				
				System.out.println();
			}
		}
	}
	
	public void setTransferFunction(double transferResult) {
		this.transferResult = transferResult;
	}

	public double getTransferResult() {
		return transferResult;
	}

	public void setTransferResult(double transferResult) {
		this.transferResult = transferResult;
	}

	private double getTransferFunction(){
		
		int numerator = 0;
		
		for(int i = 0; i < numOfForwardPaths; i++) numerator += (mios[i] * deltas[i]);
		
		return numerator / bigDelta;
	}
	
	private void findAvailableCycles(int excludedPath){
		
		ArrayList<Integer> tempForwardPath = forwardPaths.get(excludedPath); 
		
		for(int i = 0; i < numOfCycles; i++) cyclesNotInForwardPaths[i] = true;
		
		for(int i = 0; i < tempForwardPath.size(); i++){
			
			for(int j = 0; j < numOfCycles; j++){
				
				if(cyclesNotInForwardPaths[j] == false) continue;
				ArrayList<Integer> tempCycleCompare = graphcycles.get(j);
				
				for(int k = 0; k < tempCycleCompare.size(); k++){
					
					if(tempForwardPath.get(i).equals(tempCycleCompare.get(k)) ){
						
						cyclesNotInForwardPaths[j] = false;
						break;
					}
				}
			}
		}
	}
	
	private boolean checkTouching(int cyclesChosen [], int numOfNonTouching, boolean printLoops){
		
		for(int i = 0; i < cyclesChosen.length; i++){
			
			for(int j = i + 1; j < cyclesChosen.length; j++){
				
				ArrayList<Integer> firstCycle = graphcycles.get(cyclesChosen[i]);
				ArrayList<Integer> secondCycle = graphcycles.get(cyclesChosen[j]);
				
				for(int k = 0; k < firstCycle.size(); k++){
					
					for(int h = 0; h < secondCycle.size(); h++){
						
						if(firstCycle.get(k).equals(secondCycle.get(h))) return true;
					}
				}
			}
		}
		
		
		if(printLoops == true){
			
			ArrayList<Integer> addNonTouchingloops = new ArrayList<Integer>();
			
			for(int i = 0; i < cyclesChosen.length; i++) 
				addNonTouchingloops.add(cyclesChosen[i]);
			
			tempNonTouchingLoops.add(addNonTouchingloops);
		}
		
		return false;
	}
	
	private void calculateNonTouchingLoops(int numOfNonTouching, int numCyclesChosen ,
			int traverseIndex, int cyclesChosen [], boolean printLoops){
		
		if(numCyclesChosen == numOfNonTouching){
			
			if(!checkTouching(cyclesChosen, numOfNonTouching, printLoops)){
				
				int loopsGain = 1;
				
				for(int i = 0; i < numOfNonTouching; i++){
					
					loopsGain *= calculateGain(graphcycles.get(cyclesChosen[i]));
				}
				
				gainSum += loopsGain;
			}
			
			return;
		}
		
		if(traverseIndex == numOfCycles) return;
		
		calculateNonTouchingLoops(numOfNonTouching, numCyclesChosen ,traverseIndex + 1, cyclesChosen, printLoops);
		
		if(numCyclesChosen < numOfNonTouching &&  cyclesNotInForwardPaths[traverseIndex] == true ){
			
			cyclesChosen[numCyclesChosen] = traverseIndex;
			calculateNonTouchingLoops(numOfNonTouching, numCyclesChosen + 1, traverseIndex + 1, cyclesChosen, printLoops);
		}
	}
	
	private int findNonTouchingLoops(boolean printLoops){
		
		int totalGain = 1;
		int signOfLoops = -1;
		gainSum = 1;
		int i = 1;
		
		while(gainSum != 0){
			
			gainSum = 0;
			int cyclesChosen [] = new int [i];
			tempNonTouchingLoops = new ArrayList <ArrayList<Integer>>();
			calculateNonTouchingLoops(i, 0, 0, cyclesChosen, printLoops);
			totalGain += (gainSum * signOfLoops);
			
			signOfLoops *= -1;
			i++;
			nonTouchingLoops.add(tempNonTouchingLoops);
		}
		
		return totalGain;
	}
	
	private void removeDuplicateCycles(){
		
		repeatedCycles = new ArrayList<Integer>();
		
		for(int i = 0; i < numOfCycles; i++){
			
			ArrayList<Integer> firstCycle = graphcycles.get(i);
			
			for(int j = i + 1; j < numOfCycles; j++){
			
				ArrayList<Integer> secondCycle = graphcycles.get(j);
				
				boolean sameCycles = true;
				int cycleSize1 = firstCycle.size();
				int cycleSize2 = secondCycle.size();
				
				
				if(repeatedCycles.contains(j) || cycleSize1 != cycleSize2) continue;
				
				for(int k = 0; k < cycleSize1 && sameCycles == true; k++){
					
					boolean elementHit = false;
					
					for(int h = 0; h < cycleSize2 && elementHit == false; h++){
						
						if(firstCycle.get(k).equals(secondCycle.get(h))) elementHit = true;
					}
					
					if(elementHit == false) sameCycles = false;
				}
				
				if(sameCycles == true) repeatedCycles.add(j);
			}
		}
		
		implementRemovingDuplicateCycles();
	}
	
	private void implementRemovingDuplicateCycles(){
		
		Collections.sort(repeatedCycles);
		
		int repeatedCyclesLength = repeatedCycles.size(); 
		
		for(int i  = repeatedCyclesLength - 1; i >= 0; i--){
			
			int Cycleindex = repeatedCycles.get(i);
			graphcycles.remove(Cycleindex);
		}
		
		numOfCycles -= repeatedCyclesLength;
	}
	
	private void findForwardPaths(int size, ArrayList<ArrayList<GraphConnection> > verticiesEdges
			, boolean directOperation){
		
		numOfVerticies = size;
		setNumOfForwardPaths(0);
		forwardPaths = new ArrayList<ArrayList<Integer>>();
		visitedVertex = new boolean [numOfVerticies];
		graphcycles = new ArrayList <ArrayList<Integer>>();
		visitedVertex[sourceNode] = true;
		int traverseIndex = 1;
		
		tempForwardPath = new ArrayList<Integer>();
		tempForwardPath.add(sourceNode);
		dfsGraph(sourceNode, traverseIndex, verticiesEdges);
		
		if(directOperation == false){
			
			sourceNode = tempSource;
			sinkNode = tempSink;
		}
		
		tempForwardPath = new ArrayList<Integer>();
		visitedVertex[sourceNode] = false;
	    tempForwardPath.add(sourceNode);
		detectCycles(sourceNode, 1, verticiesEdges);
		removeDuplicateCycles();
	}
	
	private int calculateGain(ArrayList<Integer> pathGain){
		
		int totalGain = 1;
		Double twoNodesGain;
		
		for(int i = 0; i < pathGain.size() - 1; i++){
		
			edgeDetails = new Point(pathGain.get(i), pathGain.get(i + 1));
			twoNodesGain = connectionEdges.get(edgeDetails);
			totalGain *= twoNodesGain;
		}
		
		return totalGain;
	}
	
	private void dfsGraph(int vertex, int traverseIndex ,
			ArrayList<ArrayList<GraphConnection> > verticiesEdges){
		
		if(vertex == sinkNode){
		
			ArrayList<Integer> tempPath = new ArrayList<Integer>();
			
			for(int i = 0; i < traverseIndex; i++) 
				tempPath.add(tempForwardPath.get(i));
			
			forwardPaths.add(tempPath);
			setNumOfForwardPaths(getNumOfForwardPaths() + 1);
			return;
		}
		
		visitedVertex[vertex] = true;
		
		for(int i = 0; i < verticiesEdges.get(vertex).size(); i++){
			
			int nextVertex = verticiesEdges.get(vertex).get(i).getToVertex();
			
			if(!visitedVertex[nextVertex]){
				
				if(traverseIndex < tempForwardPath.size()) 
					tempForwardPath.set(traverseIndex, nextVertex);
				
				else tempForwardPath.add(nextVertex);
				dfsGraph(nextVertex, traverseIndex + 1 ,verticiesEdges);
			} 
		}
		
		visitedVertex[vertex] = false;
	}
	
	private void detectCycles(int vertex, int traverseIndex, ArrayList<ArrayList<GraphConnection> > verticiesEdges){
		
		visitedVertex[vertex] = true;
		
		for(int i = 0; i < verticiesEdges.get(vertex).size(); i++){
			
			int nextVertex = verticiesEdges.get(vertex).get(i).getToVertex();
			
			if(!visitedVertex[nextVertex]){
				
				if(traverseIndex < tempForwardPath.size()) 
					tempForwardPath.set(traverseIndex, nextVertex);
				
				else tempForwardPath.add(nextVertex);
				
				detectCycles(nextVertex, traverseIndex + 1 , verticiesEdges);
				tempForwardPath.remove(traverseIndex);
				
			} else {
				
				int cycleStart = 0;
				for(int j = 0; j < tempForwardPath.size(); j++){
					
					if(tempForwardPath.get(j).equals(nextVertex)) cycleStart = j;
				}
				
				if(traverseIndex < tempForwardPath.size()) 
					tempForwardPath.set(traverseIndex, nextVertex);
				
				else tempForwardPath.add(nextVertex);
				
				tempCycle = new ArrayList<Integer>();
				
				for(int j = cycleStart; j < tempForwardPath.size(); j++) 
					tempCycle.add(tempForwardPath.get(j));
				
				tempForwardPath.remove(tempForwardPath.size() - 1);
				numOfCycles++;
				graphcycles.add(tempCycle);
			}
		}
		
		visitedVertex[vertex] = false;
	}

	public int getNumOfForwardPaths() {
		return numOfForwardPaths;
	}

	public void setNumOfForwardPaths(int numOfForwardPaths) {
		this.numOfForwardPaths = numOfForwardPaths;
	}

	public int getNumOfCycles() {
		return numOfCycles;
	}

	public void setNumOfCycles(int numOfCycles) {
		this.numOfCycles = numOfCycles;
	}

	public ArrayList<ArrayList<Integer>> getForwardPaths() {
		return forwardPaths;
	}

	public ArrayList<ArrayList<Integer>> getGraphcycles() {
		return graphcycles;
	}

	public ArrayList<ArrayList<ArrayList<Integer>>> getNonTouchingLoops() {
		return nonTouchingLoops;
	}

	public double[] getDeltas() {
		return deltas;
	}

	public double getBigDelta() {
		return bigDelta;
	}
	
	
}
