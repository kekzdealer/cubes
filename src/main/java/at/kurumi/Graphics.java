package at.kurumi;

import at.kurumi.data.entity.Shaders;
import at.kurumi.util.Display;
import at.kurumi.util.GraphicsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

/**
 * Home for the Display and rendering thread.
 */
public class Graphics implements Runnable {

    private static final Logger LOG = LogManager.getLogger("Graphics");

    private final Display display;

    private Runnable onStopCallable;
    private int targetFrameTime = 1000;

    public Graphics(int width, int height) {
        display = new Display(width, height, "Cubes")
                .setErrorPrint(System.err)
                .setOGLVersion(3, 3)
                .withCoreProfile()
                .setResizable(false)
                .setVisible(false)
                .setWindowPosition(320, 180)
                .finishSetup();
    }

    public void setOnStopCallback(Runnable onStopCallback) {
        this.onStopCallable = onStopCallback;
    }

    public void setTargetFps(int targetFps) {
        targetFrameTime = 1000 / targetFps;
    }

    @Override
    public void run() {
        try {
            final var shaderManager = new Shaders();

            doRenderLoop(shaderManager);
        } catch (GraphicsException e) {
            LOG.error("Terminating on graphics error: {}", e.getMessage());
            onStopCallable.run();
        }

    }

    public void doRenderLoop(Shaders shaders) {
        double frameStart;
        while(!Thread.interrupted()) {
            frameStart = GLFW.glfwGetTime();
            try {
                Thread.sleep((long) Math.max(0, (GLFW.glfwGetTime() - frameStart) - targetFrameTime));

                LOG.debug("hello render loop");

                display.submitFrame();
                if(GLFW.glfwWindowShouldClose(display.getDisplayPointer())) {
                    if(this.onStopCallable == null) {
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
