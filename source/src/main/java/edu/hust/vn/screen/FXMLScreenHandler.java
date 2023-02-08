package edu.hust.vn.screen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class FXMLScreenHandler {
    protected FXMLLoader loader;

    protected Parent content;

    public FXMLScreenHandler(String screenPath) throws IOException {
        this.loader = new FXMLLoader(getClass().getResource(screenPath));
        this.loader.setController(this);
        this.content = loader.load();
    }

    public Parent getContent() {
        return content;
    }
}
