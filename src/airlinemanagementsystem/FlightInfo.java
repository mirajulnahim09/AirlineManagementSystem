package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class FlightInfo extends JFrame {
    
    public FlightInfo() {
        
        // Frame settings
        setTitle("Flight Information");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set background and layout
        getContentPane().setBackground(new Color(50, 50, 50));
        setLayout(new BorderLayout(15, 15));
        
        // Create a header panel with a title
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 30, 30));
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Flight Information");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Initialize the table
        JTable table = new JTable();
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setForeground(new Color(30, 30, 30));

        // Style the table header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(60, 63, 65));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(100, 40));

        // Add alternating row colors and hover effect
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    component.setBackground(new Color(102, 153, 255));
                    component.setForeground(Color.WHITE);
                } else if (row % 2 == 0) {
                    component.setBackground(new Color(230, 230, 230));
                    component.setForeground(new Color(30, 30, 30));
                } else {
                    component.setBackground(Color.WHITE);
                    component.setForeground(new Color(30, 30, 30));
                }
                return component;
            }
        });

        // Add hover effect on rows
        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                int row = table.rowAtPoint(p);
                table.setRowSelectionInterval(row, row);
            }
        });

        // Populate table with data from database
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("SELECT * FROM flight");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add rounded borders and style to JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setBackground(new Color(245, 245, 245));
        
        // Add the JScrollPane with rounded borders
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(new Color(245, 245, 245));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true));
        
        add(tablePanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FlightInfo();
    }
}
