import java.util.*;
import java.io.*;

public class ExpressionTree {

	private class Node {
		Node left;
		String data;
		Node right;

		Node(Node l, String d, Node r) {
			left = l;
			data = d;
			right = r;
		}
	}

	public static final int INFIX = 1;
	public static final int POSTFIX = 2;

	Node root;

	public ExpressionTree(String exp, int format) {
		if (format == INFIX)
			buildInfix(exp);
		else 
			buildPostfix(exp);
	}

	private void buildPostfix(String exp) {

	}

	private void buildInfix(String exp) {

	}

	public int evaluate() {
		return evaluate(root);
	}

	public int evaluate(Node r) {

	}

	public String toPostfix() {
		return toPostfix(root);
	}

	private String toPostfix(Node r) {

	}

	public String toInfix() {
		return toInfix(root);
	}

	private String toInfix(Node r) {

	}



	public static void main(String args[]) throws IOException{
		BufferedReader b1 = new BufferedReader(new FileReader(args[0]));
		ExpressionTree e;
		String exp = b1.readLine();
		while (!exp.equals("")) {
			e = new ExpressionTree(exp,ExpressionTree.POSTFIX);
			System.out.println("Infix format: " + e.toInfix());
			System.out.println("Postfix format: " + e.toPostfix());

			System.out.println("Expression value: "+e.evaluate());
			System.out.println();
			exp = b1.readLine();
		}
		exp = b1.readLine();
		while (exp != null) {
			e = new ExpressionTree(exp,ExpressionTree.INFIX);
			System.out.println("Infix format: " + e.toInfix());
			System.out.println("Postfix format: " + e.toPostfix());

			System.out.println("Expression value: "+e.evaluate());
			System.out.println();
			exp = b1.readLine();
		}


	}
}