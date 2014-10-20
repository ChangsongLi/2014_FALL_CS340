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
	}

	public void encode(String infile, String outfile) throws IOException {
	}

	public void decode(String infile, String outfile) throws IOException {
	}

	public static void main(String args[]) throws IOException {
		HuffmanASCIICompression h = new HuffmanASCIICompression();
		h.encode(args[0], args[1]);
		h.decode(args[1], args[0] + "_new");
	}
}