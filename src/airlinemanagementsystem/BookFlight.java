package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.util.*;

public class BookFlight extends JFrame implements ActionListener {

    JTextField tfnid, tfphone;
    JLabel tfname, tfnationality, tfaddress, labelgender, labelfname, labelfcode;
    JButton bookflight, fetchButton, flight;
    JComboBox<String> source, destination;
    JDateChooser dcdate;

    public BookFlight() {
        setTitle("Flight Booking System");
        getContentPane().setBackground(new Color(245, 245, 245));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel heading = new JLabel("Book Flight");
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(new Color(33, 150, 243));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(heading, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblnid = new JLabel("NID:");
        lblnid.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblnid, gbc);

        tfnid = new JTextField(15);
        gbc.gridx = 1;
        add(tfnid, gbc);

        fetchButton = new JButton("Fetch User");
        fetchButton.setBackground(new Color(33, 150, 243));
        fetchButton.setForeground(Color.WHITE);
        fetchButton.addActionListener(this);
        gbc.gridx = 2;
        add(fetchButton, gbc);

        JLabel lblphone = new JLabel("Phone Number:");
        lblphone.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblphone, gbc);

        tfphone = new JTextField(15);
        gbc.gridx = 1;
        add(tfphone, gbc);

        JLabel lblname = new JLabel("Name:");
        lblname.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblname, gbc);

        tfname = new JLabel();
        gbc.gridx = 1;
        add(tfname, gbc);

        JLabel lblnationality = new JLabel("Nationality:");
        lblnationality.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(lblnationality, gbc);

        tfnationality = new JLabel();
        gbc.gridx = 1;
        add(tfnationality, gbc);

        JLabel lbladdress = new JLabel("Address:");
        lbladdress.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(lbladdress, gbc);

        tfaddress = new JLabel();
        gbc.gridx = 1;
        add(tfaddress, gbc);

        JLabel lblgender = new JLabel("Gender:");
        lblgender.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(lblgender, gbc);

        labelgender = new JLabel();
        gbc.gridx = 1;
        add(labelgender, gbc);

        JLabel lblsource = new JLabel("Source:");
        lblsource.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(lblsource, gbc);

        source = new JComboBox<>();
        gbc.gridx = 1;
        add(source, gbc);

        JLabel lbldest = new JLabel("Destination:");
        lbldest.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(lbldest, gbc);

        destination = new JComboBox<>();
        gbc.gridx = 1;
        add(destination, gbc);

        flight = new JButton("Fetch Flights");
        flight.setBackground(new Color(33, 150, 243));
        flight.setForeground(Color.WHITE);
        flight.addActionListener(this);
        gbc.gridx = 2;
        add(flight, gbc);

        JLabel lblfname = new JLabel("Flight Name:");
        lblfname.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 9;
        add(lblfname, gbc);

        labelfname = new JLabel();
        gbc.gridx = 1;
        add(labelfname, gbc);

        JLabel lblfcode = new JLabel("Flight Code:");
        lblfcode.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 10;
        add(lblfcode, gbc);

        labelfcode = new JLabel();
        gbc.gridx = 1;
        add(labelfcode, gbc);

        JLabel lbldate = new JLabel("Date of Travel:");
        lbldate.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 11;
        add(lbldate, gbc);

        dcdate = new JDateChooser();
        gbc.gridx = 1;
        add(dcdate, gbc);

        bookflight = new JButton("Book Flight");
        bookflight.setBackground(new Color(33, 150, 243));
        bookflight.setForeground(Color.WHITE);
        bookflight.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(bookflight, gbc);

        // Populate source and destination comboboxes
        try {
            Conn c = new Conn();
            String query = "SELECT DISTINCT source FROM flight";
            ResultSet rs = c.s.executeQuery(query);

            while (rs.next()) {
                source.addItem(rs.getString("source"));
            }

            rs = c.s.executeQuery("SELECT DISTINCT destination FROM flight");
            while (rs.next()) {
                destination.addItem(rs.getString("destination"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setSize(700, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            // Fetch user details
            String nid = tfnid.getText();
            String phone = tfphone.getText();

            if (nid.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter both NID and Phone Number.");
                return;
            }

            try {
                Conn conn = new Conn();
                String query = "SELECT * FROM passenger WHERE nid = '" + nid + "' AND phone = '" + phone + "'";
                ResultSet rs = conn.s.executeQuery(query);

                if (rs.next()) {
                    tfname.setText(rs.getString("name"));
                    tfnationality.setText(rs.getString("nationality"));
                    tfaddress.setText(rs.getString("address"));
                    labelgender.setText(rs.getString("gender"));
                } else {
                    JOptionPane.showMessageDialog(null, "No user found with the given NID and Phone Number.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == flight) {
            // Fetch flight details
            String src = (String) source.getSelectedItem();
            String dest = (String) destination.getSelectedItem();
            try {
                Conn conn = new Conn();
                String query = "SELECT * FROM flight WHERE source = '" + src + "' AND destination = '" + dest + "'";
                ResultSet rs = conn.s.executeQuery(query);

                if (rs.next()) {
                    labelfname.setText(rs.getString("f_name"));
                    labelfcode.setText(rs.getString("f_code"));
                } else {
                    JOptionPane.showMessageDialog(null, "No Flights Found");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == bookflight) {
            // Book flight
            String nid = tfnid.getText();
            String phone = tfphone.getText();
            String name = tfname.getText();
            String nationality = tfnationality.getText();
            String flightname = labelfname.getText();
            String flightcode = labelfcode.getText();
            String src = (String) source.getSelectedItem();
            String des = (String) destination.getSelectedItem();
            String ddate = ((JTextField) dcdate.getDateEditor().getUiComponent()).getText();

            // Validate inputs
            if (nid.isEmpty() || phone.isEmpty() || name.isEmpty() || nationality.isEmpty() || flightname.isEmpty() || flightcode.isEmpty() || src.isEmpty() || des.isEmpty() || ddate.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields before booking.");
                return;
            }

            Random random = new Random();
            try {
                Conn conn = new Conn();
                String query = "INSERT INTO reservation VALUES('PNR-" + random.nextInt(1000000) + "', 'TIC-" + random.nextInt(10000) + "', '" + nid + "', '" + name + "', '" + nationality + "', '" + flightname + "', '" + flightcode + "', '" + src + "', '" + des + "', '" + ddate + "', '" + phone + "')";
                
                // Debugging: Print the query to see if it's correct
                System.out.println("Executing query: " + query);
                
                conn.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Ticket Booked Successfully");
                setVisible(false); // Close the booking window
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error while booking flight. SQL State: " + e.getSQLState() + ", Error Code: " + e.getErrorCode() + ", Message: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error while booking flight. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        new BookFlight();
    }
}