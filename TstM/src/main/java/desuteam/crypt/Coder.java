package desuteam.crypt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Coder {
	
	public void encrypt(File src, File dst, long key) throws IOException{
		
		DataOutputStream out = new DataOutputStream(new CryptOutputStream(new FileOutputStream(dst), key));
		InputStream in = new FileInputStream(src);
		
		int b;
		while((b = in.read())!= -1)
			out.writeByte(b);
		
		in.close();
		out.close();
		
	}
	
	public void decrypt(File src, File dst, long key) throws IOException{
		
		DataInputStream in = new DataInputStream(new CryptInputStream(new FileInputStream(src), key));
		FileOutputStream out = new FileOutputStream(dst);
		
		int b;
		while((b = in.read())!= -1)
			out.write(b);
		
		out.close();
		in.close();
		
	}
	
}
