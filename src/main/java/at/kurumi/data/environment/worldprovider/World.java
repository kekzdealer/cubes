package at.kurumi.data.environment.worldprovider;

public class World {

    public static final int CHUNK_EDGE_LENGTH = 8;
    public static final int WORLD_EDGE_LENGTH = 8;

    private final Chunk[][][] chunks;

    public World() {
        chunks = new Chunk[WORLD_EDGE_LENGTH][WORLD_EDGE_LENGTH][WORLD_EDGE_LENGTH];
    }

    /**
     * Generate a new chunk or return the existing one.
     *
     * @param x the x coordinate
     * @param y the y coorddinate
     * @param z the z coordinate
     * @return the Chunk
     */
    public Chunk genChunk(int x, int y, int z) {
        if(chunks[x][y][z] == null) {
            final var chunk = new Chunk(CHUNK_EDGE_LENGTH);
            chunks[x][y][z] = chunk;
            return chunk;
        }
        return chunks[x][y][z];
    }

    /**
     * Get the cube at the specified world coordinate.
     * <p>
     *     Returns an instance of the air cube if the cube or chunk is null.
     * </p>
     *
     * @param x the x coordinate
     * @param y the y coorddinate
     * @param z the z coordinate
     * @return the Cube
     */
    public Cube getCubeAt(int x, int y, int z) {
        final int cX = x % CHUNK_EDGE_LENGTH;
        final int cY = y % CHUNK_EDGE_LENGTH;
        final int cZ = z % CHUNK_EDGE_LENGTH;
        return chunks[x - cX][y - cY][z - cZ].getCubeAt(cX, cY, cZ);
    }

    public boolean addCubeAt(int x, int y, int z, Cube cube) {
        final int cX = x % CHUNK_EDGE_LENGTH;
        final int cY = y % CHUNK_EDGE_LENGTH;
        final int cZ = z % CHUNK_EDGE_LENGTH;
        final var chunk = genChunk(x - cX, y - cY, z - cZ);
        return chunk.addCubeAt(cX, cY, cZ, cube);
    }

}
