package at.kurumi.graphics;

import at.kurumi.data.resources.Shader;
import org.joml.Matrix4fc;

import java.io.IOException;

public class HealthShader extends Shader {

    private static final String NAME = "gui";

    private int location_diffuseMap;

    private int location_transformation;

    public HealthShader() throws GraphicsException, IOException {
        super(NAME, NAME);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "uv");
    }

    @Override
    protected void getAllUniformLocations() {
        location_diffuseMap = super.getUniformLocation("diffuse");
        location_transformation = super.getUniformLocation("transformation");
    }

    public void uploadTexture(int textureBank) {
        super.loadInt(location_diffuseMap, textureBank);
    }

    public void uploadTransformation(Matrix4fc transformation) {
        super.loadMatrix4f(location_transformation, transformation);
    }

}
