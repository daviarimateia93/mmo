package com.mmo.server.infrastructure.math;

import com.mmo.server.core.math.Rectangle;
import com.mmo.server.core.math.Vertex;

import lombok.Data;

@Data
public class RectangleDTO {

    private Vertex topLeftVertex;
    private Vertex topRightVertex;
    private Vertex bottomLeftVertex;
    private Vertex bottomRightVertex;

    public static RectangleDTO of(Rectangle rectangle) {
        RectangleDTO dto = new RectangleDTO();
        dto.setTopLeftVertex(rectangle.getTopLeftVertex());
        dto.setTopRightVertex(rectangle.getTopRightVertex());
        dto.setBottomLeftVertex(rectangle.getBottomLeftVertex());
        dto.setBottomRightVertex(rectangle.getBottomRightVertex());

        return dto;
    }

    public Rectangle toRectangle() {
        return Rectangle.builder()
                .topLeftVertex(topLeftVertex)
                .topRightVertex(topRightVertex)
                .bottomLeftVertex(bottomLeftVertex)
                .bottomRightVertex(bottomRightVertex)
                .build();
    }
}
