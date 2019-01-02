import java.util.*;

/*
 * @author Braden Busch
 * Class used to build the HuffmanTree structure, based on BinaryHeap.
 */
public class HuffmanTree {

	private class Node {
		private Node left;
		private char data;
		private Node right;
		private Node parent;

		private Node(Node L, char d, Node R, Node P) {
			left = L;
			data = d;
			right = R;
			parent = P;
		}
	}

	private Node root;
	private Node current; // this value is changed by the move methods
	public String[] paths = new String[128]; //Only using ascii characters 0 - 127
	
	//Makes an empty HuffmanTree
	public HuffmanTree() {
		root = null;
		current = null;
	}

	//Makes a single node HuffmanTree
	public HuffmanTree(char d) {
		root = new Node(null, d, null, null);
		current = new Node(null, d, null, null);
	}

	/*
	 * Uses a stack to build the HuffmanTree.
	 * Assumes that t is in proper post order string format (this is ensured in a later method).
	 * @param t the HuffmanTree representation
	 * @param nonLeaf the char value used in individual Nodes to represent them being non-leaves.
	 */
	public HuffmanTree(String t, char nonLeaf) {
	
		Stack<HuffmanTree> stack = new Stack<>();
		HuffmanTree t1, t2, tree;
		
		for (int i = 0; i < t.length(); i++) {
			if (t.charAt(i) == nonLeaf) {
				t1 = stack.pop();
				t2 = stack.pop();
				tree = new HuffmanTree(t2, t1, nonLeaf); 
				stack.push(tree);
			}
			else {
				char data = t.charAt(i);
				tree = new HuffmanTree(data);
				stack.push(tree);
			}
		}
		
		root = stack.pop().root;
	}

	/*
	 * Makes a new huffman tree.
	 * @param b1 the left subtree
	 * @param b2 the right subtree
	 * @param d the new data
	 */
	public HuffmanTree(HuffmanTree b1, HuffmanTree b2, char d) {
		// makes a new tree where b1 is the left subtree and b2 is the right subtree
		// d is the data in the root
		
		root = new Node(b1.root, d, b2.root, null);
		b1.root.parent = root;
		b2.root.parent = root;

	}

	/*
	 * The following methods are all used in HuffmanDecode to traverse the HuffmanTree. 
	 */
	public void moveToRoot() {
		// change current to reference the left child of the current node
		current = root;
	}

	public void moveToLeft() {
		// PRE: the current node is not a leaf
		// change current to reference the left child of the current node
		if (!atLeaf()) {
			current = current.left;
		}
	}

	public void moveToRight() {
		// PRE: the current node is not a leaf
		// change current to reference the right child of the current node
		if (!atLeaf()) {
			current = current.right;
		}
	}

	public void moveToParent() {
		// PRE: the current node is not the root
		// change current to reference the parent of the current node
		current = current.parent;
	}

	public boolean atRoot() {
		// returns true if the current node is the root otherwise returns false
		if (current.parent == null) {//may have to change
			return true;
		}
		else return false;
	}

	public boolean atLeaf() {
		if (current.left == null && current.right == null) {
			return true;
		}
		else return false;
	}

	public char current() {
		// returns the data value in the node referenced by current
		return current.data;
	}
	
	//Method to write the huffman tree in a post order pattern.
	public String toString() {
		
		return toString(root);
	}
	
	private String toString(Node node) {
		String end = "";
		if (node == null) {
			return "";
		}
		end += toString(node.left) + toString(node.right) + node.data;
		return end;
	}
	
	//Method to build the paths of 0's (left) and 1's (right) to later use in the decoding process. 
	public void recordPaths() {
		recordPaths(root, "");
	}
	
	private void recordPaths(Node n, String output) {
		if (n.left == null && n.right == null) {
			char symbol = n.data;
			paths[(int)symbol] = output;
			return;
		}
		recordPaths(n.left, output + "0");
		recordPaths(n.right, output + "1");
	}
}
