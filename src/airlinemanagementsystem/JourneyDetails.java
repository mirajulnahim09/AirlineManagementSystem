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
        
        getContentPane().setBackground(new Color(245, 245, 245));
        setLayout(null);
        
        
        JLabel lblpnr = new JLabel("Journey Details");
        lblpnr.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblpnr.setForeground(new Color(34, 45, 65));
        lblpnr.setBounds(300, 20, 200, 30);
        add(lblpnr);
        
        
        JLabel lblpnrLabel = new JLabel("Enter PNR:");
        lblpnrLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblpnrLabel.setForeground(new Color(34, 45, 65));
        lblpnrLabel.setBounds(180, 80, 100, 25);
        add(lblpnrLabel);

        
        pnr = new JTextField();
        pnr.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnr.setBounds(280, 80, 200, 30);
        pnr.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 150, 200), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)  // Inner padding
        ));
        add(pnr);
        
        
        show = new JButton("Show Details");
        show.setFont(new Font("Segoe UI", Font.BOLD, 14));
        show.setBackground(new Color(100, 150, 200));  
        show.setForeground(Color.WHITE);
        show.setBounds(500, 80, 150, 30);
        show.setFocusPainted(false);
        show.setBorder(BorderFactory.createEmptyBorder()); 
        show.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        
        // Button hover effect
        show.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                show.setBackground(new Color(80, 130, 180));  
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                show.setBackground(new Color(100, 150, 200));  // Original color on exi
            }
        });
        show.addActionListener(this);
        add(show);
        
        
        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);  // Increase row height for better visibility
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(100, 150, 200));
        table.getTableHeader().setForeground(Color.WHITE);

        
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(50, 150, 900, 500);
        jsp.setBackground(Color.WHITE);
        jsp.setBorder(BorderFactory.createLineBorder(new Color(100, 150, 200), 1));
        add(jsp);

        
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from reservation where PNR = '" + pnr.getText() + "'");
            
            
            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "No Information Found");
                return;
            }
            
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JourneyDetails();
    }
}
