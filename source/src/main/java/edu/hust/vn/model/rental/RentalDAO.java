package edu.hust.vn.model.rental;

import edu.hust.vn.model.DAO;
import edu.hust.vn.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class RentalDAO extends DAO {

    public RentalDAO(Connection conn) {
        super(conn);
    }

    public int saveRental(Rental rental) throws SQLException {
        try(PreparedStatement statement = conn.prepareStatement("INSERT INTO \"Rental\"(\"bikeId\", \"rentDockId\", \"rentLockId\", \"startTime\") VALUES (?, ?, ?, ?) RETURNING id;")) {
            statement.setInt(1, rental.getBike().getId());
            statement.setInt(2, rental.getLock().getDockId());
            statement.setInt(3, rental.getLock().getId());
            statement.setTimestamp(4, Utils.localDateTimeToTimeStamp(rental.getStartTime()));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public int saveReturn(int rentalId, int dockId, int lockId, LocalDateTime endTime) throws SQLException {
        try(PreparedStatement statement = conn.prepareStatement("INSERT INTO \"Return\"(\"rentalId\", \"returnDockId\", \"returnLockId\", \"returnTime\") VALUES (?, ?, ?, ?) RETURNING id;")) {
            statement.setInt(1, rentalId);
            statement.setInt(2, dockId);
            statement.setInt(3, lockId);
            statement.setTimestamp(4, Utils.localDateTimeToTimeStamp(endTime));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

}
