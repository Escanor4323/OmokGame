package GUIOmokGameConsole;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * This class contains the main() method, which is the entry point of the
 * application. It seems to create the game's GUI and start the game.
 */

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::callCreateGUIFrame);
    }

    /**
     * Using SwingUtilities calls method createAndShowGUI when necessary
     * @throws IOException e if there is an error of any type while creating GUI
     */
    public static void callCreateGUIFrame(){
        SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "An error occurred while starting the application: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
    /**
     * Creates and displays an object of type SelectionMenu
     * @throws Exception if there is an error while setting an UIManager
     */
    private static void createAndShowGUI() throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        JFrame frame = new JFrame("Omok Game Selection");
        ImageIcon icon = new ImageIcon("/assets/icon.png");
        frame.setIconImage(icon.getImage());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        SelectionMenu selectionMenu = new SelectionMenu(500, 600);
        frame.add(selectionMenu, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

