package GUIOmokGameConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SelectionMenu extends JPanel {

    private JRadioButton humanButton;
    private JRadioButton computerButton;
    private JTextField playerNameField;
    private JTextField opponentNameField;
    private final BufferedImage backgroundImage;
    private final BufferedImage icon;

    public SelectionMenu(int sizeWidth, int sizeHeight) throws IOException {
        BufferedImage originalIcon = new ImageHandler("/GUIOmokGameConsole/assets/icon.png", 150, 150).getImage();
        icon = createCircularImage(originalIcon);

        backgroundImage = new ImageHandler("/GUIOmokGameConsole/assets/background.png", sizeWidth, sizeHeight).getImage();
        initializeUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            int x = (this.getWidth() - backgroundImage.getWidth()) / 2;
            int y = (this.getHeight() - backgroundImage.getHeight()) / 2;
            g.drawImage(backgroundImage, x, y, this);
        }
    }

    public BufferedImage createCircularImage(BufferedImage inputImage) {
        int diameter = Math.min(inputImage.getWidth(), inputImage.getHeight());
        BufferedImage circleBuffer = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circleBuffer.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new Ellipse2D.Float(0, 0, diameter, diameter));

        g2.drawImage(inputImage, 0, 0, diameter, diameter, null);
        g2.dispose();

        return circleBuffer;
    }

    private void addFocusListenerToTextField(JTextField textField, String defaultText) {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(defaultText)) {
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(defaultText);
                }
            }
        });
    }

    private void initializeUI() {
        this.setPreferredSize(new Dimension(450, 450));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel iconLabel = new JLabel(new ImageIcon(icon));
        this.add(iconLabel, gbc);

        JLabel playerNameLabel = new JLabel("Your Name:");
        playerNameField = new JTextField("Me", 10);
        addFocusListenerToTextField(playerNameField, "Me");
        JLabel opponentNameLabel = new JLabel("Opponent Name:");
        opponentNameField = new JTextField("Opponent", 10);
        addFocusListenerToTextField(opponentNameField, "Opponent");
        opponentNameField.setEnabled(false);

        JLabel label = new JLabel("Select opponent:");
        this.add(label, gbc);

        humanButton = new JRadioButton("Human");
        computerButton = new JRadioButton("Computer");
        humanButton.addActionListener(e -> opponentNameField.setEnabled(true));

        computerButton.addActionListener(e -> opponentNameField.setEnabled(false));

        ButtonGroup group = new ButtonGroup();
        group.add(humanButton);
        group.add(computerButton);

        JPanel radioPanel = new JPanel(new FlowLayout());
        radioPanel.add(humanButton);
        radioPanel.add(computerButton);

        this.add(playerNameLabel, gbc);
        this.add(playerNameField, gbc);
        this.add(opponentNameLabel, gbc);
        this.add(opponentNameField, gbc);
        this.add(radioPanel, gbc);

        JButton playButton = new JButton("Play");
        playButton.addActionListener(this::playButtonActionPerformed);

        gbc.weighty = 1;
        this.add(playButton, gbc);
    }

    private void playButtonActionPerformed(ActionEvent e) {
        String playerName = playerNameField.getText().trim();
        String opponentName = opponentNameField.getText().trim();
        boolean isAI = computerButton.isSelected();

        if (playerName.isEmpty()) {
            playerName = "Player 1";
        }
        if (opponentName.isEmpty() || opponentName.equals("Opponent")) {
            opponentName = isAI ? "Gomok AI" : "Player 2";
        }

        if (humanButton.isSelected() || computerButton.isSelected()) {
            Player player1 = new HumanPlayer(Stone.BLACK, playerName);
            Player player2 = new HumanPlayer(Stone.WHITE, opponentName);
            startGame(player1, player2, isAI);
            Window topFrame = SwingUtilities.getWindowAncestor(this);
            if (topFrame != null) {
                topFrame.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an opponent!");
        }
    }


    private void startGame(Player player1, Player player2, boolean isAI) {
        JFrame gameFrame = new JFrame("Omok Game");
        SwingUtilities.invokeLater(() -> {
            try {
                BoardFrame board = new BoardFrame(player1, player2, isAI);
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.setJMenuBar(board.getMenuBar());
                gameFrame.setContentPane(board);
                gameFrame.pack();
                gameFrame.setLocationRelativeTo(null);
                gameFrame.setVisible(true);
                Window topFrame = SwingUtilities.getWindowAncestor(this);
                if (topFrame != null) {
                    topFrame.dispose();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(gameFrame, "Failed to start the game due to an error.");
            }
        });
    }

}
