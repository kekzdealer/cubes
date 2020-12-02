package kurumi.data.environment;

import kurumi.data.component.Coordinate;
import kurumi.data.component.SimpleFixedRenderer;

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
    byte[] getComponentsForId(int id);

    /**
     * Get all cube ids that have the specified component type registered.
     *
     * @param componentType a component type
     * @param scope         search area limit
     * @return all cube ids with this component
     */
    long[] getIdsForComponent(byte componentType, Scope scope);

    // COMPONENT METHODS ===============================================================================================

    // COORDINATE ------------------------------------------------------------------------------------------------------

    /**
     * Get the unique cube id for the specified coordinate.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     * @return unique cube id
     */
    long getBlockIdAt(int x, int y, int z);

    /**
     * Get the unique cube id for the specified coordinate.
     *
     * @param coordinate coordinate
     * @return unique cube id
     */
    long getBlockIdAt(Coordinate coordinate);

    /**
     * Get all cube ids with a coordinate.
     *
     * @param scope search area limit
     * @return all cube ids with a coordinate
     */
    long[] getIdsWithCoordinate(Scope scope);

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
    long[] getIdsWithSimpleFixedRender(Scope scope);

    /**
     * Get the simple fixed renderer for the specified cube id.
     *
     * @param id cube id
     * @return rendering data
     */
    SimpleFixedRenderer getSimpleFixedRendererForId(long id);

}
