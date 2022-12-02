import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Solution1 {

    private static final int[][] POINTS = {
  // He has: R  P  S
            {3, 0, 6}, // I have Rock
            {6, 3, 0}, // I have Paper
            {0, 6, 3} // I have Scissors
    };

    public static void main(final String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get("input.txt"))) {
            System.out.println(
                    stream.map(String::toCharArray)
                            .mapToInt(Solution1::playRound)
                            .sum()
            );
        }
    }

    private static int playRound(final char[] choice) {
        return POINTS[choice[2] - 'X'][choice[0] - 'A'] + choice[2] - 'W';
    }
}
