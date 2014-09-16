
public class DoubleLinkedList<T> {

	class Node {
		Node prev;
		T data;
		Node next;

		Node(Node p, T d, Node n) {
			prev = p;
			data = d;
			next = n;
		}
	}

	protected Node head;
	protected Node current;
	protected Node last;
	protected int position;
	protected int size;

	public DoubleLinkedList() {
		head = new Node(null, null, null);
		head.next = new Node(head, null, null);
		size = 0;
		position = 0;
		current = head.next;
		last = current;
		
	}

	public void insertLast(T d) {
		current = last.prev;
		current.next = new Node(current, d, last);
		last.prev = current.next;
		current = current.next;
		size++;
		position = size - 1;	
		
	}

	public void insertAt(int p, T d) {
		if( p == size ){
			insertLast(d);
		}
		else{
			int check;
			int time;
			if( position < p ){
				if( (p - position) > (size - p) ){
					check = 2;
					current = last;
					time = size - p;
				}
				else{
					check = 3;
					time = p - position;
				}
			}
			else{
				if( (position - p) > (p + 1) ){
					check = 1;
					current = head;
					time = p + 1;
				}
				else{
					check = 4;
					time = position - p;
				}
			}
			
			if( check % 2 != 0 ){				
				for( int i = 0; i < time; i++ ){
					current = current.next;
				}
			}
			else{
				for( int i = 0; i < time; i++ ){
					current = current.prev;
				}
			}
			
			current.prev.next = new Node(current.prev, d, current);
			current.prev = current.prev.next;
			current = current.prev;
			position = p;
			size++;
			
		}
	}

	public T deleteFirst() {
		T data;
		data = head.next.data;
		head.next = head.next.next;
		head.next.prev = head;
		size--;
		return data;
	}

	public int getSize() {
		return size;
	}

	public int getPosition() {
		return position;
	}

	public T getDataAt(int p) {
		int check;
		int time;
		if( position < p ){
			if( (p - position) > (size - p) ){
				check = 2;
				current = last;
				time = size - p;
			}
			else{
				check = 3;
				time = p - position;
			}
		}
		else{
			if( (position - p) > (p + 1) ){
				check = 1;
				current = head;
				time = p + 1;
			}
			else{
				check = 4;
				time = position - p;
			}
		}
		
		if( check % 2 != 0 ){				
			for( int i = 0; i < time; i++ ){
				current = current.next;
			}
		}
		else{
			for( int i = 0; i < time; i++ ){
				current = current.prev;
			}
		}	
		
		position = p;
		return current.data;
	}
}