package edu.hust.vn.screen.home;

import edu.hust.vn.DataStore;
import edu.hust.vn.controller.HomeController;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.utils.Configs;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomeScreenHandler extends BaseScreenHandler{

    @FXML
    private ImageView erbImage;

    @FXML
    private TextField searchInput;

    @FXML
    private ImageView rentedBikeImg;

    @FXML
    private FlowPane docksView;

    private ObservableList<DockCardHandler> homeItems;

    private HomeScreenHandler(String screenPath) throws IOException {
        super(screenPath);

        homeItems = FXCollections.observableArrayList();

        HomeController homectl = new HomeController();
        setBaseController(homectl);

        ObservableList<Dock> dockList = homectl.getAllDocks();

        try{
            for(Dock dock : dockList){
                DockCardHandler dockCardHandler = new DockCardHandler(Configs.DOCK_CARD_PATH, dock);
                this.homeItems.add(dockCardHandler);
                docksView.getChildren().add(dockCardHandler.getContent());
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        homeItems.addListener((ListChangeListener<DockCardHandler>) c -> {
            docksView.getChildren().clear();
            for(DockCardHandler dc : homeItems){
                docksView.getChildren().add(dc.getContent());
            }
        });

        erbImage.setOnMouseClicked(e -> {
            try {
                DataStore.getInstance().dockDAO.updateDockList();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        rentedBikeImg.setOnMouseClicked(e -> {

        });

        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() == 0){
                // reset dock view
                for(int i=0; i<homeItems.size(); i++){
                    DockCardHandler dc = homeItems.get(i);
                    boolean old = dc.getShow();
                    dc.setShow(true);
                    if(old == false) docksView.getChildren().add(i, dc.getContent());
                };
                return;
            }
            ArrayList<Dock> res = ((HomeController)this.getBaseController()).searchDockList(newValue);
            docksView.getChildren().clear();
            for(DockCardHandler dc : homeItems){
                for(Dock d : res){
                    if(d.getId() == dc.getDockID()){
                        docksView.getChildren().add(dc.getContent());
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void onShow() {
        docksView.requestFocus();
    }

    public static HomeScreenHandler getHomeScreenHandler() throws IOException {
        if(homeScreenHandler == null){
            synchronized (HomeScreenHandler.class){
                if(homeScreenHandler == null){
                    homeScreenHandler = new HomeScreenHandler(Configs.HOME_SCREEN_PATH);
                }
            }
        }
        return homeScreenHandler;
    }
}
