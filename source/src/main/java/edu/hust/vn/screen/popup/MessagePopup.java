package edu.hust.vn.screen.popup;

import edu.hust.vn.screen.home.HomeScreenHandler;
import edu.hust.vn.utils.Configs;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

public class MessagePopup extends Popup {

    @FXML
    private ImageView iconImg;

    @FXML
    private Label msg;

    @FXML
    private Button okBtn;

    private static MessagePopup instance;

    private Image errorIconImage;
    private Image infoIconImage;

    private MessagePopup() throws IOException {
        super(Configs.POPUP_MSG_SCREEN_PATH);
        okBtn.setOnMouseClicked(e -> {
            stage.close();
        });

        File file = new File(Configs.IMAGE_PATH+"/icons/"+ "error.png");
        errorIconImage = new Image(file.toURI().toString());
        file = new File(Configs.IMAGE_PATH+"/icons/"+ "info.png");
        infoIconImage = new Image(file.toURI().toString());
    }

    public static MessagePopup getInstance() throws IOException {
        if(instance == null){
            synchronized (MessagePopup.class){
                if(instance == null){
                    instance = new MessagePopup();
                }
            }
        }
        return instance;
    }

    public void show(String msg, boolean isErr){
        this.msg.setText(msg);
        iconImg.setImage(isErr ? errorIconImage : infoIconImage);
        super.show();
    }
}
