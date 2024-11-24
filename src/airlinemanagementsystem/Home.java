package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {

    public Home() {
        // Setting up frame
        setLayout(null);

        // Adding background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/front.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(0, 0, 1600, 800);
        image.setLayout(null);
        add(image);

        // Adding heading image
        ImageIcon headingIcon = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/biman bangladesh.png"));
        JLabel heading = new JLabel(headingIcon);
        heading.setBounds(500, -150, headingIcon.getIconWidth(), headingIcon.getIconHeight());
        image.add(heading);

        // Creating modern-styled buttons
        JButton flightDetailsButton = createStyledButton("Flight Details", 100, 200);
        JButton customerDetailsButton = createStyledButton("Add Customer Details", 100, 270);
        JButton bookFlightButton = createStyledButton("Book Flight", 100, 340);
        JButton journeyDetailsButton = createStyledButton("Journey Details", 100, 410);
        JButton ticketCancellationButton = createStyledButton("Cancel Ticket", 100, 480);
        JButton boardingPassButton = createStyledButton("Boarding Pass", 100, 550);
        JButton visaPaymentButton = createStyledButton("Visa Card Payment", 100, 620);
        JButton reportButton = createStyledButton("Send Report", 100, 690);

        // Adding buttons to the image panel
        image.add(flightDetailsButton);
        image.add(customerDetailsButton);
        image.add(bookFlightButton);
        image.add(journeyDetailsButton);
        image.add(ticketCancellationButton);
        image.add(boardingPassButton);
        image.add(visaPaymentButton);
        image.add(reportButton);
        
        // Adding ActionListener for all buttons
        flightDetailsButton.addActionListener(this);
        customerDetailsButton.addActionListener(this);
        bookFlightButton.addActionListener(this);
        journeyDetailsButton.addActionListener(this);
        ticketCancellationButton.addActionListener(this);
        boardingPassButton.addActionListener(this);
        visaPaymentButton.addActionListener(this);
        reportButton.addActionListener(this);
        
        // Setting frame properties
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    // Method to create styled buttons
    private JButton createStyledButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 250, 50);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(51, 153, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    public void actionPerformed(ActionEvent ae) {
        String text = ae.getActionCommand();

        if (text.equals("Add Customer Details")) {
            new AddCustomer();
        } else if (text.equals("Flight Details")) {
            new FlightInfo();
        } else if (text.equals("Book Flight")) {
            new BookFlight();
        } else if (text.equals("Journey Details")) {
            new JourneyDetails();
        } else if (text.equals("Cancel Ticket")) {
            new Cancel();
        } else if (text.equals("Boarding Pass")) {
            new BoardingPass();
        } else if (text.equals("Visa Card Payment")) {
            // Open the PaymentMethod window with a sample amount
            new PaymentMethod(250); // You can replace 250.00 with the actual amount as needed
        } else if (text.equals("Send Report")) {
            new Report();
       }
    }

    public static void main(String[] args) {
        new Home();
    }
}
