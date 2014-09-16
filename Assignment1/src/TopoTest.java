

public class TopoTest {

	public static void main(String args[]) {
	
		DirectedGraph g1 = new AdjMatrixGraph(args[0]);
		DirectedGraph g2 = new AdjListGraph(args[0]);

		System.out.println("A topological order for the "+
			"graph in the file " +args[0]+":"+g1.topo());

		System.out.println("A topological order for the "+
			"graph in the file " +args[0]+":"+g2.topo());
	}
}