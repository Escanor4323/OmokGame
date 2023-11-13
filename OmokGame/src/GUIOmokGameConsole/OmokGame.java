package GUIOmokGameConsole;

import javax.swing.*;
import java.awt.*;

public class OmokGame extends JPanel {

    private final Board board;
    private Player currentPlayer;
    private final Player player1;
    private final Player player2;
    private final boolean isAIGame;
    private boolean isGameOver;

    private final PlayerPanel player1Panel;
    private final PlayerPanel player2Panel;

    public OmokGame(PlayerPanel player1Panel, Player player1, PlayerPanel player2Panel, Player player2, boolean isAIGame) {
        this.isAIGame = isAIGame;
        board = new Board();
        this.setLayout(new BorderLayout());
        this.add(board, BorderLayout.CENTER);

        this.player1 = player1;
        this.player2 = isAIGame ? new AIPlayer(Stone.WHITE, board, player1) : player2;

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
                            "Position is already occupied. Please choose another.");
                } else {
                    makeMove(col, row);
                    player1Panel.highlight(currentPlayer == player1);
                    player2Panel.highlight(currentPlayer == player2);
                }
            }
        });
    }

    public PlayerPanel getPlayer1Panel() {
        return player1Panel;
    }

    public PlayerPanel getPlayer2Panel() {
        return player2Panel;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    private void makeMove(int x, int y) {
        board.placeStone(x, y, currentPlayer);
        currentPlayer.setLastMove(new int[]{x, y});
        if (currentPlayer == player1) {
            player1Panel.moveMade(new int[]{x, y});
        } else {
            player2Panel.moveMade(new int[]{x, y});
        }
        detectWin();
        if (!isGameOver && isAIGame && currentPlayer instanceof AIPlayer) {
            SwingUtilities.invokeLater(this::aiMakeMove);
        }
    }

    private void detectWin() {
        if (board.isWonBy(currentPlayer)) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                    currentPlayer.getName() + " wins!");
            isGameOver = true;
        } else if (board.isFull()) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                    "It's a draw!");
            isGameOver = true;
        } else {
            switchPlayer();
        }
        board.repaint();
        player1Panel.highlight(currentPlayer == player1);
        player2Panel.highlight(currentPlayer == player2);
    }

    private void aiMakeMove() {
        int[] aiMove = ((AIPlayer) currentPlayer).bestMove();
        if (aiMove != null) {
            board.placeStone(aiMove[0], aiMove[1], currentPlayer);
            currentPlayer.setLastMove(aiMove);
            player2Panel.moveMade(aiMove);
            detectWin();
        }
    }

    public void changeUserNames(String name1, String name2){
        this.player1.name = name1;
        this.player2.name = name2;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        player1Panel.highlight(currentPlayer == player1);
        player2Panel.highlight(currentPlayer == player2);
    }
    public void resetGame() {

        board.clear();
        if (currentPlayer != player1){
            switchPlayer();
        }
        currentPlayer = player1;
        isGameOver = false;
        board.repaint();
    }

    public int[] getLastMove(){
        return new int[]{board.getLastMoveX(), board.getLastMoveY()};
    }
}
