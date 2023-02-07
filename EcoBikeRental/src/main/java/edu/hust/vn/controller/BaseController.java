package edu.hust.vn.controller;

import edu.hust.vn.DataStore;

import java.sql.SQLException;

public class BaseController {
    public void updateData() throws SQLException {
        DataStore.getInstance().dockDAO.updateDockList();
    }
}
