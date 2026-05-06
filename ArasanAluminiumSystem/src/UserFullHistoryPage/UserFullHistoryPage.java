import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class UserFullHistoryPage extends JFrame {

    private int userId;
    private JTable table;

    public UserFullHistoryPage(int userId) {
        this.userId = userId;

        setTitle("User History - " + userId);
        setSize(800, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        table = new JTable(new DefaultTableModel(
                new Object[]{"Order ID","Product","Qty","Price","Date"}, 0
        ));

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadHistory();
    }

    private void loadHistory() {
        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "SELECT o.order_id, p.product_name, oi.quantity, oi.price, o.order_date " +
                "FROM orders o " +
                "JOIN order_items oi ON o.order_id = oi.order_id " +
                "JOIN product p ON oi.product_id = p.product_id " +
                "WHERE o.user_id=?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userId);

            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("order_id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getDate("order_date")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}