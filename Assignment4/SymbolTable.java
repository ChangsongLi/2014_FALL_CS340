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
		//code to test SymbolTable
	}
	
}
