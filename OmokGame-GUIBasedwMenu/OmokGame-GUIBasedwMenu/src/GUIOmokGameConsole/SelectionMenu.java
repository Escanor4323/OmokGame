package GUIOmokGameConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SelectionMenu extends JPanel {

    private JRadioButton humanButton;
    private JRadioButton computerButton;

    public SelectionMenu() {
        initializeUI();
    }

    private void initializeUI() {
        this.setPreferredSize(new Dimension(400, 200));

        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel label = new JLabel("Select opponent:");
        this.add(label, gbc);

        humanButton = new JRadioButton("Human");
        computerButton = new JRadioButton("Computer");

        ButtonGroup group = new ButtonGroup();
        group.add(humanButton);
        group.add(computerButton);

        JPanel radioPanel = new JPanel(new FlowLayout());
        radioPanel.add(humanButton);
        radioPanel.add(computerButton);

        this.add(radioPanel, gbc);

        JButton playButton = new JButton("Play");
        playButton.addActionListener(this::playButtonActionPerformed);

        gbc.weighty = 1;
        this.add(playButton, gbc);
    }

    private void playButtonActionPerformed(ActionEvent e) {
        if (humanButton.isSelected()) {
            JOptionPane.showMessageDialog(this, "Human selected!");
        } else if (computerButton.isSelected()) {
            JOptionPane.showMessageDialog(this, "Computer selected!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select an opponent!");
        }
    }
}
