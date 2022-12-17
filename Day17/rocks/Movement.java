package rocks;

public enum Movement {

    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int diffX, diffY;

    Movement(final int diffX, final int diffY) {
        this.diffX = diffX;
        this.diffY = diffY;
    }

    public int getDiffX() {
        return diffX;
    }

    public int getDiffY() {
        return diffY;
    }
}
