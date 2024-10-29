package org.example;

// Фигура Z
public class ZShape extends Shape {
    public static final int[][] SHAPE = {
            {1, 1, 0},
            {0, 1, 1},
    };

    public ZShape() {
        super(SHAPE);
    }
}
