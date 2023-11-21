package GUIOmokGameConsole;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Represents the main window/panel of the game, (subclass of JPanel)
 */
public class BoardFrame extends JPanel {
    private final boolean isAIGame;
    private OmokGame game;
    private JToolBar toolBar;

    /**
     * Sets and initializes the object with the given parameters
     * @param player1 represents the main user
     * @param player2 represents a second user or an AI
     * @param isAIGame represents if the game is against an AI
     * @throws IOException if there is any given error while initializing MenuBar, ToolBar or UI
     */
    public BoardFrame(Player player1, Player player2, boolean isAIGame) throws IOException {
        this.isAIGame = isAIGame;
        initializeToolBar();
        initializeUI(player1, player2);
    }

    /**
     * Initializes a JMenuBar that has options such as restarting the game, quitting the game, and change the usernames
     */

    private void initializeToolBar() {
        toolBar = new JToolBar("Toolbar");

        // Define the base path and file names
        String basePath = "/GUIOmokGameConsole/assets/";
        String[] fileNames = {"newGame.png", "changeUsernames.png", "quit.png", "connectServer.png"};

        // Actions corresponding to each toolbar button
        Runnable[] actions = {
                () -> game.resetGame(),        // Action for newGame.png
                this::changeUserNames,       // Action for changeUsernames.png
                this::returnToSelectionMenu,  // Action for quit.png
                this::warn
        };

        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            URL iconURL = getClass().getResource(basePath + fileName);
            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);

                // Create a button with the icon and action
                JButton button = new JButton(icon);
                button.setFocusable(false);
                int finalI = i;
                button.addActionListener(e -> actions[finalI].run());

                // Add the button to the toolbar
                toolBar.add(button);
            } else {
                System.out.println("Icon not found for: " + fileName);
            }
        }
    }

    private void warn() {
        JOptionPane.showConfirmDialog(this,
                "This is test for running a server",
                "Server connect?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Takes user back to the original selection menu.
     */
    private void returnToSelectionMenu() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "This will return you to the selection menu.\nDo you want to continue?",
                "Return to Selection Menu", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame != null) {
                topFrame.dispose();
            }
            EventQueue.invokeLater(() -> {
                try {
                    JFrame selectionFrame = new JFrame("Selection Menu");
                    selectionFrame.setSize(new Dimension(500, 600));
                    SelectionMenu selectionMenu = new SelectionMenu(selectionFrame.getWidth(), selectionFrame.getHeight());
                    selectionFrame.setContentPane(selectionMenu);
                    selectionFrame.pack();
                    selectionFrame.setLocationRelativeTo(null);
                    selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    selectionFrame.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }


    /**
     * Displays the options of changing the current usernames of the players
     */
    private void changeUserNames() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "This will change the names of the players.\nDo you want to continue?",
                "Change Names", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            String player1Name = JOptionPane.showInputDialog(this, "Enter name for Player 1:", "Change Names", JOptionPane.QUESTION_MESSAGE);
            String player2Name = JOptionPane.showInputDialog(this, "Enter name for Player 2:", "Change Names", JOptionPane.QUESTION_MESSAGE);

            if (player1Name != null && player2Name != null) {
                game.getPlayer1().setName(player1Name);
                game.getPlayer1Panel().updateName(player1Name);
                game.getPlayer2().setName(player2Name);
                game.getPlayer2Panel().updateName(player2Name);
            }
        }
    }


    /**
     * Get the current Menu Bar
     * @return the menuBar associated with the current object
     */


    /**
     * Initializes the main interface of the Omok game, setting up the menu, tools and players panel
     * @param player1 the main user
     * @param player2 second user or AI
     * @throws IOException in case any of the initialization fails
     */

    private void initializeUI(Player player1, Player player2) throws IOException {
        this.setLayout(new BorderLayout());
        initializeToolBar();

        // Add the toolbar at the top (PAGE_START) of the panel.
        this.add(toolBar, BorderLayout.PAGE_START);

        PlayerPanel player1Panel = new PlayerPanel(player1);
        PlayerPanel player2Panel = new PlayerPanel(player2);

        this.add(player1Panel, BorderLayout.WEST);
        this.add(player2Panel, BorderLayout.EAST);

        game = new OmokGame(player1Panel, player1, player2Panel, player2, isAIGame);
        this.add(game, BorderLayout.CENTER);

        player1Panel.setPreferredSize(new Dimension(100, getHeight()));
        player2Panel.setPreferredSize(new Dimension(100, getHeight()));
    }

}

/**
 *
 */
class PlayerPanel extends JPanel {
    private BufferedImage backgroundImage;
    private Timer timer;
    private final JLabel statusLabel;
    private int dotCount = 0;
    private final JLabel nameLabel;

    public int getWinCounter() {
        return winCounter;
    }

    public void setWinCounter(int winCounter) {
        this.winCounter = winCounter;
    }

    private int winCounter = 0;

    public PlayerPanel(Player player) throws IOException {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("assets/background.png")));
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null;
        }

        this.setLayout(new BorderLayout());
        ImageHandler imageHandler = new ImageHandler("assets/userIcon.png", 100, 100);
        BufferedImage myPicture = imageHandler.getImage();

        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        JPanel combinedPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
                }
            }
        };
        combinedPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;

        combinedPanel.add(picLabel, gbc);

        gbc.gridy++;

        nameLabel = new JLabel(player.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setVerticalAlignment(SwingConstants.CENTER);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        combinedPanel.add(nameLabel, gbc);

        gbc.gridy++;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setVerticalAlignment(SwingConstants.TOP);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));
        combinedPanel.add(statusLabel, gbc);

        this.add(combinedPanel, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        initTimer();
    }

    /**
     * Sets a timer to update status every 1000 milliseconds meaning 1 second
     */

    private void initTimer() {
        timer = new Timer(1000, e -> updateStatus());
        timer.setInitialDelay(0);
    }

    /**
     * Sets the number of dots displayed on screen
     */

    private void updateStatus() {
        dotCount++;
        if (dotCount > 3) {
            dotCount = 1;
        }
        statusLabel.setText(" • ".repeat(dotCount));
    }

    /**
     * set the number of dots to 0
     */
    public void resetDots() {
        dotCount = 0;
        statusLabel.setText(" ");
    }

    /**
     * Displays the last move made
     * @param lastMove an array representing the last move made
     */
    public void moveMade(int[] lastMove) {
        String moveText = String.format("<html>Move made!<br/>\nAt (%d, %d)</html>", lastMove[0], lastMove[1]);
        statusLabel.setText(moveText);
        timer.stop();
    }

    /**
     * Updates the displayed name
     * @param newName new name to be displayed
     */
    public void updateName(String newName) {
        nameLabel.setText(newName);
        revalidate();
        repaint();
    }


    /**
     * Highlights the frame of a certain color if
     * it's the turn of the given user else the
     * panel is repainted black
     * @param isTurn boolean indicating the turn of the player or not
     */
    public void highlight(boolean isTurn) {
        if (isTurn) {
            this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
            resetDots();
            timer.start();
        }
        this.repaint();
    }
}
