import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Maps {

    private Maps() {} // Non-instantiable

    public static Set<Point> parseMap(final String fileName) throws IOException {
        final Set<Point> occupiedPoints = new HashSet<>();

        try (final BufferedReader br = Files.newBufferedReader(Path.of(fileName))) {

            String line;
            while ((line = br.readLine()) != null) {
                final String[] coordinates = line.split(" -> ");

                final Deque<int[]> points = new ArrayDeque<>();
                for (final String coordinate : coordinates) {
                    points.add(Arrays.stream(coordinate.split(","))
                            .mapToInt(Integer::parseInt)
                            .toArray());
                }

                do {
                    final int[] fst = points.pop();
                    final int[] snd = points.peek();

                    for (int x = Math.min(fst[0], snd[0]); x <= Math.max(fst[0], snd[0]); x++) {
                        for (int y = Math.min(fst[1], snd[1]); y <= Math.max(fst[1], snd[1]); y++) {
                            occupiedPoints.add(new Point(x, y));
                        }
                    }
                } while (points.size() > 1);
            }
        }

        return occupiedPoints;
    }
}
