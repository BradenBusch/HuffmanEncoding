import java.io.*;
/*
 * @author Braden Busch
 * Class used to write individual bits to a binary file.
 */
public class HuffmanOutputStream {

	DataOutputStream d;
	private int bitCount = 0; //number of bits 
	private int bits = 0; //the decimal value of the byte that gets written to the file.
	
	/*
	 * Writes the HuffmanTree representation and total characters to the given file.
	 */
	public HuffmanOutputStream(String filename, String tree, int totalChars) {
		
		try {
			d = new DataOutputStream(new FileOutputStream(filename));
			d.writeUTF(tree);
			d.writeInt(totalChars);
		}
		catch (IOException e) {
			System.err.print("Error making output stream.");
		}
	}
	
	//Generates the value of each byte.
	public void writeBit(char bit) {
		if (bit == '1') {
			bits += Math.pow(2, 7 - bitCount);
		}
		if (bitCount == 7) { 
			writeBytes(bits);
			return;
		}
		bitCount++;	
	}

	//Writes any extra bits.
	public void close() { 
		try {
			if (bitCount < 8) { 
				writeBytes(bits);
				d.close();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	
	}

	//Helper method to simplify ugly try / catch.
	private void writeBytes(int b) {
		try {
			d.writeByte(b);
			bitCount = 0;
			bits = 0;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
