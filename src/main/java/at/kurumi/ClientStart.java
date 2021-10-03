package at.kurumi;

import at.kurumi.data.environment.EnvironmentGenerator;
import at.kurumi.data.environment.IWorld;
import at.kurumi.data.environment.worldprovider.DumbWorldProvider;
import at.kurumi.graphics.Graphics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Application entry point.
 */
public final class ClientStart {

    public static final String DEFAULT_STRING = "default";

    private static final Logger LOG = LogManager.getLogger();

    private final Engine engine;
    private final Thread engineThread;

    private ClientStart() {
        engine = new Engine();
        engineThread = new Thread(engine);
        engineThread.setName(Graphics.class.getName());
    }

    public static void main(String[] args) {
        final var locale = new Locale("en", "GB");
        final var bundle = ResourceBundle.getBundle("translation", locale);
        LOG.info(bundle.getString("helloMessage"));

        new ClientStart().onStart();
    }

    public void onStart() {
        engineThread.start();
    }

}
