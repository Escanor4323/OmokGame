package GUIOmokGameConsole;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class BoardFrame extends JPanel {
    private final boolean isAIGame;
    private OmokGame game;
    private JMenuBar menuBar;
    private JToolBar toolBar;

    public BoardFrame(Player player1, Player player2, boolean isAIGame) throws IOException {
        this.isAIGame = isAIGame;
        initializeMenuBar();
        initializeToolBar();
        initializeUI(player1, player2);
    }

    private void initializeMenuBar() {
        menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);

        JMenuItem newGameItem = new JMenuItem("New Game", new ImageIcon("/GUIOmokGameConsole/assets/newGame.png"));
        newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        newGameItem.addActionListener(e -> game.resetGame());
        gameMenu.add(newGameItem);

        JMenuItem changeNamesItem = new JMenuItem("Change Usernames", new ImageIcon("/GUIOmokGameConsole/assets/changeNames.png"));
        changeNamesItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        changeNamesItem.addActionListener(e -> changeUserNames());
        gameMenu.add(changeNamesItem);

        JMenuItem quitItem = new JMenuItem("Quit", new ImageIcon("/GUIOmokGameConsole/assets/changeNames.png"));
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        quitItem.addActionListener(e -> returnToSelectionMenu());
        gameMenu.add(quitItem);

        menuBar.add(gameMenu);
    }


    private void returnToSelectionMenu() {
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

    private void changeUserNames() {
        String player1Name = JOptionPane.showInputDialog(this, "Enter name for Player 1:", "Change Names", JOptionPane.QUESTION_MESSAGE);
        String player2Name = JOptionPane.showInputDialog(this, "Enter name for Player 2:", "Change Names", JOptionPane.QUESTION_MESSAGE);

        if (player1Name != null && player2Name != null) {
            game.getPlayer1().setName(player1Name);
            game.getPlayer1Panel().updateName(player1Name);
            game.getPlayer2().setName(player2Name);
            game.getPlayer2Panel().updateName(player2Name);
        }
    }
    public JMenuBar getMenuBar() {
        return menuBar;
    }
    private void initializeToolBar() {
        toolBar = new JToolBar();
        JButton newGameButton = new JButton(new ImageIcon("/GUIOmokGameConsole/assets/resetGame.png"));
        newGameButton.setToolTipText("Restart Game");
        newGameButton.addActionListener(e -> game.resetGame());
        toolBar.add(newGameButton);
    }

    private void initializeUI(Player player1, Player player2) throws IOException {
        this.setLayout(new BorderLayout());

        // Initialize menu bar and toolbar before adding other components.
        initializeMenuBar();
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

class PlayerPanel extends JPanel {
    private BufferedImage backgroundImage;
    private Timer timer;
    private final JLabel statusLabel;
    private int dotCount = 0;
    private final JLabel nameLabel;

    public PlayerPanel(Player player) throws IOException {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/GUIOmokGameConsole/assets/background.png")));
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null;
        }

        this.setLayout(new BorderLayout());
        ImageHandler imageHandler = new ImageHandler("/GUIOmokGameConsole/assets/userIcon.png", 100, 100);
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


    private void initTimer() {
        timer = new Timer(1000, e -> updateStatus());
        timer.setInitialDelay(0);
    }

    private void updateStatus() {
        dotCount++;
        if (dotCount > 3) {
            dotCount = 1;
        }
        statusLabel.setText(" • ".repeat(dotCount));
    }

    public void resetDots() {
        dotCount = 0;
        statusLabel.setText(" ");
    }

    public void moveMade(int[] lastMove) {
        String moveText = String.format("<html>Move made!<br/>\nAt (%d, %d)</html>", lastMove[0] + 1, lastMove[1] + 1);
        statusLabel.setText(moveText);
        timer.stop();
    }


    public void updateName(String newName) {
        nameLabel.setText(newName);
        revalidate();
        repaint();
    }

    public void highlight(boolean isTurn) {
        if (isTurn) {
            this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
            resetDots();
            timer.start();
        }
        this.repaint();
    }
}
