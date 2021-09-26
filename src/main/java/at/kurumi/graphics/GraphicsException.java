package at.kurumi.graphics;

/**
 * Signals that an Exception relating to OpenGL or something else in render code has occurred.
 */
public class GraphicsException extends Exception {


    public GraphicsException() {
        super();
    }

    public GraphicsException(String message) {
        super(message);
    }

    public GraphicsException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphicsException(Throwable cause) {
        super(cause);
    }
}
