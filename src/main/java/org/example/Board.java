package org.example;

public class Board {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;
    private int[][] board;

    public Board() {
        board = new int[HEIGHT][WIDTH];
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getCell(int row, int col) {
        return board[row][col];
    }

    public boolean canMove(Shape tetromino, int newX, int newY) {
        int[][] shape = tetromino.getShape();
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] != 0) { // Если клетка фигуры не пустая
                    int x = newX + c; // Новая позиция по горизонтали
                    int y = newY + r; // Новая позиция по вертикали

                    // Проверка границ поля
                    if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
                        return false; // За пределами поля
                    }

                    // Проверка на пересечение с другими фигурами
                    if (y < HEIGHT && board[y][x] != 0) {
                        return false; // Пересечение с другой фигурой
                    }
                }
            }
        }
        return true; // Движение допустимо
    }

    public void placeTetromino(Shape tetromino) {
        int[][] shape = tetromino.getShape();
        int x = tetromino.getPosX();
        int y = tetromino.getPosY();

        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] != 0) { // Если клетка фигуры не пустая
                    board[y + r][x + c] = shape[r][c]; // Добавляем фигуру на поле
                }
            }
        }
    }

    // Метод для отображения сетки в консоли
    public void display(int[][] displayBoard) {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                System.out.print(displayBoard[row][col] == 0 ? "▪" : "█");
            }
            System.out.println();
        }
        System.out.println("▭▭▭▭▭▭");
    }

    public int[][] getBoardCopy() {
        int[][] copy = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, board[i].length);
        }
        return copy;
    }
}

