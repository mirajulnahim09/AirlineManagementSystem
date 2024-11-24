package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignUp extends JFrame implements ActionListener {

    JButton create, back;
    JComboBox<String> accountType;
    JTextField username, name;
    JPasswordField password;

    public SignUp() {
        setTitle("Airline Management System - Signup");
        setBounds(450, 150, 700, 400);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(30, 30, 650, 300);
        panel.setBorder(new TitledBorder(new LineBorder(new Color(173, 216, 230), 2), "Create Account", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(72, 61, 139)));
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setForeground(new Color(34, 139, 34));
        add(panel);

        // Account Type
        JLabel heading = new JLabel("Create Account As");
        heading.setBounds(100, 50, 140, 20);
        heading.setForeground(Color.GRAY);
        heading.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(heading);

        accountType = new JComboBox<>(new String[]{"Admin", "Customer"});
        accountType.setBounds(260, 50, 150, 20);
        accountType.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(accountType);

        // Username
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(100, 90, 140, 20);
        lblUsername.setForeground(Color.GRAY);
        lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(lblUsername);

        username = new JTextField();
        username.setBounds(260, 90, 150, 20);
        username.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(username);

        // Name
        JLabel lblName = new JLabel("Name");
        lblName.setBounds(100, 130, 140, 20);
        lblName.setForeground(Color.GRAY);
        lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(lblName);

        name = new JTextField();
        name.setBounds(260, 130, 150, 20);
        name.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(name);

        // Password
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(100, 170, 140, 20);
        lblPassword.setForeground(Color.GRAY);
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(lblPassword);

        password = new JPasswordField();
        password.setBounds(260, 170, 150, 20);
        password.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(password);

        // Create Button
        create = new JButton("Create");
        styleButton(create);
        create.setBounds(140, 220, 120, 30);
        create.addActionListener(this);
        panel.add(create);

        // Back Button
        back = new JButton("Back");
        styleButton(back);
        back.setBounds(300, 220, 120, 30);
        back.addActionListener(this);
        panel.add(back);

        setVisible(true);
    }

    // Styling buttons with a modern look
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(33, 150, 243));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    // Action handling for buttons
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == create) {
            String atype = (String) accountType.getSelectedItem();
            String susername = username.getText();
            String sname = name.getText();
            String spassword = new String(password.getPassword());

            // Validate input fields
            if (susername.isEmpty() || sname.isEmpty() || spassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields");
                return;
            }

            try {
                // Create connection to the database
                Conn c = new Conn();
                String query;

                // Check if Admin or Customer account is being created
                if ("Admin".equals(atype)) {
                    query = "INSERT INTO admin(username, name, password) VALUES (?, ?, ?)";
                } else {
                    query = "INSERT INTO customer(username, name, password) VALUES (?, ?, ?)";
                }

                // Prepare statement and set values
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, susername);
                pst.setString(2, sname);
                pst.setString(3, spassword);

                // Execute update and check if successful
                int result = pst.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Account Created Successfully");
                    setVisible(false);
                    new Login();
                } else {
                    JOptionPane.showMessageDialog(null, "Error Creating Account");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Unexpected Error: " + e.getMessage());
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new SignUp();
    }
}
