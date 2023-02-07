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
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class HomeScreenHandler extends BaseScreenHandler{

    @FXML
    private ImageView erbImage;

    @FXML
    private ImageView returnBikeImg;

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
        dockList.addListener((ListChangeListener<Dock>) c -> {
            // update homeItems
            // find which item in homeItems but not in dockList => remove from homeItems
            for(DockCardHandler dc : homeItems){
                if(DataStore.getInstance().getDockById(dc.getDockID()) == null){
                    homeItems.remove(dc);
                }
            }
            // find which item in dockList but not in homeItems => add to homeList
            for(Dock dock : dockList){
                boolean found = false;
                for(DockCardHandler dc : homeItems){
                    if(dc.getDockID() == dock.getId()){
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    try {
                        homeItems.add(new DockCardHandler(Configs.DOCK_CARD_PATH, dock));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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

        });

        returnBikeImg.setOnMouseClicked(e -> {

        });

        rentedBikeImg.setOnMouseClicked(e -> {

        });
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
