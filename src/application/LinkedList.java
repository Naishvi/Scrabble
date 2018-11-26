package application;

public class LinkedList<T> {
	private Node firstNode;
	private boolean initialized = false;

	public void add(T newEntry) {
		Node newNode = new Node(newEntry);
		Node curNode, nextNode;
		if (firstNode == null)
			firstNode = newNode;
		else {
			curNode = firstNode;
			for (nextNode = curNode.getNext(); nextNode != null;) {
				curNode = nextNode;
				nextNode = curNode.getNext();
			}
			// loop finished when nextNode == null, currNode at the end
			curNode.setNext(newNode);
		}
	}

	public void add(int newPosition, T newEntry) {
		if (newPosition < 0 || newPosition > getLength())
			throw new IndexOutOfBoundsException();

		Node newNode = new Node(newEntry);

		if (newPosition == 0) { // including empty list
			newNode.setNext(firstNode);
			firstNode = newNode;
			return;
		}
		int pos = 1;
		Node before = firstNode;
		for (Node after = firstNode.getNext(); after != null;) {
			if (pos == newPosition) { // insert here
				before.setNext(newNode);
				newNode.setNext(after);
				return;
			}
			before = after;
			after = after.getNext();
			pos++;
		}
		before.setNext(newNode); // When loop finished, after == null;
									// newPosition == length; end of chain
	}
	
	public void addMultiple(T anEntry, int numberOfTimes) {
		for (int i = 0; i < numberOfTimes; i++)
			  add(anEntry);
     }

	public T remove(int givenPosition) {
		if (isEmpty())
			throw new NullPointerException();
		if (givenPosition < 0 || givenPosition >= getLength())
			throw new IndexOutOfBoundsException();
		T dataItem = firstNode.getData();

		if (givenPosition == 0)
			firstNode = firstNode.getNext();
		else {
			int idx = 0;
			Node nextNode = firstNode;
			for (Node currNode = firstNode; nextNode != null; currNode = nextNode) {
				idx++;
				nextNode = currNode.getNext();
				if (idx == givenPosition) { // nextNode is to be removed
					assert (nextNode != null);
					dataItem = nextNode.getData();
					currNode.setNext(nextNode.getNext());
					break;
				}
			}
		}
		return dataItem;
	}

	public int getLength() {
		int numEntries = 0;
		Node currNode;
		for (currNode = firstNode; currNode != null; currNode = currNode.getNext())
			numEntries++;
		return numEntries;
	}

	public boolean remove(T anEntry) {
		Node currNode;
		int index = 0;
		for (currNode = firstNode; currNode != null; currNode = currNode.getNext()) {
			if (currNode.getData().equals(anEntry)) {
				remove(index);
				return true;
			}
			index++;
		}
		return false;
	}

	public void clear() {
		firstNode = null;
	}

	public T replace(int givenPosition, T newEntry) {
		Node currNode;
		int index = 0;

		if (givenPosition < 0)
			throw new IndexOutOfBoundsException("Please enter a nonnegative position");

		for (currNode = firstNode; currNode != null; currNode = currNode.getNext()) {
			if (givenPosition == index) {
				T temp = currNode.getData();
				currNode.setData(newEntry);
				return temp;
			}
			index++;
		}

		throw new IndexOutOfBoundsException("Please enter a valid position");
	}

	public T getEntry(int givenPosition) {
		Node currNode;
		int index = 0;

		if (givenPosition < 0)
			throw new IndexOutOfBoundsException("Please enter a nonnegative position");

		for (currNode = firstNode; currNode != null; currNode = currNode.getNext()) {

			if (index == givenPosition) {
				T temp = currNode.getData();
				return temp;
			}
			index++;
		}

		throw new IndexOutOfBoundsException("Please enter a valid position");
	}
	
	public void shuffle() {
		LinkedList<T> newList = new LinkedList<>();
		int originalLength = getLength();
		for (int i = 0; i < originalLength; i++) {
			newList.add(remove(0));

		}
		for (int k = 0; k < originalLength; k++) {
			int randomIndex = (int) (Math.random() * newList.getLength());
			add(newList.remove(randomIndex));
		}

	}

	public boolean contains(T anEntry) {
		Node currNode;
		for (currNode = firstNode; currNode != null; currNode = currNode.getNext()) {
			if (currNode.getData() == anEntry) {
				return true;
			}
		}
		return false;
	}
	
	 public int getFrequencyOf(T newEntry) {

         int counter = 0;
         for (Node currentNode = firstNode; currentNode != null; currentNode = currentNode.getNext()) {
               if (currentNode.getData().equals(newEntry)) {
                      counter++;
               }
         }
         return counter;
  }

	public boolean isEmpty() {
		return (firstNode == null);
	}

	private class Node {
		private T data;
		private Node next;

		Node(T data, Node nextNode) {
			this.data = data;
			next = nextNode;
		}

		Node(T data) {
			this(data, null);

		}

		public void setData(T item) {
			data = item;
		}

		public void setNext(Node n) {
			next = n;
		}

		public T getData() {
			return data;
		}

		public Node getNext() {
			return next;
		}

	}

}
