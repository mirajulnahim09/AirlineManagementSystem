package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddCustomer extends JFrame implements ActionListener {

    JTextField tfname, tfphone, tfaadhar, tfnationality, tfaddress;
    JRadioButton rbmale, rbfemale;

    public AddCustomer() {
       
        setTitle("Add Customer Details");
        getContentPane().setBackground(new Color(245, 245, 245));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Heading Label
        JLabel heading = new JLabel("Add Customer Details");
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(new Color(33, 150, 243));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(heading, gbc);

        
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

        
        JLabel lblnationality = new JLabel("Nationality:");
        lblnationality.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblnationality, gbc);

        tfnationality = new JTextField(15);
        gbc.gridx = 1;
        add(tfnationality, gbc);

        
        JLabel lblnid = new JLabel("NID Number:");
        lblnid.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblnid, gbc);

        tfaadhar = new JTextField(15);
        gbc.gridx = 1;
        add(tfaadhar, gbc);

        
        JLabel lbladdress = new JLabel("Address:");
        lbladdress.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(lbladdress, gbc);

        tfaddress = new JTextField(15);
        gbc.gridx = 1;
        add(tfaddress, gbc);

        
        JLabel lblgender = new JLabel("Gender:");
        lblgender.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(lblgender, gbc);

        ButtonGroup gendergroup = new ButtonGroup();

        rbmale = new JRadioButton("Male");
        rbmale.setBackground(new Color(245, 245, 245)); 
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

        
        JLabel lblphone = new JLabel("Phone:");
        lblphone.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        add(lblphone, gbc);

        tfphone = new JTextField(15);
        gbc.gridx = 1;
        add(tfphone, gbc);

        
        JButton save = new JButton("Save");
        save.setBackground(new Color(33, 150, 243));
        save.setForeground(Color.WHITE);
        save.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        save.addActionListener(this);
        add(save, gbc);

        setSize(500, 500); 
        setLocationRelativeTo(null);
        setVisible(true);
    }

    
    public void actionPerformed(ActionEvent ae) {
        String name = tfname.getText();
        String nationality = tfnationality.getText();
        String phone = tfphone.getText();
        String address = tfaddress.getText();
        String NID = tfaadhar.getText();
        String gender = null;

        if (rbmale.isSelected()) {
            gender = "Male";
        } else if (rbfemale.isSelected()) {
            gender = "Female";
        }

        try {
            Conn conn = new Conn();
            String query = "insert into passenger values('" + name + "', '" + nationality + "', '" + phone + "', '" + address + "', '" + NID + "', '" + gender + "')";
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
