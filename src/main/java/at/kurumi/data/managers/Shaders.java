package at.kurumi.data.managers;

import at.kurumi.data.resources.ShaderProgram;
import at.kurumi.graphics.DefaultShader;
import at.kurumi.graphics.GraphicsException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager class for {@link ShaderProgram} implementations.
 *
 * @see ShaderProgram
 */
public class Shaders {

    public static final String DEFAULT_SHADER_NAME = "default";

    private final Map<String, ShaderProgram> shaderPrograms = new HashMap<>();

    public Shaders() throws GraphicsException {
        registerShader(DEFAULT_SHADER_NAME, DefaultShader.class);
    }

    public void registerShader(String name, Class<? extends ShaderProgram> shaderClass) throws GraphicsException {
        try {
            final var shaderProgram = shaderClass.getConstructor().newInstance();
            shaderPrograms.put(name, shaderProgram);
        } catch (InvocationTargetException e) {
            throw new GraphicsException("Failed to call shader program implementation constructor");
        } catch (InstantiationException | IllegalAccessException e) {
            throw new GraphicsException("Could not access shader program implementation constructor");
        } catch (NoSuchMethodException e) {
            throw new GraphicsException("Could not find shader program implementation default constructor");
        }
    }

    public void dispose() {
        shaderPrograms.values().forEach(ShaderProgram::deleteShader);
    }

    /**
     * Returns the specified shader program, or the default shader program if the specified shader program is
     * not registered.
     *
     * @param name shader program name
     * @return specified shader program
     */
    public ShaderProgram getShaderProgram(String name) {
        return shaderPrograms.getOrDefault(name, shaderPrograms.get(DEFAULT_SHADER_NAME));
    }
}
