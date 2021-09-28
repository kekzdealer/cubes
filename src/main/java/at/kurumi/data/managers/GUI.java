package at.kurumi.data.managers;

import at.kurumi.graphics.GraphicsException;
import at.kurumi.graphics.IHealthbar;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager class for all sorts of GUI element providing objects.
 */
public class GUI {

    private final Map<String, ? extends IHealthbar> healthbars = new HashMap<>();

    public void registerHealthbarProvider(String name, Class<? extends IHealthbar> providerClass) throws GraphicsException {
        try {
            final var provider = providerClass.getConstructor().newInstance();
        } catch (InvocationTargetException e) {
            // InvocationTargetExceptions wrap exceptions thrown by the method that was called
            throw new GraphicsException(e.getCause());
        } catch (InstantiationException | IllegalAccessException e) {
            final var msg = String
                    .format("Could not access %s constructor", name);
            throw new GraphicsException(msg);
        } catch (NoSuchMethodException e) {
            final var msg = String
                    .format("Could not find %s default constructor", name);
            throw new GraphicsException(msg);
        }
    }

    /**
     * Cleanup method for unloading and destroying all registered GUI element providers.
     */
    public void dispose() {
        healthbars.values().forEach(IHealthbar::destroyUIElement);
    }
}
