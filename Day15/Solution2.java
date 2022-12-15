import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Solution2 {

    private static final int X_MIN = 0;
    private static final int Y_MIN = 0;
    private static final int X_MAX = 4_000_000;
    private static final int Y_MAX = 4_000_000;

    public static void main(final String[] args) throws IOException {
        final List<Sensor> sensors;
        try (final Stream<String> stream = Files.lines(Path.of("input.txt"))) {
            sensors = stream.map(line -> line.split("Sensor at x=|, y=|: closest beacon is at x="))
                    .map(coordinates -> Arrays.stream(coordinates).skip(1).mapToInt(Integer::parseInt).toArray())
                    .map(coordinates -> new Sensor(coordinates[0], coordinates[1], coordinates[2], coordinates[3]))
                    .toList();
        }

        for (int row = Y_MIN; row <= Y_MAX; row++) {
            final int finalRow = row;

            final List<Interval> intervals = sensors.stream()
                    .map(sensor -> sensor.getRowInterval(X_MIN, X_MAX, finalRow))
                    .flatMap(Optional::stream)
                    .sorted(Comparator.comparingInt(Interval::from))
                    .toList();
            final List<Interval> joined = Interval.unionOverlappingIntervals(intervals);

            if (joined.size() > 1) {
                System.out.println((long) (joined.get(0).to() + 1) * 4_000_000 + row);
                return;
            }
        }
    }
}
