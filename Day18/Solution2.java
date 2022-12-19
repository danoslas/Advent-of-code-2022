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
                cubes.keySet().stream().mapToInt(Point3D::getX).min().orElseThrow(),
                cubes.keySet().stream().mapToInt(Point3D::getY).min().orElseThrow(),
                cubes.keySet().stream().mapToInt(Point3D::getZ).min().orElseThrow(),
                cubes.keySet().stream().mapToInt(Point3D::getX).max().orElseThrow(),
                cubes.keySet().stream().mapToInt(Point3D::getY).max().orElseThrow(),
                cubes.keySet().stream().mapToInt(Point3D::getZ).max().orElseThrow()
        );

        fillPockets(cubes, dimensions);
        computeSurface(cubes);

        System.out.println(cubes.size() * 6 - cubes.values().stream().mapToInt(i -> i).sum());
    }

    private static void fillPockets(final Map<Point3D, Integer> cubes, final Dimensions dimensions) {
        for (int x = dimensions.minX(); x <= dimensions.maxX(); x++) {
            for (int y = dimensions.minY(); y <= dimensions.maxY(); y++) {
                for (int z = dimensions.minZ(); z <= dimensions.maxZ(); z++) {
                    final Point3D point = new Point3D(x, y, z);

                    if (cubes.containsKey(point)) {
                        continue;
                    }

                    final Deque<Point3D> points = new ArrayDeque<>();
                    final Set<Point3D> pocketPoints = new HashSet<>();
                    points.add(point);
                    pocketPoints.add(point);

                    boolean isPocket = true;
                    outerLoop:
                    while (!points.isEmpty()) {
                        final Point3D processedPoint = points.pop();

                        for (final Direction direction : Direction.values()) {
                            final int xNew = processedPoint.getX() + direction.getX();
                            final int yNew = processedPoint.getY() + direction.getY();
                            final int zNew = processedPoint.getZ() + direction.getZ();

                            // From the 'point' we can reach the outside -> it is not a pocket
                            if (dimensions.isOutside(xNew, yNew, zNew)) {
                                isPocket = false;
                                break outerLoop;
                            }

                            final Point3D spacePointCopy = new Point3D(xNew, yNew, zNew);
                            if (!cubes.containsKey(spacePointCopy) && !pocketPoints.contains(spacePointCopy)) {
                                points.add(spacePointCopy);
                                pocketPoints.add(spacePointCopy);
                            }
                        }
                    }

                    if (isPocket) {
                        for (final Point3D pocketPoint : pocketPoints) {
                            cubes.put(pocketPoint, 0);
                        }
                    }
                }
            }
        }
    }

    private static void computeSurface(final Map<Point3D, Integer> cubes) {
        final Point3D point = new Point3D(0, 0, 0);
        for (final Map.Entry<Point3D, Integer> cubeNeighbours : cubes.entrySet()) {
            final Point3D cube = cubeNeighbours.getKey();

            for (final Direction direction : Direction.values()) {
                point.move(cube.getX() + direction.getX(),
                        cube.getY() + direction.getY(),
                        cube.getZ() + direction.getZ());

                if (cubes.containsKey(point)) {
                    cubeNeighbours.setValue(cubeNeighbours.getValue() + 1);
                }
            }
        }
    }
}
