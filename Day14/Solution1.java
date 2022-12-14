import java.awt.*;
import java.io.IOException;
import java.util.Set;

public class Solution1 {

    public static void main(final String[] args) throws IOException {
        final Set<Point> occupiedPoints = Maps.parseMap("input.txt");
        final int maxLinesDepth = occupiedPoints.stream()
                .mapToInt(point -> point.y)
                .max()
                .orElseThrow();

        int sandGrains = 0;
        Point sand = new Point(500, 0);

        while (sand.y < maxLinesDepth) {
            final int sandX = sand.x;
            final int newSandY = sand.y + 1;
            
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

            occupiedPoints.add(sand);
            sandGrains++;

            sand = new Point(500, 0);
        }

        System.out.println(sandGrains);
    }
}
