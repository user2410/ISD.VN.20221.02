package edu.hust.vn.screen.home;

import edu.hust.vn.DataStore;
import edu.hust.vn.controller.HomeController;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.utils.Configs;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomeScreenHandler extends BaseScreenHandler{

    @FXML
    private ImageView ebrImage;

    @FXML
    private TextField searchInput;

    @FXML
    private ImageView rentedBikeImg;

    @FXML
    private Label rentedBikeLabel;

    @FXML
    private FlowPane docksView;

    private static HomeScreenHandler instance;
    private ObservableList<DockCardHandler> homeItems;

    private HomeScreenHandler() throws IOException {
        super(Configs.HOME_SCREEN_PATH);

        homeItems = FXCollections.observableArrayList();

        HomeController homectl = new HomeController();
        setBaseController(homectl);

        ObservableList<Dock> dockList = DataStore.getInstance().dockList;

        try{
            for(Dock dock : dockList){
                DockCardHandler dockCardHandler = DockCardHandler.getInstance(dock);
                this.homeItems.add(dockCardHandler);
                docksView.getChildren().add(dockCardHandler.getContent());
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        ebrImage.setOnMouseClicked(e -> {
            try {
                DataStore.getInstance().dockDAO.updateDockList();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        rentedBikeImg.visibleProperty().bind(Bindings.createBooleanBinding(() -> (DataStore.getInstance().rentedBike.get() != null), DataStore.getInstance().rentedBike));
        rentedBikeLabel.visibleProperty().bind(Bindings.createBooleanBinding(() -> (DataStore.getInstance().rentedBike.get() != null), DataStore.getInstance().rentedBike));

        rentedBikeImg.setOnMouseClicked(e -> {

        });

        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() == 0){
                // reset dock view
                for(int i=0; i<homeItems.size(); i++){
                    DockCardHandler dc = homeItems.get(i);
                    boolean old = dc.getShow();
                    dc.setShow(true);
                    if(!old) docksView.getChildren().add(i, dc.getContent());
                }
                return;
            }
            ArrayList<Dock> res = ((HomeController)this.getBaseController()).searchDockList(newValue);
//            System.out.println(res);
            docksView.getChildren().clear();
            for(DockCardHandler dc : homeItems){
                boolean inRes = false;
                for(Dock d : res){
                    if(d.getId() == dc.getDockID()){
                        docksView.getChildren().add(dc.getContent());
                        inRes = true;
                        break;
                    }
                }
                dc.setShow(inRes);
            }
        });
    }

    public static HomeScreenHandler getInstance() throws IOException {
        if(instance == null){
            synchronized (HomeScreenHandler.class){
                if(instance == null){
                    instance = new HomeScreenHandler();
                }
            }
        }
        return instance;
    }

    @Override
    public void onShow() {
        docksView.requestFocus();
    }
}
