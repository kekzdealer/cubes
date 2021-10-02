package at.kurumi.data.resources;

import at.kurumi.data.providers.TextureLoader;

/**
 * <h2>Represents a single texture</h2>
 * <p>
 * Stores the OpenGL texture bank id.
 * </p>
 *
 * @see at.kurumi.data.managers.Textures
 * @see TextureLoader
 */
public class Texture implements IManagedResource {

    private final int id;

    public Texture(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public void dispose() {
        // TODO implement Texture dispose code
    }
}
