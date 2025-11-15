import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(400, 400, 320, 345);
        add(new GameField());
        setVisible(true);
    }

    static void main() {
        MainWindow mw = new MainWindow();
    }
}
