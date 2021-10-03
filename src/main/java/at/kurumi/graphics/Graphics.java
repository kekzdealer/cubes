package at.kurumi.graphics;

import at.kurumi.data.environment.worldprovider.World;
import at.kurumi.data.managers.*;
import at.kurumi.data.providers.MeshLoader;
import at.kurumi.data.resources.Mesh;
import at.kurumi.graphics.gui.HealthBar;
import at.kurumi.graphics.world.CubeEnvironmentRenderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.lwjgl.opengl.GL11C;

import java.util.Arrays;

import static at.kurumi.ClientStart.DEFAULT_STRING;

/**
 * All things that render onto the {@link Display}.
 */
public class Graphics {

    private static final Logger LOG = LogManager.getLogger("Graphics");

    private final Display display;

    private final Meshes meshes;
    private final Shaders shaders;
    private final Textures textures;
    private final Materials materials;

    private final GUI gui;

    private final CubeEnvironmentRenderer worldRenderer;

    private float fieldOfView = (float) Math.toRadians(60.0);
    private float near = 0.01f;
    private float far = 100.0f;
    private Matrix4fc projection;

    public Graphics(Display display, Meshes meshes, Shaders shaders, Textures textures, Materials materials)
            throws GraphicsException {
        this.display = display;

        this.meshes = meshes;
        this.shaders = shaders;
        this.textures = textures;
        this.materials = materials;

        gui = new GUI(meshes, materials, shaders);
        gui.registerHealthbarProvider(DEFAULT_STRING, HealthBar.class);
        gui.activateHealthBar(DEFAULT_STRING);

        worldRenderer = new CubeEnvironmentRenderer();

        projection = computeProjection();
    }

    private Matrix4fc computeProjection() {
        final var aspectRatio = (float) (display.getWidth() / display.getHeight());
        return new Matrix4f().perspective(fieldOfView, aspectRatio, near, far);
    }

    public void close() {
        meshes.close();
        shaders.close();
        textures.close();
        materials.close();
        gui.close();
    }

    public void render(World world) {
        // Prepare
        GL11C.glClearColor(0.2f, 0.4f, 0.6f, 1.0f);
        GL11C.glClear(GL11C.GL_COLOR_BUFFER_BIT | GL11C.GL_DEPTH_BUFFER_BIT);
        if(display.isResized()) {
            GL11C.glViewport(0, 0, display.getWidth(), display.getHeight());
            projection = computeProjection();
            display.setResized(false);
        }

        worldRenderer.activate();

        final var quads = new Mesh[6];
        Arrays.fill(quads, meshes.getQuad1x1());
        worldRenderer.render(world, quads, materials, projection);

        // GUI
        gui.renderHealthBar();
    }


}
