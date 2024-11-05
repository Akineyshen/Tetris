package org.example;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class TetrisGame {
    private boolean gameOver = false;

    private Board board;
    private Shape currentTetromino;
    private Shape nextTetromino; // Новая переменная для хранения следующей фигуры
    private Random random;
    private Scanner scanner;

    public TetrisGame() {
        board = new Board();
        random = new Random();
        scanner = new Scanner(System.in);
        nextTetromino = generateRandomTetromino(); // Инициализируем следующую фигуру
        spawnNewTetromino(); // Спавним текущую фигуру
    }

    private Shape generateRandomTetromino() {
        // Логика для генерации случайной фигуры
        int shapeType = random.nextInt(7);
        switch (shapeType) {
            case 0: return new IShape();
            case 1: return new JShape();
            case 2: return new LShape();
            case 3: return new OShape();
            case 4: return new SShape();
            case 5: return new TShape();
            case 6: return new ZShape();
            default: return new IShape();
        }
    }

    private void spawnNewTetromino() {
        currentTetromino = nextTetromino; // Текущей фигурой становится следующая
        nextTetromino = generateRandomTetromino(); // Генерируем новую следующую фигуру

        currentTetromino.posX = board.getWidth() / 2 - 1;
        currentTetromino.posY = 0;

        // Проверка, если новая фигура не может быть размещена на верхней позиции
        if (!board.canMove(currentTetromino, currentTetromino.getPosX(), currentTetromino.getPosY())) {
            gameOver = true; // Устанавливаем состояние игры в "проигрыш"
        }
    }

    public void play() {
        long lastFallTime = System.currentTimeMillis();
        int fallDelay = 500;

        while (!gameOver) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFallTime > fallDelay) {
                if (board.canMove(currentTetromino, currentTetromino.getPosX(), currentTetromino.getPosY() + 1)) {
                    currentTetromino.moveDown();
                } else {
                    board.placeTetromino(currentTetromino);
                    spawnNewTetromino(); // Спавним новую фигуру
                    if (gameOver) {
                        System.out.println("Game Over!");
                        break;
                    }
                }
                lastFallTime = currentTime;
            }

            displayCurrentTetromino();
            displayNextTetromino(); // Отображаем следующую фигуру

            try {
                if (System.in.available() > 0) {
                    char command = (char) System.in.read();

                    switch (command) {
                        case 'a': // Влево
                            if (board.canMove(currentTetromino, currentTetromino.getPosX() - 1, currentTetromino.getPosY())) {
                                currentTetromino.moveLeft();
                            }
                            break;
                        case 'd': // Вправо
                            if (board.canMove(currentTetromino, currentTetromino.getPosX() + 1, currentTetromino.getPosY())) {
                                currentTetromino.moveRight();
                            }
                            break;
                        case 's': // Вниз (ускоренное падение)
                            if (board.canMove(currentTetromino, currentTetromino.getPosX(), currentTetromino.getPosY() + 1)) {
                                currentTetromino.moveDown();
                            }
                            break;
                        case 'r': // Вращение
                            currentTetromino.rotate();
                            if (!board.canMove(currentTetromino, currentTetromino.getPosX(), currentTetromino.getPosY())) {
                                currentTetromino.rotate(); // Отменяем вращение, если не подходит
                            }
                            break;
                        case 'q': // Выход
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Неверная команда. Пожалуйста, попробуйте снова.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Метод для отображения текущей фигуры на доске
    private void displayCurrentTetromino() {
        if (currentTetromino == null) {
            return;
        }

        int[][] shape = currentTetromino.getShape();
        int x = currentTetromino.getPosX();
        int y = currentTetromino.getPosY();

        int[][] displayBoard = board.getBoardCopy();

        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] != 0) {
                    displayBoard[y + r][x + c] = shape[r][c];
                }
            }
        }

        board.display(displayBoard);
    }

    // Метод для отображения следующей фигуры сбоку от доски
    private void displayNextTetromino() {
        if (nextTetromino == null) {
            return;
        }

        int[][] shape = nextTetromino.getShape();
        System.out.println("Next shape:");

        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                System.out.print(shape[r][c] == 0 ? " " : "█");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        TetrisGame game = new TetrisGame();
        game.play();
    }
}
