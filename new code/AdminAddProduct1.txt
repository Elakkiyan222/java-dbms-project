import java.sql.*;
import javax.swing.*;

public class AdminAddProduct1 extends javax.swing.JFrame {

    public AdminAddProduct1() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        priceField = new javax.swing.JTextField();
        saveBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Product");

        jLabel1.setText("Product Name:");
        jLabel2.setText("Price:");

        saveBtn.setText("Save");
        saveBtn.addActionListener(evt -> saveBtnActionPerformed(evt));

        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(evt -> cancelBtnActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nameField)
                    .addComponent(priceField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(saveBtn)
                .addGap(40, 40, 40)
                .addComponent(cancelBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBtn)
                    .addComponent(cancelBtn))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO product(product_name, price) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, nameField.getText());
            pst.setDouble(2, Double.parseDouble(priceField.getText()));
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Product added!");
            this.dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!");
        }
    }

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new AdminAddProduct1().setVisible(true));
    }

    private javax.swing.JButton saveBtn;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField priceField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
}