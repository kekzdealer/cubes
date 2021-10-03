package at.kurumi.graphics.gui;

import at.kurumi.data.managers.Materials;
import at.kurumi.data.managers.Meshes;
import at.kurumi.data.managers.Shaders;
import at.kurumi.data.providers.MeshLoader;
import at.kurumi.data.resources.Material;
import at.kurumi.data.resources.Mesh;
import org.joml.Matrix4f;
import org.lwjgl.opengl.*;

/**
 * First health bar implementation
 */
public class HealthBar extends AbstractHealthBar {

    private static final String SHADER_NAME = "gui";

    private final Mesh mesh;
    private final Material materialOutline;
    private final Material materialHealth;

    public HealthBar(Meshes meshes, Materials materials, Shaders shaders) {
        super(meshes, materials, shaders);

        mesh = new MeshLoader().createQuad(1.0f, 8.0f);

        materialOutline = materials.getMaterial("gauge_outline");
        materialHealth = materials.getMaterial("health");

        shaders.registerShader(SHADER_NAME, HealthShader.class);
    }

    @Override
    public void setHealth(float value) {

    }

    @Override
    public void render() {
        final HealthShader shader = (HealthShader) shaders.getShader(SHADER_NAME);
        shader.start();

        mesh.bind();
        GL13C.glActiveTexture(GL13C.GL_TEXTURE0);
        GL11C.glBindTexture(GL11C.GL_TEXTURE_2D, materialOutline.getDiffuse().getId());

        shader.uploadTexture(0); // Activated texture bank 0 two lines ago, so pass 0 here.
        shader.uploadTransformation(new Matrix4f()
                .translate(-1.0f, -1.0f, 0.0f)
                .scale(0.1f));

        GL11C.glDrawArrays(GL11.GL_TRIANGLES, 0, mesh.getVertexCount());
        shader.stop();

        GL11C.glBindTexture(GL11C.GL_TEXTURE_2D, 0);
        mesh.unbind();
    }

    @Override
    public void destroyUIElement() {

    }
}
