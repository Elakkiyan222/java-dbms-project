import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class OrderItemsDialog extends JDialog {

    public OrderItemsDialog(int orderId) {

        setTitle("Order Items - " + orderId);
        setSize(600, 300);
        setLocationRelativeTo(null);

        JTable table = new JTable();
        add(new JScrollPane(table));

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "SELECT p.product_name, oi.quantity " +
                "FROM order_items oi " +
                "JOIN product p ON oi.product_id=p.product_id " +
                "WHERE oi.order_id=?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, orderId);

            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel(
                new String[]{"Product", "Quantity"}, 0
            );

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("product_name"),
                    rs.getInt("quantity")
                });
            }

            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}