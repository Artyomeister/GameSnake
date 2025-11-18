import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private GameField gameField;
    private JLabel scoreLabel;
    private Timer scoreUpdateTimer;

    public MainWindow() {
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Создаём основную панель с BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Создаём игровое поле
        gameField = new GameField();

        // Создаем верхнюю панель для счета
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(Color.DARK_GRAY);
        scorePanel.setPreferredSize(new Dimension(320, 30));
        scorePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scorePanel.add(scoreLabel);

        // Добавляем компоненты на основную панель
        mainPanel.add(scorePanel, BorderLayout.NORTH);
        mainPanel.add(gameField, BorderLayout.CENTER);

        // Добавляем основную панель в окно
        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        // Таймер для обновления счета
        scoreUpdateTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateScore();
            }
        });
        scoreUpdateTimer.start();
    }

    private void updateScore() {
        scoreLabel.setText("Score: " + gameField.getScore());
        if (!gameField.isInGame()) {
            scoreUpdateTimer.stop();
        }
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }
}
