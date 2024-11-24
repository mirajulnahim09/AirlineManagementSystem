package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JButton login, cancel, signup;
    JTextField usernameField;
    JPasswordField passwordField;
    JComboBox<String> loginAsCombo;

    public Login() {
        setTitle("Airline Management System - Login");
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Header Label
        JLabel header = new JLabel("Airline Management System", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setForeground(new Color(0, 128, 255));
        add(header, BorderLayout.NORTH);

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(lblUsername, gbc);

        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        // Password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(lblPassword, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        // Login as
        JLabel lblLoginAs = new JLabel("Log in as:");
        lblLoginAs.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(lblLoginAs, gbc);

        loginAsCombo = new JComboBox<>(new String[]{"Admin", "Customer"});
        loginAsCombo.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        loginPanel.add(loginAsCombo, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        login = new JButton("Login");
        styleButton(login);
        login.addActionListener(this);
        buttonPanel.add(login);

        cancel = new JButton("Cancel");
        styleButton(cancel);
        cancel.addActionListener(this);
        buttonPanel.add(cancel);

        signup = new JButton("Signup");
        styleButton(signup);
        signup.addActionListener(this);
        buttonPanel.add(signup);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginPanel.add(buttonPanel, gbc);

        add(loginPanel, BorderLayout.CENTER);

        // Frame properties
        setSize(450, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Method to style buttons
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(33, 150, 243));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    // Handling button actions
   public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == login) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String userType = (String) loginAsCombo.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        try {
            // Establish connection using Conn class
            Conn conn = new Conn();
            
            // Debug print statements
            System.out.println("Attempting login with Username: " + username + ", Role: " + userType);

            // Query for user validation with PreparedStatement, including role check (case insensitive)
            String query = "SELECT * FROM login WHERE LOWER(username) = LOWER(?) AND password = ? AND LOWER(role) = LOWER(?)";
            PreparedStatement pst = conn.c.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, userType);

            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                setVisible(false); // Close login window
                new Home(); // Open home window
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username, Password, or Role");
                usernameField.setText("");
                passwordField.setText("");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage());
        }
    } else if (ae.getSource() == cancel) {
        setVisible(false);
    } else if (ae.getSource() == signup) {
        setVisible(false);
        new SignUp();
    }
}

    public static void main(String[] args) {
        new Login();
    }
}
