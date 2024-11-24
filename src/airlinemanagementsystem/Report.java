package airlinemanagementsystem;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class Report extends JFrame implements ActionListener {

    private JTextArea messageArea;
    private JButton sendButton, attachButton;
    private JLabel wordCountLabel;
    private File attachedFile = null;
    private final int WORD_LIMIT = 200;
    private final String placeholderText = "Enter your message here (max 200 words)";
    private boolean placeholderVisible = true;

    public Report() {
        setupUI();
    }

    private void setupUI() {
        setTitle("Send Report");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        messageArea = new JTextArea(placeholderText);
        messageArea.setForeground(Color.GRAY);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        messageArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        messageArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (placeholderVisible) {
                    messageArea.setText("");
                    messageArea.setForeground(Color.BLACK);
                    placeholderVisible = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (messageArea.getText().isEmpty()) {
                    messageArea.setForeground(Color.GRAY);
                    messageArea.setText(placeholderText);
                    placeholderVisible = true;
                }
            }
        });
        messageArea.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                updateWordCount();
            }
        });

        wordCountLabel = new JLabel("Word count: 0/" + WORD_LIMIT);
        wordCountLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        wordCountLabel.setForeground(new Color(100, 100, 100));

        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBackground(new Color(245, 245, 245));
        messagePanel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
        messagePanel.add(wordCountLabel, BorderLayout.SOUTH);

        attachButton = createRoundedButton("Attach File");
        sendButton = createRoundedButton("Send Report");

        attachButton.addActionListener(this);
        sendButton.addActionListener(this);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(245, 245, 245));
        bottomPanel.add(attachButton);
        bottomPanel.add(sendButton);

        add(messagePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(new Color(30, 144, 255).darker());
                } else if (getModel().isRollover()) {
                    g.setColor(new Color(30, 144, 255).brighter());
                } else {
                    g.setColor(new Color(30, 144, 255));
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(new Color(30, 144, 255));
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    private void updateWordCount() {
        String text = messageArea.getText().trim();
        int wordCount = text.isEmpty() ? 0 : text.split("\\s+").length;
        wordCountLabel.setText("Word count: " + wordCount + "/" + WORD_LIMIT);
        wordCountLabel.setForeground(wordCount > WORD_LIMIT ? Color.RED : new Color(100, 100, 100));
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();

        if (command.equals("Attach File")) {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                attachedFile = fileChooser.getSelectedFile();
                if (attachedFile.length() > 1024 * 1024 * 5) { // Limit file size to 5MB
                    JOptionPane.showMessageDialog(this, "File size exceeds 5MB. Please choose a smaller file.");
                    attachedFile = null;
                } else {
                    JOptionPane.showMessageDialog(this, "Attached: " + attachedFile.getName());
                }
            }
        } else if (command.equals("Send Report")) {
            String messageText = messageArea.getText().trim();
            int wordCount = messageText.isEmpty() ? 0 : messageText.split("\\s+").length;
            if (wordCount > WORD_LIMIT) {
                JOptionPane.showMessageDialog(this, "Message is too long. Please limit to 200 words.");
                return;
            }

            sendEmail(messageText, attachedFile);
        }
    }

    private void sendEmail(String messageText, File fileToAttach) {
        // Dummy email sending code with confirmation message
        JOptionPane.showMessageDialog(this, "Report sent successfully");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Report());
    }
}
