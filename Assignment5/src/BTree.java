import java.io.*;
import java.util.*;

public class BTree {

	public BTree(String n, int ord) {
		// create a new B+Tree with order ord
		// n is the name of the file used to store the tree
		// if a file with name n already exists it should be deleted
	}

	public BTree(String n) {
		// open an exis2ng B+Tree
		// n is the name of the file that stores the tree
		// if a file with name n does not exists throw a RunTimeExcep2on
	}

	public void insert(int k) {
		// insert a new key with value k into the tree
	}

	public boolean searth(int k) {
		// if k is in the tree return true otherwise return false
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
