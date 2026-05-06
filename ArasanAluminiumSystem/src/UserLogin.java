import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Image;
import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;
import javafx.embed.swing.JFXPanel;
public class UserLogin extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UserLogin.class.getName());
    public UserLogin() {
    initComponents();
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Login");
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 370, 240, -1));

        jButton2.setText("Sign-in");
        jButton2.addActionListener(this::jButton2ActionPerformed);
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 410, 240, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("    Login id");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 270, 71, 22));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 51));
        jLabel3.setText("  Password");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 320, 71, 20));

        jTextField1.addActionListener(this::jTextField1ActionPerformed);
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 270, 140, -1));

        jPasswordField1.addActionListener(this::jPasswordField1ActionPerformed);
        jPanel2.add(jPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 320, 140, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 51));
        jLabel2.setText("ARASAN ALUMINIUM INDUSTRIES");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 210, -1, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 180, 350, 270));
        jPanel2.add(jLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1400, 750));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1406, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
        jButton1.doClick();
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        UserSignup signup = new UserSignup();
        signup.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.GRAY));
        jPasswordField1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.GRAY));
        boolean valid = true;
        if (jTextField1.getText().isEmpty()) {
            jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(2,2,2,2, java.awt.Color.RED));
            valid = false;
        }
        if (String.valueOf(jPasswordField1.getPassword()).isEmpty()) {
            jPasswordField1.setBorder(javax.swing.BorderFactory.createMatteBorder(2,2,2,2, java.awt.Color.RED));
            valid = false;
        }
        if (!valid) {
            javax.swing.JOptionPane.showMessageDialog(this, "Fill all fields!");
            return;
        }
        try {
            java.sql.Connection con = DBConnection.getConnection();
            String adminSql = "SELECT * FROM admin WHERE BINARY username=? AND BINARY password=?";
            java.sql.PreparedStatement adminPst = con.prepareStatement(adminSql);
            adminPst.setString(1, jTextField1.getText());
            adminPst.setString(2, String.valueOf(jPasswordField1.getPassword()));
            java.sql.ResultSet adminRs = adminPst.executeQuery();
            if (adminRs.next()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Admin Login Successful");
                new AdminDashboard().setVisible(true);
                this.dispose();
            }
            else {
               String userSql = "SELECT * FROM user WHERE BINARY name=? AND BINARY password=?";
                java.sql.PreparedStatement userPst = con.prepareStatement(userSql);
                userPst.setString(1, jTextField1.getText());
                userPst.setString(2, String.valueOf(jPasswordField1.getPassword()));

                java.sql.ResultSet userRs = userPst.executeQuery();
if (userRs.next()) {
    int userId = userRs.getInt("user_id");
    UserSession.currentUserId = userId;
    JOptionPane.showMessageDialog(this, "Login Successful");
        new ProductPage().setVisible(true);
    this.dispose();
}         
                else {
                    jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(2,2,2,2, java.awt.Color.RED));
                    jPasswordField1.setBorder(javax.swing.BorderFactory.createMatteBorder(2,2,2,2, java.awt.Color.RED));

                    javax.swing.JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    public static void main(String args[]) {
    SwingUtilities.invokeLater(() -> {
        new JFXPanel(); 
        UserLogin login = new UserLogin();
        login.setVisible(false);
        new VideoLoadingScreen("videos/login_intro.mp4", () -> {
            login.setVisible(true);
        });
    });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
