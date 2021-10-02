package at.kurumi.data.managers;

import at.kurumi.data.resources.IManagedResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractManager<T extends IManagedResource> implements AutoCloseable {

    protected final Logger LOG;
    protected final Map<String, T> resources;

    private Runnable errorReportCallback;

    public AbstractManager() {
        this(new HashMap<>());
    }

    public AbstractManager(Map<String, T> resources) {
        LOG = LogManager.getLogger();
        this.resources = resources;
    }

    public void setErrorReportCallback(Runnable errorReportCallback) {
        this.errorReportCallback = errorReportCallback;
    }

    protected Runnable getErrorReportCallback() {
        return errorReportCallback;
    }

    /**
     * If implementing classes need to define additional clean up procedures,
     * they should call this parent implementation, too, or at least insert this procedure in their own.
     */
    @Override
    public void close() {
        resources.values().forEach(IManagedResource::dispose);
    }
}
