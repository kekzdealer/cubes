package at.kurumi.data.providers;

import at.kurumi.data.resources.Mesh;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL15C;
import org.lwjgl.opengl.GL20C;
import org.lwjgl.opengl.GL30C;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

/**
 * Provider class for {@link Mesh} objects.
 *
 * @see Mesh
 * @see at.kurumi.data.managers.Meshes
 */
public class MeshLoader {

    private Mesh createQuad() {
        // init data
        final float[] vertices = {
                // Bottom left triangle
                0.0f, 1.0f, 0.0f,// l b
                1.0f, 1.0f, 0.0f, // r b
                0.0f, 0.0f, 0.0f,// l t
                // Top right triangle
                1.0f, 0.0f, 0.0f,// r t
                0.0f, 0.0f, 0.0f,// l t
                1.0f, 1.0f, 0.0f// r b
        };
        // Prime VAO with texture coordinates
        final float[] uvs = {
                // Bottom left triangle
                0.0f, 1.0f,
                1.0f, 1.0f,
                0.0f, 0.0f,
                // Top right triangle
                1.0f, 0.0f,
                0.0f, 0.0f,
                1.0f, 1.0f,
        };
        // Store in FloatBuffer
        final var vertexFloatBuffer = MemoryUtil.memAllocFloat(vertices.length);
        vertexFloatBuffer.put(vertices);
        vertexFloatBuffer.flip();
        final var uvFloatBuffer = MemoryUtil.memAllocFloat(uvs.length);
        uvFloatBuffer.put(uvs);
        uvFloatBuffer.flip();

        final int vaoId = GL30C.glGenVertexArrays();
        final int vertexVBOId = 0;
        final int uvVBOId = 1;
        GL30C.glBindVertexArray(vaoId);
        storeDataInAttributeList(vertexVBOId, 3, vertexFloatBuffer);
        storeDataInAttributeList(uvVBOId, 2, uvFloatBuffer);
        GL30C.glBindVertexArray(0);

        return new Mesh(vaoId, vertices.length / 3, vertexVBOId, uvVBOId);
    }

    private Mesh createCube() {
        final float[] vertices = {
        /*      Lower four, clockwise */
        /*0*/   0.0f, 0.0f, 0.0f,
        /*1*/   0.0f, 0.0f, -1,0f,
        /*2*/   1.0f, 0.0f, -1.0f,
        /*3*/   1.0f, 0.0f, 0.0f,
        /*      Upper four, clockwise */
        /*4*/   0.0f, 1.0f, 0.0f,
        /*5*/   0.0f, 1.0f, -1.0f,
        /*6*/   1.0f, 1.0f, -1.0f,
        /*7*/   1.0f, 1.0f, 0.0f
        };
        final int[] indices = {
                0, 1, 3, // Down (0)
                2, 3, 1,
                4, 7, 5, // Up (1)
                6, 5, 7,
                2, 1, 6, // North (2)
                5, 6, 2,
                3, 2, 7, // East (3)
                6, 7, 2,
                0, 3, 4, // South (4)
                7, 4, 3,
                1, 0, 5, // West (5)
                4, 5, 0
        };
        final float[] uvs = {
                1.0f, 1.0f, // Down (0)
                1.0f, 0.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
        /* Sets of 6 repeat for each face
            except the bottom one */
                0.0f, 1.0f, // Up (1)
                1.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f, // North (2)
                1.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f, // East (3)
                1.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f, // South (4)
                1.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f, // West (5)
                1.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 0.0f,
                1.0f, 1.0f,
        };

        // Store in FloatBuffer, outside the heap in native memory
        final var vertexFloatBuffer = MemoryUtil.memAllocFloat(vertices.length);
        vertexFloatBuffer.put(vertices);
        vertexFloatBuffer.flip();
        final var indexFloatBuffer = MemoryUtil.memAllocInt(indices.length);
        indexFloatBuffer.put(indices);
        indexFloatBuffer.flip();
        final var uvFloatBuffer = MemoryUtil.memAllocFloat(uvs.length);
        uvFloatBuffer.put(uvs);
        uvFloatBuffer.flip();

        // Create VAO, fill some EBOs and put them in the VAO
        final int vaoId = GL30C.glGenVertexArrays();
        final int vertexVBOId = 0;
        final int uvVBOId = 1;
        GL30C.glBindVertexArray(vaoId);
        storeDataInAttributeList(vertexVBOId, 3, vertexFloatBuffer);
//        storeDataInAttributeList(uvVBOId, 2, uvFloatBuffer);
        storeDataInAttributeList(uvVBOId, 2, uvFloatBuffer);
        GL30C.glBindVertexArray(0);

        return new Mesh(vaoId, vertices.length / 3, vertexVBOId, uvVBOId);
    }

    private void storeDataInAttributeList(int attributeID, int dimensions, FloatBuffer data) {
        final int vboID = GL15C.glGenBuffers();
        GL15C.glBindBuffer(GL15C.GL_ARRAY_BUFFER, vboID);
        GL15C.glBufferData(GL15C.GL_ARRAY_BUFFER, data, GL15C.GL_STATIC_DRAW);
        GL20C.glVertexAttribPointer(attributeID, dimensions, GL11C.GL_FLOAT, false, 0, 0);
        GL15C.glBindBuffer(GL15C.GL_ARRAY_BUFFER, 0);
    }
}
