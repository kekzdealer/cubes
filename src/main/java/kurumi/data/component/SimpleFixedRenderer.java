package kurumi.data.component;

public final class SimpleFixedRenderer {

    /**
     * The cube mesh will always be the first mesh loaded through OpenGL, so it's id will always be 0.
     */
    private static final int cubeMeshId = 0;

    private final int textureId;

    public SimpleFixedRenderer(int textureId) {
        this.textureId = textureId;
    }

    public static int getCubeMeshId() {
        return cubeMeshId;
    }

    public int getTextureId() {
        return textureId;
    }
}
