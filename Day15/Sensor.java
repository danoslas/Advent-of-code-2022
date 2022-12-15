import java.util.Optional;

public class Sensor {

    private final int x, y;
    private final int radius;

    public Sensor(final int xS, final int yS, final int xB, final int yB) {
        this.x = xS;
        this.y = yS;
        this.radius = getDistance(xS, yS, xB, yB);
    }

    private static int getDistance(final int xS, final int yS, final int xB, final int yB) {
        return Math.abs(xS - xB) + Math.abs(yS - yB);
    }

    public Optional<Interval> getRowInterval(final int xFrom, final int xTo, final int y) {
        final int yDistance = Math.abs(y - this.y);
        if (yDistance > radius) { // The sensor does not interfere with the y-coordinate
            return Optional.empty();
        }

        final int start = Math.max(xFrom, x - radius + yDistance);
        final int end = Math.min(xTo, x + radius - yDistance);
        if (start > end) { // The sensor does not interfere with the interval [xFrom, xTo]
            return Optional.empty();
        }

        return Optional.of(new Interval(start, end));
    }
}
