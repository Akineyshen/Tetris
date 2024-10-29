package org.example;

import java.util.Random;
import java.util.Scanner;

public class TetrisGame {
    private boolean gameOver = false; // Переменная для отслеживания состояния игры

    private Board board;
    private Shape currentTetromino;
    private Random random;
    private Scanner scanner;

    public TetrisGame() {
        board = new Board();
        random = new Random();
        scanner = new Scanner(System.in);
        spawnNewTetromino();
    }

    private void spawnNewTetromino() {
        // Здесь можно добавлять логику для разных фигур
        int shapeType = random.nextInt(2); // 0 для I, 1 для O (добавьте больше фигур по мере необходимости)
        switch (shapeType) {
            case 0: currentTetromino = new IShape(); break; // Прямоугольная фигура
            case 1: currentTetromino = new JShape(); break; // Фигура J
            case 2: currentTetromino = new LShape(); break; // Фигура L
            case 3: currentTetromino = new OShape(); break; // Квадрат
            case 4: currentTetromino = new SShape(); break; // Фигура S
            case 5: currentTetromino = new TShape(); break; // Фигура T
            case 6: currentTetromino = new ZShape(); break; // Фигура Z
            default: currentTetromino = new IShape(); break; // На всякий случай
        }
        // Установка начальных координат
        currentTetromino.posX = board.getWidth() / 2 - 1; // Центрируем по X
        currentTetromino.posY = 0; // Начальная позиция по Y
        // Проверка, если новая фигура не может быть размещена на верхней позиции
        if (!board.canMove(currentTetromino, currentTetromino.getPosX(), currentTetromino.getPosY())) {
            gameOver = true; // Устанавливаем состояние игры в "проигрыш"
        }
    }

    public void play() {
        while (!gameOver) {
            // Проверяем, если текущая фигура равна null, спавним новую
            if (currentTetromino == null) {
                spawnNewTetromino();
            }
            // Отображение текущей фигуры
            displayCurrentTetromino();

            System.out.println("Select action: \na: ←, d: →, s: ↓, r: ↻, q: ❌): ");
            String command = scanner.nextLine();

            switch (command) {
                case "a": // Влево
                    if (board.canMove(currentTetromino, currentTetromino.getPosX() - 1, currentTetromino.getPosY())) {
                        currentTetromino.moveLeft();
                    }
                    break;
                case "d": // Вправо
                    if (board.canMove(currentTetromino, currentTetromino.getPosX() + 1, currentTetromino.getPosY())) {
                        currentTetromino.moveRight();
                    }
                    break;
                case "s": // Вниз
                    if (board.canMove(currentTetromino, currentTetromino.getPosX(), currentTetromino.getPosY() + 1)) {
                        currentTetromino.moveDown();
                    } else {
                        board.placeTetromino(currentTetromino); // Фиксируем фигуру на поле
                        currentTetromino = null; // Устанавливаем текущую фигуру в null для создания новой
                    }
                    break;
                case "r": // Вращение
                    currentTetromino.rotate();
                    if (!board.canMove(currentTetromino, currentTetromino.getPosX(), currentTetromino.getPosY())) {
                        // Возвращаем фигуру в прежнее состояние, если вращение невозможно
                        currentTetromino.rotate(); // Можно добавить логику для отмены вращения
                    }
                    break;
                case "q": // Выход
                    System.exit(0);
                    break;
                default:
                    System.out.println("The wrong command. Please choose the right command.");
            }

            // Обновляем отображение доски после каждого ввода
            displayCurrentTetromino();

            // Задержка между обновлениями (если требуется)
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Метод для отображения текущей фигуры на доске
    private void displayCurrentTetromino() {
        if (currentTetromino == null) {
            return; // Проверка на null, чтобы избежать NullPointerException
        }

        int[][] shape = currentTetromino.getShape();
        int x = currentTetromino.getPosX();
        int y = currentTetromino.getPosY();

        // Копируем текущую доску и добавляем в неё текущую фигуру для отображения
        int[][] displayBoard = board.getBoardCopy();

        // Добавляем фигуру в отображение
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] != 0) {
                    displayBoard[y + r][x + c] = shape[r][c];
                }
            }
        }
        // Выводим временную доску с текущей фигурой
        board.display(displayBoard);
    }


    public static void main(String[] args) {
        TetrisGame game = new TetrisGame();
        game.play();
    }

    public void start() {
        spawnNewTetromino();
    }

    public Shape getCurrentTetromino() {
        return currentTetromino;
    }

    public Board getBoard() {
        return board;
    }
}

