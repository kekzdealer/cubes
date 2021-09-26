package at.kurumi.data.environment;

import at.kurumi.data.component.Coordinate;
import at.kurumi.data.component.SimpleFixedRenderer;

import java.util.Set;

/**
 * Implemented by World Data Providers.
 */
public interface IWorld {

    int CHUNK_EDGE_LENGTH = 64;
    int CHUNK_HEIGHT_LIMIT = 4096;
    long MAX_CHUNKS_IN_DIRECTION = 549_755_813_887L;

    // COMPONENT CONSTANTS =============================================================================================

    byte COORDINATE = 0;
    byte SIMPLE_FIXED_RENDER = 1;

    // GENERAL METHODS =================================================================================================

    /**
     * Get the type of all components registered for this cube id.
     *
     * @param id cube id
     * @return component types registered for this cube id
     */
    Set<Byte> getComponentsForId(long id);

    /**
     * Get all cube ids that have the specified component type registered.
     *
     * @param componentType a component type
     * @param scope         search area limit
     * @return all cube ids with this component
     */
    Set<Long> getIdsForComponent(byte componentType, Scope scope);

    // COMPONENT METHODS ===============================================================================================

    // COORDINATE ------------------------------------------------------------------------------------------------------

    /**
     * Get the unique cube id for the specified coordinate.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     * @return unique cube id, or {@code -1} if the coordinate is empty
     */
    long getCubeIdAt(int x, int y, int z);

    /**
     * Get the unique cube id for the specified coordinate.
     *
     * @param coordinate coordinate
     * @return unique cube id, or {@code -1} if the coordinate is empty
     */
    long getCubeIdAt(Coordinate coordinate);

    /**
     * Add a new cube at the specified coordinate.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     * @return unique cube id of the newly generated block, or {@code -1} if cube creation failed
     */
    long addCubeAt(int x, int y, int z);

    /**
     * Remove a cube at the specified coordinate.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     * @return the removed cube id, or {@code -1} if no cube was found
     */
    long removeCubeAt(int x, int y, int z);

    /**
     * Get all cube ids with a coordinate.
     *
     * @param scope search area limit
     * @return all cube ids with a coordinate
     */
    Set<Long> getIdsWithCoordinate(Scope scope);

    /**
     * Get the coordinate for the specified cube id.
     *
     * @param id cube id
     * @return coordinate data
     */
    Coordinate getCoordinateForId(long id);

    // SIMPLE FIXED RENDERER -------------------------------------------------------------------------------------------

    /**
     * Get all cube ids with a simple fixed renderer (the basic cube renderer).
     *
     * @param scope search area limit
     * @return all cube ids with a simple fixed renderer
     */
    Set<Long> getIdsWithSimpleFixedRender(Scope scope);

    /**
     * Get the simple fixed renderer for the specified cube id.
     *
     * @param id cube id
     * @return rendering data
     */
    SimpleFixedRenderer getSimpleFixedRendererForId(long id);

    /**
     * Add a new simple fixed renderer to a cube id.
     *
     * @param id                  cube id
     * @param simpleFixedRenderer component instance
     * @return {@code true} if the component was added successfully
     */
    boolean addSimpleFixedRendererTo(long id, SimpleFixedRenderer simpleFixedRenderer);

    /**
     * Remove the simple fixed renderer from a cube id.
     *
     * @param id cube id
     * @return {@code true} if the component was removed successfully
     */
    boolean removeSimpleFixedRendererFrom(long id);

}
