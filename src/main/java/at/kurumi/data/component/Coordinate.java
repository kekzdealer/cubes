package at.kurumi.data.component;

public final class Coordinate {

    private final int x;
    private final int y;
    private final int z;

    public Coordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Coordinate)) {
            return false;
        } else {
            final Coordinate c = (Coordinate) o;
            return c.getX() == x && c.getY() == y && c.getZ() == z;
        }
    }

    public boolean equals(int x, int y, int z) {
        return this.x == x && this.y == y && this.z == z;
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
