package GUIOmokGameConsole;

import javax.swing.*;
import java.awt.*;

public class OmokGame extends JPanel {

    private final Board board;
    private Player currentPlayer;
    private final Player player1;
    private final Player player2;
    private final boolean isAIGame = false;
    private boolean isGameOver;

    private final PlayerPanel player1Panel;
    private final PlayerPanel player2Panel;

    public OmokGame(PlayerPanel player1Panel, Player player1, PlayerPanel player2Panel, Player player2) {
        board = new Board();
        this.setLayout(new BorderLayout());
        this.add(board, BorderLayout.CENTER);

        this.player1 = player1;
        this.player2 = player2;

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
        currentPlayer = player1 == currentPlayer ? player1: player2;
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
    public void resetGame() {
        board.clear();
        currentPlayer = player1;
        isGameOver = false;
        board.repaint();
    }
}
