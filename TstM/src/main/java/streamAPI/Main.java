package streamAPI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) throws IOException {
		new Main();
	}

	public Main() throws IOException {

		long time = System.currentTimeMillis();
		List<File> mapsDir = Arrays.asList(new File("R:/Songs/").listFiles()).stream().filter(Main::mapFilter)
				.collect(Collectors.toList());

		List<OsuMap> maps = mapsDir.stream().map(Main::toMap).collect(Collectors.toList());

		System.out.println(System.currentTimeMillis() - time);
		maps.forEach((map) -> System.out.println(map + "\n"));

	}

	public static boolean mapFilter(File dir) {
		return Arrays.asList(dir.listFiles()).stream().filter((file) -> file.isFile())
				.filter((file) -> file.getName().endsWith(".osu")).count() > 0;
	}

	public static OsuMap toMap(File dir) {
		File osuFile = Arrays.asList(dir.listFiles()).stream().filter((file) -> file.isFile())
				.filter((file) -> file.getName().endsWith(".osu")).collect(Collectors.toList()).get(0);

		OsuMap map = new OsuMap();
		map.setDir(dir);

		try {
			Optional<String> imageURL = Files.lines(Paths.get(osuFile.getAbsolutePath()))
					.filter((line) -> line.contains("jpeg") || line.contains("jpg") || line.contains("png"))
					.filter((line) -> line.matches("\\d+,\\d+,\\\".+$")).map((line) -> {
						Matcher matcher = Pattern.compile("\\\"(.*)\\\"").matcher(line);
						matcher.find();
						return dir.getAbsolutePath() + "\\" + matcher.group(1);
					}).findFirst();

			if (imageURL.isPresent())
				map.setPoster(new File(imageURL.get()));

			Optional<String> audioURL = Files.lines(Paths.get(osuFile.getAbsolutePath()))
					.filter((line) -> line.startsWith("AudioFilename: ")).map((line) -> {
						Matcher matcher = Pattern.compile("AudioFilename:\\s(.*)").matcher(line);
						matcher.find();
						return dir.getAbsolutePath() + "\\" + matcher.group(1);
					}).findFirst();

			if (audioURL.isPresent())
				map.setSound(new File(audioURL.get()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return map;
	}

	private static class OsuMap {
		File dir;
		File sound;
		File poster;

		public File getDir() {
			return dir;
		}

		public void setDir(File dir) {
			this.dir = dir;
		}

		public File getSound() {
			return sound;
		}

		public void setSound(File sound) {
			this.sound = sound;
		}

		public File getPoster() {
			return poster;
		}

		public void setPoster(File poster) {
			this.poster = poster;
		}

		@Override
		public String toString() {
			return String.format("Dir:\t%s\nPoster:\t%s\nSound:\t%s", dir, poster, sound);
		}
	}

}
