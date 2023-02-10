package edu.hust.vn.model.dock;

import edu.hust.vn.DataStore;
import edu.hust.vn.model.DAO;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DockDAO extends DAO {
    public DockDAO(Connection conn) {
        super(conn);
    }

    public Dock findById(int dockId) throws SQLException {
        Dock dock = null;
        try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM \"Dock\" WHERE id = ?")){
            statement.setInt(1, dockId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()){
                    dock = new Dock();
                    dock.setId(resultSet.getInt("id"));
                    dock.setName(resultSet.getString("name"));
                    dock.setAddress(resultSet.getString("address"));
                    dock.setArea(resultSet.getFloat("area"));
                    dock.setCapacity(resultSet.getInt("capacity"));
                    dock.setLocks(DataStore.getInstance().lockDAO.findAllFromDock(dock.getId()));
                }
            }
        }
        return dock;
    }

    public List<Dock> findAll() throws SQLException {
        List<Dock> docks = new ArrayList<>();
        try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM \"Dock\"")){
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    Dock dock = new Dock();
                    dock.setId(resultSet.getInt("id"));
                    dock.setName(resultSet.getString("name"));
                    dock.setAddress(resultSet.getString("address"));
                    dock.setArea(resultSet.getFloat("area"));
                    dock.setCapacity(resultSet.getInt("capacity"));
                    dock.setLocks(DataStore.getInstance().lockDAO.findAllFromDock(dock.getId()));
                    docks.add(dock);
                }
            }
        }
        return docks;
    }

    public void updateDockList() throws SQLException {
        System.out.println("update");
        ObservableList<Dock> dockList = DataStore.getInstance().dockList;
        Dock dock = new Dock();
        try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM \"Dock\"")){
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    int currentId = resultSet.getInt("id");
                    String currentName = resultSet.getString("name");
                    String currentAddress = resultSet.getString("address");
                    float currentArea = resultSet.getFloat("area");
                    int currentCapacity = resultSet.getInt("capacity");
                    List<Lock> locks = DataStore.getInstance().lockDAO.findAllFromDock(currentId);
                    Dock currentDock = null;
                    for(Dock d : dockList){
                        if(currentId == d.getId()){
                            currentDock = d;
                            break;
                        }
                    }
                    if(currentDock != null){
                        currentDock.setName(currentName);
                        currentDock.setAddress(currentAddress);
                        currentDock.setArea(currentArea);
                        currentDock.setCapacity(currentCapacity);
                        currentDock.updateLocks(locks);
                    }else{
                        dock.setId(currentId);
                        dock.setName(currentName);
                        dock.setAddress(currentAddress);
                        dock.setArea(currentArea);
                        dock.setCapacity(currentCapacity);
                        dock.setLocks(locks);
                    }
                }
            }
        }
    }
}
