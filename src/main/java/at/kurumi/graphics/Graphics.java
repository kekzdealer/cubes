package at.kurumi.graphics;

import at.kurumi.data.managers.*;
import at.kurumi.data.providers.MeshLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11C;

import static at.kurumi.ClientStart.DEFAULT_STRING;

/**
 * Home for the Display and rendering thread.
 */
public class Graphics {

    private static final Logger LOG = LogManager.getLogger("Graphics");

    private final Display display;

    private final Meshes meshes;
    private final Shaders shaders;
    private final Textures textures;
    private final Materials materials;

    private final GUI gui;

    public Graphics(Display display) throws GraphicsException {
        this.display = display;

        meshes = new Meshes(new MeshLoader());
        shaders = new Shaders();
        textures = new Textures();
        materials = new Materials(textures);

        gui = new GUI(meshes, materials, shaders);
        gui.registerHealthbarProvider(DEFAULT_STRING, HealthBar.class);
        gui.activateHealthBar(DEFAULT_STRING);
    }

    public void close() {
        meshes.close();
        shaders.close();
        textures.close();
        materials.close();
        gui.close();
    }

    public void render() {
        if(display.isResized()) {
            GL11C.glViewport(0, 0, display.getWidth(), display.getHeight());
            display.setResized(false);
        }

        setWorldRenderState();

        // Prepare
        GL11C.glClearColor(0.2f, 0.4f, 0.6f, 1.0f);
        GL11C.glClear(GL11C.GL_COLOR_BUFFER_BIT | GL11C.GL_DEPTH_BUFFER_BIT);

        // GUI
        gui.renderHealthBar();
    }

    public void setWorldRenderState() {
        // Back-face culling
        GL11C.glEnable(GL11C.GL_CULL_FACE);
        GL11C.glCullFace(GL11C.GL_BACK);
        // Depth-Testing
        GL11C.glEnable(GL11C.GL_DEPTH_TEST);
        GL11C.glDepthMask(true);
    }

}
