import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.*;   
import java.awt.*;         
import java.sql.*;   


public class AdminDashboard extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminDashboard.class.getName());
 private JPanel sideMenu;
private boolean menuVisible = false;
    public AdminDashboard() {
        initComponents();
       initSideMenu();
Color mainBg = new Color(235, 235, 235);  
Color cardBg = Color.WHITE;             
Color tableBg = Color.WHITE;
Color headerBg = new Color(240, 240, 240);
Color gridColor = new Color(200, 200, 200);
Color textColor = Color.BLACK;

getContentPane().setBackground(mainBg);

jPanel1.setBackground(cardBg);
jPanel4.setBackground(cardBg);
jPanel5.setBackground(cardBg);
jPanel6.setBackground(cardBg);

JTable[] tables = {jTable1, jTable2, jTable3, jTable4};

for (JTable table : tables) {
    table.setBackground(tableBg);
    table.setForeground(textColor);
    table.setGridColor(gridColor);
    table.setRowHeight(25);

    table.getTableHeader().setBackground(headerBg);
    table.getTableHeader().setForeground(Color.BLACK);
}

jScrollPane1.getViewport().setBackground(cardBg);
jScrollPane2.getViewport().setBackground(cardBg);
jScrollPane3.getViewport().setBackground(cardBg);
jScrollPane4.getViewport().setBackground(cardBg);

JButton[] btns = {jButton2, jButton3, jButton4, jButton5};

for (JButton b : btns) {
    b.setBackground(new Color(220, 220, 220));
    b.setForeground(Color.BLACK);
    b.setFocusPainted(false);
}
        jTable1.setDefaultEditor(Object.class, null);
jTable2.setDefaultEditor(Object.class, null);
jTable3.setDefaultEditor(Object.class, null);
jTable4.setDefaultEditor(Object.class, null);
          loadOrderHistory();
    loadCurrentOrders();
    loadFeedback();
    loadStock();
    jButton2.addActionListener(e -> new OrderHistoryPage().setVisible(true));
jButton4.addActionListener(e -> new CurrentOrdersPage().setVisible(true));
jButton3.addActionListener(e -> new FeedbackPage().setVisible(true));
jButton5.addActionListener(e -> new StockPage().setVisible(true));
    }
private void initSideMenu() {

    sideMenu = new JPanel();
    sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
    sideMenu.setBackground(new Color(20, 20, 20));
    sideMenu.setBounds(0, 0, 220, getHeight());
    sideMenu.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40)));
    Dimension size = new Dimension(200, 40);
    JButton dashboardBtn = new JButton("Dashboard");
    JButton addProductBtn = new JButton("Add Product");
    JButton customersBtn = new JButton("Customers"); 
    JButton contactBtn = new JButton("Contact Messages");
    JButton reminderBtn = new JButton("Reminder");
    JButton logoutBtn = new JButton("Logout");

    JButton[] buttons = {
        dashboardBtn, addProductBtn, customersBtn,
        contactBtn, reminderBtn, logoutBtn
    };
    for (JButton btn : buttons) {
        btn.setMaximumSize(size);
        btn.setBackground(new Color(35, 35, 35));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    dashboardBtn.addActionListener(e ->
        JOptionPane.showMessageDialog(this, "Already in Dashboard")
    );
    addProductBtn.addActionListener(e ->
        new AdminAddProduct().setVisible(true)
    );
    customersBtn.addActionListener(e ->   
        new CustomersPage().setVisible(true)
    );
    contactBtn.addActionListener(e ->
        new ContactMessagesPage().setVisible(true)
    );
    reminderBtn.addActionListener(e ->
        new SmartAIAnalysisPage().setVisible(true)
    );
    logoutBtn.addActionListener(e -> logout());
   
    sideMenu.add(Box.createVerticalStrut(10));
    sideMenu.add(addProductBtn);
    sideMenu.add(Box.createVerticalStrut(10));
    sideMenu.add(customersBtn);   
    sideMenu.add(Box.createVerticalStrut(10));
    sideMenu.add(contactBtn);
    sideMenu.add(Box.createVerticalStrut(10));
    sideMenu.add(reminderBtn);
    sideMenu.add(Box.createVerticalStrut(10));
    sideMenu.add(logoutBtn);
    getLayeredPane().add(sideMenu, JLayeredPane.POPUP_LAYER);
    sideMenu.setVisible(false);
}
private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            UserSession.currentUserId = 0;
            new UserLogin().setVisible(true);
            this.dispose();
        }
    }
    @SuppressWarnings("unchecked")
    @Override
public void addNotify() {
    super.addNotify();

    getContentPane().addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {

            if (menuVisible) {
                Point click = SwingUtilities.convertPoint(
                        getContentPane(),
                        e.getPoint(),
                        sideMenu
                );

                if (!sideMenu.contains(click)) {
                    sideMenu.setVisible(false);
                    menuVisible = false;
                }
            }
        }
    });
}
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Order ID", "Date", "Status", "User ID", "Amount"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton2.setForeground(new java.awt.Color(51, 153, 255));
        jButton2.setText("ORDER HISTORY");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jSeparator1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton2)
                .addGap(5, 5, 5)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Order ID", "Date", "Status", "Amount"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton4.setForeground(new java.awt.Color(0, 153, 255));
        jButton4.setText("CURRENT ORDERS");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addGap(0, 435, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButton4)
                .addGap(5, 5, 5)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Name", "Quantity"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        jButton5.setForeground(new java.awt.Color(0, 102, 255));
        jButton5.setText("STOCK DETAILS");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator4)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Feedback ID", "Message", "Rating", "User id"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jButton3.setForeground(new java.awt.Color(0, 153, 255));
        jButton3.setText("FEEDBACK");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jSeparator3)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jButton3)
                .addGap(5, 5, 5)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton9.setText("⋮");
        jButton9.addActionListener(this::jButton9ActionPerformed);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setText("ARASAN ALUMINIUM INDUSTRIES");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton9)
                        .addGap(392, 392, 392)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(141, 141, 141))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        menuVisible = !menuVisible;
    sideMenu.setVisible(menuVisible);
    }//GEN-LAST:event_jButton9ActionPerformed

private void loadOrderHistory() {
    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM orders";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable1.getModel();

        model.setRowCount(0);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("order_id"),
                rs.getDate("order_date"),
                rs.getString("status"),
                rs.getDouble("total_amount"),
                rs.getInt("user_id")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void loadCurrentOrders() {
    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM orders WHERE LOWER(status)='pending';";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable2.getModel();

        model.setRowCount(0);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("order_id"),
                rs.getDate("order_date"),
                rs.getString("status"),
                rs.getDouble("total_amount")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void loadFeedback() {
    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM feedback";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable3.getModel();

        model.setRowCount(0);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("feedback_id"),
                rs.getString("message"),
                rs.getInt("rating"),
                rs.getInt("user_id")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void loadStock() {
    try {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT p.product_name, s.available_qty " +
                     "FROM product p JOIN stock s ON p.product_id = s.product_id";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) jTable4.getModel();

        model.setRowCount(0);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("product_name"),
                rs.getInt("available_qty")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new AdminDashboard().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    // End of variables declaration//GEN-END:variables
}
