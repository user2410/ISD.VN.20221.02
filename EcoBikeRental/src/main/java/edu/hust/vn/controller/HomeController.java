package edu.hust.vn.controller;

import edu.hust.vn.DataStore;
import edu.hust.vn.model.dock.Dock;
import javafx.collections.ObservableList;

import java.sql.SQLException;
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
}
