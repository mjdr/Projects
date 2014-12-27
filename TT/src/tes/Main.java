package tes;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception 
	{
		 NodeMethod cheme = new NodeMethod(4);
		 

		 cheme.addG(0, 2, 0.5F);
		 cheme.addG(0, 3, 1F);
		 cheme.addG(0, 1, 0.5F);
		 cheme.addG(3, 2, 0.5F);
		 cheme.addG(1, 4, 1F);
		 
		 cheme.addI(2, 1, 1);
		 

		 System.out.println(cheme.getY());
		 System.out.println(cheme.getI());
		 cheme.addU(3, 4, 4);

		 System.out.println(cheme.getY());
		 System.out.println(cheme.getI());
		 
		 System.out.println(cheme.getV());
	}

}
