package ss;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class AreaUtils {

	private static final int WALL = -1;
	private static final int EMPTY = -2;

	public static List<Area> split(BufferedImage image) {

		int[][] map = new int[image.getWidth()][image.getHeight()];

		for (int x = 0; x < image.getWidth(); x++)
			for (int y = 0; y < image.getHeight(); y++)
				map[x][y] = image.getRGB(x, y) == -1 ? EMPTY : WALL;

		Point startPoint = null;

		int id = 3;
		while ((startPoint = getPoint(map, EMPTY)) != null)
			fill(startPoint, map, -(id++));

		id--;

		int num = id - 2;
		List<Area> areas = new ArrayList<>(num);
		for (int i = 0; i < num; i++)
			areas.add(new Area());

		for (int x = 0; x < image.getWidth(); x++)
			for (int y = 0; y < image.getHeight(); y++)
				if (map[x][y] <= -3)
					areas.get((num - 1) - (id + map[x][y])).addPoint(new Point(x, y));

		for (int i = 0; i < num; i++)
			areas.get(i).normalize();

		return areas;
	}

	private static void fill(Point startPoint, int[][] map, int id) {
		map[startPoint.x][startPoint.y] = 0;
		int width = map.length;
		int height = map[0].length;

		int[] dx = { -1, 0, 1, -1, 1, -1, 0, 1 };
		int[] dy = { 1, 1, 1, 0, 0, -1, -1, -1 };

		List<Point> step = new ArrayList<Point>();
		List<Point> nextStep = new ArrayList<Point>();
		int stepCounter = 1;

		step.add(startPoint);

		do {
			List<Point> tmp = step;
			step = nextStep;
			nextStep = tmp;

			for (Point p : step) {
				for (int i = 0; i < dx.length; i++) {
					int nx = p.x + dx[i];
					int ny = p.y + dy[i];
					if (inRange(0, width - 1, nx) && inRange(0, height - 1, ny) && map[nx][ny] == EMPTY) {
						map[nx][ny] = stepCounter;
						nextStep.add(new Point(nx, ny));
					}
				}
			}
			stepCounter++;
			step.clear();
		} while (nextStep.size() > 0);

		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				if (map[x][y] >= 0)
					map[x][y] = id;

	}

	private static boolean inRange(int min, int max, int x) {
		return x >= min && x <= max;
	}

	private static Point getPoint(int[][] map, int value) {
		for (int x = 0; x < map.length; x++)
			for (int y = 0; y < map[x].length; y++)
				if (map[x][y] == value)
					return new Point(x, y);

		return null;
	}

	public static int getSine(Area area) {
		int width = 5;
		int sqrWidth = width * width;
		for (Point p1 : area.getPoints()) {
			for (Point p2 : area.getPoints()) {
				if (p1.distanceSq(p2) > sqrWidth) {
					double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);
					System.out.println(angle / Math.PI * 180);
				}
			}

		}
		return 0;
	}

}
