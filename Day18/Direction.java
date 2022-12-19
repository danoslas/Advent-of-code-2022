public enum Direction {

    UP(0, 1, 0),
    DOWN(0, -1, 0),
    RIGHT(1, 0, 0),
    LEFT(-1, 0, 0),
    FRONT(0, 0, 1),
    BACK(0, 0, -1);

    private final int x, y, z;

    Direction(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
