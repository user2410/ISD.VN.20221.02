package edu.hust.vn;

import edu.hust.vn.screen.SplashScreenHandler;
import edu.hust.vn.utils.Configs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Configs.SPLASH_SCREEN_PATH));
            fxmlLoader.setController(new SplashScreenHandler());
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 400);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}