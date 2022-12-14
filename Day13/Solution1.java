import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Solution1 {

    public static void main(final String[] args) throws IOException {
        int indicesSum = 0;

        try (final BufferedReader br = Files.newBufferedReader(Path.of("input.txt"))) {
            int pair = 1;
            do {
                if (Arrays.compareArrays(br.readLine(), br.readLine()) == Result.RIGHT_ORDER) {
                    indicesSum += pair;
                }
                pair++;
            } while (br.readLine() != null);
        }

        System.out.println(indicesSum);
    }
}
