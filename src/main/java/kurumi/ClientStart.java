package kurumi;

import kurumi.utli.Display;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public final class ClientStart extends Thread {

    private static final Logger LOGGER = LogManager.getLogger("Core");

    private static final int TARGET_FPS = 60;
    private static final int TARGET_FRAME_TIME = 1000 / TARGET_FPS;

    private final Display display;

    private ClientStart() {
        display = new Display(1280, 720, "Cubes")
                .setErrorPrint(System.err)
                .setOGLVersion(3, 3)
                .withCoreProfile()
                .setResizable(false)
                .setVisible(false)
                .setWindowPosition(1, 20)
                .finishSetup();
    }

    public static void main(String[] args) {
        new ClientStart().start();
    }

    @Override
    public void run() {
        double frameStart;
        while(!super.isInterrupted()) {
            frameStart = (float) GLFW.glfwGetTime();
            try {
                Thread.sleep((long) Math.max(0, (GLFW.glfwGetTime() - frameStart) - TARGET_FRAME_TIME));

                LOGGER.debug("nya");

                display.submitFrame();
                if(GLFW.glfwWindowShouldClose(display.getDisplayPointer())) {
                    super.interrupt();
                }
            } catch (InterruptedException e) {
                LOGGER.info("Frame thread interrupted. Exiting client.");
            }
        }

        destroy();
    }

    private void destroy() {
        display.destroy();
    }

}
