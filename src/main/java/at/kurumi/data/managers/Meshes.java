package at.kurumi.data.managers;

import at.kurumi.data.resources.Mesh;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager class for {@link at.kurumi.data.resources.Mesh} objects.
 *
 * @see Mesh
 */
public class Meshes {

    public static final String DEFAULT_MESH_NAME = "default";

    private final Map<String, Mesh> meshes = new HashMap<>();
}
