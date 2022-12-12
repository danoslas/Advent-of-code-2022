import java.awt.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution1 {

    public static void main(final String[] args) throws IOException {
        final Graph graph = new Graph("input.txt");

        final Map<Point, Integer> shortestPaths = graph.getVertices()
                .stream()
                .collect(Collectors.toMap(point -> point, point -> Integer.MAX_VALUE));
        shortestPaths.put(graph.getStart(), 0);

        final Set<Point> unvisitedVertices = new HashSet<>(graph.getVertices());
        while (!unvisitedVertices.isEmpty()) {
            final Point closestVertex = unvisitedVertices.stream()
                    .min(Comparator.comparingInt(shortestPaths::get))
                    .orElseThrow();
            final int closestVertexDistance = shortestPaths.get(closestVertex);

            for (final Point adjacentVertex : graph.getAdjacentVertices(closestVertex)) {
                final int adjVertexDistance = shortestPaths.get(adjacentVertex);
                shortestPaths.put(
                        adjacentVertex,
                        Math.min(adjVertexDistance, closestVertexDistance + 1));
            }

            unvisitedVertices.remove(closestVertex);
        }

        System.out.println(shortestPaths.get(graph.getDestination()));
    }
}
