package com.mmo.server.core.math;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Rectangle {

    private final Vertex topLeftVertex;
    private final Vertex topRightVertex;
    private final Vertex bottomLeftVertex;
    private final Vertex bottomRightVertex;

    @Builder
    private Rectangle(
            @NonNull Vertex topLeftVertex,
            @NonNull Vertex topRightVertex,
            @NonNull Vertex bottomLeftVertex,
            @NonNull Vertex bottomRightVertex) {

        this.topLeftVertex = topLeftVertex;
        this.topRightVertex = topRightVertex;
        this.bottomLeftVertex = bottomLeftVertex;
        this.bottomRightVertex = bottomRightVertex;

        validate();
    }

    public boolean intersects(Vertex vertex) {
        return intersects(vertex.getX(), vertex.getZ());
    }

    public boolean intersects(int x, int y) {
        boolean validBottomLeft = x >= bottomLeftVertex.getX()
                && y >= bottomLeftVertex.getZ();

        boolean validBottomRight = x <= bottomRightVertex.getX()
                && y >= bottomRightVertex.getZ();

        boolean validTopLeft = x >= topLeftVertex.getX()
                && y <= topLeftVertex.getZ();

        boolean validTopRight = x <= topRightVertex.getX()
                && y <= topRightVertex.getZ();

        return validBottomLeft && validBottomRight && validTopLeft && validTopRight;
    }

    public int getWidth() {
        return Math.abs(bottomLeftVertex.getX() - bottomRightVertex.getX());
    }

    public int getDepth() {
        return Math.abs(topLeftVertex.getZ() - bottomLeftVertex.getZ());
    }

    public Vertex getCenter() {
        int y = bottomLeftVertex.getZ() + (getDepth() / 2);
        int x = topLeftVertex.getX() + (getWidth() / 2);

        return new Vertex(x, y);
    }

    private void validate() throws InvalidRectangleException {
        Set<Vertex> vertices = new LinkedHashSet<>(
                List.of(bottomLeftVertex, bottomRightVertex, topLeftVertex, topRightVertex));

        Set<Integer> distances = new LinkedHashSet<>();

        for (Vertex first : vertices) {
            for (Vertex second : vertices) {
                if (!first.equals(second)) {
                    distances.add(first.getDistance(second));
                }
            }
        }

        if (distances.size() > 3)
            throw new InvalidRectangleException("Distance more than 3");

        List<Integer> sortedDistances = new ArrayList<>(distances);

        if (distances.size() == 2) {
            if (2 * sortedDistances.get(0) != sortedDistances.get(1)) {
                throw new InvalidRectangleException("Line seqments does not form a square");
            }

            return;
        }

        if (sortedDistances.get(0) + sortedDistances.get(1) != sortedDistances.get(2)) {
            throw new InvalidRectangleException("Distance of sides should satisfy pythagorean theorem");
        }
    }
}
