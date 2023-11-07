package GUIOmokGameConsole;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class BoardFrame extends JPanel {

    public BoardFrame(Player player1, Player player2) throws IOException {
        initializeUI(player1, player2);
    }

    private void initializeUI(Player player1, Player player2) throws IOException {

        this.setLayout(new BorderLayout());

        PlayerPanel player1Panel = new PlayerPanel(player1);
        PlayerPanel player2Panel = new PlayerPanel(player2);

        this.add(player1Panel, BorderLayout.WEST);
        this.add(player2Panel, BorderLayout.EAST);

        OmokGame game = new OmokGame(player1Panel, player1, player2Panel, player2);
        this.add(game, BorderLayout.CENTER);

        player1Panel.setPreferredSize(new Dimension(100, getHeight()));
        player2Panel.setPreferredSize(new Dimension(100, getHeight()));
    }
}

class PlayerPanel extends JPanel {
    public PlayerPanel(Player player) throws IOException {

        this.setLayout(new BorderLayout());
        BufferedImage myPicture = ImageIO.read(Objects.requireNonNull(getClass().getResource("/GUIOmokGameConsole/assets/userIcon.png")));

        myPicture = resize(myPicture, 100, 100);

        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        JPanel combinedPanel = new JPanel(new GridBagLayout());
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

        JLabel nameLabel = new JLabel(player.getName(), SwingConstants.CENTER);
        nameLabel.setVerticalAlignment(SwingConstants.CENTER);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        combinedPanel.add(nameLabel, gbc);

        this.add(combinedPanel, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }

    public void highlight(boolean isTurn) {
        if (isTurn) {
            this.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        } else {
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        }
        this.repaint();
    }

}