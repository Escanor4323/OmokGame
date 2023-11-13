package GUIOmokGameConsole;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::callCreateGUIFrame);
    }

    public static void callCreateGUIFrame(){
        SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "An error occurred while starting the application: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }

    private static void createAndShowGUI() throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Omok Game Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        SelectionMenu selectionMenu = new SelectionMenu(500, 600);
        frame.add(selectionMenu, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

