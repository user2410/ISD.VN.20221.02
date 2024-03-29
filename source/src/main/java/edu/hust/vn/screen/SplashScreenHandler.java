package edu.hust.vn.screen;

import edu.hust.vn.DataStore;
import edu.hust.vn.screen.home.HomeScreenHandler;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenHandler implements Initializable {

    @FXML
    VBox splash_root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load splash screen with fade in effect
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), splash_root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        // Finish splash with fade out effect
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), splash_root);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);

        // After fade in, start fade out
        fadeIn.play();
        fadeIn.setOnFinished((e) -> {
            // Initializing data
            DataStore.getInstance();
            fadeOut.play();
        });

        // After fade out, load actual content
        fadeOut.setOnFinished((e) -> {
            Stage currentStage = (Stage) splash_root.getScene().getWindow();
            currentStage.close();
            try {
                HomeScreenHandler.getInstance().show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
