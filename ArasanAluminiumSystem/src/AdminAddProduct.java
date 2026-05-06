import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.*;
public class AdminAddProduct extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminAddProduct.class.getName());
    public AdminAddProduct() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Product Name");

        jLabel2.setText("Price");

        jLabel3.setText("Image Path");

        jButton1.setText("ADD PRODUCT");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jLabel4.setText("Stock");

        jTextField4.addActionListener(this::jTextField4ActionPerformed);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("              ADD TO PRODUCT");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(501, 501, 501)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField3)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(jButton1))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(473, 473, 473)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(529, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addComponent(jButton1)
                .addContainerGap(353, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    String name = jTextField1.getText();
    String price = jTextField2.getText();
    String image = jTextField3.getText();
    String stock = jTextField4.getText();
    jTextField1.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    jTextField2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    jTextField3.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    jTextField4.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    boolean valid = true;
    if (name.isEmpty()) {
        jTextField1.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
        JOptionPane.showMessageDialog(this, "Product name is required!");
        return;
    }
    if (price.isEmpty()) {
        jTextField2.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
        JOptionPane.showMessageDialog(this, "Price is required!");
        return;
    }
    if (stock.isEmpty()) {
        jTextField4.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
        JOptionPane.showMessageDialog(this, "Stock is required!");
        return;
    }
    if (image.isEmpty()) {
        jTextField3.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
        JOptionPane.showMessageDialog(this, "Image path is required!");
        return;
    }
    int priceValue, stockValue;
    try {
       priceValue = Integer.parseInt(price);
stockValue = Integer.parseInt(stock);
if (priceValue <= 0) {
    jTextField2.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
    JOptionPane.showMessageDialog(this, "Price must be greater than 0!");
    return;
}
if (stockValue <= 0) {
    jTextField4.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
    JOptionPane.showMessageDialog(this, "Stock must be greater than 0!");
    return;
}
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Price and Stock must be numbers!");
        return;
    }
    try {
        Connection con = DBConnection.getConnection();
        String productSql = "INSERT INTO product (product_name, price, image) VALUES (?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(productSql, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, name);
        pst.setInt(2, priceValue);
        pst.setString(3, image);
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        int productId = 0;
        if (rs.next()) {
            productId = rs.getInt(1);
        }
        String stockSql = "INSERT INTO stock (product_id, available_qty) VALUES (?, ?)";
        PreparedStatement stockPst = con.prepareStatement(stockSql);
        stockPst.setInt(1, productId);
        stockPst.setInt(2, stockValue);
        stockPst.executeUpdate();
        JOptionPane.showMessageDialog(this, "Product Added Successfully!");
        new AdminDashboard().setVisible(true);
        this.dispose();
    } catch (SQLException e) {
    if (e.getMessage().toLowerCase().contains("unique")) {
        JOptionPane.showMessageDialog(this, "Product already exists!");
    } else if (e.getMessage().toLowerCase().contains("check")) {
        JOptionPane.showMessageDialog(this, "Invalid price or stock value!");
    } else {
        JOptionPane.showMessageDialog(this, "Database error!");
    }
}
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new AdminAddProduct().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
