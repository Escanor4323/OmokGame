package GUIOmokGameConsole;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Represents the game board.
 */
public class Board extends JPanel {

    private int counter_stones = 0;

    protected static final int TILE_SIZE = 30;
    protected static final int BOARD_SIZE = 15;
    private static Player[][] grid = new Player[BOARD_SIZE][BOARD_SIZE];
    private int lastMoveX = -1;
    private int lastMoveY = -1;

    private int mouseRow = -1;
    private int mouseCol = -1;
    private List<Place> places_list = new ArrayList<>();
    private BufferedImage backgroundImage;

    /**
     * Sets dimensions for size and creates both MouseListener and MouseMotionListener for the Board object
     */
    public Board() {
        this.setPreferredSize(new Dimension(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE));
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("assets/background.png")));
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null;
        }
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
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseRow = e.getY() / TILE_SIZE;
                mouseCol = e.getX() / TILE_SIZE;
                repaint();
            }
        });
    }
    /**
     * Invoked to redrawn current board and background
     * @param g Graphic component to be drawn
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Player player = grid[i][j];
                if (player != null) {
                    g.setColor(player.getStoneType().equals(Stone.BLACK) ? Color.BLACK : Color.GRAY);
                    g.fillOval(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawOval(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        List<Place> winningPlaces = winningRow();
        if (winningPlaces != null) {
            g.setColor(Color.RED);
            for (Place place : winningPlaces) {
                g.drawOval(place.y * TILE_SIZE, place.x * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(place.y * TILE_SIZE + 1, place.x * TILE_SIZE + 1, TILE_SIZE - 2, TILE_SIZE - 2);
            }
        }

        if (mouseRow >= 0 && mouseRow < BOARD_SIZE && mouseCol >= 0 && mouseCol < BOARD_SIZE) {
            g.setColor(new Color(255, 255, 0, 128));
            g.fillOval(mouseCol * TILE_SIZE, mouseRow * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
    }




    /** Returns the last move made on the X axis.
     * */
    public int getLastMoveX() {
        return lastMoveX;
    }

    /** Returns the last move made on the X axis.
     * */
    public int getLastMoveY() {
        return lastMoveY;
    }

    /** Create a new board of the specified size.
     * @param size  for the Board
     * */
    public Board(int size) {
        grid = new Player[size][size];
    }

    /**Get the size of the board
     *  @return size of this.board. */
    public int sizeBoard() {
        return grid.length;
    }

    /** Removes all the stones placed on the board, effectively
     * resetting the board to its original state.
     */
    public void clear() {
        grid = new Player[sizeBoard()][sizeBoard()];
    }
    /**Check if the board is fully occupied
     * @return boolean if board fully occupied or not
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
     * @param player Player, whose stone is to be placed
     */
    public void placeStone(int x, int y, Player player) {
        counter_stones++;
        lastMoveX = x;
        lastMoveY = y;
        grid[x][y] = player;
    }

    /**
     * Check if intersection (x,y) is empty
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @return a boolean value indicating whether the specified
     *  intersection (x, y) on the board is empty or not.
     */
    public boolean isEmpty(int x, int y) {
        return grid[x][y]== null;
    }

    /**
     * Check if intersection (x,y) is occupied
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @return a boolean value indicating whether the specified
     *  intersection (x, y) on the board is occupied or not.
     */
    public boolean isOccupied(int x, int y) {
        return grid[x][y] != null;
    }

    /**
     * Check if the intersection (x, y) on the board is occupied by the given player or not.
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */

    public boolean isOccupiedBy(int x, int y, Player player) {
        return grid[x][y].equals(player);
    }

    /**
     * Check the player who occupies the specified intersection (x, y)
     * on the board.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @return an Object of type player who occupies the given intersection (x,y)
     */

    public Player playerAt(int x, int y) {
        return grid[x][y];
    }

    /**
     * Check if a player has a winning row in any direction
     * @param x represents the 'row' coordinate in the grid
     * @param y represents the 'column' coordinate in the grid
     * @param player represents the player we want to check the directions on
     * @param dx represents the row direction in which we are going to check
     * @param dy represents the column direction in which we are going to check
     * @return boolean value indicating whether the given player has a winning row on the board.
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
    /**
     * Check if there is a winning row in any direction
     * @param x represents the 'row' coordinate in the grid
     * @param y represents the 'column' coordinate in the grid
     * @param dx represents the row direction in which we are going to check
     * @param dy represents the column direction in which we are going to check
     * @return boolean value indicating whether there is a winning row on the board.
     */
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

    /**
     * Check if a player has won from a coordinate
     * @param x represents the 'row' coordinate in the grid
     * @param y represents the 'column' coordinate in the grid
     * @param player represents the player we want to check for victory
     * @return boolean value indicating whether the given player has won.
     */
    private boolean checkWin(int x, int y, Player player) {
        if (x == -1 || y == -1){
            return true;
        }
        return checkDirection(x, y, player, 1, 0) ||
                checkDirection(x, y, player, 0, 1) ||
                checkDirection(x, y, player, 1, 1) ||
                checkDirection(x, y, player, 1, -1);

    }
    /**
     * Check if there is a win from a coordinate
     * @param x represents the 'row' coordinate in the grid
     * @param y represents the 'column' coordinate in the grid
     * @return boolean value indicating whether there is a victory.
     */
    private boolean checkWin(int x, int y) {
        return checkDirection(x, y, 1, 0) ||
                checkDirection(x, y, 0, 1) ||
                checkDirection(x, y, 1, 1) ||
                checkDirection(x, y, 1, -1);
    }

    /**
     * Check if a player has won by looking through the whole grid.
     * @param currentPlayer represents the player we want to check for victory
     * @return boolean value indicating whether the given player has won.
     */
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
    /**
     * Check if a player is the winner.
     * @param player represents the player we want to check for victory
     * @return boolean value indicating whether the given player is the winner.
     */
    public boolean isWonBy(Player player) {
        return hasCurrentPlayerWon(player);
    }

    /**
     * Get the winning row if exists
     * @return a List<Place> containing the place coordinates of the winning row. If there is no winning row, return null.
     * */
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

    /**
     * Get the current grid
     * @return a 2D array representing the current grid
     */
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
        @Override
        public String toString() {
            return "Place{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

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
    }
}



