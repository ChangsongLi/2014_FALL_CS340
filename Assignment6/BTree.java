import java.io.*;
import java.util.*;

public class BTree {

	class BTreeNode {
		long location;
		int count;
		int[] keys;
		long[] children;
		
		public BTreeNode(long loc) {
			location = loc;
			count = 0;
			keys = new int[max];
			children = new long[order];
		}

		public BTreeNode(int[] k, long[] ch, int c, long loc) {
			location = loc;
			count = c;
			children = ch;
			keys = k;
		}

		void readNode() throws IOException {
			r.seek(location);
			count = r.readInt();
			for (int i = 0; i < order-1; i++) {
				keys[i] = r.readInt();
			}
			for (int i = 0; i < order; i++) {
				children[i] = r.readLong();
			}
		}
		
		long getLocation(){
			return location;
		}

		void writeNode() throws IOException {
			r.seek(location);
			r.writeInt(count);
			for (int i = 0; i < order-1; i++) {
				r.writeInt(keys[i]);
			}
			for (int i = 0; i < order; i++) {
				r.writeLong(children[i]);
			}
		}
		
		// return array index number 0 - size
		public int getKeyLoc(int k) {
			for (int i = 0; i < count; i++) {
				if (keys[i] > k)
					return i;
			}
			return count;
		}

		
		public void setNewRoot(int k, long leftChild, long rightChild){
			keys[0] = k;
			children[0] = leftChild;
			children[1] = rightChild;
			count = 1;
		}

		public boolean isFull() {
			return count == max;
		}

		public void insert(int key, long child) throws IOException {
			if(!isFull()){
				int index = getKeyLoc(key);
				for(int i = keys.length - 1; i > index; i--){
					keys[i] = keys[i-1];
				}
				keys[index] = key;
				count++;
				if(children[0] != 0){
					for(int i = children.length - 1; i > index + 1; i--){
						children[i] = children[i-1];
					}
					children[index+1] = child;
				}
				writeNode();
				split = false;
			}
			else{
				split = true;
				split(key,child);
			}
		}

		// find a specific number
		public boolean find(int k) {
			for (int i = 0; i < count; i++) {
				if (keys[i] == k)
					return true;
				if (keys[i] > k)
					return false;
			}
			return false;
		}

		public void split(int k, long c) throws IOException {
			if(children[0] == 0){
				int[] newKeys = getNewKeys(k);
				count = min;
				keys = Arrays.copyOfRange(newKeys, 0, min);
				keys = Arrays.copyOf(keys, max);				
				newKeys = Arrays.copyOfRange(newKeys, min, order);
				newKeys = Arrays.copyOf(newKeys, max);
				long[] newChildren = new long[order];
				
				long newLoc = r.length();
				newChildren[order-1] = children[order-1];
				children[order-1] = newLoc;
				
				BTreeNode newNode = new BTreeNode(newKeys,newChildren,order - count,newLoc);
				newNode.writeNode();
				writeNode();
				splitKey = newKeys[0];
				splitChild = newLoc;
			}
			else{
				int index = getKeyLoc(k);
				int[] newKeys = getNewKeys(k);
				splitKey = newKeys[min];
				keys = Arrays.copyOfRange(newKeys, 0, min);
				keys = Arrays.copyOf(keys, max);
				newKeys = Arrays.copyOfRange(newKeys, min+1, order);
				newKeys = Arrays.copyOf(newKeys, max);
				count = min;
				
				int newCount = order - min - 1;
				long[] newChildren = getNewChildren(k,c,index);
				children = Arrays.copyOfRange(newChildren, 0, min+1);
				children = Arrays.copyOf(children, order);
				newChildren = Arrays.copyOfRange(newChildren, min+1, order+1);
				newChildren = Arrays.copyOf(newChildren, order);
				
				long newLoc = r.length();
				splitChild = newLoc;
				writeNode();
				BTreeNode newNode = new BTreeNode(newKeys,newChildren,newCount,newLoc);
				newNode.writeNode();
			}
		}
		private long[] getNewChildren(int k,long c, int index){
			long[] newChildren = new long[order+1];
			index++;
			int count = 0;
			for(int i = 0; i < index; i++){
				newChildren[count] = children[i];
				count++;
			}
			newChildren[count] = c;
			count++;
			for(int i = index; i < children.length; i++){
				newChildren[count] = children[i];
				count++;
			}
			return newChildren;
		}
		
		private int[] getNewKeys(int k){
			int[] newKeys = new int[order];
			int index = getKeyLoc(k);
			int count = 0;
			for(int i = 0; i < index; i++){
				newKeys[count] = keys[i];
				count++;
			}
			newKeys[count] = k;
			count++;
			for(int i = index; i < keys.length; i++){
				newKeys[count] = keys[i];
				count++;
			}
			return newKeys;
		}
		
