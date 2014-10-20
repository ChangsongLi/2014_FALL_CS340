import java.io.*;

public class BitInputStream {
	DataInputStream in;

	public BitInputStream(String infile) throws IOException {
		in = new DataInputStream(new FileInputStream(infile));
		// more initialization will be required
	}

	public int readInt() throws IOException {
	}

	public String readString() throws IOException {
	}

	public int readBit() throws IOException {

	}

	public void close() throws IOException {
		in.close();
	}
}
