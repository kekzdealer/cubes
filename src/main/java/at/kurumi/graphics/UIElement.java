package at.kurumi.graphics;

import at.kurumi.data.managers.GUI;
import at.kurumi.data.resources.Material;
import at.kurumi.data.resources.Mesh;
import at.kurumi.data.resources.Shader;
import org.joml.Vector2fc;

public interface UIElement {

    /**
     * Must return the {@link Mesh} that the UI element should render on.
     *
     * @return target mesh
     */
    Mesh getMesh();

    /**
     * Must return the {@link Material} that should be rendered onto the target {@link Mesh}.
     *
     * @return material to render
     */
    Material getMaterial();

    /**
     * Must return the {@link Shader} that should be used to render the UI element.
     *
     * @return the shader program
     */
    Shader getShader();

    /**
     * Get the position of this UI element in clip space coordinates.
     *
     * @return x and y coordinates between {@code -1.0} and {1.0}
     */
    Vector2fc getPosition();

    /**
     * Cleanup method for unloading and destroying this UI element provider.
     * <p>
     *     Should probably not be the primary method for closing or hiding an UI element. This is for actually unloading
     *     the entire thing from memory and will be called on application shutdown.
     * </p>
     *
     * @see GUI#close()
     */
    void destroyUIElement();
}
