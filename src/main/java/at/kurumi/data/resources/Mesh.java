package at.kurumi.data.resources;

import org.lwjgl.opengl.GL15C;
import org.lwjgl.opengl.GL20C;
import org.lwjgl.opengl.GL30C;

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

    /**
     * Binds the Mesh's VAO and activates all the necessary VBOs/EBOs.
     */
    public void bind() {
        GL30C.glBindVertexArray(vaoId);
        for(int i = 0; i < vertexBufferObjectIDs.length; i++) {
            GL20C.glEnableVertexAttribArray(i);
        }
    }

    /**
     * Unbinds the Mesh's VAO and deactivates all the necessary VBOs/EBOs.
     */
    public void unbind() {
        for(int i = vertexBufferObjectIDs.length - 1; i >= 0; i--) {
            GL20C.glDisableVertexAttribArray(i);
        }
        GL30C.glBindVertexArray(vaoId);
    }

    @Override
    public void dispose() {
        for(int vboId : vertexBufferObjectIDs) {
            GL30C.glDisableVertexAttribArray(vboId);
            GL20C.glBindBuffer(GL20C.GL_ARRAY_BUFFER, 0);
            GL15C.glDeleteBuffers(vboId);
        }
        GL30C.glBindVertexArray(0);
        GL30C.glDeleteVertexArrays(vaoId);
    }
}
