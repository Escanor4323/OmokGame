package GUIOmokGameConsole;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createBoardFrame);
    }

    public static void createBoardFrame(){
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("GoMoku!");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            BoardFrame boardFrame = null;
            try {
                boardFrame = new BoardFrame("Joel", "AI");
            } catch (IOException e) {
                System.out.println(e);
            }
            assert boardFrame != null;
            frame.add(boardFrame, BorderLayout.CENTER);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Omok Game Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        SelectionMenu selectionMenu = new SelectionMenu();
        frame.add(selectionMenu, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

