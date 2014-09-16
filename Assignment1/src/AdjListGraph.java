import java.io.*;
import java.util.*;


public class AdjListGraph extends DirectedGraph {
	protected ArrayList<DoubleLinkedList<Integer>> g;

	public AdjListGraph(String filename) {
		super(filename);
		g = new ArrayList<DoubleLinkedList<Integer>>();
		for (int i = 0; i < numNodes; i++)
			g.add(new DoubleLinkedList<Integer>());
		buildGraph();
	}


	public void addEdge(int v1, int v2) {
		DoubleLinkedList<Integer> d = g.get(v1);
		d.insertLast(v2);
	}

	public String topo() {
		//Set up indegree array and myQueue, output
		int[] inDegree = new int[numNodes];
		String output = "";
		for( int i = 0; i < numNodes; i++ ){
			for( int j = 0; j < g.get(i).getSize(); j++ ){
				int index = g.get(i).getDataAt(j);
				inDegree[index]++;
			}
		}
		MyQueue<String> mq = new MyQueue<String>();
		
		for( int i = 0; i < numNodes; i++ ){
			if( inDegree[i] == 0 ){
				mq.enqueue( vertexNames[i] );
			}
		}
		
		while( !mq.empty() ){
			String s = mq.serve();
			output = output + s;
			int index = getNodeNum( s );
			
			for( int i = 0; i < g.get(index).getSize(); i++ ){
				int num = g.get(index).getDataAt(i);
				inDegree[num]--;
				if( inDegree[num] == 0 ){
					mq.enqueue( vertexNames[num] );
				}
			}
		}
		
		// check it has a topological order or not.
		int total = 0;
		for( int i = 0; i < numNodes; i++ ){
			total = total + inDegree[i];
		}
		
		if( total != 0 ){
			output = "no topological order found";
		}
		
		return output;
			
		}	
	
}
