import javax.swing.*;

public class BaseFrame extends JFrame {

    public BaseFrame(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}