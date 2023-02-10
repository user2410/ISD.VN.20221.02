package edu.hust.vn.controller;

import edu.hust.vn.DataStore;
import edu.hust.vn.model.dock.Dock;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReturnController extends BaseController {

    /**
     * filter docks by name or address
     *
     * @return ArrayList[Dock]
     */
    public ArrayList<Dock> searchDockList(String keyword) {
        ArrayList<Dock> res = new ArrayList();
        String lowerKW = keyword.toLowerCase();
        DataStore.getInstance().dockList.forEach(dock -> {
            if(dock.getName().toLowerCase().contains(lowerKW)
                    || dock.getAddress().toLowerCase().contains(lowerKW)) {
                res.add(dock);
            }
        });
        return res;
    }
}
