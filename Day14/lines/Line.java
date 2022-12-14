package lines;

import java.awt.*;

public class Line {

    private final int minX, maxX, minY, maxY;

    Line(final Point fst, final Point snd) {
        minX = Math.min(fst.x, snd.x);
        maxX = Math.max(fst.x, snd.x);
        minY = Math.min(fst.y, snd.y);
        maxY = Math.max(fst.y, snd.y);
    }

    public boolean contains(final int x, final int y) {
        if (x < minX || x > maxX) {
            return false;
        }
        return y >= minY && y <= maxY;
    }

    public int getMaxY() {
        return maxY;
    }
}
