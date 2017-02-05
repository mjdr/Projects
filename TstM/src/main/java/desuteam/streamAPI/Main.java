package desuteam.streamAPI;

import java.io.File;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		new Main();
	}

	public Main() {

		File[] files = new File("G://").listFiles();

		System.out.println(Arrays.toString(files));

	}

}
