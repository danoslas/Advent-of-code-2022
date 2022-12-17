package rocks;

import java.awt.*;

public class Rock {

    private final Shape shape;
    private final Point leftBottomPoint;

    Rock(final Shape shape, final Point leftBottomPoint) {
        this.shape = shape;
        this.leftBottomPoint = leftBottomPoint;
    }

    public int getLeftX() {
        return leftBottomPoint.x;
    }

    public int getRightX() {
        return leftBottomPoint.x + shape.getWidth() - 1;
    }

    public int getBottomY() {
        return leftBottomPoint.y;
    }

    public int getTopY() {
        return leftBottomPoint.y + shape.getHeight() - 1;
    }

    public void move(final Movement movement) {
        leftBottomPoint.move(leftBottomPoint.x + movement.getDiffX(), leftBottomPoint.y + movement.getDiffY());
    }

    public boolean isMovementCollision(final Rock rock, final Movement movement) {
        final Point newPosition = new Point(leftBottomPoint.x + movement.getDiffX(), leftBottomPoint.y + movement.getDiffY());

        return Shape.collides(shape, newPosition, rock.shape, rock.leftBottomPoint);
    }
}
