import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class OrderItemsPage extends JFrame {

    private JTable table;
    private int orderId;

    public OrderItemsPage(int orderId) {

        this.orderId = orderId;

        setTitle("Order Details - Order ID: " + orderId);
        setSize(600, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        table = new JTable(new DefaultTableModel(
                new Object[]{"Product", "Quantity", "Price", "Subtotal"}, 0
        ));

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadOrderItems();
    }

    private void loadOrderItems() {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT p.product_name, oi.quantity, oi.price " +
                         "FROM order_items oi " +
                         "JOIN product p ON oi.product_id = p.product_id " +
                         "WHERE oi.order_id=?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, orderId);

            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            int total = 0;

            while (rs.next()) {
                int qty = rs.getInt("quantity");
                int price = rs.getInt("price");
                int subtotal = qty * price;

                total += subtotal;

                model.addRow(new Object[]{
                        rs.getString("product_name"),
                        qty,
                        price,
                        subtotal
                });
            }
            JLabel totalLabel = new JLabel("Total: ₹" + total);
            totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
            add(totalLabel, BorderLayout.SOUTH);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}