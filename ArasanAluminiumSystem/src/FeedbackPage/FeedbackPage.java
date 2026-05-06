import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class FeedbackPage extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public FeedbackPage() {

        setTitle("Admin - Product Feedback");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(
                new Object[]{
                        "Feedback ID",
                        "User Name",
                        "Product Name",
                        "Order ID",
                        "Message",
                        "Rating"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadFeedback());
        add(refreshBtn, BorderLayout.SOUTH);

        loadFeedback();
    }

    private void loadFeedback() {
        try {
            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT f.feedback_id, u.name AS user_name, " +
                    "p.product_name, f.order_id, f.message, f.rating " +
                    "FROM feedback f " +
                    "JOIN user u ON f.user_id = u.user_id " +
                    "JOIN product p ON f.product_id = p.product_id " +
                    "ORDER BY f.feedback_id DESC";

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("feedback_id"),
                        rs.getString("user_name"),
                        rs.getString("product_name"),
                        rs.getInt("order_id"),
                        rs.getString("message"),
                        rs.getInt("rating")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading feedback!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FeedbackPage().setVisible(true));
    }
}