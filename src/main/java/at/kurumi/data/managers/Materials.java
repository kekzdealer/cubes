package at.kurumi.data.managers;

import at.kurumi.data.resources.Material;
import at.kurumi.data.resources.Shader;
import at.kurumi.data.resources.Texture;
import at.kurumi.graphics.GraphicsException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static at.kurumi.ClientStart.DEFAULT_STRING;

/**
 * Manager class for {@link Material} objects.
 *
 * @see at.kurumi.data.resources.Material
 */
public class Materials extends AbstractManager<Material> {

    private final Shaders shaders;
    private final Textures textures;

    public Materials(Shaders shaders, Textures textures) throws GraphicsException {
        super(Materials.class.getSimpleName());
        this.shaders = shaders;
        this.textures = textures;

        // TODO register default material
    }

    public void registerMaterial(String name, Class<? extends Material> materialClass) {
        try {
            final var material = materialClass
                    .getConstructor(Shaders.class, Textures.class)
                    .newInstance(shaders, textures);
            resources.put(name, material);
        } catch (InvocationTargetException e) {
            super.LOG.error(e.getCause());
        } catch (InstantiationException | IllegalAccessException e) {
            super.LOG.error("Could not access {} constructor", materialClass.getName());
        } catch (NoSuchMethodException e) {
            super.LOG.error("Could not find {} default constructor", materialClass.getName());
        }
    }

    /**
     * Returns the specified material, or the default material if the specified name has no entry
     *
     * @param name material name
     * @return the material
     */
    public Material getMaterial(String name) {
        return resources.getOrDefault(name, resources.get(DEFAULT_STRING));
    }

}
