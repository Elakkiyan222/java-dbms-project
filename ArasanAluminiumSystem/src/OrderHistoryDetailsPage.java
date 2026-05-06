import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class OrderHistoryDetailsPage extends JFrame {

    private JTable table;
    private JComboBox<String> monthBox, yearBox, productBox;
    private JButton filterBtn;

    public OrderHistoryDetailsPage() {

        setTitle("Order History");
        setSize(800, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();

        monthBox = new JComboBox<>(new String[]{
                "All","01","02","03","04","05","06","07","08","09","10","11","12"
        });

        yearBox = new JComboBox<>(new String[]{
                "All","2024","2025","2026"
        });

        productBox = new JComboBox<>();
        loadProducts();

        filterBtn = new JButton("Apply Filter");

        topPanel.add(new JLabel("Month"));
        topPanel.add(monthBox);

        topPanel.add(new JLabel("Year"));
        topPanel.add(yearBox);

        topPanel.add(new JLabel("Product"));
        topPanel.add(productBox);

        topPanel.add(filterBtn);

        add(topPanel, BorderLayout.NORTH);

        table = new JTable(new DefaultTableModel(
                new Object[]{"Order ID","Product","Qty","Price","Date"}, 0
        ));

        add(new JScrollPane(table), BorderLayout.CENTER);

        filterBtn.addActionListener(e -> loadDetails());

        loadDetails();
    }

    private void loadProducts() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT product_name FROM product");

            productBox.addItem("All");

            while (rs.next()) {
                productBox.addItem(rs.getString("product_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadDetails() {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT o.order_id, p.product_name, oi.quantity, oi.price, o.order_date " +
                    "FROM orders o " +
                    "JOIN order_items oi ON o.order_id = oi.order_id " +
                    "JOIN product p ON oi.product_id = p.product_id " +
                    "WHERE o.user_id=?";

            if (!monthBox.getSelectedItem().equals("All")) {
                sql += " AND MONTH(o.order_date)=?";
            }

            if (!yearBox.getSelectedItem().equals("All")) {
                sql += " AND YEAR(o.order_date)=?";
            }

            if (!productBox.getSelectedItem().equals("All")) {
                sql += " AND p.product_name=?";
            }

            PreparedStatement pst = con.prepareStatement(sql);

            int index = 1;

            pst.setInt(index++, UserSession.currentUserId);

            if (!monthBox.getSelectedItem().equals("All")) {
                pst.setInt(index++, Integer.parseInt(monthBox.getSelectedItem().toString()));
            }

            if (!yearBox.getSelectedItem().equals("All")) {
                pst.setInt(index++, Integer.parseInt(yearBox.getSelectedItem().toString()));
            }

            if (!productBox.getSelectedItem().equals("All")) {
                pst.setString(index++, productBox.getSelectedItem().toString());
            }

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

    public static void main(String[] args) {

        UserSession.currentUserId = 1;

        SwingUtilities.invokeLater(() -> {
            new OrderHistoryPage().setVisible(true);
        });
    }
}