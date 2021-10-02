package at.kurumi.data.managers;

import at.kurumi.data.providers.TextureLoader;
import at.kurumi.data.resources.Texture;
import at.kurumi.graphics.GraphicsException;

import static at.kurumi.ClientStart.DEFAULT_STRING;

/**
 * Manager class for {@link Texture} objects.
 *
 * @see Texture
 */
public class Textures extends AbstractManager<Texture> {

    private static final String TEXTURE_LOCATION = "./src/main/resources/textures/";

    private final TextureLoader textureLoader;

    public Textures() {
        textureLoader = new TextureLoader();
        resources.put(DEFAULT_STRING, textureLoader.createFallbackTexture());
        // TODO verify default texture was created
    }

    /**
     * Get the {@link Texture} object for a texture name. Load it if it isn't yet.
     * <p>
     *     The texture name should be specified with no path and no file extension.
     * </p>
     *
     * @param name the texture name without file extension
     * @return {@link Texture} object
     */
    public Texture getTexture(String name) {
        if(resources.containsKey(name)) {
           return resources.get(name);
        }
        try {
            final var texture = textureLoader.loadTextureFromDisk(TEXTURE_LOCATION, name);
            resources.put(name, texture);
            return texture;
        } catch (GraphicsException e) {
            LOG.error("Failed to load texture: {}", e.getMessage());
            return resources.get(DEFAULT_STRING);
        }
    }

}
