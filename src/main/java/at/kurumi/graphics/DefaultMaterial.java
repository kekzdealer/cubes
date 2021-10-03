package at.kurumi.graphics;

import at.kurumi.data.managers.Shaders;
import at.kurumi.data.managers.Textures;
import at.kurumi.data.resources.Material;
import at.kurumi.data.resources.Shader;
import at.kurumi.data.resources.Texture;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL13C;

import static at.kurumi.ClientStart.DEFAULT_STRING;

/**
 * Default material with {@link DefaultShader} and default texture.
 */
public class DefaultMaterial extends Material<DefaultShader> {

    private final DefaultShader shader;
    private final Texture texture;

    public DefaultMaterial(Shaders shaders, Textures textures) {
        super(shaders, textures);
        this.shader = (DefaultShader) shaders.getShader(DEFAULT_STRING);
        this.texture = textures.getTexture(DEFAULT_STRING);
    }

    @Override
    public void bind() {
        shader.start();
        GL13C.glActiveTexture(GL13C.GL_TEXTURE0);
        GL11C.glBindTexture(GL11C.GL_TEXTURE_2D, texture.getId());
        shader.uploadTexture(0); // Activated texture bank 0 two lines ago, so pass 0 here
    }

    @Override
    public void unbind() {
        shader.stop();
        GL11C.glBindTexture(GL11C.GL_TEXTURE_2D, 0);
    }

    @Override
    public DefaultShader getShader() {
        return shader;
    }

    @Override
    public void dispose() {

    }
}
