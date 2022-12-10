import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Solution1 {

    public static void main(final String[] args) throws IOException {
        final Set<Point> tailPositions = new HashSet<>();
        final Point head = new Point(0, 0);
        Point tail = new Point(0, 0);

        try (final BufferedReader br = Files.newBufferedReader(Path.of("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                final char direction = line.charAt(0);
                for (int i = 0; i < Integer.parseInt(line.substring(2)); i++) {
                    moveHead(direction, head);
                    tail = getTailPosition(head, tail);
                    tailPositions.add(tail);
                }
            }
        }

        System.out.println(tailPositions.size());
    }

    private static void moveHead(final char direction, final Point head) {
        switch (direction) {
            case 'U' -> head.move(head.x, head.y + 1);
            case 'D' -> head.move(head.x, head.y - 1);
            case 'R' -> head.move(head.x + 1, head.y);
            case 'L' -> head.move(head.x - 1, head.y);
            default -> throw new IllegalArgumentException("Unknown direction '" + direction + "'.");
        }
    }

    private static Point getTailPosition(final Point head, final Point tail) {
        if (head.distanceSq(tail) <= 2) {
            return tail;
        }

        return new Point(
                tail.x + Integer.signum(head.x - tail.x),
                tail.y + Integer.signum(head.y - tail.y)
        );
    }
}
