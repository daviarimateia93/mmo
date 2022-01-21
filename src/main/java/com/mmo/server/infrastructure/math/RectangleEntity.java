package com.mmo.server.infrastructure.math;

import com.mmo.server.core.math.Rectangle;
import com.mmo.server.core.math.Vertex;

import lombok.Data;

@Data
public class RectangleEntity {

    private Vertex topLeftVertex;
    private Vertex topRightVertex;
    private Vertex bottomLeftVertex;
    private Vertex bottomRightVertex;

    public Rectangle toRectangle() {
        return Rectangle.builder()
                .topLeftVertex(topLeftVertex)
                .topRightVertex(topRightVertex)
                .bottomLeftVertex(bottomLeftVertex)
                .bottomRightVertex(bottomRightVertex)
                .build();
    }

    public static RectangleEntity of(Rectangle rectangle) {
        RectangleEntity entity = new RectangleEntity();
        entity.setTopLeftVertex(rectangle.getTopLeftVertex());
        entity.setTopRightVertex(rectangle.getTopRightVertex());
        entity.setBottomLeftVertex(rectangle.getBottomLeftVertex());
        entity.setBottomRightVertex(rectangle.getBottomRightVertex());

        return entity;
    }
}
