package desuteam.voiezEffect;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JPanel implements ActionListener {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(new Main());
		frame.pack();
		frame.setVisible(true);
	}

	BufferedImage tex;
	BufferedImage tex2;
	BufferedImage combined;
	WeightMap map;
	Timer timer = new Timer(0, this);
	double time = -2;

	public Main() {

		map = new WeightMap(9, 0.5);

		try {
			tex = ImageIO.read(new File("res/bg2.jpg"));
			tex2 = ImageIO.read(new File("res/bg1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		combined = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
		combinedImages(map, 0);

		// map.print();

		timer.start();
	}

	@Override
	public void addNotify() {
		super.addNotify();
		setPreferredSize(new Dimension(512, 512));
	}

	@Override
	public void paint(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(combined, 0, 0, null);
	}

	private void combinedImages(WeightMap map, double t) {

		for (int x = 0; x < 512; x++)
			for (int y = 0; y < 512; y++) {
				double dt = t - map.getAltitude(x, y);
				int color;
				int texColor1 = tex2.getRGB(x, y);
				int texColor2 = 0x00FFFFFF;

				if (dt > 0) {
					color = texColor1;
				} else if (dt > -0.04) {
					double ct = f((dt + 0.04) * 25);
					color = mix(texColor2, texColor1, ct);

				} else color = texColor2;

				combined.setRGB(x, y, color);

			}

	}

	private int mix(int rgb1, int rgb2, double t) {
		int r1 = (rgb1 & 0x00FF0000) >> 16;
		int g1 = (rgb1 & 0x0000FF00) >> 8;
		int b1 = (rgb1 & 0x000000FF) >> 0;

		int r2 = (rgb2 & 0x00FF0000) >> 16;
		int g2 = (rgb2 & 0x0000FF00) >> 8;
		int b2 = (rgb2 & 0x000000FF) >> 0;

		int rf = (int) (r1 * (1 - t) + r2 * t);
		int gf = (int) (g1 * (1 - t) + g2 * t);
		int bf = (int) (b1 * (1 - t) + b2 * t);

		return (rf << 16) | (gf << 8) | bf;
	}

	private double f(double t) {
		if (t < 0.35)
			return (1 - Math.cos(Math.PI / 0.35 * t)) / 2 * 0.3;
		if (t < 0.65)
			return 0.3;

		return (1 - Math.cos(Math.PI / 2.0 / 0.35 * (t - 0.5))) / 2 * 0.7 + 0.3;

		// return (f2(t - 0.03) + f2(t - 0.02) + f2(t) + f2(t + 0.02) + f2(t +
		// 0.03))/5;
	}

	private double f2(double t) {
		if (t < 0)
			t = 0;
		if (t > 1)
			t = 1;
		int n = (int) (t * 3);
		return n / 3.0;
	}

	public void actionPerformed(ActionEvent e) {
		time += 0.01;

		combinedImages(map, time);
		repaint();
	}

}
