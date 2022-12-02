import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Solution2 {

    private static final int[][] POINTS = {
  // He has: R  P  S
            {3, 1, 2}, // I need to lose
            {1, 2, 3}, // I need Draw
            {2, 3, 1} // I need to win
    };

    public static void main(final String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get("input.txt"))) {
            System.out.println(
                    stream.map(String::toCharArray)
                            .mapToInt(Solution2::playRound)
                            .sum()
            );
        }
    }

    private static int playRound(final char[] choice) {
        return POINTS[choice[2] - 'X'][choice[0] - 'A'] + (choice[2] - 'X') * 3;
    }
}
