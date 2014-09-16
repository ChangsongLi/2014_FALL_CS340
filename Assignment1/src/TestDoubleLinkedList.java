public class TestDoubleLinkedList {
	
	public static void main(String[] args){
		@SuppressWarnings("unused")
		TestDoubleLinkedList t = new TestDoubleLinkedList();
	}
	
	public TestDoubleLinkedList(){
		DoubleLinkedList<Integer> d = new DoubleLinkedList<Integer>();
		d.insertLast(350);
		System.out.println("Size is:" + d.getSize());
		
		insertNums(d);
		// Test get data
		printList(d);
		
		//Test Get position and test the movement of the Current, getData
		System.out.println(d.getPosition());
		d.insertAt(0, -1);
		printList(d);
		System.out.println("Size is:" + d.getSize());
		d.insertAt(4, 6);
		System.out.println(d.getPosition());
		d.insertAt(5, 7);
		d.insertAt(11, 100);
		System.out.println(d.getPosition());
		d.insertAt(9, 50);
		System.out.println(d.getPosition());
		d.insertAt(2, 100);
		System.out.println(d.getPosition());
		
		//Test inserting number to special location  
		d.insertAt(d.getSize(), 250);
		printList(d);
		System.out.println("Size is:" + d.getSize());
		d.insertAt(0, 250);
		printList(d);
		System.out.println("Size is:" + d.getSize());
		
		//test deleteFirst
		while(d.getSize() > 1){
			System.out.println("Delete:"+d.deleteFirst());
		}
		int i = d.getDataAt(0);
		System.out.println(i);
		
	}
	
	private void insertNums(DoubleLinkedList<Integer> d){
		for(int i = 0; i < 10; i++){
			d.insertAt(i, i*2);
		}
	}
	
	private void printList(DoubleLinkedList<Integer> d){
		for(int i = 0; i < d.getSize(); i++){
			System.out.println(i+":  "+d.getDataAt(i));
		}
	}
	
	
}
