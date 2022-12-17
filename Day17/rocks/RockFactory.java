package rocks;

import java.awt.*;

public class RockFactory {

    private RockFactory() {} // Non-instantiable

    public static Rock getRock(final int round, final int maxHeight) {
        // 3 is the space and 1 is, so it can fall one step immediately after creation
        return new Rock(Shape.getShape(round), new Point(2, maxHeight + 3 + 1));
    }
}
