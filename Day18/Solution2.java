import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution2 {

    public static void main(final String[] args) throws IOException {
        final Map<Point3D, Integer> cubes;
        try (final Stream<String> stream = Files.lines(Path.of("input.txt"))) {
            cubes = stream.map(line -> line.split(","))
                    .map(coordinates -> Arrays.stream(coordinates).mapToInt(Integer::parseInt).toArray())
                    .map(coordinates -> new Point3D(coordinates[0], coordinates[1], coordinates[2]))
                    .collect(Collectors.toMap(point -> point, ignored -> 0));
        }
        final Dimensions dimensions = new Dimensions(
                cubes.keySet().stream().mapToInt(Point3D::getX).min().orElseThrow() - 1,
                cubes.keySet().stream().mapToInt(Point3D::getY).min().orElseThrow() - 1,
                cubes.keySet().stream().mapToInt(Point3D::getZ).min().orElseThrow() - 1,
                cubes.keySet().stream().mapToInt(Point3D::getX).max().orElseThrow() + 1,
                cubes.keySet().stream().mapToInt(Point3D::getY).max().orElseThrow() + 1,
                cubes.keySet().stream().mapToInt(Point3D::getZ).max().orElseThrow() + 1
        );

        final Point3D start = new Point3D(dimensions.minX(), dimensions.minY(), dimensions.minZ());

        final Deque<Point3D> points = new ArrayDeque<>();
        final Set<Point3D> visited = new HashSet<>();
        points.add(start);
        visited.add(start);

        while (!points.isEmpty()) {
            final Point3D point = points.pop();

            for (final Direction direction : Direction.values()) {
                final int xNew = point.getX() + direction.getX();
                final int yNew = point.getY() + direction.getY();
                final int zNew = point.getZ() + direction.getZ();

                final Point3D pointNeighbour;
                if (dimensions.isOutside(xNew, yNew, zNew)
                        || visited.contains((pointNeighbour = new Point3D(xNew, yNew, zNew)))) {
                    continue;
                }

                if (cubes.containsKey(pointNeighbour)) {
                    cubes.compute(pointNeighbour, (pointIgnored, old) -> old + 1);
                } else {
                    points.add(pointNeighbour);
                    visited.add(pointNeighbour);
                }
            }
        }

        System.out.println(cubes.values().stream().mapToInt(i -> i).sum());
    }
}
