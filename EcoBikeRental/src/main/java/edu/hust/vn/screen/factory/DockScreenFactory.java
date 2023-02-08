package edu.hust.vn.screen.factory;

import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.screen.dock.DockScreenHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.io.IOException;

public class DockScreenFactory {
    private static ObservableMap<Dock, DockScreenHandler> dockScreens = FXCollections.observableHashMap();

    public static DockScreenHandler getInstance(Dock dock) throws IOException {
        if(!dockScreens.containsKey(dock)){
            synchronized (DockScreenHandler.class){
                if(!dockScreens.containsKey(dock)){
                    dockScreens.put(dock, new DockScreenHandler(dock));
                }
            }
        }
        return dockScreens.get(dock);
    }
}
