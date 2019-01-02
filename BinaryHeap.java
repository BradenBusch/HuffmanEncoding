
public class BinaryHeap {

	/* 
	 * @author Braden Busch
	 * implements a binary heap where the heap rule is the value in the parent
	 * node is less than or equal to the values in the child nodes
	 * the implementation uses parallel arrays to store the priorities
	 */
	
	int priority[]; //the value used to store the priorities of each char.
	HuffmanTree trees[];
	int size; //the size of the heap.

	public BinaryHeap(int s) { 
		priority = new int[s + 1];
		trees = new HuffmanTree[s + 1];
		size = 0;
	}

	//fill the arrays with initially empty values.
	public void fillArrays(int[] freq) { 
		for (int i = 0; i < freq.length; i++) {
			
			if (freq[i] != 0) {	
				char data = (char)i;
				int frequency = freq[i];
				HuffmanTree hT = new HuffmanTree(data);
				insert(frequency, hT);
			}
		}
	}

	
	public void removeMin() {
		// PRE: size != 0
		// removes the priority and tree at the root of the heap
		//use this to remove the smallest thing from the heap to make the nodes.
		if (!empty()) {
			int parent;
			int child;
			int x = priority[size];
			HuffmanTree t = trees[size];
			size--;
			child = 2;
			while (child <= size) {
				parent = child / 2;
				if ((child < size) && priority[child + 1] < priority[child]) {
					child++;
				}
				if (x < priority[child]) {
					break;
				}
				priority[parent] = priority[child];
				trees[parent] = trees[child];
				child = 2 * child;
			}
			priority[child / 2] = x;
			trees[child / 2] = t;
		}
	}

	public int getMinPriority() {
		if (!empty()) {
			return priority[1];
		}
		return -1;	
	}
	
	private boolean empty() {
		
		return size == 0;
	}
	
	public HuffmanTree getMinTree() {
		if (!empty()) {
			return trees[1];
		}
		return new HuffmanTree(); 
		
	}

	public boolean full() {

		return size == priority.length - 1;
	}

	public void insert(int p, HuffmanTree t) {
		if (!full()) {
		
			int parent;
			int child;
			size++;
			child = size;
			parent = child / 2;
			priority[0] = p;
			trees[0] = t;
			while (priority[parent] > p) {
				priority[child] = priority[parent];
				trees[child] = trees[parent];
				child = parent;
				parent = child / 2;
			}
			priority[child] = p;
			trees[child] = t;
		}		
	}

	public int getSize() {
		
		return size;
	}
	
	//Makes the huffman tree by removing 2 trees / priorities from the heap and making 1 huffman tree, then reinserting the 
	//new huffman tree into the heap.
	public HuffmanTree makeHuffmanTree() {
		HuffmanTree t1, t2;
		HuffmanTree tree = new HuffmanTree();
		int f1 = 0;
		int f2 = 0;
		
		while (size > 1) {
			
			t1 = getMinTree();
			f1 = getMinPriority();
			removeMin();
			
			t2 = getMinTree();
			f2 = getMinPriority();
			removeMin();
			
			tree = new HuffmanTree(t1, t2, (char)128); 
			int newFreq = f1 + f2;
			insert(newFreq, tree);
		}
		return tree;
	}
	


}
