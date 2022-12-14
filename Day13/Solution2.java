import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution2 {

    public static void main(final String[] args) throws IOException {
        final List<String> sortedLines;

        try (final Stream<String> stream = Files.lines(Path.of("input.txt"))) {
            sortedLines = Stream.concat(Stream.of("[[2]]", "[[6]]"), stream)
                    .filter(Predicate.not(String::isEmpty))
                    .sorted((fst, snd) -> {
                        final Result result = Arrays.compareArrays(fst, snd);
                        if (result == Result.DUNNO) {
                            return 0;
                        }
                        return result == Result.RIGHT_ORDER ? -1 : 1;
                    })
                    .toList();
        }

        System.out.println(
                IntStream.range(0, sortedLines.size())
                        .filter(i -> "[[2]]".equals(sortedLines.get(i)) || "[[6]]".equals(sortedLines.get(i)))
                        .limit(2)
                        .map(i -> i + 1)
                        .reduce(1, (fst, snd) -> fst * snd)
        );
    }
}
