package edu.hust.vn.model.dock;

import edu.hust.vn.DataStore;
import edu.hust.vn.model.DAO;
import edu.hust.vn.model.bike.Bike;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LockDAO extends DAO {
    public LockDAO(Connection conn) {
        super(conn);
    }

    /*
    public Lock findById(int id) throws SQLException {
        Lock lock = null;
        try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM \"Lock\" WHERE id = ? LIMIT 1")){
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()){
                    lock = new Lock();
                    lock.setId(resultSet.getInt("id"));
                    lock.setBarCode(resultSet.getString("barCode"));
                    lock.setDockId(resultSet.getInt("dockId"));
//                    lock.setBike();
                }
            }
        }
        return lock;
    }
    */

    public List<Lock> findAllFromDock(int dockId) throws SQLException {
        List<Lock> locks = new ArrayList<>();
        try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM \"Lock\" WHERE \"dockId\" = ?")){
            statement.setInt(1, dockId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    Lock lock = new Lock();
                    lock.setId(resultSet.getInt("id"));
                    lock.setBarCode(resultSet.getString("barCode"));
                    lock.setDockId(resultSet.getInt("dockId"));
                    int bikeId = resultSet.getInt("bikeId");
                    Bike attachedBike = DataStore.getInstance().getBikeById(bikeId);
                    lock.setBike(attachedBike);
                    if(attachedBike != null){
                        attachedBike.setLock(lock);
                    }
                    locks.add(lock);
                }
            }
        }
        return locks;
    }

    /*
    public int create(Lock lock) throws SQLException {
        try(PreparedStatement statement = conn.prepareStatement("INSERT INTO \"Lock\"(barCode, dockId) VALUES (?, ?)")){
            statement.setString(1, lock.getBarCode());
            statement.setInt(2, lock.getDockId());
            return statement.executeUpdate();
        }
    }
    */

    public int putBike(int lockId) throws SQLException {

        try(PreparedStatement statement = conn.prepareStatement("UPDATE \"Lock\" SET \"bikeId\" = ? , \"status\" = ? WHERE \"id\" = ? ;")){
            statement.setNull(1, Types.BIGINT);
            statement.setObject(2, Lock.LOCK_STATE.RELEASED, Types.OTHER);
            statement.setInt(3, lockId);
            System.out.println(statement.toString());
            return statement.executeUpdate();
        }
    }

    public int addBike(int lockId, int bikeId) throws SQLException {

        try(PreparedStatement statement = conn.prepareStatement("UPDATE \"Lock\" SET \"bikeId\" = ? , \"status\" = ? WHERE \"id\" = ? ;")){
            statement.setInt(1, bikeId);
            statement.setObject(2, Lock.LOCK_STATE.LOCKED, Types.OTHER);
            statement.setInt(3, lockId);
            System.out.println(statement.toString());
            return statement.executeUpdate();
        }
    }
}
