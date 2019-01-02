import java.io.*;
import java.util.*;
/*
 * @author Braden Busch
 * Class used to decode bits into text, according to the Huffman Decoding algorithm.
 */
public class HuffmanDecode {

	/*
	 * Implements the Huffman Decoding Algorithm.
	 * @param
	 * in the binary file being read in.
	 * out the text file being written to.
	 */
	public HuffmanDecode(String in, String out) {
		
		HuffmanInputStream h = new HuffmanInputStream(in); 
		writeFile(h, h.getTree(), h.getTotalChars(), out);
		
	}

	public static void main(String[] args) {
		// args[0] is the name of a input file (a file created by Huffman Encode)
		// args[1] is the name of the output file for the uncompressed file
		new HuffmanDecode(args[0], args[1]);
		// do not add anything here
	}

	/*
	 * Used to write the text file based off of the binary file. This is done by traversing the tree, left and right,
	 * until a leaf is hit. At this point, the current char will be added to the text file.
	 * @param
	 * his a HuffmanInputStream used to read in the bits
	 * treeRepresentation the post order string representation of a tree
	 * totalChars the total number of chars in the file
	 * outputFile the file being written to
	 */
	private void writeFile(HuffmanInputStream his, String treeRepresentation, int totalChars, String outputFile) {
		HuffmanTree theTree = new HuffmanTree(treeRepresentation, (char)128);
		theTree.moveToRoot();
		
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(outputFile));
			int numChars = 0;
			int t;
			
			while (numChars < totalChars) {
				t = his.readBit();
				if (theTree.atLeaf()) {
					pw.print(theTree.current());	
					theTree.moveToRoot();
					numChars++;
				}
				if  (t == 0) {
					theTree.moveToLeft();
				}
				else if (t == 1) {
					theTree.moveToRight();
				}
			}
			pw.flush();
			pw.close();
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
