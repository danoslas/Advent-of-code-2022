import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class Solution1 {

    private static final int BUFFER_SIZE = 4;

    public static void main(final String[] args) throws IOException {

        try (final BufferedReader br = Files.newBufferedReader(Path.of("input.txt"))) {
            final int[] chars = new int[BUFFER_SIZE];
            for (int i = 0; i < BUFFER_SIZE - 1; i++) {
                chars[i] = br.read();
            }

            int marker = BUFFER_SIZE;
            int character;
            for (; (character = br.read()) != '\n'; marker++) {
                chars[(marker - 1) % BUFFER_SIZE] = character;

                if (isAllUnique(chars)) {
                    System.out.println(marker);
                    return;
                }
            }
        }

        System.out.println(-1);
    }

    private static boolean isAllUnique(final int[] chars) {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            for (int j = i + 1; j < BUFFER_SIZE; j++) {
                if (chars[i] == chars[j]) {
                    return false;
                }
            }
        }
        return true;
    }
}