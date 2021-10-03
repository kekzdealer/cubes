package at.kurumi.data.resources;

import at.kurumi.data.managers.Shaders;
import at.kurumi.data.managers.Textures;

/**
 * <h2>Represents a material</h2>
 * <p>
 * Each material has a {@link Shader} and one or more {@link Texture}s
 * </p>
 *
 * @see at.kurumi.data.managers.Materials
 */
public abstract class Material implements IManagedResource {

    protected final Shaders shaders;
    protected final Textures textures;

    protected Material(Shaders shaders, Textures textures) {
        this.shaders = shaders;
        this.textures = textures;
    }

    /**
     * Bind the Shader, activate textures, and upload any material-relevant shader parameters.
     */
    public abstract void bind();

    /**
     * Unbind the Shader, and deactivate textures.
     */
    public abstract void unbind();

}
