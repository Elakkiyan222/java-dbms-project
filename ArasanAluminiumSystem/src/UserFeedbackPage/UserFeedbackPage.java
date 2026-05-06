import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserFeedbackPage extends JFrame {

    private JTable orderTable;
    private JPanel productPanel;
    private int selectedOrderId = -1;
    private String filter = "ALL";

    public UserFeedbackPage() {

        setTitle("Product Feedback");
        setSize(950, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        orderTable = new JTable(new javax.swing.table.DefaultTableModel(
                new Object[]{"Order ID", "Date", "Amount", "Review Status"}, 0
        ));

        JScrollPane orderScroll = new JScrollPane(orderTable);
        orderScroll.setPreferredSize(new Dimension(300, 0));

        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));

        JScrollPane productScroll = new JScrollPane(productPanel);

        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                orderScroll,
                productScroll
        );

        split.setDividerLocation(300);
        add(split, BorderLayout.CENTER);

        JPanel topPanel = new JPanel();

        JButton allBtn = new JButton("All");
        JButton reviewedBtn = new JButton("Reviewed");
        JButton notReviewedBtn = new JButton("Not Reviewed");

        topPanel.add(allBtn);
        topPanel.add(reviewedBtn);
        topPanel.add(notReviewedBtn);

        add(topPanel, BorderLayout.NORTH);

        allBtn.addActionListener(e -> {
            filter = "ALL";
            loadOrders();
        });

        reviewedBtn.addActionListener(e -> {
            filter = "REVIEWED";
            loadOrders();
        });

        notReviewedBtn.addActionListener(e -> {
            filter = "NOT_REVIEWED";
            loadOrders();
        });

        loadOrders();

        orderTable.getSelectionModel().addListSelectionListener(e -> {
            int row = orderTable.getSelectedRow();
            if (row != -1) {
                selectedOrderId = (int) orderTable.getValueAt(row, 0);
                loadProducts(selectedOrderId);
            }
        });
    }

    private void loadOrders() {
        try {
            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT o.order_id, o.order_date, o.total_amount, " +
                    "CASE WHEN COUNT(f.feedback_id) = COUNT(oi.product_id) AND COUNT(oi.product_id) > 0 " +
                    "THEN 'Reviewed' ELSE 'Not Reviewed' END AS review_status " +
                    "FROM orders o " +
                    "JOIN order_items oi ON o.order_id = oi.order_id " +
                    "LEFT JOIN feedback f ON f.order_id = o.order_id AND f.product_id = oi.product_id " +
                    "WHERE o.user_id=? AND o.status='delivered' " +
                    "GROUP BY o.order_id " +
                    "ORDER BY o.order_id DESC";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, UserSession.currentUserId);

            ResultSet rs = pst.executeQuery();

            var model = (javax.swing.table.DefaultTableModel) orderTable.getModel();
            model.setRowCount(0);

            while (rs.next()) {

                String status = rs.getString("review_status");

                if (filter.equals("REVIEWED") && !status.equals("Reviewed")) continue;
                if (filter.equals("NOT_REVIEWED") && !status.equals("Not Reviewed")) continue;

                model.addRow(new Object[]{
                        rs.getInt("order_id"),
                        rs.getDate("order_date"),
                        rs.getDouble("total_amount"),
                        status
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProducts(int orderId) {

        productPanel.removeAll();

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT p.product_id, p.product_name, p.image, oi.quantity, " +
                    "f.rating, f.message " +
                    "FROM order_items oi " +
                    "JOIN product p ON oi.product_id = p.product_id " +
                    "LEFT JOIN feedback f ON f.product_id = p.product_id " +
                    "AND f.order_id = oi.order_id AND f.user_id=? " +
                    "WHERE oi.order_id=?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, UserSession.currentUserId);
            pst.setInt(2, orderId);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                productPanel.add(
                        createProductCard(
                                rs.getInt("product_id"),
                                rs.getString("product_name"),
                                rs.getString("image"),
                                rs.getInt("quantity"),
                                rs.getInt("rating"),
                                rs.getString("message")
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        productPanel.revalidate();
        productPanel.repaint();
    }

    private JPanel createProductCard(int productId, String name, String imagePath,
                                     int qty, int existingRating, String existingMsg) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setPreferredSize(new Dimension(550, 200));

        JLabel img;

        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image im = icon.getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH);
            img = new JLabel(new ImageIcon(im));
        } catch (Exception e) {
            img = new JLabel("No Image");
        }

        JLabel title = new JLabel("<html><b>" + name + "</b><br>Qty: " + qty + "</html>");

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(img);
        top.add(title);

        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        ButtonGroup group = new ButtonGroup();
        JRadioButton[] stars = new JRadioButton[5];

        final int[] rating = {0};

        for (int i = 0; i < 5; i++) {
            int value = i + 1;

            stars[i] = new JRadioButton(value + " ★");
            group.add(stars[i]);
            ratingPanel.add(stars[i]);

            stars[i].addActionListener(e -> rating[0] = value);
        }

        JTextArea comment = new JTextArea(3, 25);
        JScrollPane scroll = new JScrollPane(comment);

        JButton submit = new JButton("Submit Review");

        if (existingMsg != null) {

            comment.setText(existingMsg);
            comment.setEditable(false);
            submit.setEnabled(false);

            for (int i = 0; i < existingRating; i++) {
                stars[i].setSelected(true);
            }

            for (JRadioButton btn : stars) {
                btn.setEnabled(false);
            }

        } else {

            submit.addActionListener(e -> {

                if (rating[0] == 0) {
                    JOptionPane.showMessageDialog(this, "Select rating!");
                    return;
                }

                try {
                    Connection con = DBConnection.getConnection();

                    String sql = "INSERT INTO feedback(message, rating, user_id, order_id, product_id) VALUES (?,?,?,?,?)";

                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, comment.getText());
                    pst.setInt(2, rating[0]);
                    pst.setInt(3, UserSession.currentUserId);
                    pst.setInt(4, selectedOrderId);
                    pst.setInt(5, productId);

                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Review Submitted!");

                    loadProducts(selectedOrderId);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(ratingPanel, BorderLayout.NORTH);
        bottom.add(scroll, BorderLayout.CENTER);
        bottom.add(submit, BorderLayout.SOUTH);

        panel.add(top, BorderLayout.NORTH);
        panel.add(bottom, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserFeedbackPage().setVisible(true));
    }
}