import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.*;
import javafx.scene.media.*;
import javafx.scene.layout.StackPane;
import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.*;

public class VideoLoadingScreen extends JFrame {

    private JFXPanel fxPanel;
    private MediaPlayer player;

    public VideoLoadingScreen(String videoPath, Runnable onFinish) {

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fxPanel = new JFXPanel();
        fxPanel.setBackground(Color.BLACK);
        add(fxPanel, BorderLayout.CENTER);

        Platform.runLater(() -> initFX(videoPath, onFinish));
    }
    

    private void initFX(String videoPath, Runnable onFinish) {

        try {
             java.net.URL url = getClass().getResource("/" + videoPath);

if (url == null) {
    System.out.println("❌ Video NOT FOUND: " + videoPath);
    stopAndContinue(onFinish);
    return;
}

Media media = new Media(url.toExternalForm());

            player = new MediaPlayer(media);
            MediaView view = new MediaView(player);

            StackPane root = new StackPane(view);
            root.setStyle("-fx-background-color: black;");

            Scene scene = new Scene(root);

            view.fitWidthProperty().bind(scene.widthProperty());
            view.fitHeightProperty().bind(scene.heightProperty());
            view.setPreserveRatio(false);

            scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ESCAPE) {
                    stopAndContinue(onFinish);
                }
            });

            fxPanel.setScene(scene);

            player.setOnReady(() -> {
                SwingUtilities.invokeLater(() -> {
                    setVisible(true);
                    player.play();
                });
            });

            player.setOnEndOfMedia(() -> stopAndContinue(onFinish));

            player.setOnError(() -> {
                System.out.println("Media Error: " + player.getError());
                stopAndContinue(onFinish);
            });

        } catch (Exception e) {
            e.printStackTrace();
            stopAndContinue(onFinish);
        }
    }

    private void stopAndContinue(Runnable onFinish) {
        if (player != null) {
            player.stop();
        }

        SwingUtilities.invokeLater(() -> {
            if (onFinish != null) onFinish.run();
            dispose();
        });
    }
}