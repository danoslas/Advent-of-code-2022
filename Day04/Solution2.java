import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Solution2 {

    public static void main(final String[] args) throws IOException {
        try (final Stream<String> stream = Files.lines(Path.of("input.txt"))) {
            System.out.println(
                    stream.map(line -> line.split("[,-]"))
                            .map(ranges -> Stream.of(ranges)
                                    .mapToInt(Integer::parseInt)
                                    .toArray())
                            .mapToInt(ranges -> (ranges[0] <= ranges[2] && ranges[1] >= ranges[2])
                                    || (ranges[2] <= ranges[0] && ranges[3] >= ranges[0])
                                    ? 1 : 0)
                            .sum()
            );
        }
    }
}
