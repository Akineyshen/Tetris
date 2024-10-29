package org.example;

// Фигура S
public class SShape extends Shape {
    public static final int[][] SHAPE = {
            {0, 1, 1},
            {1, 1, 0},
    };

    public SShape() {
        super(SHAPE);
    }
}
