import lines.Line;
import lines.Lines;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Solution1 {

    public static void main(final String[] args) throws IOException {
        final Collection<Line> lines = Lines.getLines("input.txt");
        final int maxLinesDepth = lines.stream().mapToInt(Line::getMaxY).max().orElseThrow();

        final Set<Point> sandGrains = new HashSet<>();
        Point sand = new Point(500, 0);

        while (sand.y < maxLinesDepth) {
            final int sandX = sand.x;
            final int newSandY = sand.y + 1;
            
            final boolean canFallDown = lines.stream()
                    .noneMatch(line -> line.contains(sandX, newSandY))
                    && !sandGrains.contains(new Point(sandX, newSandY));
            if (canFallDown) {
                sand.move(sandX, newSandY);
                continue;
            }

            final boolean canRollLeft = lines.stream()
                    .noneMatch(line -> line.contains(sandX - 1, newSandY))
                    && !sandGrains.contains(new Point(sandX - 1, newSandY));
            if (canRollLeft) {
                sand.move(sandX - 1, newSandY);
                continue;
            }

            final boolean canRollRight = lines.stream()
                    .noneMatch(line -> line.contains(sandX + 1, newSandY))
                    && !sandGrains.contains(new Point(sandX + 1, newSandY));
            if (canRollRight) {
                sand.move(sandX + 1, newSandY);
                continue;
            }

            sandGrains.add(sand);
            sand = new Point(500, 0);
        }

        System.out.println(sandGrains.size());
    }
}
