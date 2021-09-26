package at.kurumi.data.managers;

import at.kurumi.data.resources.Material;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager class for {@link Material} objects.
 *
 * @see at.kurumi.data.resources.Material
 */
public class Materials {

    public static String DEFAULT_MATERIAL_NAME = "default";

    public final Map<String, Material> materials = new HashMap<>();
}
