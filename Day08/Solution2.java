import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution2 {

    public static void main(final String[] args) throws IOException {
        final int[][] forest;

        try (final Stream<String> stream = Files.lines(Path.of("input.txt"))) {
            forest = stream.map(line -> line.chars().toArray())
                    .toArray(int[][]::new);
        }

        final int forestHeight = forest.length;
        final int forestWidth = forest[0].length;

        System.out.println(
                IntStream.range(1, forestHeight - 1)
                        .mapToLong(y ->
                                IntStream.range(1, forestWidth - 1)
                                        .mapToLong(x -> getTreeRange(forest, x, y))
                                        .max()
                                        .orElseThrow()
                        ).max()
                        .orElseThrow()
        );
    }

    private static long getTreeRange(final int[][] forest, final int x, final int y) {
        final int forestHeight = forest.length;
        final int forestWidth = forest[0].length;
        final int treeHeight = forest[y][x];

        final long viewRight = 1 + IntStream.range(x + 1, forestWidth - 1)
                .takeWhile(i -> forest[y][i] < treeHeight)
                .count();
        final long viewLeft = 1 + IntStream.range(1, x)
                .takeWhile(i -> forest[y][x - i] < treeHeight)
                .count();
        final long viewTop = 1 + IntStream.range(1, y)
                .takeWhile(i -> forest[y - i][x] < treeHeight)
                .count();
        final long viewBottom = 1 + IntStream.range(y + 1, forestHeight - 1)
                .takeWhile(i -> forest[i][x] < treeHeight)
                .count();

        return viewRight * viewLeft * viewTop * viewBottom;
    }
}
