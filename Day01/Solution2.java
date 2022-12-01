import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Solution2 {

    public static void main(final String[] args) throws IOException {
        final PriorityQueue<Integer> elfCalories = new PriorityQueue<>(Collections.reverseOrder());

        try (final BufferedReader br = Files.newBufferedReader(Path.of("input.txt"))) {
            int singleElfCalories = 0;

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    elfCalories.offer(singleElfCalories);
                    singleElfCalories = 0;
                    continue;
                }

                singleElfCalories += Integer.parseInt(line);
            }
        }

        System.out.println(
                Stream.generate(elfCalories::poll)
                        .limit(3)
                        .reduce(Integer::sum)
                        .orElseThrow()
        );
    }
}