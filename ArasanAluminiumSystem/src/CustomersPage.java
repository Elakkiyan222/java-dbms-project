import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CustomersPage extends JFrame {

    private JTable table;

    public CustomersPage() {

        setTitle("Customers");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        table = new JTable();
        JScrollPane scroll = new JScrollPane(table);

        add(scroll, BorderLayout.CENTER);

        loadCustomers();
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                int row = table.getSelectedRow();

                if (row == -1) return;

                int userId = Integer.parseInt(
                        table.getValueAt(row, 0).toString()
                );

                new CustomerOrdersPage(userId).setVisible(true);
            }
        });
    }

    private void loadCustomers() {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT user_id, name, phone, email FROM user";

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"User ID", "Name", "Phone", "Email"}, 0
            );

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email")
                });
            }

            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading customers!");
        }
    }
}