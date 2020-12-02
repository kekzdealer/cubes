package kurumi.utli;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.io.PrintStream;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Display {
	
	private final long window;

	private int width;
	private int height;
	private boolean isInitialised = false;

	public Display(int width, int height, String title) {
		// Initialize GLFW
		if (!glfwInit()) {
			throw new RuntimeException("GLFW initialization failed!");
		}

		this.width = width;
		this.height = height;

		// Create window
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Could not create Window");
		}

		// ESC to close
		GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) ->{
			if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS)
				GLFW.glfwSetWindowShouldClose(window, true);
		});
	}

	public boolean isInitialised() {
		return isInitialised;
	}

	public Display setErrorPrint(PrintStream target) {
		if(isInitialised) {
			throw new IllegalStateException("Method has to be called before finishSetup()");
		}
		GLFWErrorCallback.createPrint(target).set();
		return this;
	}

	public Display setOGLVersion(int major, int minor) {
		if(isInitialised) {
			throw new IllegalStateException("Method has to be called before finishSetup()");
		}
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, major);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, minor);
		return this;
	}

	public Display withCoreProfile() {
		if(isInitialised) {
			throw new IllegalStateException("Method has to be called before finishSetup()");
		}
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		return this;
	}

	public Display setResizable(boolean isResizable) {
		if(isInitialised) {
			throw new IllegalStateException("Method has to be called before finishSetup()");
		}
		glfwWindowHint(GLFW_RESIZABLE, isResizable ? GLFW_TRUE : GLFW_FALSE);
		return this;
	}

	public Display setVisible(boolean isVisible) {
		if(isInitialised) {
			throw new IllegalStateException("Method has to be called before finishSetup()");
		}
		glfwWindowHint(GLFW_VISIBLE, isVisible ? GLFW_TRUE : GLFW_FALSE);
		return this;
	}

	public Display setWindowPosition(int x, int y) {
		if(isInitialised) {
			throw new IllegalStateException("Method has to be called before finishSetup()");
		}
		glfwSetWindowPos(window, x, y);
		return this;
	}

	public Display enableCursorCapture() {
		if(isInitialised) {
			throw new IllegalStateException("Method has to be called before finishSetup()");
		}
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		return this;
	}

	public Display finishSetup() {
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();

		glfwShowWindow(window);

		final IntBuffer w = BufferUtils.createIntBuffer(1);
		final IntBuffer h = BufferUtils.createIntBuffer(1);
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

	public void submitFrame() {
		if (!isInitialised()) {
			throw new IllegalStateException("Display not initialised!");
		}
		glfwSwapBuffers(window);
	}

	public void destroy() {
		if (!isInitialised()) {
			throw new IllegalStateException("Display not initialised!");
		}
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

}
