package ss;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Area {
	List<Point> points = new ArrayList<Point>();
	int minX = Integer.MAX_VALUE;
	int minY = Integer.MAX_VALUE;
	int maxX = Integer.MIN_VALUE;
	int maxY = Integer.MIN_VALUE;

	void addPoint(Point point) {

		if (point.x < minX)
			minX = point.x;
		if (point.y < minY)
			minY = point.y;
		if (point.x > maxX)
			maxX = point.x;
		if (point.y > maxY)
			maxY = point.y;

		// Add point
		points.add(point);
		point = null;
	}

	public Point get(int index) {
		return points.get(index);
	}

	public List<Point> getPoints() {
		return points;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getMinX() {
		return minX;
	}

	public int getMinY() {
		return minY;
	}

	public int size() {
		return points.size();
	}

	public void normalize() {
		if (minX == 0 && minY == 0)
			return;
		for (Point p : points) {
			p.x -= minX;
			p.y -= minY;
		}
		maxX -= minX;
		maxY -= minY;
		minX = 0;
		minY = 0;
	}

	public BufferedImage print() {
		BufferedImage result = new BufferedImage(maxX - minX + 1, maxY - minY + 1, BufferedImage.TYPE_BYTE_BINARY);
		System.out.println(maxX - minX);
		for (Point p : points)
			result.setRGB(p.x - minX, p.y - minY, -1);
		return result;
	}

}