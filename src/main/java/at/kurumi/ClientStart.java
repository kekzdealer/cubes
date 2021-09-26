package at.kurumi;

import at.kurumi.data.environment.EnvironmentGenerator;
import at.kurumi.data.environment.IWorld;
import at.kurumi.data.environment.worldprovider.DumbWorldProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Application entry point.
 */
public final class ClientStart {

    private static final Logger LOG = LogManager.getLogger();

    private final Graphics graphics;
    private final Thread graphicsThread;

    private final IWorld world;

    private ClientStart() {
        graphics = new Graphics(1280, 720);
        graphicsThread = new Thread(graphics);
        graphicsThread.setName(Graphics.class.getName());

        world = new DumbWorldProvider();
        EnvironmentGenerator.generatePlane(world, 0, 1, 0, 10, 10);
    }

    public static void main(String[] args) {
        final var locale = new Locale("en", "GB");
        final var bundle = ResourceBundle.getBundle("translation", locale);
        LOG.info(bundle.getString("helloMessage"));

        new ClientStart().onStart();
    }

    public void onStart() {
        graphics.setOnStopCallback(this::onStop);
        graphics.setTargetFps(60);
        graphicsThread.start();
    }

    public void onStop() {
        graphicsThread.interrupt();
        graphics.destroy();
    }
}
