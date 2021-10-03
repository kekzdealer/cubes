package at.kurumi.data.environment.worldprovider;

public class Chunk {

    private final Cube[][][] cubes;

    private boolean isActive = false;

    public Chunk(int edgeLength) {
        cubes = new Cube[edgeLength][edgeLength][edgeLength];
    }

    public Cube getCubeAt(int x, int y, int z) {
        return cubes[x][y][z];
    }

    public boolean addCubeAt(int x, int y, int z, Cube cube) {
        if(getCubeAt(x, y, z) == null) {
            cubes[x][y][z] = cube;
            return true;
        }
        return false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void activate() {
        // load
    }

    public void deactivate() {
        // save
    }
}
