package at.kurumi.data.resources;

/**
 * Common interface implements by all resources that should be managed by a resource manager for memory
 * or clean up reasons.
 */
public interface IManagedResource {

    /**
     * Delete this resource
     */
    void dispose();
}
