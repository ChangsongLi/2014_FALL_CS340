
public class MyQueue<T> implements FIFOQueue<T> {

	DoubleLinkedList<T> q;

	public MyQueue() {
		q = new DoubleLinkedList<>();
	}

	public boolean empty() {
		return q.getSize() == 0;
	}

	public void enqueue(T d) {
		q.insertLast(d);
	}

	public T serve() {
		return q.deleteFirst();
	}
}