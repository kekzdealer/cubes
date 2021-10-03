package at.kurumi.graphics.world;

import at.kurumi.data.environment.worldprovider.World;
import at.kurumi.data.managers.Materials;
import at.kurumi.data.resources.Mesh;
import at.kurumi.graphics.DefaultShader;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11C;

public class CubeEnvironmentRenderer {

    public void activate() {
        // Back-face culling
        GL11C.glEnable(GL11C.GL_CULL_FACE);
        GL11C.glCullFace(GL11C.GL_BACK);
        // Depth-Testing
        GL11C.glEnable(GL11C.GL_DEPTH_TEST);
        GL11C.glDepthMask(true);
    }

    public void render(World world, Mesh[] sideQuadMeshes, Materials materials, Matrix4fc projection) {
        final var cube = world.getCubeAt(0, 0, 0);
        for(int i = 0; i < 6; i++) {
            final var mesh = sideQuadMeshes[i];
            final var material = materials.getMaterial(cube.getMaterials()[i]);

            mesh.bind();
            material.bind();

            final DefaultShader shader = (DefaultShader) material.getShader();
            shader.uploadTransformation(new Matrix4f());
            shader.uploadProjection(projection);

            GL11C.glDrawArrays(GL11.GL_TRIANGLES, 0, mesh.getVertexCount());

            mesh.unbind();
            material.unbind();

        }
    }

}
