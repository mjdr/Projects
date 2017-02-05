package video;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.swing.JFrame;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class VideoProcessor {

	private FFmpegFrameGrabber grabber;
	private FFmpegFrameRecorder recorder;
	private CanvasFrame canvasFrame;
	private OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();

	public VideoProcessor() throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {

		canvasFrame = new CanvasFrame("");
		grabber = new FFmpegFrameGrabber("G:\\Gravity Falls (Сыендук)\\Gravity.Falls.S01E01.rus.vo.sienduk.mkv");

		canvasFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		grabber.start();

		System.out.println(grabber.getImageWidth());
		System.out.println(grabber.getImageHeight());
		System.out.println();
		recorder = new FFmpegFrameRecorder("G:\\Gravity Falls (Сыендук)\\GFS01E01.egde.mp4", grabber.getImageWidth(),
				grabber.getImageHeight());
		recorder.setAudioChannels(grabber.getAudioChannels());
		recorder.setAudioBitrate(grabber.getAudioBitrate());

		// System.out.println(grabber.getVideoBitrate());
		recorder.setVideoBitrate(2 * 1024 * 1024);
		recorder.setVideoCodec(grabber.getVideoCodec());
		recorder.setFrameRate(grabber.getFrameRate());
		recorder.setFormat("mp4");

		recorder.start();

		Frame frame;
		for (int i = 0; (frame = grabber.grab()) != null; i++) {
			recorder.setTimestamp(grabber.getTimestamp());

			if (frame.image != null) {
				IplImage image = javacv(frame);
				Frame ff = converterToMat.convert(image);
				recorder.record(ff);
				if (frame.keyFrame)
					canvasFrame.showImage(ff);
				image.release();
			} else {
				recorder.record(frame);
			}
		}

		grabber.stop();
		grabber.release();

		recorder.stop();
		recorder.release();

	}

	private IplImage javacv(Frame frame) {
		IplImage image = converterToMat.convertToIplImage(frame);
		//
		IplImage gray = IplImage.create(image.width(), image.height(), opencv_core.IPL_DEPTH_8U, 1);

		//
		opencv_imgproc.cvCvtColor(image, gray, opencv_imgproc.CV_RGB2GRAY);

		IplImage blur = IplImage.create(image.width(), image.height(), opencv_core.IPL_DEPTH_8U, 1);
		IplImage edge = IplImage.create(image.width(), image.height(), opencv_core.IPL_DEPTH_8U, 1);
		IplImage mul = IplImage.create(image.width(), image.height(), opencv_core.IPL_DEPTH_8U, 1);

		opencv_imgproc.cvSmooth(gray, blur);
		opencv_imgproc.cvCanny(blur, edge, 60, 100);
		gray.release();
		blur.release();

		opencv_core.cvSub(gray, edge, mul);

		edge.release();
		return mul;
	}

	private void java2d(Frame frame) {
		Java2DFrameConverter converter = new Java2DFrameConverter();
		BufferedImage image = converter.convert(frame);
		BufferedImage imageDy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

		// To grayscale
		BufferedImage gray = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = (Graphics2D) gray.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();

		Kernel kernel = new Kernel(3, 3, new float[] { -1, -2, -1, 0, 0, 0, 1, 2, 1 });
		Kernel kernel2 = new Kernel(3, 3, new float[] { -1, 0, 1, -2, 0, 2, -1, 0, 1 });

		ConvolveOp op = new ConvolveOp(kernel2);

		op.filter(image, imageDy);

		canvasFrame.showImage(imageDy);

	}

	public static void main(String[] args) throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {
		new VideoProcessor();
	}

}
