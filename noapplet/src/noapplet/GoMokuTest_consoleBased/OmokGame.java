package noapplet.GoMokuTest_consoleBased;

import noapplet.NoApplet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class OmokGame extends NoApplet {

    private static final int BOARD_SIZE = 15;
    private static final int GRID_SIZE = 30;
    private Stone[][] board = new Stone[BOARD_SIZE][BOARD_SIZE];
    private Player currentPlayer;

    private boolean gameOver = false;
    private boolean isAIGame;

    public OmokGame(boolean isAIGame) {
        init();
        this.isAIGame = isAIGame;

        Random rand = new Random();
        if (rand.nextBoolean()) {
            currentPlayer = new HumanPlayer(Stone.BLACK);
            showStatus("Human goes first!");
        } else {
            if (isAIGame) {
                currentPlayer = new AIPlayer(Stone.BLACK, board);
            } else {
                currentPlayer = new HumanPlayer(Stone.BLACK);
            }
        }
    }

    @Override
    public void init() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = Stone.EMPTY;
            }
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameOver) return;

                int x = e.getX() / GRID_SIZE;
                int y = e.getY() / GRID_SIZE;

                if (board[x][y] == Stone.EMPTY) {
                    board[x][y] = currentPlayer.getStoneType();
                    repaint();

                    if (checkWin(x, y, currentPlayer.getStoneType())) {
                        showStatus(currentPlayer.getStoneType() + " WINS!");
                        gameOver = true;
                        return;
                    }

                    switchPlayer();
                }
            }
        });
    }

    @Override
    public void showStatus(String message) {
        JOptionPane.showMessageDialog(this, message, "Game Status", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBoard(g);

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == Stone.BLACK) {
                    g.setColor(Color.BLACK);
                    g.fillOval(i * GRID_SIZE, j * GRID_SIZE, GRID_SIZE, GRID_SIZE);
                } else if (board[i][j] == Stone.WHITE) {
                    g.setColor(Color.WHITE);
                    g.fillOval(i * GRID_SIZE, j * GRID_SIZE, GRID_SIZE, GRID_SIZE);
                }
            }
        }
    }

    private void drawBoard(Graphics g) {
        g.setColor(Color.GRAY);
        for (int i = 0; i < BOARD_SIZE; i++) {
            g.drawLine(i * GRID_SIZE, 0, i * GRID_SIZE, (BOARD_SIZE - 1) * GRID_SIZE);
            g.drawLine(0, i * GRID_SIZE, (BOARD_SIZE - 1) * GRID_SIZE, i * GRID_SIZE);
        }
    }

    private void switchPlayer() {
        Stone nextStone = currentPlayer.getStoneType() == Stone.BLACK ? Stone.WHITE : Stone.BLACK;

        if (currentPlayer instanceof HumanPlayer) {
            currentPlayer = isAIGame ? new AIPlayer(nextStone, board) : new HumanPlayer(nextStone);
            if (currentPlayer instanceof AIPlayer) {
                aiTurn();
                switchPlayer();
            }
        } else {
            currentPlayer = new HumanPlayer(nextStone);
        }
    }


    private void aiTurn() {
        int[] move = currentPlayer.makeMove(board);
        board[move[0]][move[1]] = currentPlayer.getStoneType();

        if (checkWin(move[0], move[1], currentPlayer.getStoneType())) {
            showStatus("AI WINS!");
            gameOver = true;
        }
    }

    private boolean checkWin(int x, int y, Stone s) {
        return checkDirection(x, y, s, 1, 0) ||
                checkDirection(x, y, s, 0, 1) ||
                checkDirection(x, y, s, 1, 1) ||
                checkDirection(x, y, s, 1, -1);
    }

    private boolean checkDirection(int x, int y, Stone s, int dx, int dy) {
        int count = 0;
        for (int i = -4; i <= 4; i++) {
            int newX = x + dx * i;
            int newY = y + dy * i;

            if (newX >= 0 && newX < BOARD_SIZE && newY >= 0 && newY < BOARD_SIZE && board[newX][newY] == s) {
                count++;
                if (count == 5) return true;
            } else {
                count = 0;
            }
        }

        return false;
    }

    public void run() {
        JFrame frame = new JFrame("Go! Mok Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(BOARD_SIZE * GRID_SIZE, BOARD_SIZE * GRID_SIZE);
        frame.add(this);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new OmokGame(true).run();
    }
}
