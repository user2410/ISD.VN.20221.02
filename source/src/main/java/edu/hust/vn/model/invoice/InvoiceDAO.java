package edu.hust.vn.model.invoice;

import edu.hust.vn.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class InvoiceDAO extends DAO {

    public InvoiceDAO(Connection conn) {
        super(conn);
    }

    public void saveInvoice(Invoice invoice) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO \"Invoice\"(rid, rtype, \"totalAmount\") VALUES (?, ?, ?)")){
            stmt.setInt(1, invoice.getRental().getId());
            stmt.setObject(2, invoice.getType(), Types.OTHER);
            stmt.setInt(3, invoice.getAmount());
            stmt.executeUpdate();
        }
    }
}
