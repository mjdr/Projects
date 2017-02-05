package ss;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class PixelUtils {
	public static BufferedImage crop(BufferedImage image) {

		int minX = image.getWidth(), maxX = 0;
		int minY = image.getHeight(), maxY = 0;

		for (int x = 0; x < image.getWidth(); x++)
			for (int y = 0; y < image.getHeight(); y++) {
				if (image.getRGB(x, y) == -1) {
					if (x < minX)
						minX = x;
					if (x > maxX)
						maxX = x;
					if (y < minY)
						minY = y;
					if (y > maxY)
						maxY = y;
				}
			}

		return image.getSubimage(minX, minY, maxX - minX + 1, maxY - minY + 1);
	}

	public static BufferedImage rescale(BufferedImage image, float scale) {
		BufferedImage result = new BufferedImage((int) (image.getWidth() * scale), (int) (image.getHeight() * scale),
				image.getType());
		Graphics2D g = (Graphics2D) result.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawImage(image, 0, 0, result.getWidth(), result.getHeight(), null);
		g.dispose();
		return result;

	}

	public static BufferedImage binary(BufferedImage image, int eps) {
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

		for (int x = 0; x < image.getWidth(); x++)
			for (int y = 0; y < image.getHeight(); y++) {
				RGB imgColor = new RGB(image.getRGB(x, y));
				if (imgColor.module() < eps)
					result.setRGB(x, y, 0);
				else result.setRGB(x, y, -1);
			}
		return result;
	}

	public static BufferedImage minus(BufferedImage image, RGB color) {

		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

		for (int x = 0; x < image.getWidth(); x++)
			for (int y = 0; y < image.getHeight(); y++) {
				RGB imgColor = new RGB(image.getRGB(x, y));
				result.setRGB(x, y, imgColor.minus(color).abs().getRGB());
			}
		return result;
	}

	public static RGB getAvgColor(BufferedImage image) {
		long allR = 0;
		long allG = 0;
		long allB = 0;

		for (int x = 0; x < image.getWidth(); x++)
			for (int y = 0; y < image.getHeight(); y++) {
				RGB color = new RGB(image.getRGB(x, y));
				allR += color.r;
				allG += color.g;
				allB += color.b;
			}

		return new RGB((int) (allR / (image.getWidth() * image.getHeight())),
				(int) (allG / (image.getWidth() * image.getHeight())),
				(int) (allB / (image.getWidth() * image.getHeight())));

	}
}
