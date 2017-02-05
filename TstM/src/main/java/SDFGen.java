import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class SDFGen {
	public SDFGen() throws Exception {

		BufferedImage raw = ImageIO.read(new File("plane.jpg"));

		raw = rescale(raw, 0.2f);

		BufferedImage bin = binary(raw, 127 * 3);

		BufferedImage sdfMap = new BufferedImage(bin.getWidth(), bin.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

		sdfBlackPart(bin, sdfMap, 5);
		ImageIO.write(sdfMap, "PNG", new File("SDFOut.png"));

	}

	private void sdfBlackPart(BufferedImage bin, BufferedImage sdfMap, float width) {
		for (int x = 0; x < bin.getWidth(); x++) {
			System.out.println(x);
			for (int y = 0; y < bin.getHeight(); y++) {
				if ((bin.getRGB(x, y) & 0xff) != 0)
					continue;
				float minDist = getMinDistance(bin, x, y, -1);

				if (minDist > width) {
					sdfMap.setRGB(x, y, -1);
					continue;
				}
				float step = (minDist) / width;
				if (step > 0)
					sdfMap.setRGB(x, y, (int) (255 * (0.5f + step)));
			}
		}
	}

	private void sdfWhitePart(BufferedImage bin, BufferedImage sdfMap, float width) {
		for (int x = 0; x < bin.getWidth(); x++) {
			// System.out.println(x);
			for (int y = 0; y < bin.getHeight(); y++) {
				if ((bin.getRGB(x, y) & 0xff) != -1)
					continue;
				float minDist = getMinDistance(bin, x, y, 0);

				if (minDist > width)
					sdfMap.setRGB(x, y, 0xFFFFFFFF);

				float step = (minDist) / width;

				if (step > 0)
					sdfMap.setRGB(x, y, (int) (255 * (0.5f + step)));
			}
		}
	}

	private float getMinDistance(BufferedImage bin, int sx, int sy, int val) {
		float minDist = Float.MAX_VALUE;

		for (int x = 0; x < bin.getWidth(); x++)
			for (int y = 0; y < bin.getHeight(); y++) {
				if (bin.getRGB(x, y) != val)
					continue;
				float dist = (float) Math.sqrt((x - sx) * (x - sx) + (y - sy) * (y - sy));
				if (dist < minDist)
					minDist = dist;
			}

		return minDist;
	}

	private BufferedImage rescale(BufferedImage image, float scale) {
		BufferedImage result = new BufferedImage((int) (image.getWidth() * scale), (int) (image.getHeight() * scale),
				image.getType());
		Graphics2D g = (Graphics2D) result.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawImage(image, 0, 0, result.getWidth(), result.getHeight(), null);
		g.dispose();
		return result;

	}

	private BufferedImage binary(BufferedImage image, int eps) {
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

	public static void main(String[] args) throws Exception {
		new SDFGen();
	}
}
