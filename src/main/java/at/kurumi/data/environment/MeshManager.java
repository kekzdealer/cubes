package at.kurumi.data.environment;

public class MeshManager {

    private static final float[] cubeVertices = {
            0.0f, 0.0f, 0.0f, // lower four, clockwise
            0.0f, 0.0f, -1,0f,
            1.0f, 0.0f, -1.0f,
            1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, // upper four, clockwise
            0.0f, 1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, 0.0f
    };

    private static final int[] cubeIndices = {
            0, 2, 1, // down (0)
            0, 3, 2,
            4, 6, 5, // up (1)
            4, 7, 6,
            2, 5, 6, // north (2)
            2, 1, 5,
            3, 6, 7, // east (3)
            3, 2, 6,
            0, 7, 4, // south (4)
            0, 3, 7,
            1, 4, 5, // west (5)
            1, 0, 4
    };

}
