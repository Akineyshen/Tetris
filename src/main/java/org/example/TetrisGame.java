package org.example;

import java.io.IOException;
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
        int shapeType = random.nextInt(7); // 0 для I, 1 для O (добавьте больше фигур по мере необходимости)
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
        long lastFallTime = System.currentTimeMillis(); // Таймер для автоматического падения
        int fallDelay = 500; // Задержка в миллисекундах

        while (!gameOver) {
            // Автоматическое падение фигуры
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFallTime > fallDelay) {
                if (board.canMove(currentTetromino, currentTetromino.getPosX(), currentTetromino.getPosY() + 1)) {
                    currentTetromino.moveDown(); // Перемещение вниз
                } else {
                    board.placeTetromino(currentTetromino); // Фиксация на поле
                    currentTetromino = null; // Подготовка для следующей фигуры
                    spawnNewTetromino(); // Создаем новую фигуру
                    if (gameOver) { // Проверка, если новая фигура не влезла
                        System.out.println("Game Over!");
                        break;
                    }
                }
                lastFallTime = currentTime; // Обновление времени
            }

            // Отображение текущего состояния поля
            displayCurrentTetromino();

            // Обработка пользовательского ввода без блокировки
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

            // Небольшая задержка для плавности обновлений
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
}

