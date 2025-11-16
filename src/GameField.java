import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    // Размеры
    private final int DOT_SIZE = 16;  // Размер клетки в пикселях
    private final int GRID_SIZE = 20; // Ширина поля в клетках
    private final int FIELD_SIZE = GRID_SIZE * DOT_SIZE; // Ширина поля в пикселях
    private final int ALL_DOTS = GRID_SIZE * GRID_SIZE;  // Количество клеток в поле
    // Изображения точки и яблока
    private Image dot;
    private Image apple;
    // Координаты яблока
    private int appleX;
    private int appleY;
    // Массивы позиций x и y соответственно
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;    // Размер змейки
    private Timer timer; // Таймер
    // Движение в сторону
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    // Пауза
    private boolean paused = false;
    // Змейка в игре
    private boolean inGame = true;
    // Счёт
    private int score = 0;

    // Конструктор по умолчанию
    public GameField() {
        setBackground(Color.black); // Цвет игрового поля
        setPreferredSize(new Dimension(FIELD_SIZE, FIELD_SIZE));
        loadImages();               // Загрузка изображений
        initGame();                 // Инициализация игры
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    // Создание игры
    public void initGame() {
        dots = 3; // Начальный размер змейки
        score = 0;

        // Начальная позиция змейки
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }

        timer = new Timer(125, this); // Период [мс]
        timer.start();
        createApple();
    }

    public void createApple() {
        appleX = new Random().nextInt(GRID_SIZE) * DOT_SIZE;
        appleY = new Random().nextInt(GRID_SIZE) * DOT_SIZE;
    }

    // Загрузка изображений
    public void loadImages() {
        ImageIcon iiapple = new ImageIcon("apple.png");
        apple = iiapple.getImage();
        ImageIcon iidot = new ImageIcon("dot.png");
        dot = iidot.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            // Отображение счёта
            g.setColor(Color.white);
            g.drawString("Score: " + score, 10, 15);

            // Отрисовка яблока и змейки
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
            if (paused) {
                g.setColor(Color.white);
                g.drawString("PAUSED", FIELD_SIZE / 2 - 30, FIELD_SIZE / 2);
            }
        } else {
            String gameOverStr = "Game Over";
            String finalScoreStr = "Final Score: " + score;
            g.setColor(Color.white);
            g.drawString(gameOverStr, FIELD_SIZE / 2 - 40, FIELD_SIZE / 2 - 10);
            g.drawString(finalScoreStr, FIELD_SIZE / 2 - 50, FIELD_SIZE / 2 + 10);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) x[0] -= DOT_SIZE;
        if (right) x[0] += DOT_SIZE;
        if (up) y[0] -= DOT_SIZE;
        if (down) y[0] += DOT_SIZE;
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            score += 10;
            createApple();
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }

        if (x[0] >= FIELD_SIZE || y[0] >= FIELD_SIZE || x[0] < 0 || y[0] < 0) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame && !paused) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_SPACE) {
                paused = !paused;
            }
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                left = false;
                right = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                left = false;
                right = false;
            }
        }
    }
}
