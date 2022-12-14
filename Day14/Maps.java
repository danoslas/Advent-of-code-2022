import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Maps {

    private Maps() {} // Non-instantiable

    public static Set<Point> parseMap(final String fileName) throws IOException {
        final Set<Point> occupiedPoints = new HashSet<>();

        try (final BufferedReader br = Files.newBufferedReader(Path.of(fileName))) {

            String line;
            while ((line = br.readLine()) != null) {
                final String[] coordinates = line.split(" -> ");

                int[] fst = Arrays.stream(coordinates[0].split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                for (int i = 1; i < coordinates.length; i++) {
                    final int[] snd = Arrays.stream(coordinates[i].split(","))
                            .mapToInt(Integer::parseInt)
                            .toArray();

                    for (int x = Math.min(fst[0], snd[0]); x <= Math.max(fst[0], snd[0]); x++) {
                        for (int y = Math.min(fst[1], snd[1]); y <= Math.max(fst[1], snd[1]); y++) {
                            occupiedPoints.add(new Point(x, y));
                        }
                    }

                    fst = snd;
                }
            }
        }

        return occupiedPoints;
    }
}
