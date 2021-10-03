package at.kurumi;

import at.kurumi.graphics.Display;
import at.kurumi.graphics.Graphics;
import at.kurumi.graphics.GraphicsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Engine implements Runnable {

    private static final Logger LOG = LogManager.getLogger(Engine.class.getSimpleName());

    private final Timer timer;
    private Display display;
    private Graphics graphics;

    private int targetFps = 60;
    private int targetTps = 20;

    public Engine() {
        timer = new Timer();
    }

    @Override
    public void run() {
        try {
            display = new Display(1280, 720, "Cubes", true)
                    .setWindowPosition(320, 180)
                    .setErrorPrint(System.err)
                    .setOGLVersion(3, 3, true, true)
                    .setResizable(true)
                    .setVisible(false)
                    .finishSetup();
            graphics = new Graphics(display);

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

            graphics.render();
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
