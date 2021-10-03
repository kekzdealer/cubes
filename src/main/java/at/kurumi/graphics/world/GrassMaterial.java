package at.kurumi.graphics.world;

import at.kurumi.data.managers.Shaders;
import at.kurumi.data.managers.Textures;
import at.kurumi.data.resources.Material;
import at.kurumi.data.resources.Texture;
import at.kurumi.graphics.DefaultShader;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL13C;

import static at.kurumi.ClientStart.DEFAULT_STRING;

public class GrassMaterial extends Material {

    private final DefaultShader shader;
    private final Texture texture;

    public GrassMaterial(Shaders shaders, Textures textures) {
        super(shaders, textures);
        this.shader = (DefaultShader) shaders.getShader(DEFAULT_STRING);
        this.texture = textures.getTexture("grass_top");
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
    public void dispose() {

    }
}
