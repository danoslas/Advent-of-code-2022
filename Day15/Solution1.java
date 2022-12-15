import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Solution1 {

    private static final int Y_COORDINATE = 2_000_000;

    public static void main(final String[] args) throws IOException {
        final List<Sensor> sensors;
        try (final Stream<String> stream = Files.lines(Path.of("input.txt"))) {
            sensors = stream.map(line -> line.split("Sensor at x=|, y=|: closest beacon is at x="))
                    .map(coordinates -> Arrays.stream(coordinates).skip(1).mapToInt(Integer::parseInt).toArray())
                    .map(coordinates -> new Sensor(coordinates[0], coordinates[1], coordinates[2], coordinates[3]))
                    .toList();
        }

        final List<Interval> intervals = sensors.stream()
                .map(sensor -> sensor.getRowInterval(Integer.MIN_VALUE, Integer.MAX_VALUE, Y_COORDINATE))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.comparingInt(Interval::from))
                .toList();
        final List<Interval> joined = Interval.unionOverlappingIntervals(intervals);

        System.out.println(joined.stream().mapToInt(Interval::length).sum());
    }
}
