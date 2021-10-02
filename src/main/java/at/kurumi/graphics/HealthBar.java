package at.kurumi.graphics;

import at.kurumi.data.managers.Materials;
import at.kurumi.data.managers.Meshes;
import at.kurumi.data.managers.Shaders;
import at.kurumi.data.providers.MeshLoader;
import at.kurumi.data.resources.Material;
import at.kurumi.data.resources.Mesh;
import at.kurumi.data.resources.Shader;
import org.joml.Vector2fc;

/**
 * First health bar implementation
 */
public class HealthBar extends AbstractHealthBar {

    private final Mesh mesh;
    private final Material materialOutline;
    private final Material materialHealth;

    public HealthBar(Meshes meshes, Materials materials, Shaders shaders) {
        super(meshes, materials, shaders);


        mesh = new MeshLoader().createQuad(1.0f, 8.0f);

        materialOutline = materials.getMaterial("gauge_outline");
        materialHealth = materials.getMaterial("health");
    }

    @Override
    public void setHealth(float value) {

    }

    @Override
    public Mesh getMesh() {
        return mesh;
    }

    @Override
    public Material getMaterial() {
        return null;
    }

    @Override
    public Shader getShader() {
        return null;
    }

    @Override
    public Vector2fc getPosition() {
        return null;
    }

    @Override
    public void destroyUIElement() {

    }
}
