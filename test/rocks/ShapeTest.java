package rocks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class ShapeTest {

    private static Set<Point> getMinus(final int x, final int y) {
        return Set.of(
                new Point(x, y),
                new Point(x + 1, y),
                new Point(x + 2, y),
                new Point(x + 3, y)
        );
    }

    private static Set<Point> getPlus(final int x, final int y) {
        return Set.of(
                new Point(x + 1, y),
                new Point(x, y + 1),
                new Point(x + 1, y + 1),
                new Point(x + 2, y + 1),
                new Point(x + 1, y + 2)
        );
    }

     private static Set<Point> getL(final int x, final int y) {
        return Set.of(
                new Point(x, y),
                new Point(x + 1, y),
                new Point(x + 2, y),
                new Point(x + 2, y + 1),
                new Point(x + 2, y + 2)
        );
    }

     private static Set<Point> getI(final int x, final int y) {
        return Set.of(
                new Point(x, y),
                new Point(x, y + 1),
                new Point(x, y + 2),
                new Point(x, y + 3)
        );
    }

     private static Set<Point> getSquare(final int x, final int y) {
        return Set.of(
                new Point(x, y),
                new Point(x + 1, y),
                new Point(x, y + 1),
                new Point(x + 1, y + 1)
        );
    }

    private static Set<Point> getAppropriateSet(final Shape shape, final int x, final int y) {
        return switch (shape) {
            case MINUS -> getMinus(x, y);
            case PLUS -> getPlus(x, y);
            case L -> getL(x, y);
            case I -> getI(x, y);
            case SQUARE -> getSquare(x, y);
        };
    }

    private static void test(final Shape s1, final Shape s2) {
        final Point p1 = new Point();
        final Point p2 = new Point();

        final Set<Point> sh1 = getAppropriateSet(s1, 0, 0);

        for (int x = -20; x <= 20; x++) {
            for (int y = -20; y <= 20; y++) {
                final Set<Point> sh2 = new HashSet<>(getAppropriateSet(s2, x, y));

                p2.move(x, y);
                Assertions.assertEquals(sh2.removeAll(sh1), Shape.collides(s1, p1, s2, p2));
            }
        }
    }

    @Test
    void minusMinus() {
        test(Shape.MINUS, Shape.MINUS);
    }

    @Test
    void minusPlus() {
        test(Shape.MINUS, Shape.PLUS);
    }

    @Test
    void minusL() {
        test(Shape.MINUS, Shape.L);
    }

    @Test
    void minusI() {
        test(Shape.MINUS, Shape.I);
    }

    @Test
    void minusSquare() {
        test(Shape.MINUS, Shape.SQUARE);
    }

    @Test
    void plusPlus() {
        test(Shape.PLUS, Shape.PLUS);
    }

    @Test
    void plusL() {
        test(Shape.PLUS, Shape.L);
    }

    @Test
    void plusI() {
        test(Shape.PLUS, Shape.I);
    }

    @Test
    void plusSquare() {
        test(Shape.PLUS, Shape.SQUARE);
    }

    @Test
    void lL() {
        test(Shape.L, Shape.L);
    }

    @Test
    void lI() {
        test(Shape.L, Shape.I);
    }

    @Test
    void lSquare() {
        test(Shape.L, Shape.SQUARE);
    }

    @Test
    void iI() {
        test(Shape.I, Shape.I);
    }

    @Test
    void iSquare() {
        test(Shape.I, Shape.SQUARE);
    }

    @Test
    void squareSquare() {
        test(Shape.SQUARE, Shape.SQUARE);
    }
}