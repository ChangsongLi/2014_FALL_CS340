import java.io.*;

public class BitInputStream {
    DataInputStream in;
    StringBuilder num;
    
    public BitInputStream(String infile) throws IOException {
        in = new DataInputStream(new FileInputStream(infile));
        num = new StringBuilder("");
        // more initialization will be required
    }
    
    public int readInt() throws IOException {
        return  in.readInt();
    }
    
    public String readString() throws IOException {
        return in.readUTF();
    }
    
    public int readBit() throws IOException {
        if( num.length() == 0 )
            num.append(toBinary(in.readUnsignedByte()));
        char bit = num.charAt(num.length()-1);
        num.setLength(num.length()-1);
        
        if(bit == '1')
            return 1;
        else
            return 0;
    }
    
    private String toBinary(int num){
        StringBuilder s = new StringBuilder(8);
        for( int i = 0; i < 8; i++ ){
            if( num == 0){
                s.append("" + 0);
            }
            else{
                int bit = num % 2;
                s.append("" + bit);
                num = num / 2;
            }
        }
        return s.toString();
    }
    
    public void close() throws IOException {
        in.close();
    }
}