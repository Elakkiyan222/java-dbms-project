import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class OrdersPage extends JFrame {

    private JPanel orderPanel;
    private JComboBox<String> monthBox;
    private JComboBox<String> yearBox;
    private JComboBox<String> productBox;

    public OrdersPage() {

        setTitle("My Orders");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel();

        monthBox = new JComboBox<>(new String[]{
                "All Months","01","02","03","04","05","06",
                "07","08","09","10","11","12"
        });

        yearBox = new JComboBox<>();
        loadYears();

        productBox = new JComboBox<>();
        loadProducts();

        JButton apply = new JButton("Apply");
        apply.addActionListener(e -> loadOrders());

        top.add(new JLabel("Month:"));
        top.add(monthBox);
        top.add(new JLabel("Year:"));
        top.add(yearBox);
        top.add(new JLabel("Product:"));
        top.add(productBox);
        top.add(apply);

        add(top, BorderLayout.NORTH);

        orderPanel = new JPanel();
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
        orderPanel.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(orderPanel);
        add(scroll, BorderLayout.CENTER);

        loadOrders();
    }

    private void loadYears() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT DISTINCT YEAR(order_date) y FROM orders WHERE user_id=? ORDER BY y DESC";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, UserSession.currentUserId);

            ResultSet rs = pst.executeQuery();

            yearBox.addItem("All Years");

            while (rs.next()) {
                yearBox.addItem(rs.getString("y"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProducts() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT DISTINCT product_name FROM product";

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            productBox.addItem("All Products");

            while (rs.next()) {
                productBox.addItem(rs.getString("product_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadOrders() {

        orderPanel.removeAll();

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT DISTINCT o.order_id, o.order_date, o.total_amount " +
                    "FROM orders o " +
                    "JOIN order_items oi ON o.order_id=oi.order_id " +
                    "JOIN product p ON oi.product_id=p.product_id " +
                    "WHERE o.user_id=?";

            if (!monthBox.getSelectedItem().equals("All Months")) {
                sql += " AND MONTH(o.order_date)=?";
            }

            if (!yearBox.getSelectedItem().equals("All Years")) {
                sql += " AND YEAR(o.order_date)=?";
            }

            if (!productBox.getSelectedItem().equals("All Products")) {
                sql += " AND p.product_name=?";
            }

            sql += " ORDER BY o.order_id DESC";

            PreparedStatement pst = con.prepareStatement(sql);

            int i = 1;
            pst.setInt(i++, UserSession.currentUserId);

            if (!monthBox.getSelectedItem().equals("All Months")) {
                pst.setInt(i++, Integer.parseInt((String) monthBox.getSelectedItem()));
            }

            if (!yearBox.getSelectedItem().equals("All Years")) {
                pst.setInt(i++, Integer.parseInt((String) yearBox.getSelectedItem()));
            }

            if (!productBox.getSelectedItem().equals("All Products")) {
                pst.setString(i++, (String) productBox.getSelectedItem());
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                orderPanel.add(createOrderCard(
                        rs.getInt("order_id"),
                        rs.getString("order_date"),
                        rs.getDouble("total_amount")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        orderPanel.revalidate();
        orderPanel.repaint();
    }

    private JPanel createOrderCard(int orderId, String date, double total) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));

        JLabel header = new JLabel(
                "Order ID: " + orderId +
                " | Date: " + date +
                " | Total: ₹" + total
        );
        header.setFont(new Font("Arial", Font.BOLD, 13));

        card.add(header, BorderLayout.NORTH);

        JPanel products = new JPanel();
        products.setLayout(new BoxLayout(products, BoxLayout.Y_AXIS));
        products.setBackground(Color.WHITE);

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT p.product_name, p.image, oi.quantity, f.rating, f.message " +
                    "FROM order_items oi " +
                    "JOIN product p ON oi.product_id=p.product_id " +
                    "LEFT JOIN feedback f ON f.product_id=p.product_id AND f.order_id=oi.order_id " +
                    "WHERE oi.order_id=?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, orderId);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                products.add(createProductRow(
                        rs.getString("product_name"),
                        rs.getString("image"),
                        rs.getInt("quantity"),
                        rs.getInt("rating"),
                        rs.getString("message")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane productScroll = new JScrollPane(products);
        productScroll.setPreferredSize(new Dimension(850, 150));
        productScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        card.add(productScroll, BorderLayout.CENTER);

        return card;
    }

    private JPanel createProductRow(String name, String image,
                                    int qty, int rating, String msg) {

        JPanel row = new JPanel(new BorderLayout());
        row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        row.setBackground(Color.WHITE);

        JLabel img;
        try {
            ImageIcon icon = new ImageIcon(image);
            Image im = icon.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
            img = new JLabel(new ImageIcon(im));
        } catch (Exception e) {
            img = new JLabel("No Image");
        }

        JLabel text = new JLabel("<html><b>" + name + "</b><br>Qty: " + qty + "</html>");

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.setBackground(Color.WHITE);
        left.add(img);
        left.add(text);

        JLabel right;

        if (rating > 0) {
            right = new JLabel(getStars(rating) + " - " + msg);
        } else {
            right = new JLabel("Not Reviewed");
        }

        row.add(left, BorderLayout.WEST);
        row.add(right, BorderLayout.EAST);

        return row;
    }

    private String getStars(int rating) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(i < rating ? "★" : "☆");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OrdersPage().setVisible(true));
    }
}