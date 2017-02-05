import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test {
	public static void main(String[] args) {
		new Test();
	}

	public Test() {
		List<File> files = Arrays.asList(new File("C://windows/").listFiles());

		files.forEach((file) -> System.out.println(file));

		List<String> names = files.stream().map(this::map).sorted().collect(Collectors.toList());
		Map<String, Integer> namesCount = files.stream().map(this::map).sorted()
				.collect(Collectors.toMap((name) -> name, (name) -> name.length()));

	}

	public String map(File file) {
		return file.getName();
	}
}
