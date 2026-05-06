import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class InactiveCustomerPage extends JFrame {

    private JTable table;

    public InactiveCustomerPage() {

        setTitle("Inactive Customers");
        setSize(900, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        System.out.println("InactiveCustomerPage opened"); 

        table = new JTable(new DefaultTableModel(
                new Object[]{"User ID", "Name", "Product", "Total Orders", "Last Purchase"}, 0
        ));

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadInactiveUsers();
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                int row = table.getSelectedRow();

                if (row != -1) {
                    int userId = Integer.parseInt(table.getValueAt(row, 0).toString());
                    System.out.println("Opening history for user: " + userId);

                    new UserFullHistoryPage(userId).setVisible(true);
                }
            }
        });
    }

    private void loadInactiveUsers() {
        try {
            System.out.println("Loading inactive users..."); 

            Connection con = DBConnection.getConnection();

           String sql =
"SELECT o.user_id, u.name, p.product_name, " +
"COUNT(*) as total_orders, " +
"MAX(o.order_date) as last_date, " +
"DATEDIFF(CURDATE(), MAX(o.order_date)) as days_since_last " +
"FROM orders o " +
"JOIN order_items oi ON o.order_id = oi.order_id " +
"JOIN product p ON oi.product_id = p.product_id " +
"JOIN user u ON o.user_id = u.user_id " +
"GROUP BY o.user_id, p.product_id";

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("User Found: " + rs.getInt("user_id")); 

                model.addRow(new Object[]{
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("product_name"),
                        rs.getInt("total_orders"),
                        rs.getDate("last_order_date")
                });
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "No data found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}