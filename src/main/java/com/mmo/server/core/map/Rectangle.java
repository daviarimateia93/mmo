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

    private final Position topLeftPosition;
    private final Position topRightPosition;
    private final Position bottomLeftPosition;
    private final Position bottomRightPosition;

    @Builder
    private Rectangle(
            @NonNull Position topLeftPosition,
            @NonNull Position topRightPosition,
            @NonNull Position bottomLeftPosition,
            @NonNull Position bottomRightPosition) {

        this.topLeftPosition = topLeftPosition;
        this.topRightPosition = topRightPosition;
        this.bottomLeftPosition = bottomLeftPosition;
        this.bottomRightPosition = bottomRightPosition;

        validate();
    }

    public boolean intersects(Position position) {
        boolean validBottomLeft = position.getX() >= bottomLeftPosition.getX()
                && position.getY() >= bottomLeftPosition.getY()
                && position.getZ() >= bottomLeftPosition.getZ();

        boolean validBottomRight = position.getX() <= bottomRightPosition.getX()
                && position.getY() >= bottomRightPosition.getY()
                && position.getZ() >= bottomRightPosition.getZ();

        boolean validTopLeft = position.getX() >= topLeftPosition.getX()
                && position.getY() <= topLeftPosition.getY()
                && position.getZ() <= topLeftPosition.getZ();

        boolean validTopRight = position.getX() <= topRightPosition.getX()
                && position.getY() <= topRightPosition.getY()
                && position.getZ() <= topRightPosition.getZ();

        return validBottomLeft && validBottomRight && validTopLeft && validTopRight;
    }

    private void validate() throws InvalidRectangleException {
        long a1 = bottomRightPosition.getX() - bottomLeftPosition.getX();
        long b1 = bottomRightPosition.getY() - bottomLeftPosition.getY();
        long c1 = bottomRightPosition.getZ() - bottomLeftPosition.getZ();
        long a2 = topLeftPosition.getX() - bottomRightPosition.getX();
        long b2 = topLeftPosition.getY() - bottomRightPosition.getY();
        long c2 = topLeftPosition.getZ() - bottomRightPosition.getZ();
        long a = b1 * c2 - b2 * c1;
        long b = a2 * c1 - a1 * c2;
        long c = a1 * b2 - b1 * a2;
        long d = (-a * bottomLeftPosition.getX() - b * bottomLeftPosition.getY() - c * bottomLeftPosition.getZ());

        if (a * topRightPosition.getX() + b * topRightPosition.getY() + c * topRightPosition.getZ() + d != 0) {
            throw new InvalidRectangleException("This is a invalid rectangle");
        }
    }
}
