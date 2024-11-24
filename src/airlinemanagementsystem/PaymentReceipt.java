package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentReceipt extends JFrame implements Printable {
    private String cardHolder;
    private String cardNumberHash;
    private String expiryDate;
    private double amount;

    public PaymentReceipt(String cardHolder, String cardNumberHash, String expiryDate, double amount) {
        this.cardHolder = cardHolder;
        this.cardNumberHash = cardNumberHash;
        this.expiryDate = expiryDate;
        this.amount = amount;

        // Set up the JFrame
        setTitle("Payment Receipt");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create receipt content in JTextArea
        JTextArea receiptArea = new JTextArea();
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        receiptArea.setText(generateReceipt());
        receiptArea.setEditable(false);
        add(new JScrollPane(receiptArea), BorderLayout.CENTER);

        // Create Print Button
        JButton printButton = new JButton("Print Receipt");
        printButton.addActionListener(e -> printReceipt());
        add(printButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        // Dynamic receipt details with spacing for alignment
        receipt.append("---------- Payment Receipt ----------\n");
        receipt.append("Date:           ").append(formatter.format(date)).append("\n");
        receipt.append("Card Holder:    ").append(cardHolder).append("\n");
        receipt.append("Card Number:    ").append(maskCardNumber(cardNumberHash)).append("\n");
        receipt.append("Expiry Date:    ").append(expiryDate).append("\n");
        receipt.append("Amount Paid:    $").append(String.format("%.2f", amount)).append("\n");
        receipt.append("Status:         Payment Completed\n");
        receipt.append("------------------------------------\n");

        return receipt.toString();
    }

    private String maskCardNumber(String cardNumberHash) {
        // Display only the last 4 digits of the card number for security
        if (cardNumberHash.length() >= 4) {
            return "**** **** **** " + cardNumberHash.substring(cardNumberHash.length() - 4);
        }
        return "Invalid Card Number";
    }

    private void printReceipt() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Printing Error: " + e.getMessage());
            }
        }
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));

        // Print each line from the generated receipt at specific Y coordinates
        int y = 100;
        for (String line : generateReceipt().split("\n")) {
            g2d.drawString(line, 100, y);
            y += 15;
        }
        return PAGE_EXISTS;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentReceipt("John Doe", "1234567812345678", "12/25", 250.00));
    }
}
