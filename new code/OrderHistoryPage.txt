import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class OrderHistoryPage extends JFrame {

    private JTable table;
    private JComboBox<String> monthBox, yearBox, productBox;
    private JButton filterBtn, resetBtn;

    public OrderHistoryPage() {

        setTitle("Order History (Admin)");
        setSize(950, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();

        monthBox = new JComboBox<>(new String[]{
                "All","1","2","3","4","5","6","7","8","9","10","11","12"
        });

        yearBox = new JComboBox<>(new String[]{
                "All","2024","2025","2026"
        });

        productBox = new JComboBox<>();
        loadProducts();

        filterBtn = new JButton("Apply Filter");
        resetBtn = new JButton("Reset");

        topPanel.add(new JLabel("Month"));
        topPanel.add(monthBox);

        topPanel.add(new JLabel("Year"));
        topPanel.add(yearBox);

        topPanel.add(new JLabel("Product"));
        topPanel.add(productBox);

        topPanel.add(filterBtn);
        topPanel.add(resetBtn);

        add(topPanel, BorderLayout.NORTH);

        table = new JTable(new DefaultTableModel(
                new Object[]{"Order ID","User ID","Product","Qty","Price","Date"}, 0
        ));

        add(new JScrollPane(table), BorderLayout.CENTER);

       table.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {

        int row = table.getSelectedRow();
        System.out.println("Row clicked: " + row);

        if (row != -1) {
            int orderId = Integer.parseInt(table.getValueAt(row, 0).toString());
            System.out.println("Opening Order ID: " + orderId);

            new OrderItemsPage(orderId).setVisible(true);
        }
    }
});

        filterBtn.addActionListener(e -> loadDetails());

        resetBtn.addActionListener(e -> {
            monthBox.setSelectedIndex(0);
            yearBox.setSelectedIndex(0);
            productBox.setSelectedIndex(0);
            loadDetails();
        });

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

            StringBuilder sql = new StringBuilder(
                    "SELECT o.order_id, o.user_id, p.product_name, oi.quantity, oi.price, o.order_date " +
                    "FROM orders o " +
                    "JOIN order_items oi ON o.order_id = oi.order_id " +
                    "JOIN product p ON oi.product_id = p.product_id"
            );

            ArrayList<String> conditions = new ArrayList<>();

            if (!monthBox.getSelectedItem().equals("All")) {
                conditions.add("MONTH(o.order_date)=?");
            }

            if (!yearBox.getSelectedItem().equals("All")) {
                conditions.add("YEAR(o.order_date)=?");
            }

            if (!productBox.getSelectedItem().equals("All")) {
                conditions.add("p.product_name=?");
            }

            if (!conditions.isEmpty()) {
                sql.append(" WHERE ");
                sql.append(String.join(" AND ", conditions));
            }

            PreparedStatement pst = con.prepareStatement(sql.toString());

            int index = 1;

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
                        rs.getInt("user_id"),
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
        SwingUtilities.invokeLater(() -> new OrderHistoryDetailsPage().setVisible(true));
    }
}