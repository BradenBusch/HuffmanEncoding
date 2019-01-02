import java.io.*;
import java.util.*;
/*
 * author Braden Busch
 * Class used to encode text into bits, according to the huffman encoding algorithm.
 */
public class HuffmanEncode {

	public int[] freq = new int[128]; //the array of frequencies, this is used in BinaryHeap. 
	
	/*
	 * Implements the Huffman encoding algorithm.
	 * @param in the file being read in
	 * @param the binary file being written to
	 */
	public HuffmanEncode(String in, String out) {
		//Implements the Huffman encoding algorithm	
	
		//count total amount of characters in the file.
		int letterCount = readFile(in); 
		
		//BinaryHeap of size >=128.
		BinaryHeap bHeap = new BinaryHeap(127); 
		
		//Uses insert to fill the arrays with freq data.
		bHeap.fillArrays(freq); 
		
		//Makes the huffman tree.
		HuffmanTree encoder = bHeap.makeHuffmanTree(); 
		encoder.recordPaths(); 
		
		//The codes for each character.
		String[] codes = encoder.paths;
		
		//Returns the treeRepresentation of HuffmanTree to write to the file.
		String treeRepresentation = encoder.toString();
		
		//Writes the binary file.
		writeFile(in, treeRepresentation, letterCount, out, codes); 
		
	}
	public static void main(String[] args) {
		//args[0] is the name of the file whose contents should be compressed
		//args[1] is the name of the output file that will hold the compressed
		//content of the input file
		 new HuffmanEncode(args[0], args[1]); 
		 //do not add anything here
	}
	
	//Uses a BufferedReader to read in a file char by char, and incrementing freq[index] for each char. 
	private int readFile(String file) {
		int numChars = 0;	
		int currentChar;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			currentChar = br.read();
			while (currentChar != -1) { //reads until the end of the file
				freq[currentChar]++;
				numChars++;
				currentChar = br.read();
			}
			br.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return numChars;
	}
	
	/*
	 * @param
	 * inputFile the input file
	 * treeRepresentation String representation of a huffman tree
	 * charCount the total number of chars in the input file
	 * outputFile the file the binary data is being written to
	 * paths the combination of 0's and 1's that lead to each char in the tree
	 */
	private void writeFile(String inputFile, String treeRepresentation, int charCount, String outputFile, String[] paths) {
		
		HuffmanOutputStream h = new HuffmanOutputStream(outputFile, treeRepresentation, charCount);
		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			int val = br.read();
			while (val != -1) {
				for (int i = 0; i < paths[val].length(); i++) {
					
					h.writeBit(paths[val].charAt(i));
				}
				val = br.read();
			}
			h.close();
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
