import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;

public class Jssc {
	public static void main(String[] args) {

		try {
			new Jssc();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Jssc() throws Exception {

		System.out.println();

	}

	private void pdf() throws Exception {
		Document pdfDoc = new Document();

		pdfDoc.open();

		pdfDoc.setPageSize(new Rectangle(800, 600));
		pdfDoc.newPage();

		BufferedImage buffer = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

		Image image = convert(buffer);

		image.setAbsolutePosition(0, 0);

		pdfDoc.add(image);

		pdfDoc.close();

		FileOutputStream fos = new FileOutputStream("file.pdf");

		fos.close();
	}

	private Image convert(BufferedImage buffer) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Image iTextImage;
		ImageIO.write(buffer, "png", baos);
		iTextImage = Image.getInstance(baos.toByteArray());
		return iTextImage;

	}

}
