package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentMethod extends JFrame implements ActionListener {

    private JTextField cardNumberField, expiryDateField, cvvField, cardHolderField;
    private JButton payButton, cancelButton;
    private double amount;

    public PaymentMethod(double amount) {
        this.amount = amount; // The amount to be paid
        setTitle("Visa Card Payment");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Using modern Nimbus Look and Feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Header
        JLabel headerLabel = new JLabel("Enter Visa Card Details", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Payment Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Card Holder Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Card Holder Name:"), gbc);
        
        gbc.gridx = 1;
        cardHolderField = new JTextField(15);
        formPanel.add(cardHolderField, gbc);

        // Card Number
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Card Number:"), gbc);
        
        gbc.gridx = 1;
        cardNumberField = new JTextField(15);
        formPanel.add(cardNumberField, gbc);

        // Expiry Date
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Expiry Date (MM/YY):"), gbc);
        
        gbc.gridx = 1;
        expiryDateField = new JTextField(10);
        formPanel.add(expiryDateField, gbc);

        // CVV
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("CVV:"), gbc);
        
        gbc.gridx = 1;
        cvvField = new JTextField(5);
        formPanel.add(cvvField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        payButton = new JButton("Pay");
        payButton.addActionListener(this);
        buttonPanel.add(payButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == payButton) {
            String cardHolder = cardHolderField.getText().trim();
            String cardNumber = cardNumberField.getText().trim();
            String expiryDate = expiryDateField.getText().trim();
            String cvv = cvvField.getText().trim();

            if (validateCardDetails(cardHolder, cardNumber, expiryDate, cvv)) {
                processPayment(cardHolder, cardNumber, expiryDate, amount);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid card details. Please try again.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == cancelButton) {
            dispose();
        }
    }

    private boolean validateCardDetails(String cardHolder, String cardNumber, String expiryDate, String cvv) {
        if (cardHolder.isEmpty() || cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
            return false;
        }
        if (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            return false;
        }
        if (cvv.length() != 3 || !cvv.matches("\\d+")) {
            return false;
        }
        return true;
    }

    private void processPayment(String cardHolder, String cardNumber, String expiryDate, double amount) {
        try {
            String cardNumberHash = hashCardNumber(cardNumber);
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/airlinemanagementsystem", "root", "nahim.12n*");
            
            String query = "INSERT INTO Payment (cardholder_name, card_number_hash, expiry_date, amount, payment_status) VALUES (?, ?, ?, ?, 'COMPLETED')";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, cardHolder);
            pst.setString(2, cardNumberHash);
            pst.setString(3, expiryDate);
            pst.setDouble(4, amount);

            int result = pst.executeUpdate();
            if (result > 0) {
                new PaymentReceipt(cardHolder, cardNumberHash, expiryDate, amount);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Error processing payment.");
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "SQL Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error processing payment: " + e.getMessage());
        }
    }

    private String hashCardNumber(String cardNumber) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(cardNumber.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentMethod(250.00)); // Example usage with a payment amount of 250.00
    }
}
