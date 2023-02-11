package edu.hust.vn.model.bike;

import edu.hust.vn.DataStore;
import edu.hust.vn.model.DAO;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.Lock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BikeDAO extends DAO {
    public BikeDAO(Connection conn) {
        super(conn);
    }

    public Bike findByID(int id) throws SQLException{
        Bike bike = null;
        try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM \"Bike\" WHERE id=?")){
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()){
                    String type = resultSet.getString("type");
                    if(type.equals("STANDARD_BIKE")){
                        bike = new StandardBike();
                    }else if(type.equals("TWIN_BIKE")){
                        bike = new TwinBike();
                    }else{
                        PreparedStatement ebikeStmt = conn.prepareStatement("SELECT \"batteryLife\" FROM \"EBike\" WHERE id=? LIMIT 1");
                        ebikeStmt.setInt(1, id);
                        ResultSet res = ebikeStmt.executeQuery();
                        res.next();
                        bike = new StandardEBike(res.getInt("batteryLife"));
                    }
                    bike.setId(id);
                    bike.setLicensePlate(resultSet.getString("licensePlate"));
                    bike.setPrice(resultSet.getInt("price"));
                }
            }
        }
        return bike;
    }

    public List<Bike> findAll() throws SQLException {
        List<Bike> bikes = new ArrayList<>();
        try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM \"Bike\"")){
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    Bike bike;
                    String type = resultSet.getString("type");
                    if(type.equals("STANDARD_BIKE")){
                        bike = new StandardBike();
                    }else if(type.equals("TWIN_BIKE")){
                        bike = new TwinBike();
                    }else{
                        PreparedStatement ebikeStmt = conn.prepareStatement("SELECT \"batteryLife\" FROM \"EBike\" WHERE id=? LIMIT 1");
                        ebikeStmt.setInt(1, id);
                        ResultSet res = ebikeStmt.executeQuery();
                        res.next();
                        bike = new StandardEBike(res.getInt("batteryLife"));
                    }
                    bike.setId(id);
                    bike.setLicensePlate(resultSet.getString("licensePlate"));
                    bike.setPrice(resultSet.getInt("price"));
                    bikes.add(bike);
                }
            }
        }
        return bikes;
    }
}
