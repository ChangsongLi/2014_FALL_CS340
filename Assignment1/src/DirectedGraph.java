import java.io.*;
import java.util.*;

public abstract class DirectedGraph {

	protected String vertexNames[];
        protected BufferedReader b;
	protected int numNodes;

	public DirectedGraph(String filename) {
		try {
			b = new BufferedReader(new FileReader(filename));
			numNodes = Integer.parseInt(b.readLine());
		}
		catch (Exception e) {
			System.out.println("Error in file contents or file not found");
		}
	}
		

	protected void buildGraph() {
		try {
			@SuppressWarnings("unused")
			String str,v1,v2;
			int i1,i2;
			vertexNames = new String[numNodes];
			for(int i = 0; i < numNodes; i++){
				vertexNames[i] = b.readLine();
			}
			
			while((str=b.readLine())!=null){
				Scanner scan = new Scanner(str);
				v1 = scan.next();
				v2 = scan.next();
				//get node number
				i1 = getNodeNum(v1);
				i2 = getNodeNum(v2);
				
				addEdge(i1,i2);
			}
		}
		catch (Exception e) {
			System.out.println("Error in file contents or file not found");
		}
	}

	protected int getNodeNum(String v) {
		for (int i = 0; i < numNodes; i++)
			if (vertexNames[i].equals(v))
				return i;
		return -1;
	}
			

	public abstract void addEdge(int v1, int v2);

	public abstract String topo();
}