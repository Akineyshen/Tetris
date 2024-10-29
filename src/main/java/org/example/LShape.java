package org.example;

// Фигура L
public class LShape extends Shape {
    public static final int[][] SHAPE = {
            {1, 0},
            {1, 0},
            {1, 1}
    };

    public LShape() {
        super(SHAPE);
    }
}
