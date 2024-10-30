package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class JourneyDetails extends JFrame implements ActionListener {
    JTable table;
    JTextField pnr;
    JButton show;

    public JourneyDetails() {
        // Set modern background color
        getContentPane().setBackground(new Color(245, 245, 245)); // light grey
        setLayout(null);
        
        // Title Label
        JLabel lblpnr = new JLabel("Journey Details");
        lblpnr.setFont(new Font("Segoe UI", Font.BOLD, 22));  // Modern sans-serif font
        lblpnr.setForeground(new Color(34, 45, 65));  // Dark greyish-blue
        lblpnr.setBounds(300, 20, 200, 30);
        add(lblpnr);
        
        // PNR Label
        JLabel lblpnrLabel = new JLabel("Enter PNR:");
        lblpnrLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblpnrLabel.setForeground(new Color(34, 45, 65));
        lblpnrLabel.setBounds(180, 80, 100, 25);
        add(lblpnrLabel);

        // PNR Input Field with Modern Design
        pnr = new JTextField();
        pnr.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnr.setBounds(280, 80, 200, 30);
        pnr.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 150, 200), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)  // Inner padding
        ));
        add(pnr);
        
        // Show Details Button with modern styling
        show = new JButton("Show Details");
        show.setFont(new Font("Segoe UI", Font.BOLD, 14));
        show.setBackground(new Color(100, 150, 200));  // Soft blue color
        show.setForeground(Color.WHITE);
        show.setBounds(500, 80, 150, 30);
        show.setFocusPainted(false);  // Remove focus outline
        show.setBorder(BorderFactory.createEmptyBorder());  // Remove default border
        show.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Change cursor to hand on hover
        
        // Button hover effect
        show.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                show.setBackground(new Color(80, 130, 180));  // Darker blue on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                show.setBackground(new Color(100, 150, 200));  // Original color on exit
            }
        });
        show.addActionListener(this);
        add(show);
        
        // Table for displaying journey details
        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);  // Increase row height for better visibility
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(100, 150, 200));
        table.getTableHeader().setForeground(Color.WHITE);

        // Scroll Pane for Table with increased size
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(50, 150, 900, 500);  // Adjusted scroll pane size to fit more data
        jsp.setBackground(Color.WHITE);
        jsp.setBorder(BorderFactory.createLineBorder(new Color(100, 150, 200), 1));
        add(jsp);

        // Frame Properties
        setSize(1000, 800);  // Increased frame width and height for larger table
        setLocationRelativeTo(null);  // Center the window on the screen
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from reservation where PNR = '" + pnr.getText() + "'");
            
            // If no data found for the given PNR, show a dialog
            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "No Information Found");
                return;
            }
            // Populate the table with data
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JourneyDetails();
    }
}
