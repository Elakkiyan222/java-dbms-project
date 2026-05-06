import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class CustomerOrdersPage extends BaseFrame {

    private JTable table;
    private int userId;

    public CustomerOrdersPage(int userId) {
        super("Customer Orders", 700, 400); // calling parent constructor
        this.userId = userId;

        table = new JTable();
        add(new JScrollPane(table));

        loadOrders();
    }

    private void loadOrders() {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT order_id, order_date, status, total_amount " +
                         "FROM orders WHERE user_id=? ORDER BY order_id DESC";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userId);

            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel(
    new String[]{"Order ID", "Date", "Status", "Amount"}, 0
) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("order_id"),
                    rs.getDate("order_date"),
                    rs.getString("status"),
                    rs.getDouble("total_amount")
                });
            }

            table.setModel(model);

            table.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int row = table.getSelectedRow();
                    int orderId = (int) table.getValueAt(row, 0);

                    new OrderItemsDialog(orderId).setVisible(true);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}