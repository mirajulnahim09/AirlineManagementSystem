package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JButton submit, reset, close;
    JTextField tfUsername;
    JPasswordField tfPassword;

    public Login() {
        setTitle("Login");
        setLayout(new BorderLayout());
        
        JPanel gradientPanel = new GradientPanel();
        gradientPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font defaultFont = new Font("Arial", Font.PLAIN, 16);

        JLabel lblLogin = new JLabel("User Login", JLabel.CENTER);
        lblLogin.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gradientPanel.add(lblLogin, gbc);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(defaultFont);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        gradientPanel.add(lblUsername, gbc);

        tfUsername = new JTextField(15);
        gbc.gridx = 1;
        gradientPanel.add(tfUsername, gbc);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(defaultFont);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        gradientPanel.add(lblPassword, gbc);

        tfPassword = new JPasswordField(15);
        gbc.gridx = 1;
        gradientPanel.add(tfPassword, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        submit = new JButton("Login");
        styleButton(submit);
        submit.addActionListener(this);
        buttonPanel.add(submit);

        reset = new JButton("Reset");
        styleButton(reset);
        reset.addActionListener(this);
        buttonPanel.add(reset);

        close = new JButton("Close");
        styleButton(close);
        close.addActionListener(this);
        buttonPanel.add(close);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gradientPanel.add(buttonPanel, gbc);

        add(gradientPanel, BorderLayout.CENTER);
        
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, Color.CYAN, 0, getHeight(), Color.MAGENTA);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(33, 150, 243));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String username = tfUsername.getText();
            String password = String.valueOf(tfPassword.getPassword());

            try {
                Conn c = new Conn();
                String query = "SELECT * FROM login WHERE username = ? AND password = ?";

                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, username);
                pst.setString(2, password);

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    setVisible(false);
                    new Home(); // Opens the Home window after successful login
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                }
            } catch (SQLException sqlException) {
                JOptionPane.showMessageDialog(null, "SQL Exception: " + sqlException.getMessage());
                sqlException.printStackTrace();
            }
        } else if (ae.getSource() == close) {
            setVisible(false);
        } else if (ae.getSource() == reset) {
            tfUsername.setText("");
            tfPassword.setText("");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
