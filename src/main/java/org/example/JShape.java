package org.example;

// Фигура J
public class JShape extends Shape {
    public static final int[][] SHAPE = {
            {0, 1},
            {0, 1},
            {1, 1}
    };

    public JShape() {
        super(SHAPE);
    }
}
