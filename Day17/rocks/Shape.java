package rocks;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

enum Shape {

    MINUS(1, new boolean[][] {
            { true, true, true, true }
    }),
    PLUS(2, new boolean[][] {
            { false, true, false },
            { true, true, true },
            { false, true, false }
    }),
    L(3, new boolean[][] {
            { true, true, true },
            { false, false, true },
            { false, false, true }
    }),
    I(4, new boolean[][] {
            { true },
            { true },
            { true },
            { true }
    }),
    SQUARE(5, new boolean[][] {
            { true, true },
            { true, true }
    });

    private static final Shape[] shapes = Arrays.stream(Shape.values())
            .sorted(Comparator.comparingInt(shape -> shape.ordinal))
            .toArray(Shape[]::new);

    private final int ordinal;
    private final boolean[][] shape;

    Shape(final int ordinal, final boolean[][] shape) {
        this.ordinal = ordinal;
        this.shape = shape;
    }

    int getHeight() {
        return shape.length;
    }

    int getWidth() {
        return shape[0].length;
    }

    static Shape getShape(final int round) {
        return shapes[round % shapes.length];
    }

    static boolean collides(final Shape fst, final Point leftBottomFst, final Shape snd, final Point leftBottomSnd) {
        final Shape lower, upper;
        if (leftBottomFst.y < leftBottomSnd.y) {
            lower = fst;
            upper = snd;
        } else {
            lower = snd;
            upper = fst;
        }
        final int yDist = Math.abs(leftBottomFst.y - leftBottomSnd.y);

        final Shape left, right;
        if (leftBottomFst.x < leftBottomSnd.x) {
            left = fst;
            right = snd;
        } else {
            left = snd;
            right = fst;
        }
        final int xDist = Math.abs(leftBottomFst.x - leftBottomSnd.x);

        final boolean leftIsLower = leftBottomFst.x < leftBottomSnd.x == leftBottomFst.y < leftBottomSnd.y;

        for (int y = 0; y < Math.min(lower.getHeight() - yDist, upper.getHeight()); y++) {
            final int lowerY = y + yDist;
            for (int x = 0; x < Math.min(left.getWidth() - xDist, right.getWidth()); x++) {
                final int leftX = x + xDist;
                if (leftIsLower) {
                    if (lower.shape[lowerY][leftX] && upper.shape[y][x]) {
                        return true;
                    }
                } else if (right.shape[lowerY][x] && left.shape[y][leftX]) {
                    return true;
                }
            }
        }

        return false;
    }
}
