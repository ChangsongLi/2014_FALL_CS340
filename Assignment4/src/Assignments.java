import java.io.*;
import java.util.*;


public class Assignments {
	
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
	
	Vector<String> v;
	SymbolTable s;
	Node root;
	private final String gtExp = "!";
	private final String gtMult = "!^*/%";
	private final String gtAdd = "!^*/%+-";
	
	
	public static void main(String[] args) throws IOException{
		@SuppressWarnings("unused")
		Assignments a = new Assignments(args[0]);
	}
	
	public Assignments(String file) throws IOException{
		v = new Vector<String>(10,10);
		BufferedReader bf = new BufferedReader( new FileReader(file)); 
		String line;
		while((line = bf.readLine()) != null){
			v.add(line);
		}
		bf.close();
		s = new SymbolTable(v.size());
		print();
	}
	
	private void print(){
		for(int i = 0; i < v.size(); i++){
			Scanner scan = new Scanner(v.elementAt(i));
			String key = scan.next();
			s.insert(key);
			scan.next();
			String line = scan.nextLine();
			if(checkValid(line)){
				buildInfix(line);
				int value = evaluate(root);
				s.setValue(key, value);
			}
			else{
				System.out.println("Error: "+v.elementAt(i));
				System.out.print( getInvalid(line));
			}
			scan.close();
		}
		
		printVar();
	}
	
	private void printVar(){
		System.out.println("Final Variable Values");
		SymbolTable.STIterator sti = s.new STIterator();
		while(sti.hasNext()){
			String output = sti.next();
			if(s.getData(output) != null){
				int value = (int)s.getData(output);
				System.out.println(output+ " "+value);
			}
			else{
				System.out.println(output+ " unassigned");
			}
		}
	}
	
	private boolean checkValid(String str){
		Scanner scan = new Scanner(str);
		while(scan.hasNext()){
			String part = scan.next();
			if(Character.isLetter(part.charAt(0))){
				if(s.getData(part) == null){
					scan.close();
					return false;
				}
			}
		}
		scan.close();
		return true;
	}
	
	private String getInvalid(String str){
		String ret = "";
		Scanner scan = new Scanner(str);
		while(scan.hasNext()){
			String part = scan.next();
			if(Character.isLetter(part.charAt(0))){
				if(s.getData(part) == null){
					ret = ret + part +" has not been assigned a value\n";
				}
			}
		}
		ret = ret + "\n";
		scan.close();
		return ret;
	}
	
	private void buildInfix(String exp) {
		Stack<Node> operators = new Stack<>();
		Stack<Node> operands = new Stack<>();
		Scanner s = new Scanner(exp);
		while (s.hasNext()) {
			String token = s.next();
			if (Character.isDigit(token.charAt(0)) || Character.isLetter(token.charAt(0)))
				operands.push(new Node(null, token, null));
			else 
				doOperator(operators, operands, token);
		}
		doOperator(operators, operands, "");
		root = operands.pop();	
	}
	
	private void doOperator(Stack<Node> operators, Stack<Node> operands, String op) {
		while (!operators.empty() && !operators.peek().data.equals("(") && precLess(op, operators.peek().data)) {
			Node oper = operators.pop();
			if (oper.data.charAt(0) != '!') 
				oper.right = operands.pop();
			oper.left = operands.pop();
			operands.push(oper);
		}
		if (op.equals(")"))  operators.pop();
		if (!op.equals(")")) operators.push(new Node(null, op, null));
	}
	
	private boolean precLess(String op1, String op2) {

		switch (op1) {
			case "(": return false;
			case ")": return true;
			case "!": return false;
			case "^": return gtExp.indexOf(op2) != -1;
			case "*":
			case "/":
			case "%": return gtMult.indexOf(op2) != -1;
			case "+": 
			case "-": return gtAdd.indexOf(op2) != -1;
			case "" : return true;
		}
		return true; //will not be reached
	}
	
	public int evaluate(Node r) {
		int x;
		int y = 0;
		if (isLeaf(r)){ 
			if(Character.isLetter(r.data.charAt(0))){
				return (int)s.getData(r.data);
			}
			return Integer.parseInt(r.data);
		}
		x = evaluate(r.left);
		if (r.data.charAt(0) != '!')
			y = evaluate(r.right);

		switch (r.data) {
			case "!":	return -x;

			case "^":	return (int) Math.pow(x,y);

			case "*":	return x*y;

			case "/":	return x/y;

			case "%":	return x%y;

			case "+":	return x+y;

			case "-":	return x-y;

		}
		return 0; //never reached included for compiler
	}
	
	private boolean isLeaf(Node r) {
		return r.left == null && r.right == null;
	}
	
	private String printTree(Node n){
		if(n == null)
			return "";
		String left = printTree(n.left);
		String right = printTree(n.right);
		
		return left+" "+n.data+" "+right;
	}
}
