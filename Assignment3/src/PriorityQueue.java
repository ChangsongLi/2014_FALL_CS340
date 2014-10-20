public class PriorityQueue {
	// A priority queue in which the keys are Integers and the associated data
	// is any object
	// the queue must be implemented as a binary heap
	class Data {
		int key;
		Object obj;

		Data(int k, Object o) {
			key = k;
			obj = o;
		}

	}

	private int curSize;
	private Data[] data;

	public PriorityQueue(int qsize) {
		data = new Data[qsize + 1];
		curSize = 0;
	}

	public Object deleteMin() {
		// PRE: !empty()
		Data d = data[1];
		curSize--;
		if (!empty()) {
			int index = 1;
			data[1] = data[curSize++];
			data[curSize++] = null;
			while (index * 2 + 1 <= curSize) {
				if (data[index].key > data[2 * index].key
						&& data[index].key > data[2 * index + 1].key) {
					if (data[2 * index + 1].key > data[2 * index].key) {
						swap(index, 2 * index);
						index = 2 * index;
					} else {
						swap(index, 2 * index + 1);
						index = 2 * index + 1;
					}
				}
			}
			if (index * 2 == curSize)
				if (data[index].key > data[curSize].key)
					swap(index, curSize);
		} else {
			data[1] = null;
		}
		return d.obj;
	}

	public void swap(int i1, int i2) {
		Data tmp = data[i1];
		data[i1] = data[i2];
		data[i2] = tmp;
	}

	public Integer getMinKey() {
		// PRE: !empty()
		return data[1].key;
	}

	public boolean empty() {
		return curSize == 0;
	}

	public Object getMinData() {
		// PRE: !empty()
		return data[1].obj;
	}

	public boolean full() {
		return curSize == data.length - 1;
	}

	public void insert(Integer k, Object d) {
		// PRE !full()
		Data tmp = new Data(k, d);
		if (empty()) {
			data[1] = tmp;
			curSize++;
		} else {
			int index = ++curSize;

			while (index / 2 != 0 && data[index / 2].key > k) {
				data[index] = data[index / 2];
				index = index / 2;
			}
			data[index] = tmp;
		}

	}

	public int getSize() {
		return data.length - 1;
	}

}
