import java.io.*;
import java.util.*;

public class BTree {

	class BTreeNode{
		long location;
		int count;
		int[] keys;
		long[] children;
		long next;
		
		public BTreeNode(long loc) {
			location = loc;
			count = 0;
			keys = new int[max];
			children = new long[order];
			next = 0;
		}

		void setNext(long n){
			next = n;
		}
		
		void readNode() throws IOException{
			r.seek(location);
			count = r.readInt();
			for(int i = 0; i < keys.length; i++){
				keys[i] = r.readInt();
			}
			for(int i = 0; i < children.length; i++){
				children[i] = r.readLong();
			}
			if(children[0] == 0)
				next = r.readLong();
		}
		
		void writeNode() throws IOException{
			r.seek(location);
			r.writeInt(count);
			for(int i = 0; i < keys.length; i++){
				r.writeInt(keys[i]);
			}
			for(int i = 0; i < children.length; i++){
				r.writeLong(children[i]);
			}
			if(children[0] == 0)
				r.writeLong(next);
		}

		public int getLoc(int k){
			for(int i = 0; i < count; i++){
				if(keys[i] > k)
					return i;
			}
			return count;
		}
		public long getChild(int k) {
			
			return children[getLoc(k)];
		}

		public boolean isFull() {
			return count == max;
		}

		public void insert(int k) {
			int loc = getLoc(k);
			for(int i = count ; i > loc; i++){
				keys[i] = keys[i-1];
			}
			keys[loc] = k;
		}
		

		public boolean find(int k) {
			for(int i = 0; i < count; i++){
				if(keys[i] == k)
					return true;
			}
			return false;
		}
		
		public void split(int k) throws IOException{

		}
		
		public void split(int k, BTreeNode n){
			
		}
	}
	
	long head;
	RandomAccessFile r;
	String name;
	int order,max,min;
	BTreeNode root;
	Stack<BTreeNode> stack;
	
	public BTree(String n, int ord) throws IOException{
		// create a new B+Tree with order ord
		// n is the name of the file used to store the tree
		// if a file with name n already exists it should be deleted
		
		File f = new File(n);
		if(f.exists()) f.delete();
		name = n;
		order = ord;
		max = ord - 1;
		r = new RandomAccessFile(name,"rw");
		r.seek(0);
		r.writeInt(order);
		head = 0;
		r.writeLong(head);
		stack = new Stack<BTreeNode>();
	}

	public BTree(String n) throws IOException, RuntimeException{
		// open an exis2ng B+Tree
		// n is the name of the file that stores the tree
		// if a file with name n does not exists throw a RunTimeExcep2on
		File f = new File(n);
		name = n;
		if(!f.exists()) throw new RuntimeException();
		r = new RandomAccessFile(n,"rw");
		r.seek(0);
		order = r.readInt();
		head = r.readLong();
		max = order - 1;
		if(head != 0){
			root = new BTreeNode(head);
			root.readNode();
		}
	}

	public void insert(int k) throws IOException {
		// insert a new key with value k into the tree
		stack.removeAllElements();
		if( head == 0 ){
			head = r.length();
			root = new BTreeNode(head);
			root.insert(k);
			root.writeNode();
			r.seek(4);
			r.writeLong(head);
			return;
		}
		
		if(!searth(k)){
			BTreeNode tmp = stack.pop();
			if(tmp.isFull()){
				tmp.split(k);
			}
			else{
				tmp.insert(k);
				tmp.writeNode();
			}
		}
	}



	public boolean searth(int k) throws IOException{
		// if k is in the tree return true otherwise return false
		if(head != 0){
			BTreeNode tmp = root;
			long childLoc;
			while((childLoc = tmp.getChild(k)) != 0){
				stack.push(tmp);
				tmp = new BTreeNode(childLoc);
				tmp.readNode();
			}
			if(tmp.find(k))
				return true;
			stack.push(tmp);
		}
		return false;
	}

	public class BTIterator implements Iterator<Integer> {
		// An iterator that iterates through a range of keys in the tree
		// The range is provided in the arguments to the constructor

		public BTIterator(int low, int high) {
			// an iterator that can be used to find all the keys, k, in //the
			// tree such that low <= k <= high
		}

		public boolean hasNext() {
		}

		public Integer next() {
			// PRE: hasNext();
		}

		public void remove() {
			// op2onal method not implemented
		}
	}

	public Iterator<Integer> iterator(int low, int high) {
		// return a new iterator object
		return new BTIterator(low, high);
	}

	public void close() {
	}
}
