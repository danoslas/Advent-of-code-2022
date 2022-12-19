import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution1 {

    public static void main(final String[] args) throws IOException {
        final Map<Point3D, Integer> cubes;
        try (final Stream<String> stream = Files.lines(Path.of("input.txt"))) {
            cubes = stream.map(line -> line.split(","))
                    .map(coordinates -> Arrays.stream(coordinates).mapToInt(Integer::parseInt).toArray())
                    .map(coordinates -> new Point3D(coordinates[0], coordinates[1], coordinates[2]))
                    .collect(Collectors.toMap(point -> point, ignored -> 0));
        }

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

        System.out.println(cubes.size() * 6 - cubes.values().stream().mapToInt(i -> i).sum());
    }
}
