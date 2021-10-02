package at.kurumi.data.resources;

/**
 * <h2>Represents a single material</h2>
 * <p>
 * Each material can consist of multiple textures. Examples texture types are: diffuse, normal, specular
 * </p>
 *
 * @see at.kurumi.data.managers.Materials
 */
public class Material implements IManagedResource {

    private final Texture diffuse;

    public Material(Texture diffuse) {
        this.diffuse = diffuse;
    }

    public Texture getDiffuse() {
        return diffuse;
    }

    @Override
    public void dispose() {
        // TODO implement Material dispose code
    }
}
