package at.kurumi.graphics;

import at.kurumi.data.managers.*;
import at.kurumi.data.providers.MeshLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

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

            doRenderLoop();

        } catch (GraphicsException e) {
            LOG.error("Terminating on graphics error: {}", e.getMessage());
            onStopCallable.run();
        }

    }

    public void doRenderLoop() {
        double frameStart;
        while (!Thread.interrupted()) {
            frameStart = GLFW.glfwGetTime();
            try {
                Thread.sleep((long) Math.max(0, (GLFW.glfwGetTime() - frameStart) - targetFrameTime));

                LOG.debug("hello render loop");

                display.submitFrame();
                if (GLFW.glfwWindowShouldClose(display.getDisplayPointer())) {
                    if (this.onStopCallable == null) {
                        throw new IllegalStateException("onStop procedure has not been set");
                    }
                    onStopCallable.run();
                }
            } catch (InterruptedException e) {
                LOG.info("Graphics thread interrupted. Executing onStop procedure");
            }
        }
    }

    public void destroy() {
        display.destroy();
    }

}
