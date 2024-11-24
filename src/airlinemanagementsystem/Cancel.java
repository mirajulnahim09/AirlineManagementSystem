package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class Cancel extends JFrame implements ActionListener {
    
    JTextField tfpnr;
    JLabel tfname, cancellationno, lblfcode, lbldateoftravel;
    JButton fetchButton, flight;
    
    public Cancel() {
        
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        Random random = new Random();
        
        
        JLabel heading = new JLabel("Ticket Cancellation");
        heading.setBounds(180, 20, 400, 35);
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        heading.setForeground(Color.DARK_GRAY);
        add(heading);
        
        // PNR Label
        JLabel lblaadhar = new JLabel("PNR Number:");
        lblaadhar.setBounds(60, 80, 150, 25);
        lblaadhar.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblaadhar);
        
        // PNR Input Field
        tfpnr = new JTextField();
        tfpnr.setBounds(220, 80, 200, 25);
        tfpnr.setFont(new Font("Arial", Font.PLAIN, 14));
        add(tfpnr);
        
        // Fetch Button (Flat Design)
        fetchButton = new JButton("Show Details");
        fetchButton.setBackground(Color.BLUE);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        fetchButton.setBounds(440, 80, 150, 25);
        fetchButton.addActionListener(this);
        add(fetchButton);
        
        // Name Label
        JLabel lblname = new JLabel("Name:");
        lblname.setBounds(60, 130, 150, 25);
        lblname.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblname);
        
        // Name Display
        tfname = new JLabel();
        tfname.setBounds(220, 130, 200, 25);
        tfname.setFont(new Font("Arial", Font.PLAIN, 16));
        add(tfname);
        
        // Cancellation Number Label
        JLabel lblnationality = new JLabel("Cancellation No:");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblnationality);
        
        // Random Cancellation Number
        cancellationno = new JLabel("" + random.nextInt(1000000));
        cancellationno.setBounds(220, 180, 200, 25);
        cancellationno.setFont(new Font("Arial", Font.PLAIN, 16));
        add(cancellationno);
        
        // Flight Code Label
        JLabel lbladdress = new JLabel("Flight Code:");
        lbladdress.setBounds(60, 230, 150, 25);
        lbladdress.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lbladdress);
        
        // Flight Code Display
        lblfcode = new JLabel();
        lblfcode.setBounds(220, 230, 200, 25);
        lblfcode.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblfcode);
        
        // Travel Date Label
        JLabel lblgender = new JLabel("Date of Travel:");
        lblgender.setBounds(60, 280, 150, 25);
        lblgender.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblgender);
        
        // Date of Travel Display
        lbldateoftravel = new JLabel();
        lbldateoftravel.setBounds(220, 280, 200, 25);
        lbldateoftravel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lbldateoftravel);
        
        // Cancel Button (Modern Style)
        flight = new JButton("Cancel Ticket");
        flight.setBackground(Color.RED);
        flight.setForeground(Color.WHITE);
        flight.setFont(new Font("Arial", Font.PLAIN, 14));
        flight.setBounds(220, 330, 150, 30);
        flight.addActionListener(this);
        add(flight);
        
        // Frame Properties
        setSize(700, 450);
        setLocation(350, 150);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == fetchButton) {
        String pnr = tfpnr.getText();
        
        try {
            Conn conn = new Conn();
            String query = "select * from reservation where PNR = '" + pnr + "'";
            ResultSet rs = conn.s.executeQuery(query);
            
            if (rs.next()) {
                String flightCode = rs.getString("flightcode"); // Updated column name
                String travelDate = rs.getString("ddate");       // Changed back to "ddate"
                
                // Debugging output to the console
                System.out.println("Flight Code: " + flightCode);
                System.out.println("Date of Travel: " + travelDate);
                
                // Updating the fields with fetched data
                tfname.setText(rs.getString("name"));
                lblfcode.setText(flightCode);
                lbldateoftravel.setText(travelDate);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter correct PNR");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching details: " + e.getMessage());
        }
    } else if (ae.getSource() == flight) {
        String name = tfname.getText();
        String pnr = tfpnr.getText();
        String cancelno = cancellationno.getText();
        String fcode = lblfcode.getText();
        String date = lbldateoftravel.getText();
        
        try {
            Conn conn = new Conn();
            String query = "insert into cancel values('" + pnr + "', '" + name + "', '" + cancelno + "', '" + fcode + "', '" + date + "')";
            conn.s.executeUpdate(query);
            conn.s.executeUpdate("delete from reservation where PNR = '" + pnr + "'");
            
            JOptionPane.showMessageDialog(null, "Ticket Cancelled");
            setVisible(false);
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}


    public static void main(String[] args) {
        new Cancel();
    }
}
