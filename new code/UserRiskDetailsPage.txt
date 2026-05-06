import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class UserRiskDetailsPage extends JFrame {

    private int userId;
    private String product;

    private JTable table;
    private JTextArea reasonArea;

    public UserRiskDetailsPage(int userId, String product) {

        this.userId = userId;
        this.product = product;

        setTitle("User Risk Analysis");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        reasonArea = new JTextArea();
        reasonArea.setFont(new Font("Segoe UI", Font.BOLD, 14));
        reasonArea.setEditable(false);

        table = new JTable(new DefaultTableModel(
                new Object[]{"Order ID", "Product", "Qty", "Price", "Date"}, 0
        ));

        add(reasonArea, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadDetails();
    }

    private void loadDetails() {

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

            boolean boughtAgain = false;

            while (rs.next()) {

                String prod = rs.getString("product_name");

                if (prod.equals(product)) {
                    boughtAgain = true;
                }

                model.addRow(new Object[]{
                        rs.getInt("order_id"),
                        prod,
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getDate("order_date")
                });
            }
            if (!boughtAgain) {
                reasonArea.setText(
                        "⚠ USER " + userId +
                        " IS AT RISK\n\n" +
                        "Product: " + product +
                        "\nReason: User stopped buying this product.\n" +
                        "Action: Contact customer for re-engagement."
                );
            } else {
                reasonArea.setText(
                        "✔ USER " + userId +
                        " IS ACTIVE AGAIN\nProduct purchased again."
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}