		public boolean hasNextNode(){
			return children[order-1] != 0;
		}

	}

	long head;
	RandomAccessFile r;
	String name;
	int order, max, min;
	BTreeNode root;
	Stack<BTreeNode> stack = new Stack<BTreeNode>();
	boolean split = false;
	int splitKey;
	long splitChild;
	

	public BTree(String n, int ord) throws IOException {
		// create a new B+Tree with order ord
		// n is the name of the file used to store the tree
		// if a file with name n already exists it should be deleted

		File f = new File(n);
		if (f.exists())
			f.delete();
		name = n;
		order = ord;
		max = ord - 1;
		min = ((int)Math.ceil(order/2.0))-1;
		r = new RandomAccessFile(name, "rw");
		r.seek(0);
		r.writeInt(order);
		head = 0;
		r.writeLong(head);
	}

	public BTree(String n) throws IOException, RuntimeException {
		// open an exis2ng B+Tree
		// n is the name of the file that stores the tree
		// if a file with name n does not exists throw a RunTimeExcep2on
		File f = new File(n);
		name = n;
		if (!f.exists())
			throw new RuntimeException();
		r = new RandomAccessFile(n, "rw");
		r.seek(0);
		order = r.readInt();
		head = r.readLong();
		max = order - 1;
		min = ((int)Math.ceil(order/2.0))-1;
		if (head != 0) {
			root = new BTreeNode(head);
			root.readNode();
		}
	}

	public void insert(int k) throws IOException {
		// insert a new key with value k into the tree
		if (head == 0) {
			head = r.length();
			root = new BTreeNode(head);
			root.insert(k,0);
			root.writeNode();
			r.seek(4);
			r.writeLong(head);
			return;
		}
		if (!searth(k)) {
			BTreeNode tmp = stack.pop();
			tmp.insert(k,0);
			while(split && !stack.empty()){
				tmp = stack.pop();
				tmp.insert(splitKey,splitChild);
			}
			
			if(split){
				long loc = r.length();
				tmp = new BTreeNode(loc);
				tmp.setNewRoot(splitKey, root.getLocation(), splitChild);
				root = tmp;
				root.writeNode();
				r.seek(4);
				r.writeLong(loc);
				head = loc;
			}
		}
	}

	public boolean searth(int k) throws IOException {
		// if k is in the tree return true otherwise return false
		while(!stack.isEmpty())
			stack.pop();
		if (head != 0) {
			BTreeNode tmp = root;
			while (tmp.children[0] != 0) {
				stack.push(tmp);
				tmp = new BTreeNode(tmp.children[tmp.getKeyLoc(k)]);
				tmp.readNode();
			}
			stack.push(tmp);
			if (tmp.find(k))
				return true;
		}
		return false;
	}

	public class BTIterator implements Iterator<Integer> {
		// An iterator that iterates through a range of keys in the tree
		// The range is provided in the arguments to the constructor
		boolean check = true;
		int next;
		BTreeNode n;
		int p,l,h;
		public BTIterator(int low, int high) throws IOException {
			// an iterator that can be used to find all the keys, k, in //the
			// tree such that low <= k <= high
			l = low;
			h = high;
			if(low > high || head == 0){
				check = false;
			}
			else{
				if(searth(low)){
					n = stack.pop();
					next = low;
					p = n.getKeyLoc(low) - 1;
					return;
				}
				n = stack.pop();
				int pos = n.getKeyLoc(low);
				if(pos == n.count){
					if(!n.hasNextNode()){
						check = false;
						return;
					}
					n = new BTreeNode(n.children[order-1]);
					n.readNode();
					if(check(n.keys[0])){
						next = n.keys[0];
						p = 0;
					}else{
						check = false;
					}
				}
				else{
					if(check(n.keys[pos])){
						next = n.keys[pos];
						p = pos;
					}
					else{
						check = false;
					}
				}
				
			}
		}

		private boolean check(int num){
			return num >= l && num <= h;
		}
		public boolean hasNext() {
			return check;
		}

		public Integer next(){
			// PRE: hasNext();
			int ret = next;
			p++;
			if( p == n.count){
				if(n.hasNextNode()){
					n = new BTreeNode(n.children[order-1]);
					try {
						n.readNode();
					} catch (IOException e) {
						e.printStackTrace();
					}
					next = n.keys[0];
					p = 0;
					check = check(next);
					
				}else{
					check = false;
				}
			}
			else{
				check = check(n.keys[p]);
				next = n.keys[p];
			}
			return ret;
		}

		public void remove() {
			// op2onal method not implemented
		}
	}

	public Iterator<Integer> iterator(int low, int high) throws IOException {
		// return a new iterator object
		return new BTIterator(low, high);
	}

	public void close() throws IOException {
		r.close();
	}

}
