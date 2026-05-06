import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CurrentOrdersPage extends JFrame {

    private JTable table;

    public CurrentOrdersPage() {

        setTitle("Current Orders");
        setSize(700, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 

        table = new JTable(new DefaultTableModel(
                new Object[]{"Order ID", "User", "Status", "Amount"}, 0
        ) {
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0: return Integer.class;
                    case 1: return Integer.class;
                    case 3: return Double.class;
                    default: return String.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        });
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                String status = table.getValueAt(row, 2).toString();

                if (status.equalsIgnoreCase("pending")) {
                    c.setBackground(new Color(255, 255, 150)); // light yellow
                } else {
                    c.setBackground(Color.WHITE);
                }

                return c;
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {

                    int row = table.getSelectedRow();

                    if (row != -1) {

                        int orderId = (int) table.getValueAt(row, 0);

                        new OrderDetailsPage(orderId, CurrentOrdersPage.this)
                                .setVisible(true);
                    }
                }
            }
        });

        loadOrders();
    }
    public void loadOrders() {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM orders WHERE status='pending'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getString("status"),
                        rs.getDouble("total_amount")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading orders!");
        }
    }
    public void refreshTable() {
        loadOrders();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CurrentOrdersPage().setVisible(true);
        });
    }
}