package edu.hust.vn.screen.factory;

import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.screen.home.DockCardHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.io.IOException;

public class DockCardFactory {
    private static ObservableMap<Dock, DockCardHandler> dockCards = FXCollections.observableHashMap();

    public static DockCardHandler getInstance(Dock dock) throws IOException {
        if(!dockCards.containsKey(dock)){
            synchronized (DockCardHandler.class){
                if(!dockCards.containsKey(dock)){
                    dockCards.put(dock, new DockCardHandler(dock));
                }
            }
        }
        return dockCards.get(dock);
    }
}
