import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StockPage extends JFrame {
    private JTable table;

    public StockPage() {
        setTitle("Stock Details");
        setSize(500, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

       table = new JTable(new DefaultTableModel(
        new Object[]{"Product ID", "Product Name", "Quantity"}, 0
) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; 
    }
});

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton addStockBtn = new JButton("Add Stock");
        addStockBtn.addActionListener(e -> addStock());

        JButton removeBtn = new JButton("Remove Product");
        removeBtn.addActionListener(e -> removeProduct());

        buttonPanel.add(addStockBtn);
        buttonPanel.add(removeBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        loadStock();
    }

    public void loadStock() {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT p.product_id, p.product_name, COALESCE(s.available_qty, 0) AS available_qty " +
                         "FROM product p LEFT JOIN stock s ON p.product_id = s.product_id " +
                         "ORDER BY available_qty ASC";

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                int qty = rs.getInt("available_qty");

                if (!stockExists(con, productId)) {
                    insertStockRow(con, productId);
                    qty = 0;
                }

                model.addRow(new Object[]{productId, productName, qty});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }

    private boolean stockExists(Connection con, int productId) throws SQLException {
        String checkSql = "SELECT 1 FROM stock WHERE product_id=?";
        PreparedStatement pst = con.prepareStatement(checkSql);
        pst.setInt(1, productId);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    private void insertStockRow(Connection con, int productId) throws SQLException {
        String insertSql = "INSERT INTO stock(product_id, available_qty) VALUES (?, 0)";
        PreparedStatement pst = con.prepareStatement(insertSql);
        pst.setInt(1, productId);
        pst.executeUpdate();
    }

    private void addStock() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product first!");
            return;
        }

        int productId = (int) table.getValueAt(selectedRow, 0);
        String productName = table.getValueAt(selectedRow, 1).toString();

        String qtyStr = JOptionPane.showInputDialog(this, "Enter quantity to add for " + productName + ":");

        if (qtyStr == null || qtyStr.isEmpty()) return;

        int qtyToAdd;

        try {
            qtyToAdd = Integer.parseInt(qtyStr);
            if (qtyToAdd <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity!");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            if (!stockExists(con, productId)) insertStockRow(con, productId);

            String sql = "UPDATE stock SET available_qty = available_qty + ? WHERE product_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, qtyToAdd);
            pst.setInt(2, productId);

            int updated = pst.executeUpdate();

            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Stock updated successfully!");
                loadStock();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update stock!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void removeProduct() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product!");
            return;
        }

        int productId = (int) table.getValueAt(selectedRow, 0);
        String productName = table.getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to DELETE " + productName + " completely?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            String deleteCart = "DELETE FROM cart WHERE product_id=?";
            PreparedStatement pst1 = con.prepareStatement(deleteCart);
            pst1.setInt(1, productId);
            pst1.executeUpdate();

            String deleteStock = "DELETE FROM stock WHERE product_id=?";
            PreparedStatement pst2 = con.prepareStatement(deleteStock);
            pst2.setInt(1, productId);
            pst2.executeUpdate();

            String deleteOrderItems = "DELETE FROM order_items WHERE product_id=?";
            PreparedStatement pst0 = con.prepareStatement(deleteOrderItems);
            pst0.setInt(1, productId);
            pst0.executeUpdate();

            String deleteProduct = "DELETE FROM product WHERE product_id=?";
            PreparedStatement pst3 = con.prepareStatement(deleteProduct);
            pst3.setInt(1, productId);
            int deleted = pst3.executeUpdate();

            con.commit();

            if (deleted > 0) {
                JOptionPane.showMessageDialog(this, "Product deleted successfully!");
                loadStock();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete product!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void reduceStockOnOrder(int productId, int quantityOrdered) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE stock SET available_qty = available_qty - ? WHERE product_id = ? AND available_qty >= ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, quantityOrdered);
            pst.setInt(2, productId);
            pst.setInt(3, quantityOrdered);

            int updated = pst.executeUpdate();

            if (updated == 0) {
                JOptionPane.showMessageDialog(null, "Stock insufficient for product ID: " + productId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reducing stock: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StockPage().setVisible(true);
        });
    }
}