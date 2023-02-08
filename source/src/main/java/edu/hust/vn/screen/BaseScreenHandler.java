package edu.hust.vn.screen;

import edu.hust.vn.DataStore;
import edu.hust.vn.controller.BaseController;
import edu.hust.vn.screen.home.HomeScreenHandler;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public abstract class BaseScreenHandler extends FXMLScreenHandler{

    private Scene scene;
    private static final Stage stage;
    static
    {
        stage = new Stage();
        stage.setOnCloseRequest(e -> {
            e.consume();
            try {
                DataStore.getInstance().dbConn.close();
                System.out.println("Shutdown database");
            } catch (SQLException ex) {
                System.err.println("Failed to shutdown database");
                ex.printStackTrace();
            }
            Platform.exit();
        });
    }
    protected static volatile HomeScreenHandler homeScreenHandler;
    private BaseScreenHandler prevScreenHandler;
    private BaseController baseController;

    public BaseScreenHandler(String screenPath) throws IOException {
        super(screenPath);
    }

    public void show(){
        if(this.scene == null){
            this.scene = new Scene(this.content);
        }
        onShow();
        stage.setScene(this.scene);
        stage.show();
    }

    public abstract void onShow();

    public void setScreenTitle(String string) {
        stage.setTitle(string);
    }

    public void setPrevScreenHandler(BaseScreenHandler prevScreenHandler) {
        this.prevScreenHandler = prevScreenHandler;
    }

    public BaseScreenHandler getPrevScreenHandler() {
        return prevScreenHandler;
    }

    public BaseController getBaseController() {
        return baseController;
    }

    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

}
