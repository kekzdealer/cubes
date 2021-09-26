package at.kurumi.graphics;

import at.kurumi.data.resources.ShaderProgram;
import at.kurumi.util.GraphicsException;
import org.joml.Matrix4fc;

import java.io.IOException;

public class DefaultShader extends ShaderProgram {

    private static final String NAME = "default";

    // Textures
    private int location_diffuseMap;
    private int location_transformation;
    private int location_projection;

    public DefaultShader() throws GraphicsException, IOException {
        super(NAME, NAME);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "texCoord");
    }

    @Override
    protected void getAllUniformLocations() {
        location_diffuseMap = super.getUniformLocation("diffuse");
        location_transformation = super.getUniformLocation("transformation");
        location_projection = super.getUniformLocation("projection");
    }

    public void uploadTexture(int textureBank) {
        super.loadInt(location_diffuseMap, textureBank);
    }

    public void uploadTransformation(Matrix4fc transformation) {
        super.loadMatrix4f(location_transformation, transformation);
    }

    public void uploadProjection(Matrix4fc projection) {
        super.loadMatrix4f(location_projection, projection);
    }
}
