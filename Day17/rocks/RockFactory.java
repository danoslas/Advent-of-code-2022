package rocks;

import java.awt.*;

public class RockFactory {

    private RockFactory() {} // Non-instantiable

    // Get the first dummy rock, so the computation can start correctly
    public static Rock getBottomRock() {
        return new Rock(Shape.MINUS, new Point(0, -1));
    }

    public static Rock getRock(final int round, final int highestYCoordinate) {
        // 3 is the space, 1 is for its own position and 1 is, so it can fall after creation
        return new Rock(Shape.getShape(round), new Point(2, highestYCoordinate + 3 + 1 + 1));
    }
}
