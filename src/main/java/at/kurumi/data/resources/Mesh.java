package at.kurumi.data.resources;

/**
 * <h2>Represents a single mesh</h2>
 * <p>
 * Stores the OpenGL Vertex Array Object (VAO) id, containing all the vertices, indices, UVs, etc for this mesh.
 * </p>
 *
 * @see at.kurumi.data.managers.Meshes
 */
public class Mesh implements IManagedResource {

    private final int vaoId;
    private final int vertexCount;
    private final int[] vertexBufferObjectIDs;

    public Mesh(int vaoId, int vertexCount, int... vertexBufferObjectIDs) {
        this.vaoId = vaoId;
        this.vertexCount = vertexCount;
        this.vertexBufferObjectIDs = vertexBufferObjectIDs;
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int[] getVertexBufferObjectIDs() {
        return vertexBufferObjectIDs;
    }

    @Override
    public void dispose() {
        // TODO implement Mesh dispose code
    }
}
