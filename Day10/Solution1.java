import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Solution1 {

    private static final int CYCLE_CHECK_INCREMENT = 40;
    private static final int MAX_CYCLE_CHECK = 20 + (5 * CYCLE_CHECK_INCREMENT);
    private static final int WAITING_CYCLES = 2;

    public static void main(final String[] args) throws IOException {
        int signalStrengthSum = 0;

        try (final BufferedReader br = Files.newBufferedReader(Path.of("input.txt"))) {
            int cycleCheck = 20;
            int registerX = 1;
            int processingWait = -1;

            String line = br.readLine();
            for (int cycle = 1; line != null && cycleCheck <= MAX_CYCLE_CHECK; cycle++) {
                if (cycle == cycleCheck) {
                    signalStrengthSum += cycle * registerX;
                    cycleCheck += CYCLE_CHECK_INCREMENT;
                }

                if (!"noop".equals(line)) {
                    if (processingWait == -1) {
                        processingWait = WAITING_CYCLES - 1;
                    } else {
                        processingWait--;
                    }

                    if (processingWait == 0) {
                        registerX += Integer.parseInt(line.substring(5));
                        processingWait = -1;
                    } else {
                        continue;
                    }
                }

                line = br.readLine();
            }
        }

        System.out.println(signalStrengthSum);
    }
}
