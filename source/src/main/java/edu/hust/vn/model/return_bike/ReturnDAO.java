package edu.hust.vn.model.return_bike;

import edu.hust.vn.model.DAO;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.Lock;
import edu.hust.vn.model.rental.Rental;

import java.sql.Connection;
import java.time.LocalDateTime;

public class ReturnDAO extends DAO {
    public ReturnDAO(Connection conn) {
        super(conn);
    }

    public Return create(Dock dock, Lock lock, Rental rental, Bike bike, LocalDateTime returnTime){
        Return returnBike = Return.getInstance();

        returnBike.setId(1);
        returnBike.setDock(dock);
        returnBike.setLock(lock);
        returnBike.setRental(rental);
        returnBike.setBike(bike);
        returnBike.setReturnTime(returnTime);
        return returnBike;
    }
}
