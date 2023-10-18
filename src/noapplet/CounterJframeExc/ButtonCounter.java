package noapplet.CounterJframeExc;

import javax.swing.*;
import java.awt.*;

public class ButtonCounter {

    private int count = 0;
    private final JLabel countLabel;

    public ButtonCounter() {
        JButton increaseButton = new JButton("Increase");
        increaseButton.addActionListener(e -> {
            count++;
            updateCounter();
        });

        JButton decreaseButton = new JButton("Decrease");
        decreaseButton.addActionListener(e -> {
            count--;
            updateCounter();
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            count = 0;
            updateCounter();
        });

        countLabel = new JLabel("Count: 0", SwingConstants.CENTER);
        countLabel.setFont(new Font(countLabel.getFont().getName(), Font.PLAIN, 20));

        increaseButton.setPreferredSize(new Dimension(100, 40));
        decreaseButton.setPreferredSize(new Dimension(100, 40));
        resetButton.setPreferredSize(new Dimension(100, 40));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(increaseButton);
        buttonPanel.add(decreaseButton);
        buttonPanel.add(resetButton);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BorderLayout());
        labelPanel.add(countLabel, BorderLayout.CENTER);

        JFrame frame = new JFrame("Counter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(labelPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void updateCounter() {
        countLabel.setText("Count: " + count);
    }

    public static void main(String[] args) {
        new ButtonCounter();
    }
}
