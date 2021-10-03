package at.kurumi;

import at.kurumi.data.environment.worldprovider.Cube;
import at.kurumi.data.environment.worldprovider.World;
import at.kurumi.data.managers.Materials;
import at.kurumi.data.managers.Meshes;
import at.kurumi.data.managers.Shaders;
import at.kurumi.data.managers.Textures;
import at.kurumi.data.providers.MeshLoader;
import at.kurumi.graphics.Display;
import at.kurumi.graphics.Graphics;
import at.kurumi.graphics.GraphicsException;
import at.kurumi.graphics.world.GrassMaterial;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class Engine implements Runnable {

    private static final Logger LOG = LogManager.getLogger(Engine.class.getSimpleName());

    private final Timer timer;

    private World world;

    private Display display;
    private Graphics graphics;

    private Meshes meshes;
    private Shaders shaders;
    private Textures textures;
    private Materials materials;

    private int targetFps = 60;
    private int targetTps = 20;

    public Engine() {
        timer = new Timer();
    }

    public void initWorld() {
        world = new World();

        final var grassMaterialName = "grass";
        materials.registerMaterial(grassMaterialName, GrassMaterial.class);
        final String[] cubeMaterials = new String[6];
        Arrays.fill(cubeMaterials, grassMaterialName);
        final var grassCube = new Cube(cubeMaterials);

        world.addCubeAt(0, 0, 0, grassCube);
    }

    @Override
    public void run() {
        try {
            display = new Display(1280, 720, "Cubes", false)
                    .setWindowPosition(320, 180)
                    .setErrorPrint(System.err)
                    .setOGLVersion(3, 3, true, true)
                    .setResizable(true)
                    .setVisible(false)
                    .finishSetup();

            meshes = new Meshes(new MeshLoader());
            shaders = new Shaders();
            textures = new Textures();
            materials = new Materials(shaders, textures);

            graphics = new Graphics(display, meshes, shaders, textures, materials);

            initWorld();

            loop();
        } catch (GraphicsException e) {
            LOG.error("Terminating on graphics error: {}", e.getMessage());
        } finally {
            graphics.close();
            display.destroy();
        }
    }

    private void loop() {
        double elapsedTime;
        double accumulator = 0.0;
        double interval = 1.0 / targetTps;

        while (!Thread.interrupted() && !display.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while(accumulator >= interval) {
                tick();
                accumulator -= interval;
            }

            graphics.render(world);
            display.update();

            if(!display.isVSyncOn()) {
                sync();
            }
        }
    }

    private void input() {

    }

    private void tick() {

    }

    private void sync() {
        double loopSlot = 1.0 / targetFps;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while(timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                LOG.error("Engine sync process interrupted while sleeping");
                // Re-set interrupt flag after it has been cleared by read
                Thread.currentThread().interrupt();
            }
        }
    }
}
