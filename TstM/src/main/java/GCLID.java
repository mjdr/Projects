
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GCLID {
	public static void main(String[] args) {
		new GCLID();
	}

	public GCLID() {

		try {
			long time = ((int) (System.currentTimeMillis() / 1000));
			System.out.println(time);
			System.out.println(new Date(time * 1000L));
			String gclid = encode(time);
			System.out.println(gclid);
			long decTime = decode(gclid);
			System.out.println(new Date(decTime * 1000L));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String encode(long time) throws UnsupportedEncodingException {
		BigInteger[] ret = new BigInteger[] { new BigInteger(time + "0" + "24100"), new BigInteger("182494220"),
				new BigInteger("2919263803") };

		List<Byte> strCode = new ArrayList<Byte>();

		for (int j = 0; j < ret.length; j++) {
			List<Integer> val = new ArrayList<Integer>();
			if (j == 0) {
				do {
					int tmp = ret[j].mod(new BigInteger("128")).intValue() + (val.size() > 6 ? 0 : 128);
					val.add(tmp);
					ret[j] = ret[j].divide(new BigInteger("128"));

				} while (ret[j].compareTo(BigInteger.ZERO) != 0);

			} else {
				do {
					int tmp2 = ret[j].mod(new BigInteger("256")).intValue();
					val.add(tmp2);
					ret[j] = ret[j].divide(new BigInteger("256"));

				} while (ret[j].compareTo(BigInteger.ZERO) != 0);
			}

			strCode.add((byte) (j == 0 ? 8 : (j == 1 ? 21 : 29)));

			for (int i = 0; i < val.size(); i++) {
				strCode.add((byte) (int) val.get(i));
			}

		}
		String strCodeS = new String(org.apache.commons.codec.binary.Base64.encodeBase64(getBytes(strCode)));

		return strCodeS.replace('+', '_').replace('/', '-');
	}

	public long decode(String gclid) throws UnsupportedEncodingException {
		byte[] gclidD = org.apache.commons.codec.binary.Base64
				.decodeBase64((gclid.replace('_', '+').replace('-', '/')).getBytes("ASCII"));

		if (Byte.toUnsignedInt(gclidD[0]) != 8)
			throw new IllegalArgumentException("Invalid gclid code");
		int[] timeDigits = new int[gclidD.length - 1];
		int counter = 0;
		for (; counter < gclidD.length - 1; counter++) {
			int b = Byte.toUnsignedInt(gclidD[counter + 1]);

			if (b == 21)
				break;
			if (counter <= 6)
				timeDigits[counter] = b - 128;
			else
				timeDigits[counter] = b;
		}
		BigInteger result = new BigInteger("0");
		for (int i = 0; i < counter; i++) {
			result = result.add(new BigInteger(timeDigits[i] + "").multiply(new BigInteger("128").pow(i)));

		}

		String resultS = result.toString(10);
		if (!resultS.endsWith("024100"))
			throw new IllegalArgumentException("Invalid gclid code");

		return Long.parseLong(resultS.substring(0, resultS.length() - 6));
	}

	private byte[] getBytes(List<Byte> list) {
		byte[] result = new byte[list.size()];
		for (int i = 0; i < result.length; i++)
			result[i] = list.get(i);
		return result;
	}

}
