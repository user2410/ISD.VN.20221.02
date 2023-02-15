package edu.hust.vn.controller;

import edu.hust.vn.DataStore;
import edu.hust.vn.model.dock.Dock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseController {
    public void updateData() throws SQLException {
        DataStore.getInstance().dockDAO.updateDockList();
    }

    /**
     * filter docks by name or address
     *
     * @return ArrayList[Dock]
     */
    public ArrayList<Dock> searchDockList(List<Dock> dockList, String keyword) {
        ArrayList<Dock> res = new ArrayList();
        String lowerKW = keyword.toLowerCase();
        dockList.forEach(dock -> {
            if(dock.getName().toLowerCase().contains(lowerKW)
                || dock.getAddress().toLowerCase().contains(lowerKW)) {
                res.add(dock);
            }
        });
        return res;
    }
}
