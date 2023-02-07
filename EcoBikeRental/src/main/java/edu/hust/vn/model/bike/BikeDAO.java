package edu.hust.vn.model.bike;

import edu.hust.vn.model.DAO;
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

    public List<Bike> findAll() throws SQLException {
        List<Bike> bikes = new ArrayList<>();
        try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM \"Bike\"")){
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    Bike bike = new Bike();
                    bike.setId(resultSet.getInt("id"));
                    bike.setLicensePlate(resultSet.getString("licensePlate"));
                    bike.setPrice(resultSet.getInt("price"));
                    String type = resultSet.getString("type");
                    if(type.equals("STANDARD_BIKE")){
                        bike.setType(Bike.BIKE_TYPE.STANDARD_BIKE);
                    }else if(type.equals("TWIN_BIKE")){
                        bike.setType(Bike.BIKE_TYPE.TWIN_BIKE);
                    }else{
                        bike.setType(Bike.BIKE_TYPE.STANDARD_EBIKE);
                        PreparedStatement ebikeStmt = conn.prepareStatement("SELECT \"batteryLife\" FROM \"EBike\" WHERE id=? LIMIT 1");
                        ebikeStmt.setInt(1, bike.getId());
                        ResultSet res = ebikeStmt.executeQuery();
                        res.next();
                        bike = new StandardEBike(bike, res.getInt("batteryLife"));
                    }
                    bikes.add(bike);
                }
            }
        }
        return bikes;
    }
}
