package lines;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class Lines {

    private Lines() {} // Non-instantiable

    public static Collection<Line> getLines(final String fileName) throws IOException {
        final List<Line> lines = new ArrayList<>();

        try (final BufferedReader br = Files.newBufferedReader(Path.of(fileName))) {

            String line;
            while ((line = br.readLine()) != null) {
                final String[] coordinates = line.split(" -> ");

                final Deque<Point> points = new ArrayDeque<>();
                for (final String coordinate : coordinates) {
                    final String[] point = coordinate.split(",");
                    points.add(new Point(Integer.parseInt(point[0]), Integer.parseInt(point[1])));
                }

                do {
                    final Point fst = points.pop();
                    final Point snd = points.peek();

                    lines.add(new Line(fst, snd));
                } while (points.size() > 1);
            }
        }

        return lines;
    }
}
