package edu.hust.vn.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EBRDB {
    private static Connection conn;

    public EBRDB(String dbUrl) throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection(dbUrl);
        System.out.println("Connected to database");
    }

    public Connection getConn(){
        return conn;
    }
}
