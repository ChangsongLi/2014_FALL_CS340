public class BinarySearchTree<T extends Comparable<? super T>> {

	class Node {
		Node left;
		T data;
		Node right;
		int count;

		Node(Node l, T d, Node r) {
			left = l;
			right = r;
			data = d;
			count = 1;
		}
	}

	Node root;

	public BinarySearchTree() {
		root = null;
	}

	public void insert(T d) {
		root = insert(root, d);
	}

	public Node insert(Node r, T d) {
		if (r == null)
			return new Node(null, d, null);
		if (r.data.compareTo(d) > 0)
			r.left = insert(r.left, d);
		else if (r.data.compareTo(d) < 0)
			r.right = insert(r.right, d);
		else
			r.count++;
		return r;
	}

	public int getCount(T d) {
		return getCount(root, d);
	}

	public int getCount(Node r, T d) {
		if (r == null)
			return 0;
		if (r.data.compareTo(d) > 0)
			return getCount(r.left, d);
		else if (r.data.compareTo(d) < 0)
			return getCount(r.right, d);
		return r.count;
	}

	public T getData(T d) {
		return getData(root, d);
	}

	public T getData(Node r, T d) {
		if (r == null)
			return null;
		if (r.data.compareTo(d) > 0)
			getData(r.left, d);
		else if (r.data.compareTo(d) < 0)
			getData(r.right, d);
		return r.data;
	}

	public void delete(T d) {
		root = delete(root, d);
	}

	public Node delete(Node r, T d) {
		if (r == null)
			return null;
		Node ret = r;
		if (r.data.compareTo(d) == 0) {
			r.count--;
			if (r.count == 0) {
				if (r.left == null)
					ret = r.right;
				else if (r.right == null)
					ret = r.left;
				else
					r.left = replace(r.left, r);
			}
		} else if (r.data.compareTo(d) > 0)
			r.left = delete(r.left, d);
		else
			r.right = delete(r.right, d);

		return ret;
	}

	private Node replace(Node r, Node copyToHere) {
		if (r.right == null) {
			copyToHere.data = r.data;
			return r.left;
		} else {
			r.right = replace(r.right, copyToHere);
			return r;
		}
	}
}
