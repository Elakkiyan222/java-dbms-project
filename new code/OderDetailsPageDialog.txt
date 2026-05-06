import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class OrderDetailsPageDialog extends JDialog {

    private JTable table;

    public OrderDetailsPageDialog(Frame parent, int orderId) {
        super(parent, "Order Details - " + orderId, true);

        setSize(500, 350);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        table = new JTable(new DefaultTableModel(
                new Object[]{"Product", "Quantity", "Price"}, 0
        ));

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadOrderDetails(orderId);
    }

    private void loadOrderDetails(int orderId) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT p.product_name, oi.quantity, p.price " +
                         "FROM order_items oi " +
                         "JOIN product p ON oi.product_id = p.product_id " +
                         "WHERE oi.order_id=?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, orderId);

            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}