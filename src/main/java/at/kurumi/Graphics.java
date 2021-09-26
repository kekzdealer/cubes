package at.kurumi;

import at.kurumi.util.Display;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.util.function.Function;

/**
 * Home for the Display and rendering thread.
 */
public class Graphics implements Runnable {

    private static final Logger LOG = LogManager.getLogger();

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
        double frameStart;
        while(!Thread.interrupted()) {
            frameStart = (float) GLFW.glfwGetTime();
            try {
                Thread.sleep((long) Math.max(0, (GLFW.glfwGetTime() - frameStart) - targetFrameTime));

                LOG.debug("hello render loop");

                display.submitFrame();
                if(GLFW.glfwWindowShouldClose(display.getDisplayPointer())) {
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
