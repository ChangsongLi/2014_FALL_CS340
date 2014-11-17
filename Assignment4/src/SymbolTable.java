import	java.util.*;

public class SymbolTable {
	
	class Node{
		String key;
		Object data;
		Node next;
		
		Node(String k, Object d, Node x){
			key = k;
			data = d;
			next = x;
		}
	}
	
	Node table[];
	
	public SymbolTable(int s){
		table = new Node[s];
		for(int i = 0; i < s; i++){
			table[i] = new Node(null, null, null);
		}
	}
	
	private	int	hash(String k){
		long num = 0;
		for(int i = 0; i < k.length(); i++){
			num = num + k.charAt(i);
		}
		return (int)(num % table.length);
	}
	
	public boolean insert(String k){
		int i = hash(k);
		Node tmp = table[i];
		while(tmp.next != null){
			if( tmp.next.key.equals(k))
				return false;
			tmp = tmp.next;
		}
		tmp.next = new Node(k, null, null);
		return true;
	}
	
	public boolean insert(String k,	Object d){
		int i = hash(k);
		Node tmp = table[i];
		while(tmp.next != null){
			if( tmp.next.key.equals(k))
				return false;
			tmp = tmp.next;
		}
		tmp.next = new Node(k, d, null);
		return true;
	}
	
	public boolean find(String k){
		int i = hash(k);
		Node tmp = table[i];
		while(tmp.next != null){
			if( tmp.next.key.equals(k))
				return true;
			tmp = tmp.next;
		}
		return false;
	}
	
	public Object getData(String k){
		int i = hash(k);
		Node tmp = table[i];
		while(tmp.next != null){
			if( tmp.next.key.equals(k))
				return tmp.next.data;
			tmp = tmp.next;
		}
		return null;
	}
	
	public void setValue(String k, Object d){
		int i = hash(k);
		Node tmp = table[i];
		while(tmp.next != null){
			if( tmp.next.key.equals(k)){
				tmp.next.data = d;
			}
			tmp = tmp.next;
		}
	}
	
	public class STIterator implements Iterator<String>{
		Node n;
		public STIterator(){
			for(int i = 0; i < table.length; i++){
				if(table[i].next != null){
					n = table[i].next;
					break;
				}
			}
		}
		@Override
		public boolean hasNext() {
			return n != null;
		}

		@Override
		public String next() {
			String ret = n.key;
			if(n.next != null){
				n = n.next;
			}
			else{
				int num = hash(n.key);
				for(int i = num + 1; i < table.length; i++){
					if(table[i].next != null){
						n = table[i].next;
						return ret;
					}
				}
				n = null;
			}
			return ret;
		}
		
		public boolean delete(String k){
			if(find(k)){
				int num = hash(k);
				Node node = table[num];
				while(!node.next.key.equals(k)){
					node = node.next;
				}
				node.next = node.next.next;
				return true;
			}
			return false;
		}
		
		public Iterator<String> iterator() { 
			//return a new iterator object
			return this;
		}
		
	}
	
	public static void main(String args[]){
		SymbolTable s = new SymbolTable(10);
		
		// test insert methods
		System.out.println("\ntest insert methods");
		System.out.println(s.insert("This"));
		System.out.println(s.toString());
		
		System.out.println(s.insert("is"));
		System.out.println(s.toString());
		
		System.out.println(s.insert("a",100));
		System.out.println(s.toString());
		
		System.out.println(s.insert("debug",199));
		System.out.println(s.toString());
		
		System.out.println(s.insert("debug",1));
		System.out.println(s.toString());
		
		System.out.println(s.insert("test",4000));
		System.out.println(s.toString());
		
		// test find method
		System.out.println("\ntest find method");
		System.out.println(s.find("test"));
		System.out.println(s.find("debug"));
		System.out.println(s.find("a"));
		System.out.println(s.find("A"));
		System.out.println(s.find("is"));
		
		// test getData;
		System.out.println("\ntest getData");
		System.out.println(s.getData("test"));
		System.out.println(s.getData("dasdsdadasd"));
		System.out.println(s.getData("debug"));
		System.out.println(s.getData("a"));
		System.out.println(s.getData("is"));
		
		// test set value
		System.out.println("\ntest set value");
		s.setValue("a", 200);
		System.out.println(s.toString());
		s.setValue("This", 999);
		System.out.println(s.toString());
		
		// test STIterator		
		System.out.println("\ntest STIterator");
		System.out.println("The output should be: is a This test debug");
		STIterator sti = s.new STIterator();
		System.out.print("The output is: ");
		while(sti.hasNext()){
			System.out.print(sti.next()+" ");
		}
		System.out.println();
	}
	
	public String toString(){
		StringBuilder ret = new StringBuilder("");
		for(int i = 0; i < table.length; i++){
			ret.append(i+": ");
			Node node = table[i];
			int index = 0;
			while(node.next != null){
				ret.append(index +  " :key: "+node.next.key+", data: "+node.next.data + ",  ");
				node = node.next;
				index++;
			}
			ret.append("\n");
		}
		return ret.toString();
	}
}
