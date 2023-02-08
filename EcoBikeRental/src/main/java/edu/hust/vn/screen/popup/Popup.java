package edu.hust.vn.screen.popup;

import edu.hust.vn.screen.FXMLScreenHandler;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public abstract class Popup extends FXMLScreenHandler {

    protected static Stage stage;
    static {
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
    }
    private Scene scene;

    public Popup(String screenPath) throws IOException {
        super(screenPath);
    }

    public void show(){
        if(this.scene == null){
            this.scene = new Scene(this.content);
        }
        stage.setScene(this.scene);
        stage.show();
    }
}
