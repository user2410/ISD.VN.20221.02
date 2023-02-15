package edu.hust.vn.db;

import edu.hust.vn.DataStore;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Lock;

import java.sql.*;
import java.util.Random;
import java.util.UUID;

public class Seed {

    private static final String[] BIKE_TYPES = {"STANDARD_BIKE", "STANDARD_EBIKE", "TWIN_BIKE"};

    private static final Random RANDOM = new Random();

    public static String randomUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("_", "");
    }

    public static int randomInt(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }

    private static void generateBikes(int count) {
        Connection conn = DataStore.getInstance().dbConn;
        try {
            PreparedStatement psBike = conn.prepareStatement("INSERT INTO \"Bike\" (\"licensePlate\", price, type) VALUES (?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement psEBike = conn.prepareStatement("INSERT INTO \"EBike\" (id, \"batteryLife\") VALUES (?, ?)");
            for (int i = 0; i < count; i++) {
                String type = BIKE_TYPES[RANDOM.nextInt(BIKE_TYPES.length)];
                psBike.setString(1, "Bike" + i);
                psBike.setInt(2, RANDOM.nextInt(1000) + 1);
                psBike.setObject(3, type, Types.OTHER);
                psBike.executeUpdate();
                if (type.equals("STANDARD_EBIKE")) {
                    ResultSet rs = psBike.getGeneratedKeys();
                    if (rs.next()) {
                        long bikeId = rs.getLong(1);
                        psEBike.setLong(1, bikeId);
                        psEBike.setFloat(2, RANDOM.nextFloat() * 100);
                        psEBike.executeUpdate();
                    }
                }
            }
            psBike.close();
            psEBike.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void generateLocks(int count) {
        Connection conn = DataStore.getInstance().dbConn;
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO \"Lock\" (\"barCode\", \"bikeId\", \"dockId\", status) VALUES (?, ?, ?, ?)");
            for (int i = 0; i < count; i++) {
                int bikeId = (i + 1);
                ps.setString(1, randomUUID());
                ps.setInt(2, bikeId);
                ps.setInt(3, RANDOM.nextInt(50) + 1);
                ps.setObject(4, Lock.LOCK_STATE.LOCKED, Types.OTHER);
                ps.executeUpdate();
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void generateDocks(int count) {
        Connection conn = DataStore.getInstance().dbConn;
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO \"Dock\" (name, address, area, capacity) VALUES (?, ?, ?, ?)");
            for (int i = 0; i < count; i++) {
                ps.setString(1, "Dock" + i);
                ps.setString(2, "Address" + i);
                ps.setFloat(3, RANDOM.nextFloat() * 100);
                ps.setInt(4, RANDOM.nextInt(50) + 1);
                ps.executeUpdate();
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void genAll(){
        generateBikes(100);
        generateDocks(50);
        generateLocks(100);
    }
}
