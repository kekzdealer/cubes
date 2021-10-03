package at.kurumi.data.resources;

import at.kurumi.graphics.GraphicsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Matrix4fc;
import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.joml.Vector4fc;
import org.lwjgl.opengl.GL20C;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <h2>Represents a single shader program</h2>
 * <p>
 * Shader programs can consist of multiple shader parts, but they have to
 * at least have a vertex and fragment shader.
 * </p>
 *
 * @see at.kurumi.data.managers.Shaders
 */
public abstract class Shader implements IManagedResource {

    private static final Logger LOG = LogManager.getLogger(Shader.class.getSimpleName());

    private static final String SHADER_LOC = "/shader/";
    private static final String VERTEX_SHADER_PREFIX = "v_";
    private static final String FRAGMENT_SHADER_PREFIX = "f_";
    private static final String SHADER_FILE_EXTENSION = ".glsl";

    private final int programId;

    /**
     * Load and build a new shader program. GLSL source files must have the prefix {@code v_} or {@code f_} for
     * their shader type in their file name as well as have {@code .glsl} as file extension.
     *
     * @param vertexFile   vertex shader resource name
     * @param fragmentFile fragment shader resource name
     * @throws IOException       thrown if an error occurred while reading the shader
     * @throws GraphicsException thrown if shader program creation fails
     */
    protected Shader(String vertexFile, String fragmentFile) throws GraphicsException, IOException {
        programId = buildShaderProgram(
                VERTEX_SHADER_PREFIX + vertexFile + SHADER_FILE_EXTENSION,
                FRAGMENT_SHADER_PREFIX + fragmentFile + SHADER_FILE_EXTENSION);
        getAllUniformLocations();
    }

    private int buildShaderProgram(String vShader, String fShader) throws GraphicsException, IOException {
        // Load and create shader
        final var vShaderId = createShader(vShader, GL20C.GL_VERTEX_SHADER);
        final var fShaderId = createShader(fShader, GL20C.GL_FRAGMENT_SHADER);
        // Link shader
        final var programId = GL20C.glCreateProgram();
        GL20C.glAttachShader(programId, vShaderId);
        GL20C.glAttachShader(programId, fShaderId);

        bindAttributes();

        GL20C.glLinkProgram(programId);
        if (GL20C.glGetProgrami(programId, GL20C.GL_LINK_STATUS) == 0) {
            throw new GraphicsException(GL20C.glGetProgramInfoLog(programId));
        }

        if(vShaderId != 0) {
            GL20C.glDetachShader(programId, vShaderId);
        }

        if(fShaderId != 0) {
            GL20C.glDetachShader(programId, fShaderId);
        }

        GL20C.glValidateProgram(programId);
        if(GL20C.glGetProgrami(programId, GL20C.GL_VALIDATE_STATUS) == 0) {
            LOG.error("Shader validation: {}", GL20C.glGetProgramInfoLog(programId));
        }

        return programId;
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
        if (shaderId == 0) {
            throw new GraphicsException("Error creating shader");
        }
        GL20C.glShaderSource(shaderId, readShaderFile(shaderFile));
        GL20C.glCompileShader(shaderId);
        if (GL20C.glGetShaderi(shaderId, GL20C.GL_COMPILE_STATUS) == 0) {
            throw new GraphicsException(GL20C.glGetShaderInfoLog(shaderId));
        }
        return shaderId;
    }

    private String readShaderFile(String fileName) throws IOException {
        final var shaderLoc = String.format("%s%s", SHADER_LOC, fileName);
        try (final var is = Shader.class.getResourceAsStream(shaderLoc)) {
            if (is == null) {
                throw new IOException();
            }
            /*
            By far the fastest way of reading all kinds of String lengths according to
            https://stackoverflow.com/a/35446009
             */
            final var baos = new ByteArrayOutputStream();
            final var buffer = new byte[1024];
            for (int length; (length = is.read(buffer)) != -1; ) {
                baos.write(buffer, 0, length);
            }

            return baos.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IOException("Error while reading shader file: " + fileName);
        }
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

    @Override
    public void dispose() {
        stop();
        if(programId != 0) {
            GL20C.glDeleteProgram(programId);
        }
    }
}
