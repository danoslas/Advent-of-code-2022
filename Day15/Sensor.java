import java.util.Optional;

public class Sensor {

    private final int xS, yS;
    private final int radius;

    public Sensor(final int xS, final int yS, final int xB, final int yB) {
        this.xS = xS;
        this.yS = yS;
        this.radius = getDistance(xS, yS, xB, yB);
    }

    private static int getDistance(final int xS, final int yS, final int xB, final int yB) {
        return Math.abs(xS - xB) + Math.abs(yS - yB);
    }

    public Optional<Interval> getRowInterval(final int xFrom, final int xTo, final int y) {
        final int yDistance = Math.abs(y - yS);
        if (yDistance > radius) {
            return Optional.empty();
        }

        return Optional.of(new Interval(
                Math.max(xFrom, xS - radius + yDistance),
                Math.min(xTo, xS + radius - yDistance)
        ));
    }
}
