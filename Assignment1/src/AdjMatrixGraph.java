import java.io.*;


@SuppressWarnings("unused")
public class AdjMatrixGraph extends DirectedGraph {
	protected int g[][];

	public AdjMatrixGraph(String filename) { 
		super(filename);
		g = new int[numNodes][numNodes];
		buildGraph();
	}

	public void addEdge(int v1, int v2) {
		g[v1][v2] = 1;
	}

	public String topo() {
		//set up indegree array and Myqueue
		int[] inDegree = new int[numNodes];

		for( int col = 0; col < numNodes; col++ ){
			for( int row = 0; row < numNodes; row++ ){
				inDegree[col] = inDegree[col] + g[row][col];	
			}
		}	
		MyQueue<String> mq = new MyQueue<String>();
		String output = "";
		
		//finging topological order
		
		for( int i = 0; i < numNodes; i++ ){
			if( inDegree[i] == 0 ){
				mq.enqueue(vertexNames[i]);
			}
		}
		
		while( !mq.empty() ){
			//get node number when dequeue.
			String s = mq.serve();
			output = output+s;
			int index = getNodeNum(s);
			
			//substract the inDegree of correspond Nodes.
			//check if there is an node which point to the correspond Node
			//if not, add this node to queue.
			for( int i = 0; i < numNodes; i++ ){
				if( g[index][i] == 1 ){
					inDegree[i]--;
					
					if(inDegree[i] == 0){
						mq.enqueue(vertexNames[i]);
					}
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
