import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;

public class ReminderPage extends JFrame {

    JTable table;

    public ReminderPage() {

        setTitle("Customer Purchase Reminder System");
        setSize(1000, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        table = new JTable(new DefaultTableModel(
                new Object[]{"User ID", "Product", "Last Order Date", "Days Gap", "Status"}, 0
        ));

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadReminderData();
    }

    private void loadReminderData() {

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT o.user_id, p.product_name, MAX(o.order_date) AS last_date " +
                    "FROM orders o " +
                    "JOIN order_items oi ON o.order_id = oi.order_id " +
                    "JOIN product p ON oi.product_id = p.product_id " +
                    "GROUP BY o.user_id, p.product_name";

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            while (rs.next()) {

                int userId = rs.getInt("user_id");
                String product = rs.getString("product_name");
                Date lastDate = rs.getDate("last_date");

                long daysGap = (System.currentTimeMillis() - lastDate.getTime())
                        / (1000 * 60 * 60 * 24);

                if (daysGap > 15) {

                    model.addRow(new Object[]{
                            userId,
                            product,
                            lastDate,
                            daysGap,
                            "⚠ DID NOT BUY AGAIN"
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReminderPage().setVisible(true));
    }
}