public class Tree {

    private final int height;
    private boolean spotted = false;

    public Tree(final int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void spot() {
        spotted = true;
    }

    public boolean isSpotted() {
        return spotted;
    }
}
