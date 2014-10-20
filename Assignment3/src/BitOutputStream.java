import java.io.*;

public class BitOutputStream {
	DataOutputStream out;

	public BitOutputStream(String filename) throws IOException {
		out = new DataOutputStream(new FileOutputStream(filename));
		// more initialization is needed
	}

	public void writeInt(int i) throws IOException {
	}

	public void writeString(String s) throws IOException {
	}

	public void writeBits(String s) throws IOException {
	}

	public void close() throws IOException {
		// add code to write the last byte
		out.close();
	}
}
