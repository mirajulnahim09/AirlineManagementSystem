package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;

public class Splash extends JFrame implements Runnable {
    Thread t;

    public Splash() {
        // Load image with a check for existence
        ImageIcon i1 = null;
        try {
            i1 = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/Splash2.png"));
            if (i1.getImageLoadStatus() != MediaTracker.COMPLETE) {
                throw new Exception("Image could not be loaded.");
            }
        } catch (Exception e) {
            System.err.println("Image file not found. Please check the path.");
            e.printStackTrace();
            return;  // Exit constructor if image loading fails
        }

        // Resize and display image
        Image i2 = i1.getImage().getScaledInstance(730, 550, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(i2));
        add(image);

        setVisible(true);

        // Animation for splash screen expansion
        int x = 1;
        for (int i = 2; i < 600; i += 4, x += 1) {
            setSize(i + x, i);
            setLocation(700 - ((i + x) / 2), 400 - (i / 2));
            try {
                Thread.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Start the thread
        t = new Thread(this);
        t.start();

        setVisible(true);
    }

    public void run() {
        try {
            Thread.sleep(3000);  // Display splash screen for 4 seconds
            setVisible(false);

            // Open the login frame for Airline Management System
            new Login();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Splash();
    }
}
