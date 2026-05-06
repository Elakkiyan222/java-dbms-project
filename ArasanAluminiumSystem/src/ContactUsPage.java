import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ContactUsPage extends JFrame {

    private JTextField nameField, emailField, phoneField;
    private JTextArea messageArea;

    public ContactUsPage() {

        setTitle("Contact Us");
        setSize(600, 450); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

    
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(25); 
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(25);
        panel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Phone:"), gbc);

        gbc.gridx = 1;
        phoneField = new JTextField(25);
        panel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Message:"), gbc);

        gbc.gridx = 1;
        messageArea = new JTextArea(5, 25);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(messageArea);
        scroll.setPreferredSize(new Dimension(300, 100)); 
        panel.add(scroll, gbc);

        gbc.gridx = 1; gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton submitBtn = new JButton("Submit");
        submitBtn.setPreferredSize(new Dimension(120, 35));
        panel.add(submitBtn, gbc);

        add(panel, BorderLayout.CENTER);

        submitBtn.addActionListener(e -> submitForm());
    }

    private void submitForm() {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String msg = messageArea.getText().trim();

        // EMPTY CHECK
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || msg.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        // EMAIL VALIDATION
        if (!email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {
            JOptionPane.showMessageDialog(this, "Enter valid Gmail (example@gmail.com)");
            return;
        }

        // PHONE VALIDATION
        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Phone must be 10 digits!");
            return;
        }

        // MESSAGE LENGTH
        if (msg.length() < 10) {
            JOptionPane.showMessageDialog(this, "Message must be at least 10 characters!");
            return;
        }

        // SAVE TO DB
        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO contact_us(name,email,phone,message) VALUES (?,?,?,?)";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setString(4, msg);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Message Sent Successfully!");

            // CLEAR
            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
            messageArea.setText("");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContactUsPage().setVisible(true));
    }
}