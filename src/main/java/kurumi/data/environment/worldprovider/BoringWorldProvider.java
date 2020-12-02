package kurumi.data.environment.worldprovider;

import kurumi.data.component.Coordinate;
import kurumi.data.component.SimpleFixedRenderer;
import kurumi.data.environment.IWorld;
import kurumi.data.environment.Scope;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class BoringWorldProvider implements IWorld {

    private final Map<Long, Set<Byte>> componentMapping = new HashMap<>();

    private final Map<Long, Coordinate> coordinateMap = new HashMap<>();
    private final Map<Long, SimpleFixedRenderer> simpleFixedRendererMap = new HashMap<>();

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
        // TODO
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
        // TODO
    }
}
