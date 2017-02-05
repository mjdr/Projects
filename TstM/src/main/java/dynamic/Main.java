package dynamic;

public class Main {

	public Main() {

		SomeInterface impl = Magic.getImplementation(SomeInterface.class);

		// impl.printHello();

	}

	public static void main(String[] args) {
		new Main();
	}

}
