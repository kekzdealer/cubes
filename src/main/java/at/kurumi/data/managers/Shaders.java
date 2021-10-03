package at.kurumi.data.managers;

import at.kurumi.data.resources.Shader;
import at.kurumi.graphics.DefaultShader;
import at.kurumi.graphics.GraphicsException;

import java.lang.reflect.InvocationTargetException;

import static at.kurumi.ClientStart.DEFAULT_STRING;

/**
 * Manager class for {@link Shader} implementations.
 *
 * @see Shader
 */
public class Shaders extends AbstractManager<Shader> {

    public Shaders() throws GraphicsException {
        super(Shaders.class.getSimpleName());

        registerShader(DEFAULT_STRING, DefaultShader.class);
        if(resources.get(DEFAULT_STRING) == null) {
            throw new GraphicsException("Failed to load default Shader");
        }
    }

    public void registerShader(String name, Class<? extends Shader> shaderClass) {
        try {
            final var shaderProgram = shaderClass.getConstructor().newInstance();
            resources.put(name, shaderProgram);
        } catch (InvocationTargetException e) {
            super.LOG.error(e.getCause());
        } catch (InstantiationException | IllegalAccessException e) {
            super.LOG.error("Could not access {} constructor", shaderClass.getName());
        } catch (NoSuchMethodException e) {
            super.LOG.error("Could not find {} default constructor", shaderClass.getName());
        }
    }

    /**
     * Returns the specified shader, or the default shader if the specified shader has no entry.
     *
     * @param name shader name
     * @return the shader
     */
    public Shader getShader(String name) {
        return resources.getOrDefault(name, resources.get(DEFAULT_STRING));
    }

}
