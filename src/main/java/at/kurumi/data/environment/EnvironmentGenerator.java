package at.kurumi.data.environment;

public class EnvironmentGenerator {

    /**
     * Generates a flat, one cube thick plane.
     *
     * @param world  world to write to
     * @param x      center x coordinate
     * @param y      center y coordinate
     * @param z      center z coordinate
     * @param widthX width of the plane along the x-axis
     * @param widthZ width of the plane along the z-axis
     */
    public static void generatePlane(IWorld world, int x, int y, int z, int widthX, int widthZ) {
        final int halfWidthX = widthX / 2;
        final int halfWidthZ = widthZ / 2;
        for (int ix = x - halfWidthX; ix < halfWidthX; ix++) {
            for (int iz = z - halfWidthZ; iz < halfWidthZ; iz++) {
                world.addCubeAt(ix, y, iz);
            }
        }
    }

}
