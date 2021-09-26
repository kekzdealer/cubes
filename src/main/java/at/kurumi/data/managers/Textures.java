package at.kurumi.data.managers;

import at.kurumi.data.resources.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager class for {@link Texture} objects.
 *
 * @see Texture
 */
public class Textures {

    public static String DEFAULT_TEXTURE_NAME = "default";

    private final Map<String, Texture> textures = new HashMap<>();
}
