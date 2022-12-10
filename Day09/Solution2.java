import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Solution2 {

    private static final int ROPE_LENGTH = 10;

    public static void main(final String[] args) throws IOException {
        final Set<Point> tailPositions = new HashSet<>();
        final Point[] rope = IntStream.range(0, ROPE_LENGTH)
                .mapToObj(i -> new Point(0, 0))
                .toArray(Point[]::new);

        try (final BufferedReader br = Files.newBufferedReader(Path.of("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                final char direction = line.charAt(0);
                for (int i = 0; i < Integer.parseInt(line.substring(2)); i++) {
                    moveHead(direction, rope[0]);
                    for (int j = 1; j < ROPE_LENGTH; j++) {
                        moveFollowKnot(rope[j - 1], rope[j]);
                    }
                    tailPositions.add(new Point(rope[ROPE_LENGTH - 1]));
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

    private static void moveFollowKnot(final Point lead, final Point follow) {
        if (lead.distanceSq(follow) > 2) {
            follow.move(
                    follow.x + Integer.signum(lead.x - follow.x),
                    follow.y + Integer.signum(lead.y - follow.y)
            );
        }
    }
}
