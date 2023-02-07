package edu.hust.vn;

import edu.hust.vn.controller.ReturnController;
import edu.hust.vn.entity.Bike;
import edu.hust.vn.entity.Invoice;
import edu.hust.vn.entity.Rental;
import edu.hust.vn.utils.Configs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static Stage mainWindow;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindow = primaryStage;
        try{
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Configs.SPLASH_SCREEN_PATH));
//            Parent root = fxmlLoader.load();
//            Scene scene = new Scene(root, 600, 400);
//            primaryStage.initStyle(StageStyle.UNDECORATED);
//            primaryStage.setScene(scene);
//            primaryStage.show();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Configs.RETURN_SCREEN_PATH));
            ReturnController returnController = new ReturnController(
                    new Bike(1, "18G-12345", 120000),
                    new Rental(1, 1, 0),
                    new Invoice(1, 1)
            );
            fxmlLoader.setController(returnController);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 600);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}