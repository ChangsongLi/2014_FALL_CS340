import java.io.*;

public class BitOutputStream {
    DataOutputStream out;
    StringBuilder bits;
    
    public BitOutputStream(String filename) throws IOException {
        out = new DataOutputStream(new FileOutputStream(filename));
        bits = new StringBuilder("");
        // more initialization is needed
    }
    
    public void writeInt(int i) throws IOException {
        out.writeInt(i);
    }
    
    public void writeString(String s) throws IOException {
        out.writeUTF(s);
    }
    
    public void writeBits(String s) throws IOException {
        bits.append(s);
        while(bits.length() >= 8){
            String bit8 = bits.substring(0,8);
            int num = getByte(bit8);
            out.writeByte(num);
            bits = new StringBuilder(bits.substring(8, bits.length()));
        }
    }
    
    private int getByte( String s ){
        int num = 0;
        for( int i = 0; i < 8; i++ ){
            if(s.charAt(i) == '0')
                num = num * 2;
            else
                num = num * 2 + 1;
        }
        return num;
    }
    
    public void close() throws IOException {
        // add code to write the last byte
        for(int i = 0; i < 8 - bits.length(); i++){
            bits.append("0");
        }
        String last = bits.substring(0);
        out.writeByte(getByte(last));
        out.close();
    }
}