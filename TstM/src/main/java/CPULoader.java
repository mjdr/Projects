
public class CPULoader implements Runnable {
	public static void main(String[] args) {
		new CPULoader();
	}

	public CPULoader() {

		for (int i = 0; i < 10; i++)
			new Thread(this).start();

	}

	@Override
	public void run() {

		int x = 2;
		int y = 1;

		while (true) {
			int tx = x * y;
			int ty = x - y;
			x = tx;
			y = ty;
		}
	}
}
