package GUIOmokGameConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Board extends JPanel {

    private int counter_stones = 0;
    protected static final int TILE_SIZE = 30;
    protected static final int BOARD_SIZE = 15;
    private static Player[][] grid = new Player[BOARD_SIZE][BOARD_SIZE];
    private int lastMoveX = -1;
    private int lastMoveY = -1;
    private List<Place> places_list = new ArrayList<>();

    public Board() {
        this.setPreferredSize(new Dimension(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE));
        clear();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / TILE_SIZE;
                int y = e.getY() / TILE_SIZE;
                lastMoveX = x;
                lastMoveY = y;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Player player = grid[i][j];
                if (player != null) {
                    g.setColor(player.getStoneType().equals(Stone.BLACK)? Color.BLACK : Color.GRAY);
                    g.fillOval(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawOval(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }
    /** Create a new board of the default size. */
    /** Create a new board of the specified size. */
    public Board(int size) {
        grid = new Player[size][size];
    }

    /** Return the size of this board. */
    public int sizeBoard() {
        return grid.length;
    }

    /** Removes all the stones placed on the board, effectively
     * resetting the board to its original state.
     */
    public void clear() {
        grid = new Player[sizeBoard()][sizeBoard()];
    }
    /**Return a boolean value indicating whether all the places
     * on the board are occupied or not.
     */
    public boolean isFull() {
          return counter_stones == sizeBoard()*sizeBoard();
    }

    /**
     * Place a stone for the specified player at a specified
     * intersection (x, y) on the board.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @param player Player whose stone is to be placed
     */
    public void placeStone(int x, int y, Player player) {
        counter_stones++;
        grid[x][y] = player;
    }

    /**
     * Return a boolean value indicating whether the specified
     * intersection (x, y) on the board is empty or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isEmpty(int x, int y) {
        return grid[x][y]== null;
    }

    /**
     * Is the specified place on the board occupied?
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupied(int x, int y) {
        return grid[x][y] != null;
    }

    /**
     * Rreturn a boolean value indicating whether the specified
     * intersection (x, y) on the board is occupied by the given
     * player or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */

    public boolean isOccupiedBy(int x, int y, Player player) {
        return grid[x][y].equals(player);
    }

    /**
     * Return the player who occupies the specified intersection (x, y)
     * on the board. If the place is empty, this method returns null.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */

    public Player playerAt(int x, int y) {
        return grid[x][y];
    }

    /**
     * Return a boolean value indicating whether the given player
     * has a winning row on the board. A winning row is a consecutive
     * sequence of five or more stones placed by the same player in
     * a horizontal, vertical, or diagonal direction.
     */
    private boolean checkDirection(int x, int y, Player player, int dx, int dy) {
        int count = 0;
        for (int i = -4; i <= 4; i++) {
            int newX = x + dx * i;
            int newY = y + dy * i;

            if (newX >= 0 && newX < sizeBoard() && newY >= 0 && newY < sizeBoard() && grid[newX][newY] == player) {
                count++;
                places_list.add(new Place(newX, newY));
                if (count == 5) return true;
            } else {
                places_list.clear();
                count = 0;
            }
        }

        return false;
    }
    private boolean checkDirection(int x, int y, int dx, int dy) {
        int count = 0;
        Object currentPlayer = grid[x][y];

        if (currentPlayer == null) {
            return false;
        }

        List<Place> tempPlacesList = new ArrayList<>();

        for (int i = -4; i <= 4; i++) {
            int newX = x + dx * i;
            int newY = y + dy * i;

            if (newX >= 0 && newX < sizeBoard() && newY >= 0 && newY < sizeBoard() && grid[newX][newY] == currentPlayer) {
                count++;
                tempPlacesList.add(new Place(newX, newY));
                if (count == 5) {
                    places_list = tempPlacesList;
                    return true;
                }
            } else {
                tempPlacesList.clear();
                count = 0;
            }
        }

        return false;
    }
    private boolean checkWin(int x, int y, Player player) {
        if (x == -1 || y == -1){
            return true;
        }
        return checkDirection(x, y, player, 1, 0) ||
                checkDirection(x, y, player, 0, 1) ||
                checkDirection(x, y, player, 1, 1) ||
                checkDirection(x, y, player, 1, -1);
    }
    private boolean checkWin(int x, int y) {
        return checkDirection(x, y, 1, 0) ||
                checkDirection(x, y, 0, 1) ||
                checkDirection(x, y, 1, 1) ||
                checkDirection(x, y, 1, -1);
    }

    private boolean hasCurrentPlayerWon(Player currentPlayer) {
        for (int i = 0; i < sizeBoard(); i++) {
            for (int j = 0; j < sizeBoard(); j++) {
                if (grid[i][j] == currentPlayer &&
                        checkWin(i, j, currentPlayer)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isWonBy(Player player) {
        return hasCurrentPlayerWon(player);
    }

    /** Return the winning row. For those who are not familiar with
     * the Iterable interface, you may return an object of
     * List<Place>. */
    public List<Place> winningRow() {
        for (int i = 0; i < sizeBoard(); i++) {
            for (int j = 0; j < sizeBoard(); j++) {
                if (checkWin(i, j)) {
                    return places_list;
                }
            }
        }
        return null;

    }

    public Player[][] getGrid(){
        return grid;
    }

    /**
     * An intersection on an Omok board identified by its 0-based column
     * index (x) and row index (y). The indices determine the position
     * of a place on the board, with (0, 0) denoting the top-left
     * corner and (n-1, n-1) denoting the bottom-right corner,
     * where n is the size of the board.
     */
    public static class Place {
        /** 0-based column index of this place. */
        public final int x;

        /** 0-based row index of this place. */
        public final int y;

        /** Create a new place of the given indices.
         *
         * @param x 0-based column (vertical) index
         * @param y 0-based row (horizontal) index
         */
        public Place(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int[] getXY(){
            return new int[]{this.x, this.y};
        }
    }
}



