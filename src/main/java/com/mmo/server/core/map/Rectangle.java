package com.mmo.server.core.map;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Rectangle {

    private final Vertex topLefVertex;
    private final Vertex topRightVertex;
    private final Vertex bottomLeftVertex;
    private final Vertex bottomRightVertex;

    @Builder
    private Rectangle(
            @NonNull Vertex topLeftVertex,
            @NonNull Vertex topRightVertex,
            @NonNull Vertex bottomLeftVertex,
            @NonNull Vertex bottomRightVertex) {

        this.topLefVertex = topLeftVertex;
        this.topRightVertex = topRightVertex;
        this.bottomLeftVertex = bottomLeftVertex;
        this.bottomRightVertex = bottomRightVertex;

        validate();
    }

    public boolean intersects(Vertex vertex) {
        return intersects(vertex.getX(), vertex.getY(), vertex.getZ());
    }

    public boolean intersects(long x, long y, long z) {
        boolean validBottomLeft = x >= bottomLeftVertex.getX()
                && y >= bottomLeftVertex.getY()
                && z >= bottomLeftVertex.getZ();

        boolean validBottomRight = x <= bottomRightVertex.getX()
                && y >= bottomRightVertex.getY()
                && z >= bottomRightVertex.getZ();

        boolean validTopLeft = x >= topLefVertex.getX()
                && y <= topLefVertex.getY()
                && z <= topLefVertex.getZ();

        boolean validTopRight = x <= topRightVertex.getX()
                && y <= topRightVertex.getY()
                && z <= topRightVertex.getZ();

        return validBottomLeft && validBottomRight && validTopLeft && validTopRight;
    }

    private void validate() throws InvalidRectangleException {
        long a1 = bottomRightVertex.getX() - bottomLeftVertex.getX();
        long b1 = bottomRightVertex.getY() - bottomLeftVertex.getY();
        long c1 = bottomRightVertex.getZ() - bottomLeftVertex.getZ();
        long a2 = topLefVertex.getX() - bottomRightVertex.getX();
        long b2 = topLefVertex.getY() - bottomRightVertex.getY();
        long c2 = topLefVertex.getZ() - bottomRightVertex.getZ();
        long a = b1 * c2 - b2 * c1;
        long b = a2 * c1 - a1 * c2;
        long c = a1 * b2 - b1 * a2;
        long d = (-a * bottomLeftVertex.getX() - b * bottomLeftVertex.getY() - c * bottomLeftVertex.getZ());

        if (a * topRightVertex.getX() + b * topRightVertex.getY() + c * topRightVertex.getZ() + d != 0) {
            throw new InvalidRectangleException("This is a invalid rectangle");
        }
    }
}
