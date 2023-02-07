package edu.hust.vn.controller;

import edu.hust.vn.DataStore;
import edu.hust.vn.model.dock.Dock;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeController extends BaseController {
    /**
     * this methods get all Docks in Database
     *
     * @return List[Dock]
     * @throws java.sql.SQLException
     */
    public ObservableList<Dock> getAllDocks() {
        return DataStore.getInstance().dockList;
    }

    /**
     * filter docks by name or address
     *
     * @return ArrayList[Dock]
     */
    public ArrayList<Dock> searchDockList(String keyword) {
        ArrayList<Dock> res = new ArrayList();
        DataStore.getInstance().dockList.forEach(dock -> {
            if(dock.getName().contains(keyword) || dock.getAddress().contains(keyword)) res.add(dock);
        });
        return res;
    }
}
