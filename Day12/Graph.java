import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class Graph {

    private final Map<Point, List<Point>> vertices = new HashMap<>();
    private final Point start;
    private final Point destination;

    public Graph(final String inputFileName) throws IOException {
        final char[][] map;
        try (final Stream<String> stream = Files.lines(Path.of(inputFileName))) {
            map = stream.map(String::toCharArray)
                    .toArray(char[][]::new);
        }

        Point start = null;
        Point destination = null;

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                final Point vertex = new Point(x, y);

                if (map[y][x] == 'S') {
                    start = vertex;
                } else if (map[y][x] == 'E') {
                    destination = vertex;
                }

                final List<Point> adjacentVertices = new ArrayList<>(4);
                if (x > 0 && heightOf(map[y][x - 1]) - heightOf(map[y][x]) <= 1) {
                    adjacentVertices.add(new Point(x - 1, y));
                }
                if (y > 0 && heightOf(map[y - 1][x]) - heightOf(map[y][x]) <= 1) {
                    adjacentVertices.add(new Point(x, y - 1));
                }
                if (x < map[0].length - 1 && heightOf(map[y][x + 1]) - heightOf(map[y][x]) <= 1) {
                    adjacentVertices.add(new Point(x + 1, y));
                }
                if (y < map.length - 1 && heightOf(map[y + 1][x]) - heightOf(map[y][x]) <= 1) {
                    adjacentVertices.add(new Point(x, y + 1));
                }

                vertices.put(vertex, adjacentVertices);
            }
        }

        this.start = Objects.requireNonNull(start);
        this.destination = Objects.requireNonNull(destination);
    }

    private static char heightOf(final char vertexSymbol) {
        return switch (vertexSymbol) {
            case 'S' -> 'a';
            case 'E' -> 'z';
            default -> vertexSymbol;
        };
    }

    public Set<Point> getVertices() {
        return Collections.unmodifiableSet(vertices.keySet());
    }

    public List<Point> getAdjacentVertices(final Point vertex) {
        return Collections.unmodifiableList(vertices.get(vertex));
    }

    public Point getStart() {
        return new Point(start);
    }

    public Point getDestination() {
        return new Point(destination);
    }
}
