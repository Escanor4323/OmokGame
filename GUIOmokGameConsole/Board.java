package noapplet.GUIOmokGameConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Board extends JPanel {
    private static final int BOARD_SIZE = 15;
    private static final int TILE_SIZE = 30;
    private Stone[][] grid = new Stone[BOARD_SIZE][BOARD_SIZE];
    private int lastMoveX = -1;
    private int lastMoveY = -1;

    public Board() {
        this.setPreferredSize(new Dimension(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE));
        initBoard();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / TILE_SIZE;
                int y = e.getY() / TILE_SIZE;
                // Here you can update the board state when it is clicked, for now, it will just store the x and y.
                updateLastMove(x, y);
                repaint();
            }
        });
    }


    private void initBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                grid[i][j] = Stone.EMPTY;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                switch (grid[j][i]) {
                    case BLACK:
                        g.setColor(Color.BLACK);
                        g.fillOval(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                        break;
                    case WHITE:
                        g.setColor(Color.WHITE);
                        g.fillOval(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                        break;
                    default:
                        g.setColor(Color.LIGHT_GRAY);
                        g.drawOval(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                        break;
                }
            }
        }
    }


    public int getLastMoveX() {
        return lastMoveX;
    }

    public int getLastMoveY() {
        return lastMoveY;
    }

    public void updateLastMove(int x, int y) {
        this.lastMoveX = x;
        this.lastMoveY = y;
    }

    public Stone[][] getGrid() {
        return grid;
    }

    public int getSizeBoard() {
        return grid.length;
    }

    public void setGrid(Stone[][] board) {
        grid = board;
    }
}
