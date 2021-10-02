package at.kurumi.data.managers;

import at.kurumi.graphics.GraphicsException;
import at.kurumi.graphics.AbstractHealthBar;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static at.kurumi.ClientStart.DEFAULT_STRING;

/**
 * Manager class for all sorts of GUI element providing objects.
 */
public class GUI implements AutoCloseable {

    private final Meshes meshes;
    private final Materials materials;
    private final Shaders shaders;

    private final Map<String, AbstractHealthBar> healthBars = new HashMap<>();
    private AbstractHealthBar activeHealthbar;

    public GUI(Meshes meshes, Materials materials, Shaders shaders) {
        this.meshes = meshes;
        this.materials = materials;
        this.shaders = shaders;
    }

    public void registerHealthbarProvider(String name, Class<? extends AbstractHealthBar> providerClass) throws GraphicsException {
        try {
            final var provider = providerClass
                    .getConstructor(Meshes.class, Materials.class, Shaders.class)
                    .newInstance(meshes, materials, shaders);
            healthBars.put(name, provider);
        } catch (InvocationTargetException e) {
            // InvocationTargetExceptions wrap exceptions thrown by the method that was called
            throw new GraphicsException(e.getCause());
        } catch (InstantiationException | IllegalAccessException e) {
            final var msg = String
                    .format("Could not access %s constructor", providerClass.getName());
            throw new GraphicsException(msg);
        } catch (NoSuchMethodException e) {
            final var msg = String
                    .format("Could not find %s constructor", providerClass.getName());
            throw new GraphicsException(msg);
        }
    }

    public void activateHealthBar(String name) {
        activeHealthbar = healthBars.getOrDefault(name, healthBars.get(DEFAULT_STRING));
    }

    public void renderHealthBar() {
        activeHealthbar.render();
    }

    @Override
    public void close() {
        healthBars.values().forEach(AbstractHealthBar::destroyUIElement);
    }
}
