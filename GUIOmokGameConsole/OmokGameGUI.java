package noapplet.GUIOmokGameConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OmokGameGUI {

    private JFrame frame;
    private JRadioButton humanButton;
    private JRadioButton computerButton;

    public OmokGameGUI() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Omok");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel label = new JLabel("Select opponent:");
        mainPanel.add(label, gbc);

        humanButton = new JRadioButton("Human", true);
        computerButton = new JRadioButton("Computer");

        ButtonGroup group = new ButtonGroup();
        group.add(humanButton);
        group.add(computerButton);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout());
        radioPanel.add(humanButton);
        radioPanel.add(computerButton);

        mainPanel.add(radioPanel, gbc);

        JButton playButton = new JButton("Play");
        playButton.addActionListener(this::playButtonActionPerformed);

        gbc.weighty = 1;  // Add extra space below radio buttons
        mainPanel.add(playButton, gbc);

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void playButtonActionPerformed(ActionEvent e) {
        if (humanButton.isSelected()) {
            JOptionPane.showMessageDialog(frame, "Human selected!");
        } else if (computerButton.isSelected()) {
            JOptionPane.showMessageDialog(frame, "Computer selected!");
        } else {
            JOptionPane.showMessageDialog(frame, "Please select an opponent!");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(OmokGameGUI::new);
    }
}
