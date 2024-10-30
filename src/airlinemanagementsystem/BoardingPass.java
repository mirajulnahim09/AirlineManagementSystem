package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BoardingPass extends JFrame implements ActionListener {

    JTextField tfpnr;
    JLabel tfname, tfnationality, lblsrc, lbldest, labelfname, labelfcode, labeldate;
    JButton fetchButton;

    public BoardingPass() {
        // Set background color
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Heading
        JLabel heading = new JLabel("AIRLINE TICKET");
        heading.setBounds(320, 10, 450, 35);
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        add(heading);

        // Subheading
        JLabel subheading = new JLabel("Boarding Pass");
        subheading.setBounds(360, 50, 300, 30);
        subheading.setFont(new Font("Arial", Font.PLAIN, 22));
        subheading.setForeground(Color.DARK_GRAY);
        add(subheading);

        // PNR input
        JLabel lblaadhar = new JLabel("PNR Number");
        lblaadhar.setBounds(60, 100, 150, 25);
        lblaadhar.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblaadhar);

        tfpnr = new JTextField();
        tfpnr.setBounds(220, 100, 200, 25);
        tfpnr.setFont(new Font("Arial", Font.PLAIN, 14));
        add(tfpnr);

        fetchButton = new JButton("Fetch Details");
        fetchButton.setBackground(Color.BLUE);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        fetchButton.setBounds(440, 100, 150, 25);
        fetchButton.addActionListener(this);
        add(fetchButton);

        // Name
        JLabel lblname = new JLabel("Name:");
        lblname.setBounds(60, 140, 150, 25);
        lblname.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblname);

        tfname = new JLabel();
        tfname.setBounds(220, 140, 200, 25);
        tfname.setFont(new Font("Arial", Font.PLAIN, 16));
        add(tfname);

        // Nationality
        JLabel lblnationality = new JLabel("Nationality:");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblnationality);

        tfnationality = new JLabel();
        tfnationality.setBounds(220, 180, 200, 25);
        tfnationality.setFont(new Font("Arial", Font.PLAIN, 16));
        add(tfnationality);

        // Source
        JLabel lblsrc = new JLabel("From:");
        lblsrc.setBounds(60, 220, 150, 25);
        lblsrc.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblsrc);

        this.lblsrc = new JLabel();
        this.lblsrc.setBounds(220, 220, 200, 25);
        this.lblsrc.setFont(new Font("Arial", Font.PLAIN, 16));
        add(this.lblsrc);

        // Destination
        JLabel lbldest = new JLabel("To:");
        lbldest.setBounds(60, 260, 150, 25);
        lbldest.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lbldest);

        this.lbldest = new JLabel();
        this.lbldest.setBounds(220, 260, 200, 25);
        this.lbldest.setFont(new Font("Arial", Font.PLAIN, 16));
        add(this.lbldest);

        // Flight Name
        JLabel lblfname = new JLabel("Flight Name:");
        lblfname.setBounds(60, 300, 150, 25);
        lblfname.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblfname);

        labelfname = new JLabel();
        labelfname.setBounds(220, 300, 200, 25);
        labelfname.setFont(new Font("Arial", Font.PLAIN, 16));
        add(labelfname);

        // Flight Code
        JLabel lblfcode = new JLabel("Flight Code:");
        lblfcode.setBounds(60, 340, 150, 25);
        lblfcode.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblfcode);

        labelfcode = new JLabel();
        labelfcode.setBounds(220, 340, 200, 25);
        labelfcode.setFont(new Font("Arial", Font.PLAIN, 16));
        add(labelfcode);

        // Flight Date
        JLabel lbldate = new JLabel("Date:");
        lbldate.setBounds(60, 380, 150, 25);
        lbldate.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lbldate);

        labeldate = new JLabel();
        labeldate.setBounds(220, 380, 200, 25);
        labeldate.setFont(new Font("Arial", Font.PLAIN, 16));
        add(labeldate);

        // Frame properties
        setSize(800, 500);
        setLocation(300, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String pnr = tfpnr.getText();

        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a valid PNR number");
            return;
        }

        try {
            Conn conn = new Conn(); // Assuming Conn is properly defined elsewhere

            String query = "select * from reservation where PNR = '" + pnr + "'";
            ResultSet rs = conn.s.executeQuery(query);

            if (rs.next()) {
                tfname.setText(rs.getString("name"));
                tfnationality.setText(rs.getString("nationality"));
                lblsrc.setText(rs.getString("src"));
                lbldest.setText(rs.getString("des"));
                labelfname.setText(rs.getString("flightname"));
                labelfcode.setText(rs.getString("flightcode"));
                labeldate.setText(rs.getString("ddate"));
            } else {
                JOptionPane.showMessageDialog(null, "Invalid PNR");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BoardingPass();
    }
}
