import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Solution {

    public static void main(final String[] args) throws IOException {
        int maxCalories = 0;

        try (final BufferedReader br = Files.newBufferedReader(Path.of("input.txt"))) {
            int singleElfCalories = 0;

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    if (singleElfCalories > maxCalories) {
                        maxCalories = singleElfCalories;
                    }
                    singleElfCalories = 0;
                    continue;
                }

                singleElfCalories += Integer.parseInt(line);
            }
        }

        System.out.println(maxCalories);
    }
}