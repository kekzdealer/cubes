package at.kurumi.graphics;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import java.io.PrintStream;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Display {

    private final long window;
    private final boolean isVSyncOn;

    private int width;
    private int height;
    private boolean isInitialised = false;

    private boolean isResized;

    public Display(int width, int height, String title, boolean isVSyncOn) throws GraphicsException {
        // Initialize GLFW
        if (!glfwInit()) {
            throw new GraphicsException("GLFW initialization failed!");
        }

        this.width = width;
        this.height = height;
        this.isVSyncOn = isVSyncOn;

        // Create window
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL) {
            throw new GraphicsException("Could not create Window");
        }

        // ESC to close
        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
                GLFW.glfwSetWindowShouldClose(window, true);
        });
    }

    public boolean isInitialised() {
        return isInitialised;
    }

    public Display setErrorPrint(PrintStream target) {
        if (isInitialised) {
            throw new IllegalStateException("Method has to be called before finishSetup()");
        }
        GLFWErrorCallback.createPrint(target).set();
        return this;
    }

    /**
     * Set the minimum OpenGL version, if core profile is enforced, and if forward compatibility is
     * allowed.
     * <p>
     * <h3>Mac OS Hint:</h3>
     * Forward compat apparently has to be <code>true</code> to work on Mac OS.
     * </p>
     *
     * @param major           OpenGL major version
     * @param minor           OpenGL minor version
     * @param usesCoreProfile <code>true</code> to enforce core profile
     * @param isForwardCompat <code>true</code> to activate forward compatibility
     * @return this {@link Display} instance
     */
    public Display setOGLVersion(int major, int minor, boolean usesCoreProfile, boolean isForwardCompat) {
        if (isInitialised) {
            throw new IllegalStateException("Method has to be called before finishSetup()");
        }
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, major);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, minor);
        if (usesCoreProfile) {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        }
        if (isForwardCompat) {
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        }
        return this;
    }

    public Display setResizable(boolean isResizable) {
        if (isInitialised) {
            throw new IllegalStateException("Method has to be called before finishSetup()");
        }
        glfwWindowHint(GLFW_RESIZABLE, isResizable ? GLFW_TRUE : GLFW_FALSE);

        if (isResizable) {
            glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
                this.width = width;
                this.height = height;
                this.isResized = true;
            });
        }

        return this;
    }

    public Display setVisible(boolean isVisible) {
        if (isInitialised) {
            throw new IllegalStateException("Method has to be called before finishSetup()");
        }
        glfwWindowHint(GLFW_VISIBLE, isVisible ? GLFW_TRUE : GLFW_FALSE);
        return this;
    }

    public Display setWindowPosition(int x, int y) {
        if (isInitialised) {
            throw new IllegalStateException("Method has to be called before finishSetup()");
        }
        glfwSetWindowPos(window, x, y);
        return this;
    }

    public Display enableCursorCapture() {
        if (isInitialised) {
            throw new IllegalStateException("Method has to be called before finishSetup()");
        }
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        return this;
    }

    public Display finishSetup() {
        glfwMakeContextCurrent(window);

        if(isVSyncOn) {
            glfwSwapInterval(1); // 1 => full refresh rate, 2 => half refresh rate
        }

        GL.createCapabilities();

        glfwShowWindow(window);

        final IntBuffer w = MemoryUtil.memAllocInt(1);
        final IntBuffer h = MemoryUtil.memAllocInt(1);
        glfwGetWindowSize(window, w, h);
        width = w.get(0);
        height = h.get(0);


        isInitialised = true;
        return this;
    }

    public long getDisplayPointer() {
        if (!isInitialised()) {
            throw new IllegalStateException("Display not initialised!");
        }
        return window;
    }

    public boolean isVSyncOn() {
        if (!isInitialised()) {
            throw new IllegalStateException("Display not initialised!");
        }
        return isVSyncOn;
    }

    public int getWidth() {
        if (!isInitialised()) {
            throw new IllegalStateException("Display not initialised!");
        }
        return width;
    }

    public int getHeight() {
        if (!isInitialised()) {
            throw new IllegalStateException("Display not initialised!");
        }
        return height;
    }

    public boolean isResized() {
        if (!isInitialised()) {
            throw new IllegalStateException("Display not initialised!");
        }
        return isResized;
    }

    public void setResized(boolean resized) {
        if (!isInitialised()) {
            throw new IllegalStateException("Display not initialised!");
        }
        isResized = resized;
    }



    public boolean isKeyPressed(int keyCode) {
        if (!isInitialised()) {
            throw new IllegalStateException("Display not initialised!");
        }
        return glfwGetKey(window, keyCode) == GLFW_PRESS;
    }

    public boolean windowShouldClose() {
        if (!isInitialised()) {
            throw new IllegalStateException("Display not initialised!");
        }
        return glfwWindowShouldClose(window);
    }

    /**
     * Displays the changed buffer on screen and polls events.
     */
    public void update() {
        if (!isInitialised()) {
            throw new IllegalStateException("Display not initialised!");
        }
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    public void destroy() {
        if (!isInitialised()) {
            throw new IllegalStateException("Display not initialised!");
        }
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        final var glfwErrorCallback = glfwSetErrorCallback(null);
        if (glfwErrorCallback != null) {
            glfwErrorCallback.free();
        }
    }

}
