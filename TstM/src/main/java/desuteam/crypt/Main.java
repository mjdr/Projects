package desuteam.crypt;

import java.io.File;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		
		
		//new Coder().encrypt(new File("G://pic1_Contrast.png"), new File("G://pic.crpt"), 17);
		new Coder().decrypt(new File("G://pic.crpt"), new File("G://pic1_Contrast_E_D.png"), 17);
		
	}
}
