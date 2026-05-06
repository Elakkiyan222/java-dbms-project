import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class OrderDetailsPage extends JFrame {

    private int orderId;
    private JTable table;
    private CurrentOrdersPage parent;

    public OrderDetailsPage(int orderId, CurrentOrdersPage parent) {
        this.orderId = orderId;
        this.parent = parent;

        setTitle("Order Details - " + orderId);
        setSize(650, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (parent != null) parent.setVisible(true);
            }
        });

        table = new JTable(new DefaultTableModel(
                new Object[]{"Delivered", "Product", "Quantity", "Price"}, 0
        ) {
            public Class<?> getColumnClass(int column) {
                if (column == 0) return Boolean.class;
                return Object.class;
            }

            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        table.getModel().addTableModelListener(e -> {
            if (e.getColumn() == 0) {
                int row = e.getFirstRow();
                boolean checked = (boolean) table.getValueAt(row, 0);
                updateItemDelivered(row, checked);
            }
        });

        JButton btn = new JButton("Check Order Delivered");
        btn.addActionListener(e -> checkOrderDelivered());
        add(btn, BorderLayout.SOUTH);

        loadOrderDetails();
    }

    private void loadOrderDetails() {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT p.product_id, p.product_name, oi.quantity, oi.price, oi.delivered " +
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
                        rs.getBoolean("delivered"),  
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getInt("product_id") 
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateItemDelivered(int row, boolean delivered) {
        try {
            Connection con = DBConnection.getConnection();

            String productName = table.getValueAt(row, 1).toString();

            String sql = "UPDATE order_items oi " +
                         "JOIN product p ON oi.product_id = p.product_id " +
                         "SET oi.delivered=? " +
                         "WHERE oi.order_id=? AND p.product_name=?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setBoolean(1, delivered);
            pst.setInt(2, orderId);
            pst.setString(3, productName);

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkOrderDelivered() {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) AS total, " +
                         "SUM(CASE WHEN delivered = TRUE THEN 1 ELSE 0 END) AS done " +
                         "FROM order_items WHERE order_id=?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, orderId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                int done = rs.getInt("done");

                if (done == 0) {
                    JOptionPane.showMessageDialog(this, "No items delivered!");
                    return;
                }

                if (done < total) {
                    JOptionPane.showMessageDialog(this, "Order is PARTIALLY delivered!");
                    return;
                }

                String update = "UPDATE orders SET status='Delivered' WHERE order_id=?";
                PreparedStatement up = con.prepareStatement(update);
                up.setInt(1, orderId);
                up.executeUpdate();

                JOptionPane.showMessageDialog(this, "Order Fully Delivered ✅");

                if (parent != null) {
                    parent.loadOrders();
                    parent.setVisible(true);
                }

                dispose();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}