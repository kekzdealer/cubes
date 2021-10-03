package at.kurumi.graphics;

import at.kurumi.data.managers.*;
import at.kurumi.data.providers.MeshLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11C;

import static at.kurumi.ClientStart.DEFAULT_STRING;

/**
 * Home for the Display and rendering thread.
 */
public class Graphics implements Runnable {

    private static final Logger LOG = LogManager.getLogger("Graphics");

    private final int displayWidth;
    private final int displayHeight;
    private Display display;

    private Runnable onStopCallable;
    private int targetFrameTime = 1000;

    public Graphics(int displayWidth, int displayHeight) {
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
    }

    public void setOnStopCallback(Runnable onStopCallback) {
        this.onStopCallable = onStopCallback;
    }

    public void setTargetFps(int targetFps) {
        targetFrameTime = 1000 / targetFps;
    }

    @Override
    public void run() {
        display = new Display(displayWidth, displayHeight, "Cubes")
                .setErrorPrint(System.err)
                .setOGLVersion(3, 3)
                .withCoreProfile()
                .withForwardCompat()
                .setResizable(false)
                .setVisible(false)
                .setWindowPosition(320, 180)
                .finishSetup();

        try (final var meshes = new Meshes(new MeshLoader());
             final var shaders = new Shaders();
             final var textures = new Textures();
             final var materials = new Materials(textures);
             final var gui = new GUI(meshes, materials, shaders)) {

            gui.registerHealthbarProvider(DEFAULT_STRING, HealthBar.class);
            gui.activateHealthBar(DEFAULT_STRING);

            doRenderLoop(gui);

        } catch (GraphicsException e) {
            LOG.error("Terminating on graphics error: {}", e.getMessage());
            onStopCallable.run();
        }

    }

    public void doRenderLoop(GUI gui) {
        GL11C.glViewport(0, 0, displayHeight, displayWidth);
        // Back-face culling
        GL11C.glEnable(GL11C.GL_CULL_FACE);
        GL11C.glCullFace(GL11C.GL_BACK);
        // Depth-Testing
        GL11C.glEnable(GL11C.GL_DEPTH_TEST);
        GL11C.glDepthMask(true);

        double frameStart;
        while (!Thread.interrupted()) {
            // Prepare
            frameStart = GLFW.glfwGetTime();
            GLFW.glfwPollEvents();
            GL11C.glClearColor(0.2f, 0.4f, 0.6f, 1.0f);
            GL11C.glClear(GL11C.GL_COLOR_BUFFER_BIT | GL11C.GL_DEPTH_BUFFER_BIT);

            // GUI
            gui.renderHealthBar();

            // Swap frame buffer content to display
            display.submitFrame();

            // Check for window close event (x or ESC)
            if (GLFW.glfwWindowShouldClose(display.getDisplayPointer())) {
                if (this.onStopCallable == null) {
                    throw new IllegalStateException("onStop procedure has not been set");
                }
                onStopCallable.run();
            }

            // Manage loop speed
            try {
                final var frameTime = GLFW.glfwGetTime() - frameStart;
                LOG.info("Frame time: {}", frameTime);
                Thread.sleep((long) Math.max(0, targetFrameTime - frameTime));
            } catch (InterruptedException e) {
                LOG.info("Graphics thread interrupted. Executing onStop procedure");
            }
        }
    }

    public void destroy() {
        display.destroy();
    }

}
