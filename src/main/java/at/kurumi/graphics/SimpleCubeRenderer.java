package at.kurumi.graphics;

import at.kurumi.data.environment.worldprovider.Cube;
import at.kurumi.data.managers.Materials;
import at.kurumi.data.managers.Meshes;
import at.kurumi.data.managers.Shaders;

public class SimpleCubeRenderer {

    private final Meshes meshes;
    private final Materials materials;
    private final Shaders shaders;

    public SimpleCubeRenderer(Meshes meshes, Materials materials, Shaders shaders) {
        this.meshes = meshes;
        this.materials = materials;
        this.shaders = shaders;
    }

    public void render(Cube cube) {

    }
}
