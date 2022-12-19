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

        computeSurface(cubes);

        System.out.println(cubes.size() * 6 - cubes.values().stream().mapToInt(i -> i).sum());
    }

    private static void computeSurface(final Map<Point3D, Integer> cubes) {
        final Point3D point = new Point3D(0, 0, 0);
        for (final Map.Entry<Point3D, Integer> cubeNeighbours : cubes.entrySet()) {
            final Point3D cube = cubeNeighbours.getKey();

            for (int i = 1; i <= 4; i <<= 1) {
                for (int j = -1; j <= 1; j += 2) {
                    point.move(cube.getX() + ((i & 1) * j),
                            cube.getY() + (((i & 2) >> 1) * j),
                            cube.getZ() + (((i & 4) >> 2) * j));

                    if (cubes.containsKey(point)) {
                        cubeNeighbours.setValue(cubeNeighbours.getValue() + 1);
                    }
                }
            }
        }
    }
}
