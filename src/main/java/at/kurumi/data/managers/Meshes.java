package at.kurumi.data.managers;

import at.kurumi.data.providers.MeshLoader;
import at.kurumi.data.resources.Mesh;
import org.joml.Vector2f;
import org.joml.Vector2fc;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager class for {@link at.kurumi.data.resources.Mesh} objects.
 *
 * @see Mesh
 */
public class Meshes extends AbstractManager<Mesh> {

    public static final String QUAD_1X1 = "quad_1x1";
    public static final String CUBE = "cube";

    private final Map<String, Vector2fc> customQuads = new HashMap<>();
    private final MeshLoader meshLoader;

    public Meshes(MeshLoader meshLoader) {
        super(Meshes.class.getSimpleName());
        this.meshLoader = meshLoader;

        resources.put(QUAD_1X1, meshLoader.createQuad());
        resources.put(CUBE, meshLoader.createCube());
    }

    public void registerQuadWithDimensions(String name, float width, float height) {
        customQuads.put(name, new Vector2f(width, height));
    }

    public Mesh getQuad1x1() {
        return resources.get(QUAD_1X1);
    }

    public Mesh getCube() {
        return resources.get(CUBE);
    }

}
