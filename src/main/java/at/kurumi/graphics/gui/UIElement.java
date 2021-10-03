package at.kurumi.graphics.gui;

import at.kurumi.data.managers.GUI;

public interface UIElement {

    /**
     * Render the UI element
     */
    void render();

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
