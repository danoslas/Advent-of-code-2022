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

    public Optional<Interval> getRowInterval(final int x1, final int x2, final int y) {
        if (Math.abs(y - yS) > radius) {
            return Optional.empty();
        }

        return Optional.of(new Interval(
                Math.max(Math.min(x1, x2), xS - radius + Math.abs(yS - y)),
                Math.min(Math.max(x1, x2), xS + radius - Math.abs(yS - y))
        ));
    }
}
