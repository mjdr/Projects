package ss;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class SeosprintCapcha {

	public SeosprintCapcha() throws IOException {

		BufferedImage raw = ImageIO.read(new File("SS-capha/3.png"));
		// raw = rescale(raw, 4f);
		System.out.println("AVG");
		RGB avgColor = PixelUtils.getAvgColor(raw);
		System.out.println("Minus");
		BufferedImage mRaw = PixelUtils.minus(raw, avgColor);
		System.out.println("Binary");
		BufferedImage binaryImage = PixelUtils.binary(mRaw, 62);
		System.out.println("Crop");
		BufferedImage cropedBinaryImage = PixelUtils.crop(binaryImage);

		List<Area> areas = AreaUtils.split(cropedBinaryImage);

		AreaUtils.getSine(areas.get(1));

		for (int i = 0; i < areas.size(); i++)
			ImageIO.write(areas.get(i).print(), "PNG", new File("output" + (i + 1) + ".png"));

		// ImageIO.write(cropedBinaryImage, "PNG", new File("output.png"));

	}

	public static void main(String[] args) {
		try {
			new SeosprintCapcha();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
