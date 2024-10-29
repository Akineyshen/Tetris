package org.example;

// Фигура T
public class TShape extends Shape {
    public static final int[][] SHAPE = {
            {1, 1, 1},
            {0, 1, 0},
    };

    public TShape() {
        super(SHAPE);
    }
}
