import java.awt.*;
import java.io.IOException;
import java.util.Set;

public class Solution2 {

    public static void main(final String[] args) throws IOException {
        final Set<Point> occupiedPoints = Maps.parseMap("input.txt");
        final int maxDepth = occupiedPoints.stream()
                .mapToInt(point -> point.y)
                .max()
                .orElseThrow() + 2;

        int sandGrains = 0;
        Point sand = new Point(500, 0);

        while (true) {
            final int sandX = sand.x;
            final int newSandY = sand.y + 1;

            if (newSandY < maxDepth) {
                final boolean canFallDown = !occupiedPoints.contains(new Point(sandX, newSandY));
                if (canFallDown) {
                    sand.move(sandX, newSandY);
                    continue;
                }

                final boolean canRollLeft = !occupiedPoints.contains(new Point(sandX - 1, newSandY));
                if (canRollLeft) {
                    sand.move(sandX - 1, newSandY);
                    continue;
                }

                final boolean canRollRight = !occupiedPoints.contains(new Point(sandX + 1, newSandY));
                if (canRollRight) {
                    sand.move(sandX + 1, newSandY);
                    continue;
                }
            }

            occupiedPoints.add(sand);
            sandGrains++;
            if (sand.y == 0) {
                break;
            }

            sand = new Point(500, 0);
        }

        System.out.println(sandGrains);
    }
}
