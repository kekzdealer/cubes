package at.kurumi.graphics;

import at.kurumi.data.resources.Shader;
import org.joml.Matrix4fc;

import java.io.IOException;

import static at.kurumi.ClientStart.DEFAULT_STRING;

/**
 * Default shader implementation. Is used as fallback if a registered shader can't be found.
 * Only supports a diffuse map and basic transformation and projection.
 *
 * @see at.kurumi.data.managers.Shaders
 */
public class DefaultShader extends Shader {

    private int location_diffuseMap;

    private int location_transformation;
    private int location_projection;

    public DefaultShader() throws GraphicsException, IOException {
        super(DEFAULT_STRING, DEFAULT_STRING);
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
