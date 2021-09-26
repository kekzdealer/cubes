package at.kurumi.graphics;

import at.kurumi.util.GraphicsException;
import org.joml.Matrix4fc;
import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.joml.Vector4fc;
import org.lwjgl.opengl.GL20C;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class ShaderProgram {

    private static final String SHADER_LOC = "/shader/";

    private final int programId;

    protected ShaderProgram(String vertexFile, String fragmentFile) throws GraphicsException, IOException {
        programId = buildShaderProgram("v_" + vertexFile, "f_" + fragmentFile);
        getAllUniformLocations();
    }

    private int buildShaderProgram(String vShader, String fShader) throws GraphicsException, IOException {
        // Load and create shader
        final var vShaderId = createShader(vShader, GL20C.GL_VERTEX_SHADER);
        final var fShaderId = createShader(fShader, GL20C.GL_FRAGMENT_SHADER);
        // Link shader
        final var shaderProgramId = GL20C.glCreateProgram();
        GL20C.glAttachShader(shaderProgramId, vShaderId);
        GL20C.glAttachShader(shaderProgramId, fShaderId);

        bindAttributes();

        GL20C.glLinkProgram(shaderProgramId);
        if (GL20C.glGetProgrami(shaderProgramId, GL20C.GL_LINK_STATUS) != 1) {
            System.err.println(GL20C.glGetProgramInfoLog(shaderProgramId));
            System.exit(1);
        }
        GL20C.glDeleteShader(vShaderId);
        GL20C.glDeleteShader(fShaderId);
        return shaderProgramId;
    }

    private String readShaderFile(String fileName) throws IOException {
        final var shaderLoc = String.format("%s%s", SHADER_LOC, fileName);
        try (final var is = ShaderProgram.class.getResourceAsStream(shaderLoc)) {
            if(is == null) {
                throw new IOException();
            }
            /*
            By far the fastest way of reading all kinds of String lengths according to
            https://stackoverflow.com/a/35446009
             */
            final var baos = new ByteArrayOutputStream();
            final var buffer = new byte[1024];
            for(int length; (length = is.read(buffer)) != -1;) {
                baos.write(buffer, 0, length);
            }

            return baos.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IOException("Error while reading shader file: " + fileName);
        }
    }

    /**
     * Interface with OpenGL to create and load a shader from its shader source.
     * <p>
     * Valid shader types are:
     *     <ul>
     *         <li>{@link GL20C#GL_VERTEX_SHADER}</li>
     *         <li>{@link GL20C#GL_FRAGMENT_SHADER}</li>
     *     </ul>
     * </p>
     *
     * @param shaderFile shader source code
     * @param shaderType GL flag specifying shader type
     * @return handle of the shader program
     * @throws IOException       thrown if an error occurred while reading the shader
     * @throws GraphicsException thrown if shader program creation fails
     */
    private int createShader(String shaderFile, int shaderType) throws GraphicsException, IOException {
        final var shaderId = GL20C.glCreateShader(shaderType);
        GL20C.glShaderSource(shaderId, readShaderFile(shaderFile));
        GL20C.glCompileShader(shaderId);
        if (GL20C.glGetShaderi(shaderId, GL20C.GL_COMPILE_STATUS) != 1) {
            throw new GraphicsException(GL20C.glGetShaderInfoLog(shaderId));
        }
        return shaderId;
    }

    public void deleteShader() {
        GL20C.glDeleteProgram(programId);
    }

    protected abstract void bindAttributes();

    protected abstract void getAllUniformLocations();

    protected int getUniformLocation(String uniformName) {
        return GL20C.glGetUniformLocation(programId, uniformName);
    }

    protected void bindAttribute(int attribute, String variableName) {
        GL20C.glBindAttribLocation(programId, attribute, variableName);
    }

    public void start() {
        GL20C.glUseProgram(programId);
    }

    public void stop() {
        GL20C.glUseProgram(0);
    }

    protected void loadInt(int location, int value) {
        GL20C.glUniform1i(location, value);
    }

    protected void loadFloat(int location, float value) {
        GL20C.glUniform1f(location, value);
    }

    protected void loadVector2f(int location, Vector2fc vector) {
        GL20C.glUniform2f(location, vector.x(), vector.y());
    }

    protected void loadVector3f(int location, Vector3fc vector) {
        GL20C.glUniform3f(location, vector.x(), vector.y(), vector.z());
    }

    protected void loadVector4f(int location, Vector4fc vector) {
        GL20C.glUniform4f(location, vector.x(), vector.y(), vector.z(), vector.w());
    }

    protected void loadMatrix4f(int location, Matrix4fc matrix) {
        final float[] matrixBuffer4f = new float[16];
        matrix.get(matrixBuffer4f);
        GL20C.glUniformMatrix4fv(location, false, matrixBuffer4f);
    }

}
