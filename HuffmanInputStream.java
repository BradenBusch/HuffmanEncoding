import java.io.*;
/*
 * @author Braden Busch
 * Makes a DataInputStream object to read each bit from a binary file.
 */
public class HuffmanInputStream {

	private String tree;
	private int totalChars;
	private DataInputStream d;
	private int bitCount = 8;
	public int[] bits = new int[8];
	private int theByte = 0;	
	
	public HuffmanInputStream(String filename) {
		
		try {
			d = new DataInputStream(new FileInputStream(filename));
			tree = d.readUTF();
			totalChars = d.readInt();
			
		}
		catch (IOException e) {
			System.err.print("Error reading file.");
		}
		
	}

	//returns the next bit in the file, which will either be a 0 or 1.
	public int readBit() {
		try {
			if (bitCount == 8) {
				bitCount = 0;
				if (d.available() != 0) theByte = d.readUnsignedByte();

				for(int i = 7; i >= 0; i--) {
					bits[i] = theByte % 2;
					theByte = theByte / 2;
				}
			}
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		bitCount++;
		return bits[bitCount-1];
	}
	
	public String getTree() {
		return tree;
	}
	
	public int getTotalChars() {
		// return the character count read from the file
		return totalChars;
	}

}
