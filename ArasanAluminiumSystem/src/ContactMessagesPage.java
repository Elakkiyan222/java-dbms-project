import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ContactMessagesPage extends JFrame {

    JPanel container;

    public ContactMessagesPage() {

        setTitle("Contact Messages");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(container);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll);

        loadMessages();
    }

    private void loadMessages() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM contact_us ORDER BY id DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                JPanel card = new JPanel();
                card.setLayout(new GridLayout(0, 1));
                card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                card.setBackground(new Color(245, 245, 245));

                JLabel name = new JLabel("Name: " + rs.getString("name"));
                JLabel email = new JLabel("📧 Email: " + rs.getString("email"));
                JLabel phone = new JLabel("📞 Phone: " + rs.getString("phone"));
                JLabel msg = new JLabel(" Message: " + rs.getString("message"));
                JLabel date = new JLabel("📅 Date: " + rs.getTimestamp("created_at"));

                name.setFont(new Font("Arial", Font.BOLD, 14));
                msg.setFont(new Font("Arial", Font.PLAIN, 13));

                JButton deleteBtn = new JButton("Delete");
                deleteBtn.setBackground(Color.RED);
                deleteBtn.setForeground(Color.WHITE);

                int id = rs.getInt("id");

                deleteBtn.addActionListener(e -> deleteMessage(id));

                card.add(name);
                card.add(email);
                card.add(phone);
                card.add(msg);
                card.add(date);
                card.add(deleteBtn);

                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

                container.add(Box.createVerticalStrut(10));
                container.add(card);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteMessage(int id) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM contact_us WHERE id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Deleted!");

            container.removeAll();
            loadMessages();
            container.revalidate();
            container.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ContactMessagesPage().setVisible(true);
    }
}








