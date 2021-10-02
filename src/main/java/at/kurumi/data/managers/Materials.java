package at.kurumi.data.managers;

import at.kurumi.data.resources.Material;
import at.kurumi.graphics.GraphicsException;

import static at.kurumi.ClientStart.DEFAULT_STRING;

/**
 * Manager class for {@link Material} objects.
 *
 * @see at.kurumi.data.resources.Material
 */
public class Materials extends AbstractManager<Material> {

    private final Textures textures;

    public Materials(Textures textures) throws GraphicsException {
        super(Materials.class.getSimpleName());
        this.textures = textures;

        resources.put(DEFAULT_STRING, new Material(textures.getTexture(DEFAULT_STRING)));
    }

    /**
     * Get the {@link Material} object for a material name. Load it if it isn't yet.
     *
     * @param name the material name
     * @return {@link Material} object
     */
    public Material getMaterial(String name) {
        if(resources.containsKey(name)){
           return resources.get(name);
        }
        final var material = new Material(textures.getTexture(name));
        resources.put(name, material);
        return material;
    }

}
