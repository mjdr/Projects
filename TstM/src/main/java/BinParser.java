import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class BinParser {
	public static void main(String[] args) {
		try {
			new BinParser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BinParser() throws Exception {

		FileInputStream in = new FileInputStream("data.bin");

		List<String> data = readData(in);
		System.out.println(data.size());

		in.close();

		// System.out.println(data);

	}

	private List<String> readData(InputStream inputStream) throws IOException {
		int counter = 1;
		List<String> result = new LinkedList<>();
		byte[] buffer = new byte[inputStream.available()];
		inputStream.read(buffer);

		int pointer = 0;
		while (pointer < buffer.length - 1) {
			int startPos = pointer;
			while (true) {
				for (; pointer < buffer.length && buffer[pointer] != (int) ' '; pointer++);
				int next = getNext(buffer, pointer, (byte) ' ');
				if (next >= 1)
					break;
				else pointer += next;
			}
			// byte[] tmp = new byte[pointer - startPos];
			// System.arraycopy(buffer, startPos, tmp, 0, tmp.length);
			// result.add(new String(tmp));
			if (counter % 10000 == 0)
				System.out.println(counter);
			counter++;
			pointer += getNext(buffer, pointer, (byte) ' ');
		}

		return result;
	}

	private int getNext(byte[] data, int pos, byte val) {
		for (int i = pos; i < data.length; i++)
			if (data[i] != val)
				return i - pos;
		return data.length - pos;
	}

}
