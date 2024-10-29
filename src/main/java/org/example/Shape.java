package org.example;

public abstract  class Shape {
    protected int[][] shape;
    protected int posX;
    protected int posY;

    public Shape(int[][] shape){
        this.shape = shape;
        this.posX = 4;
        this.posY = 0;
    }

    public int[][] getShape() {
        return shape;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
    public void moveDown() {
        posY++;
    }

    public void moveLeft() {
        posX--;
    }

    public void moveRight() {
        posX++;
    }

    public void rotate() {
        shape = rotateShape(shape);
    }

    private int[][] rotateShape(int[][] shape) {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotated = new int[cols][rows];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rotated[c][rows - 1 - r] = shape[r][c];
            }
        }
        return rotated;
    }
}
