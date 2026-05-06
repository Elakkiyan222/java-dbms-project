import javax.swing.*;   
import java.awt.*;         
import java.sql.*;   

public class ProductPage extends javax.swing.JFrame {
 
    private JPanel sideMenu;
    private boolean menuVisible = false;
    
    CartPage cartPage = new CartPage(this);
    private java.util.List<JPanel> productPanel = new java.util.ArrayList<>();
    private int getProductIdByName(String productName) {
    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT product_id FROM product WHERE product_name=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, productName);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt("product_id");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return -1; 
}
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ProductPage.class.getName());

    public ProductPage() {
        initComponents();
        initSideMenu();
        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {

    if (!(event instanceof java.awt.event.MouseEvent)) return;

    java.awt.event.MouseEvent me = (java.awt.event.MouseEvent) event;

    if (me.getID() == java.awt.event.MouseEvent.MOUSE_PRESSED) {

        if (menuVisible) {

            Point clickPoint = SwingUtilities.convertPoint(
                    me.getComponent(),
                    me.getPoint(),
                    sideMenu
            );

            if (clickPoint.x < 0 || clickPoint.y < 0
                    || clickPoint.x > sideMenu.getWidth()
                    || clickPoint.y > sideMenu.getHeight()) {

                sideMenu.setVisible(false);
                menuVisible = false;
            }
        }
    }

}, AWTEvent.MOUSE_EVENT_MASK);
        getContentPane().setBackground(java.awt.Color.WHITE);
        JButton feedbackBtn = new JButton("Feedback");

feedbackBtn.addActionListener(e -> {
    new FeedbackPage().setVisible(true);
});

jPanel6.add(feedbackBtn);
mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
    loadProducts();
    //jButton2.setFont(new java.awt.Font("Segoe UI Emoji", java.awt.Font.PLAIN, 20));
    }
   private void loadProducts() {
    productPanel.clear();
    mainPanel.removeAll();
    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM product"; 
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            String name = rs.getString("product_name");
            String price = rs.getString("price");
            String image = rs.getString("image");
            JPanel p = createProductPanel(name, price, image);
            productPanel.add(p);
            mainPanel.add(p);
        }

        mainPanel.revalidate();
        mainPanel.repaint();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void initSideMenu() {

    sideMenu = new JPanel();
    sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));

    sideMenu.setBackground(new Color(20, 20, 20));
    sideMenu.setBounds(0, 0, 320, getHeight());

    JButton ordersBtn = createMenuButton(" Orders");
    JButton feedbackBtn = createMenuButton(" Feedback");
    JButton cartBtn = createMenuButton(" Cart");
    JButton contactBtn = createMenuButton(" Contact Us");
    JButton logoutBtn = createMenuButton(" Logout");

  
    ordersBtn.addActionListener(e -> new OrdersPage().setVisible(true));

    feedbackBtn.addActionListener(e -> new UserFeedbackPage().setVisible(true));

    cartBtn.addActionListener(e -> {

    if (CartPage.instance == null) {
        CartPage.instance = new CartPage(this);
    }
    CartPage.instance.loadCartFromDB();

    CartPage.instance.setVisible(true);
    this.setVisible(false);
});

    contactBtn.addActionListener(e -> new ContactUsPage().setVisible(true));

    logoutBtn.addActionListener(e -> {
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
    });

    sideMenu.add(Box.createVerticalStrut(40));

    sideMenu.add(ordersBtn);
    sideMenu.add(Box.createVerticalStrut(15));

    sideMenu.add(feedbackBtn);
    sideMenu.add(Box.createVerticalStrut(15));

    sideMenu.add(cartBtn);
    sideMenu.add(Box.createVerticalStrut(15));

    sideMenu.add(contactBtn);
    sideMenu.add(Box.createVerticalStrut(15));

    sideMenu.add(logoutBtn);

    getLayeredPane().add(sideMenu, JLayeredPane.POPUP_LAYER);

    sideMenu.setVisible(false);
}
private JButton createMenuButton(String text) {

    JButton btn = new JButton(text);

    btn.setMaximumSize(new Dimension(260, 45));
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(new Color(35, 35, 35));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            btn.setBackground(new Color(45, 45, 45));
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
            btn.setBackground(new Color(28, 28, 28));
        }
    });

    return btn;
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jPanel6.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setText("🔍");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton7.setText("⋮");
        jButton7.addActionListener(this::jButton7ActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jButton7)
                .addGap(105, 105, 105)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(742, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addGap(13, 13, 13))
        );

        jScrollPane1.setBackground(new java.awt.Color(242, 242, 242));
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        mainPanel.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1388, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 622, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(655, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(269, 269, 269)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    String searchText = jTextField1.getText().trim().toLowerCase();

    mainPanel.removeAll();

    if (searchText.isEmpty()) {

        for (JPanel p : productPanel) {
            mainPanel.add(p);
        }

    } else {

        for (JPanel p : productPanel) {

            JLabel textLabel = (JLabel) ((BorderLayout)p.getLayout())
                    .getLayoutComponent(BorderLayout.NORTH);

           
            String raw = textLabel.getText();

            String clean = raw.replaceAll("<.*?>", "");
            String productName = clean.split("₹")[0].trim();
            if (productName.toLowerCase().contains(searchText)) {
                mainPanel.add(p);
            }
        }
    }

    mainPanel.revalidate();
    mainPanel.repaint();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        menuVisible = !menuVisible;
    sideMenu.setVisible(menuVisible);
    }//GEN-LAST:event_jButton7ActionPerformed

