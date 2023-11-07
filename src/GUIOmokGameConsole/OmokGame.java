package GUIOmokGameConsole;

import javax.swing.*;
import java.awt.*;

public class OmokGame extends JPanel {

    private final Board board;
    private Player currentPlayer;
    private final Player player1;
    private final Player player2;
    private boolean isGameOver;

    private PlayerPanel player1Panel;
    private PlayerPanel player2Panel;

    public OmokGame(PlayerPanel player1Panel, PlayerPanel player2Panel) {
        board = new Board();
        this.setLayout(new BorderLayout());
        this.add(board, BorderLayout.CENTER);

        // Assuming HumanPlayer is a defined class that extends Player
        player1 = new HumanPlayer(Stone.BLACK, "Player 1");
        player2 = new HumanPlayer(Stone.WHITE, "Player 2");

        this.player1Panel = player1Panel;
        this.player2Panel = player2Panel;

        player1Panel.highlight(true);
        player2Panel.highlight(false);

        currentPlayer = player1;
        isGameOver = false;
        setupMouseListener();
    }

    private void setupMouseListener() {
        board.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (isGameOver) return;

                int col = e.getY() / Board.TILE_SIZE;
                int row = e.getX() / Board.TILE_SIZE;

                if (board.isOccupied(col, row)) {
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(OmokGame.this),
                            "Position (" + col + ", " + row + ") is already occupied. Please choose another.");
                } else {
                    makeMove(col, row);
                    player1Panel.highlight(currentPlayer == player1);
                    player2Panel.highlight(currentPlayer == player2);
                }
            }
        });
    }

    private void makeMove(int x, int y) {
        board.placeStone(x, y, currentPlayer);

        if (board.isWonBy(currentPlayer)) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                    currentPlayer.getName() + " wins!");
            isGameOver = true;
        } else if (board.isFull()) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                    "It's a draw!");
            isGameOver = true;
        }

        switchPlayer();
        board.repaint();
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    // Optionally, if you need to start a new game, you can add a method like this:
    public void resetGame() {
        board.clear(); // Assuming Board class has a method to clear the board
        currentPlayer = player1; // Or whoever should start the new game
        isGameOver = false;
        board.repaint();
    }
}
