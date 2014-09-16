
public interface FIFOQueue<T> {

	void enqueue(T t);

	T serve();

	boolean empty();

}
