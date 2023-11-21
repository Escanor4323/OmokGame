package GUIOmokGameConsole;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class testToolBar {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Toolbar with Icon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JToolBar toolBar = new JToolBar("Toolbar");

        // Define the base path and file names
        String basePath = "/GUIOmokGameConsole/assets/";
        String[] fileNames = {"resetGame.png", "newGame.png", "changeUsernames.png", "quit.png"};

        for (String fileName : fileNames) {
            URL iconURL = testToolBar.class.getResource(basePath + fileName);
            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);

                // Create a button with the icon
                JButton button = new JButton(icon);
                button.setFocusable(false); // Optional: to remove a focus border around the button

                // Add the button to the toolbar
                toolBar.add(button);
            } else {
                System.out.println("Icon not found for: " + fileName);
            }
        }

        // Add the toolbar to the frame
        frame.add(toolBar, BorderLayout.NORTH);

        // Finalize and display the frame
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
