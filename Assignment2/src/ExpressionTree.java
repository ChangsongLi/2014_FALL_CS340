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
	
	public static final int PL = 0;
	public static final int PM = 1;
	public static final int MD = 2;
	public static final int SQ = 3;
	public static final int UM = 4;
	public static final int PR = 5;

	Node root;

	public ExpressionTree(String exp, int format) {
		if (format == INFIX)
			buildInfix(exp);
		else 
			buildPostfix(exp);
	}

	private void buildPostfix(String exp) {
		Stack<Node> tree = new Stack<Node>();
		String operator = "+-*/^!";
		StringTokenizer st = new StringTokenizer(exp);
		
		while( st.hasMoreTokens() ){
			String str = st.nextToken();
			if( operator.contains( str ) ){
				Node n;
				Node right = tree.pop();
				if( !str.equals("!") ){
					Node left = tree.pop();	
					n = new Node( left, str, right );
				}
				else{
					n = new Node( null, str, right );
				}
				tree.push( n );
			}
			else{
				tree.push( new Node( null, str, null ) );
			}
		}
		
		root = tree.pop();	
	}

	private void buildInfix(String exp) {
		Stack<Node> tree = new Stack<Node>();
		Stack<Node> op = new Stack<Node>();
		String operator = "+-*/^!()";
		StringTokenizer st = new StringTokenizer(exp);
		while( st.hasMoreTokens() ){
			String str = st.nextToken();
			if( !operator.contains(str) ){
				if( op.isEmpty() ){
					tree.push(new Node( null, str, null ));
				}
				else{
					String l = op.peek().data;
					int last = precedent( l );
					if( last == UM ){
						Node n = op.pop();
						n.right = new Node( null, str, null );
						tree.push(n);
					}
					else{
						tree.push(new Node( null, str, null ));
					}
				}
			}
			else{
				if( op.isEmpty() ){
					op.push( new Node( null, str, null ));
				}
				else{
					String l = op.peek().data;
					int last = precedent( l );
					int cur = precedent( str );
					
					//Unary minus
					if( last == UM ){
						if ( cur == PL )
							op.push( new Node( null, str, null ));
						else{
							Node right = tree.pop();
							op.pop();
							Node newNode = new Node( null, "!", right );
							tree.push( newNode );
							if( !str.equals(")") )
								op.push(new Node( null, str, null ));
							else{
								while( !(l = op.pop().data).equals("(") ){
									Node r = tree.pop();
									Node left = tree.pop();
									Node n = new Node( left, l, r );
									tree.push( n );
								}
							}
						}
					}
					else if( cur == PR ){
						while( !(l = op.pop().data).equals("(") ){
							Node right = tree.pop();
							Node left = tree.pop();
							Node newNode = new Node( left, l, right );
							tree.push( newNode );
						}
					}
					else if( cur == 0 ){
						op.push( new Node( null, str, null ));
					}
					else if( cur > last ){
						op.push( new Node( null, str, null ));
					}
					else if( cur == last &&( cur == 4 ||
						cur == 0 ||cur == 3 )){
						op.push( new Node( null, str, null ));
					}
					else if( cur == last &&( cur == 1 || cur == 2 )){
						Node right = tree.pop();
						Node left = tree.pop();
						Node newNode = new Node( left, op.pop().data , right );
						tree.push(newNode);
						op.push( new Node( null, str, null ));
					}
					else if( cur < last ){
						while( cur <= last ){
							Node right = tree.pop();
							Node left = tree.pop();
							Node newNode = new Node( left, op.pop().data , right );
							tree.push(newNode);
							if(!op.empty())
								last = precedent(op.peek().data);
							else
								last = 0;
						}
						op.push( new Node( null, str, null ));
					}
					
				}
			}
		}
		while( !op.isEmpty() ){
			String o = op.pop().data;
			Node right = tree.pop();
			Node newNode;
			if( !o.equals("!") ){
				Node left = tree.pop();
				newNode = new Node( left, o, right );
			}
			else{
				newNode = new Node( null, o, right );
			}
			tree.push( newNode );
		}
		root = tree.pop();
	}
	public int precedent( String s ){
		if( s.equals("(") )
			return 0;
		else if( s.equals("+") || s.equals("-") )
			return 1;
		else if( s.equals("*") || s.equals("/") )
			return 2;
		else if( s.equals("^") )
			return 3;
		else if( s.equals("!") )
			return 4;
		else
			return 5;
	}

	public int evaluate() {
		return evaluate(root);
	}

	public int evaluate(Node r) {
		String operator = "+-*/^!";
		if( !operator.contains(r.data) ){
			return Integer.parseInt( r.data );
		}
		
		if(r.data.equals("+"))
			return evaluate( r.left ) + evaluate( r.right );
		else if( r.data.equals("-") )
			return evaluate( r.left ) - evaluate( r.right );
		else if(r.data.equals("*"))
			return evaluate( r.left ) * evaluate( r.right );
		else if( r.data.equals("/") )
			return evaluate( r.left ) / evaluate( r.right );
		else if( r.data.equals("^") )
			return (int)Math.pow( evaluate( r.left ), evaluate( r.right ) );
		else
			return -evaluate( r.right );

	}

	public String toPostfix() {
		return toPostfix(root);
	}

	private String toPostfix(Node r) {
		if( r == null )
			return "";
		
		String left = toPostfix(r.left);
		String right = toPostfix(r.right);
		
		if( !left.equals("") )
			left = left + " ";
		if( !right.equals("") )
			right = right + " ";
		
		return left + right + r.data;
	}

	public String toInfix() {
		return toInfix(root);
	}

	private String toInfix(Node r) {
		if( r == null )
			return "";
		
		String left = toInfix(r.left);
		String right = toInfix(r.right);
		
		if( r.data.equals("!") ){
			return "( "+r.data + " " +right+ " )";
		}
		if( !left.equals("") )
			left = "( "+left + " ";
		if( !right.equals("") )
			right = " " + right + " )";
		
		return left + r.data + right ;
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