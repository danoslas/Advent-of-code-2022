import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Solution2 {

    private static final int WAITING_CYCLES = 2;
    private static final int SCREEN_WIDTH = 40;

    public static void main(final String[] args) throws IOException {
        int registerX = 1;
        int processingWait = 0;

        try (final BufferedReader br = Files.newBufferedReader(Path.of("input.txt"))) {
            String line = br.readLine();
            for (int cycle = 1; line != null; cycle++) {
                printPixel(cycle, registerX);

                if (!"noop".equals(line)) {
                    if (processingWait == 0) {
                        processingWait = WAITING_CYCLES - 1;
                    } else {
                        processingWait--;
                    }

                    if (processingWait == 0) {
                        registerX += Integer.parseInt(line.substring(5));
                    } else {
                        continue;
                    }
                }

                line = br.readLine();
            }
        }
    }

    private static void printPixel(final int cycle, final int registerX) {
        final int column = (cycle - 1) % SCREEN_WIDTH;
        if (Math.abs(registerX - column) <= 1) {
            System.out.print('#');
        } else {
            System.out.print('.');
        }

        if (column == SCREEN_WIDTH - 1) {
            System.out.println();
        }
    }
}
