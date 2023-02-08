package edu.hust.vn.screen.popup;

import edu.hust.vn.screen.FXMLScreenHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public abstract class Popup extends FXMLScreenHandler {

    protected static Stage stage = new Stage();
    private Scene scene;

    public Popup(String screenPath) throws IOException {
        super(screenPath);
        stage.initStyle(StageStyle.UNDECORATED);
    }

    public void show(){
        if(this.scene == null){
            this.scene = new Scene(this.content);
        }
        stage.setScene(this.scene);
        stage.show();
    }
}
