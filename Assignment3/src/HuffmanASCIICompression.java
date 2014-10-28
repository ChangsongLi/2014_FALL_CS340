import java.io.*;
import java.util.*;

public class HuffmanASCIICompression {

	class Node {
		Node left;
		char ch;
		Node right;

		Node(Node L, char c, Node r) {
			left = L;
			ch = c;
			right = r;
		}
	}

	Node root;
	int frequency[];
	int totalChars;
	String encodings[];

	public HuffmanASCIICompression() {
		frequency = new int[128];
		totalChars = 0;
		encodings = new String[128];
	}

	public void encode(String infile, String outfile) throws IOException {
		readFile(infile);
		root = buildHuffmanTree();
		String s = postOrder(root);
		getEncoding(root,"");
		writeCode(s,infile,outfile);
	}
	public void decode(String infile, String outfile) throws IOException {
		BitInputStream b = new BitInputStream(infile);
		PrintWriter w = new PrintWriter( new FileWriter(outfile));
		totalChars= b.readInt();
		String po = b.readString();
		root = buildTree(po);
		int count = 0;
		while(count < totalChars){
			Node r = root;
			while(r.ch == (char)128){
				int bit = b.readBit();
				if(bit == 0)
					r = r.left;
				else
					r = r.right;
			}
			w.print(r.ch);
			count++;
		}
		w.flush();
		w.close();
		
	}
	
	private Node buildTree( String postOrder ){
		Stack<Node> s = new Stack<Node>();
		for (int i = 0; i < postOrder.length(); i++){
			if(postOrder.charAt(i) != (char)128){
				s.push( new Node(null, postOrder.charAt(i), null) );
			}
			else{
				Node r = s.pop();
				Node l = s.pop();
				s.push( new Node(l, (char)128, r));
			}
		}
		return s.pop();
	}
	
	public void writeCode(String po, String infile, String outfile) throws IOException{
		BitOutputStream b = new BitOutputStream(outfile);
		BufferedReader bf = new BufferedReader( new FileReader(infile) );
		b.writeInt(totalChars);
		b.writeString(po);
		int c;
		while( (c = bf.read()) != -1 ){
			b.writeBits(encodings[c]);
		}
		b.close();
		bf.close();
	}
	
	private void getEncoding(Node root, String s){
		if(root.ch != (char)128 ){
			encodings[root.ch] = s;
			return;
		}
		getEncoding(root.right,s+"1");
		getEncoding(root.left,s+"0");
	}
	
	private String postOrder(Node root){
		if(root == null)
			return	"";
		String l = postOrder(root.left);
		String r = postOrder(root.right);
		return l+r+root.ch;
	}
	
	private Node buildHuffmanTree(){
		PriorityQueue p = new PriorityQueue(128);
		for(int i = 0; i < 128; i++){
			if(frequency[i] != 0){
				p.insert( frequency[i], new Node(null, (char)i, null) );
			}
		}
		while( p.getSize() != 1){
			int fre = p.getMinKey();
			Node l = (Node)p.deleteMin();
			fre = fre + p.getMinKey();
			Node r = (Node)p.deleteMin();
			p.insert( fre, new Node( l, (char)128, r) );
		}
		return (Node)p.deleteMin();
	}
	
	public void readFile ( String infile ) throws IOException{
		BufferedReader bf = new BufferedReader( new FileReader(infile) );
		int c;
		while((c = bf.read()) != -1 ){
			totalChars++;
			frequency[c]++;
		}
		bf.close();
	}

	public static void main(String args[]) throws IOException {
		HuffmanASCIICompression h = new HuffmanASCIICompression();
		h.encode(args[0], args[1]);
		h.decode(args[1], args[0] + "_new");
	}
	
}