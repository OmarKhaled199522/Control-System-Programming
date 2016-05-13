
import java.util.ArrayList;

import org.junit.Test;


public class Testing {

	@Test
	public void test1() {
		
		SignalImplementation implement = new SignalImplementation();
		GraphConnection graph = new GraphConnection(0, 1, 1);
		ArrayList<ArrayList<GraphConnection>> arr = new ArrayList<ArrayList< GraphConnection > >();
		
		ArrayList<GraphConnection> add = new ArrayList<GraphConnection>();
		add.add(graph);
		arr.add(add);
		
		add = new ArrayList<GraphConnection>();
		graph = new GraphConnection(1, 2, 5);
		add.add(graph);
		graph = new GraphConnection(1, 6, 10);
		add.add(graph);
		arr.add(add);
		
		
		add = new ArrayList<GraphConnection>();
		graph = new GraphConnection(2, 3, 10);
		add.add(graph);;
		arr.add(add);
		
		add = new ArrayList<GraphConnection>();
		graph = new GraphConnection(3, 2, -1);
		add.add(graph);
		graph = new GraphConnection(3, 4, 2);
		add.add(graph);
		arr.add(add);
		
		
		add = new ArrayList<GraphConnection>();
		graph = new GraphConnection(4, 5, 1);
		add.add(graph);
		graph = new GraphConnection(4, 3, -2);
		add.add(graph);
		graph = new GraphConnection(4, 1, -1);
		add.add(graph);
		arr.add(add);
		
		add = new ArrayList<GraphConnection>();
		arr.add(add);
		
		add = new ArrayList<GraphConnection>();
		graph = new GraphConnection(6, 4 , 2);
		add.add(graph);
		graph = new GraphConnection(6, 6, -1);
		add.add(graph);
		arr.add(add);
		
		implement.input(7, arr, 0, 5, true);
	}
	
	
	@Test
	public void test2() {
	
		SignalImplementation implement = new SignalImplementation();
		GraphConnection graph = new GraphConnection(0, 0, 1);
		ArrayList<ArrayList<GraphConnection>> arr = new ArrayList<ArrayList< GraphConnection > >();
		ArrayList<GraphConnection> inner = new ArrayList<GraphConnection>();
		
		inner = new ArrayList<GraphConnection>();
		inner.add(graph);
		graph = new GraphConnection(0, 1, 5);
		inner.add(graph);
		
		arr.add(inner);
		
		
		inner = new ArrayList<GraphConnection>();
		graph = new GraphConnection(1, 1, 5);
		inner.add(graph);
		
		graph = new GraphConnection(1, 2, 5);
		inner.add(graph);
		
		arr.add(inner);
		
		inner = new ArrayList<GraphConnection>();
		arr.add(inner);
		
		GraphBuilder builder = implement.input(3, arr, 0, 2, true);
		System.out.println(builder.getNonTouchingLoops().get(1).get(0).get(0));
		
		
	}

}
