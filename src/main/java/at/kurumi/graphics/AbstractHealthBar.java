package at.kurumi.graphics;

import at.kurumi.data.managers.Materials;
import at.kurumi.data.managers.Meshes;
import at.kurumi.data.managers.Shaders;

/**
 * Interface for a UI Element providing a health bar.
 */
public abstract class AbstractHealthBar implements UIElement {

    protected final Meshes meshes;
    protected final Materials materials;
    protected final Shaders shaders;

    protected AbstractHealthBar(Meshes meshes, Materials materials, Shaders shaders) {
        this.meshes = meshes;
        this.materials = materials;
        this.shaders = shaders;
    }

    /**
     * Set the health bar fill level as percentage.
     *
     * @param value value between {@code 0.0} and {@code 1.0}
     */
    public abstract void setHealth(float value);

}
