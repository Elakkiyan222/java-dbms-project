import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JFrame {

    public LoadingScreen() {
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel label = new JLabel(
            new ImageIcon("videos/loadingscreen.gif")
        );

        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);

        setVisible(true);
    }
}