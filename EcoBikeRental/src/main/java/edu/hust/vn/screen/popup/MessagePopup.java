package edu.hust.vn.screen.popup;

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

    private String message;

    public MessagePopup(String msg, boolean isErr) throws IOException {
        super(Configs.POPUP_MSG_SCREEN_PATH);
        this.message = msg;
        okBtn.setOnMouseClicked(e -> {
            stage.close();
        });
        this.msg.setText(msg);
        File file = new File(Configs.IMAGE_PATH+"/icons/"+(isErr ? "error.png" : "info.png"));
        Image image = new Image(file.toURI().toString());
        iconImg.setImage(image);
    }


}
