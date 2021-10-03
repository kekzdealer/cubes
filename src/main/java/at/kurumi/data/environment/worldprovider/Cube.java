package at.kurumi.data.environment.worldprovider;

public class Cube {

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int NORTH = 2;
    public static final int EAST = 3;
    public static final int SOUTH = 4;
    public static final int WEST = 5;

    private final String[] materials;

    /**
     * Create a new cube object.
     *
     * @param materials array of Materials for each side
     */
    public Cube(String[] materials) {
        if(materials.length != 6) {
            throw new IllegalArgumentException("Insufficient materials or shaders for cube");
        }
        this.materials = materials;
    }

    public String[] getMaterials() {
        return materials;
    }

}