public JPanel createProductPanel(String name, String price, String imagePath) {


    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(200, 300)); 
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    panel.setLayout(new BorderLayout());


    ImageIcon icon = new ImageIcon(imagePath);
    Image img = icon.getImage().getScaledInstance(140, 100, Image.SCALE_SMOOTH);
    JLabel imgLabel = new JLabel(new ImageIcon(img));
    imgLabel.setHorizontalAlignment(JLabel.CENTER);


    JLabel text = new JLabel("<html><center>" + name + "<br>₹" + price + "</center></html>");
    text.setHorizontalAlignment(JLabel.CENTER);

 
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    bottomPanel.setPreferredSize(new Dimension(200, 50));

    JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
    qtySpinner.setMinimumSize(new Dimension(60, 25));
    qtySpinner.setMaximumSize(new Dimension(60, 25));

    JButton btn = new JButton("Add to Cart");
    btn.setPreferredSize(new Dimension(110, 25));

    bottomPanel.add(new JLabel("Qty:"));
    bottomPanel.add(qtySpinner);
    bottomPanel.add(btn);

 
    int productId = getProductIdByName(name);


    int availableStock = 0;
    try {
        if (productId != -1) {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(
                "SELECT available_qty FROM stock WHERE product_id=?"
            );
            pst.setInt(1, productId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                availableStock = rs.getInt("available_qty");
            }
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }


    if (availableStock <= 0) {
        btn.setEnabled(false);
        btn.setText("Out of Stock");
    } else {
        btn.addActionListener(e -> {
            int quantity = (int) qtySpinner.getValue();

            try {
                Connection con = DBConnection.getConnection();

                PreparedStatement stockPst = con.prepareStatement(
                    "SELECT available_qty FROM stock WHERE product_id=?"
                );
                stockPst.setInt(1, productId);
                ResultSet stockRs = stockPst.executeQuery();

                int stock = 0;
                if (stockRs.next()) stock = stockRs.getInt("available_qty");

                int cartQty = 0;
                PreparedStatement cartPst = con.prepareStatement(
                    "SELECT quantity FROM cart WHERE user_id=? AND product_id=?"
                );
                cartPst.setInt(1, UserSession.currentUserId);
                cartPst.setInt(2, productId);
                ResultSet cartRs = cartPst.executeQuery();

                if (cartRs.next()) {
                    cartQty = cartRs.getInt("quantity");
                }

                int remaining = stock - cartQty;

                if (remaining <= 0) {
                    JOptionPane.showMessageDialog(null, "Out of stock!");
                    return;
                }

                if (quantity > remaining) {
                    JOptionPane.showMessageDialog(null,
                        "Only " + remaining + " available.");
                    quantity = remaining; 
                }

                String checkSql = "SELECT quantity FROM cart WHERE user_id=? AND product_id=?";
                PreparedStatement checkPst = con.prepareStatement(checkSql);
                checkPst.setInt(1, UserSession.currentUserId);
                checkPst.setInt(2, productId);
                ResultSet rs = checkPst.executeQuery();

                if (rs.next()) {
                    String updateSql = "UPDATE cart SET quantity=? WHERE user_id=? AND product_id=?";
                    PreparedStatement updatePst = con.prepareStatement(updateSql);
                    updatePst.setInt(1, rs.getInt("quantity") + quantity);
                    updatePst.setInt(2, UserSession.currentUserId);
                    updatePst.setInt(3, productId);
                    updatePst.executeUpdate();
                } else {
                    String insertSql = "INSERT INTO cart(user_id, product_id, quantity) VALUES (?, ?, ?)";
                    PreparedStatement insertPst = con.prepareStatement(insertSql);
                    insertPst.setInt(1, UserSession.currentUserId);
                    insertPst.setInt(2, productId);
                    insertPst.setInt(3, quantity);
                    insertPst.executeUpdate();
                }

                JOptionPane.showMessageDialog(null, "Added to cart!");
                loadProducts();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    panel.add(text, BorderLayout.NORTH);
    panel.add(imgLabel, BorderLayout.CENTER);
    panel.add(bottomPanel, BorderLayout.SOUTH);

    return panel;

}
    public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(() -> {
        new ProductPage().setVisible(true);
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}