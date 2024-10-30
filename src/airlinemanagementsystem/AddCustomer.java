package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddCustomer extends JFrame implements ActionListener {

    JTextField tfname, tfphone, tfaadhar, tfnationality, tfaddress;
    JRadioButton rbmale, rbfemale;

    public AddCustomer() {
        // Set window title and background color
        setTitle("Add Customer Details");
        getContentPane().setBackground(new Color(245, 245, 245));
        setLayout(new GridBagLayout());  // Use GridBagLayout for flexibility
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Add padding between components

        // Heading Label
        JLabel heading = new JLabel("Add Customer Details");
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(new Color(33, 150, 243));  // Use modern blue color
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(heading, gbc);

        // Name Label and Text Field
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblname = new JLabel("Name:");
        lblname.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblname, gbc);

        tfname = new JTextField(15);
        gbc.gridx = 1;
        add(tfname, gbc);

        // Nationality Label and Text Field
        JLabel lblnationality = new JLabel("Nationality:");
        lblnationality.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblnationality, gbc);

        tfnationality = new JTextField(15);
        gbc.gridx = 1;
        add(tfnationality, gbc);

        // NID (Aadhar) Label and Text Field
        JLabel lblnid = new JLabel("NID Number:");
        lblnid.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblnid, gbc);

        tfaadhar = new JTextField(15);
        gbc.gridx = 1;
        add(tfaadhar, gbc);

        // Address Label and Text Field
        JLabel lbladdress = new JLabel("Address:");
        lbladdress.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(lbladdress, gbc);

        tfaddress = new JTextField(15);
        gbc.gridx = 1;
        add(tfaddress, gbc);

        // Gender Label and Radio Buttons
        JLabel lblgender = new JLabel("Gender:");
        lblgender.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(lblgender, gbc);

        ButtonGroup gendergroup = new ButtonGroup();

        rbmale = new JRadioButton("Male");
        rbmale.setBackground(new Color(245, 245, 245));  // Use the same background color for modern design
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        add(rbmale, gbc);

        rbfemale = new JRadioButton("Female");
        rbfemale.setBackground(new Color(245, 245, 245));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        add(rbfemale, gbc);

        gendergroup.add(rbmale);
        gendergroup.add(rbfemale);

        // Phone Label and Text Field
        JLabel lblphone = new JLabel("Phone:");
        lblphone.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        add(lblphone, gbc);

        tfphone = new JTextField(15);
        gbc.gridx = 1;
        add(tfphone, gbc);

        // Save Button
        JButton save = new JButton("Save");
        save.setBackground(new Color(33, 150, 243));  // Use the modern blue color for buttons
        save.setForeground(Color.WHITE);
        save.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        save.addActionListener(this);
        add(save, gbc);

        setSize(500, 500);  // Adjust the window size for a cleaner interface
        setLocationRelativeTo(null);  // Center the window
        setVisible(true);
    }

    // Handle button click action
    public void actionPerformed(ActionEvent ae) {
        String name = tfname.getText();
        String nationality = tfnationality.getText();
        String phone = tfphone.getText();
        String address = tfaddress.getText();
        String aadhar = tfaadhar.getText();
        String gender = null;

        if (rbmale.isSelected()) {
            gender = "Male";
        } else if (rbfemale.isSelected()) {
            gender = "Female";
        }

        try {
            Conn conn = new Conn();
            String query = "insert into passenger values('" + name + "', '" + nationality + "', '" + phone + "', '" + address + "', '" + aadhar + "', '" + gender + "')";
            conn.s.executeUpdate(query);

            JOptionPane.showMessageDialog(null, "Customer Details Added Successfully");
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AddCustomer();
    }
}
