package edu.hust.vn.model;

import edu.hust.vn.model.dock.DockDAO;
import edu.hust.vn.model.dock.LockDAO;

import java.sql.Connection;

public class DAO {
    protected Connection conn;

    public DAO(Connection conn){
        this.conn = conn;
    }
}
