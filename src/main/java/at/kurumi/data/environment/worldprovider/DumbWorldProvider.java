package at.kurumi.data.environment.worldprovider;

import at.kurumi.data.component.Coordinate;
import at.kurumi.data.component.SimpleFixedRenderer;
import at.kurumi.data.environment.IWorld;
import at.kurumi.data.environment.Scope;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class DumbWorldProvider implements IWorld {

    private final Map<Long, Set<Byte>> componentMapping = new HashMap<>();

    private final Map<Long, Coordinate> coordinateMap = new HashMap<>();
    private final Map<Long, SimpleFixedRenderer> simpleFixedRendererMap = new HashMap<>();

    private long nextCubeId;

    private boolean isInScope(long cubeId, Scope scope) {
        return scope.contains(coordinateMap.get(cubeId));
    }

    @Override
    public Set<Byte> getComponentsForId(long id) {
        return componentMapping.get(id);
    }

    @Override
    public Set<Long> getIdsForComponent(byte componentType, Scope scope) {
        return componentMapping.keySet().stream().filter(cubeId ->
                isInScope(cubeId, scope) && componentMapping.get(cubeId).contains(componentType)
        ).collect(Collectors.toSet());
    }

    @Override
    public long getCubeIdAt(int x, int y, int z) {
        return coordinateMap.keySet().stream().filter(cubeId ->
                coordinateMap.get(cubeId).equals(x, y, z)
        ).findFirst().orElse(-1L);
    }

    @Override
    public long getCubeIdAt(Coordinate coordinate) {
        return getCubeIdAt(coordinate.getX(), coordinate.getY(), coordinate.getZ());
    }

    @Override
    public long addCubeAt(int x, int y, int z) {
        // Make sure not to overwrite an existing cube
        if (getCubeIdAt(x, y, z) != -1) {
            return -1L;
        } else {
            final long newId = nextCubeId++;
            // Add coordinate
            coordinateMap.put(newId, new Coordinate(x, y, z));
            // Initialize component mapping with a coordinate component
            final Set<Byte> comps = new HashSet<>();
            comps.add(IWorld.COORDINATE);
            componentMapping.put(newId, comps);

            return newId;
        }
    }

    @Override
    public long removeCubeAt(int x, int y, int z) {
        final long cubeId = getCubeIdAt(x, y, z);
        if (cubeId != -1L) {
            // remove all associated components
            removeSimpleFixedRendererFrom(cubeId);

            coordinateMap.remove(cubeId);

            componentMapping.remove(cubeId);
        }
        return cubeId;
    }

    @Override
    public Set<Long> getIdsWithCoordinate(Scope scope) {
        return getIdsForComponent(IWorld.COORDINATE, scope);
    }

    @Override
    public Coordinate getCoordinateForId(long id) {
        return coordinateMap.get(id);
    }

    @Override
    public Set<Long> getIdsWithSimpleFixedRender(Scope scope) {
        return getIdsForComponent(IWorld.SIMPLE_FIXED_RENDER, scope);
    }

    @Override
    public SimpleFixedRenderer getSimpleFixedRendererForId(long id) {
        return simpleFixedRendererMap.get(id);
    }

    @Override
    public boolean addSimpleFixedRendererTo(long id, SimpleFixedRenderer simpleFixedRenderer) {
        if (!componentMapping.containsKey(id)) {
            return false;
        } else {
            simpleFixedRendererMap.put(id, simpleFixedRenderer);
            componentMapping.get(id).add(IWorld.SIMPLE_FIXED_RENDER);
            return true;
        }
    }

    @Override
    public boolean removeSimpleFixedRendererFrom(long id) {
        return simpleFixedRendererMap.remove(id) != null; // TODO clean up render stuff here
    }
}
