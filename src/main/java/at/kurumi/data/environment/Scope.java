package at.kurumi.data.environment;

import at.kurumi.data.component.Coordinate;

/**
 * Used to limit World Data Provider interactions to a specified area.
 */
public final class Scope {

    private final int x;
    private final int y;
    private final int z;
    private final int radius;

    public Scope(int x, int y, int z, int radius) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
    }

    public boolean contains(int x, int y, int z) {
        return (x * x - this.x * this.x < 0) && (y * y - this.y * this.y < 0) && (z * z - this.z * this.z < 0);
    }

    public boolean contains(Coordinate coordinate) {
        return contains(coordinate.getX(), coordinate.getY(), coordinate.getZ());
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

    public int getRadius() {
        return radius;
    }
}
