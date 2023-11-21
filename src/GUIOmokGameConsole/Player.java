package GUIOmokGameConsole;

/**
 * Abstract that represents a basic player functionalities.
 */
public abstract class Player {
    protected Stone stoneType;
    protected String name;
    protected int[] lastMove;

    /**
     * Sets up a player with basic attributes
     * @param name string representing the name of the player
     * @param stoneType Stone representing which type of stone the player has.
     */
    public Player(String name ,Stone stoneType) {
        this.stoneType = stoneType;
        this.name = name;
    }

    /**
     * Player makes a move, to be implemented by subclasses
     * @param board representing the current board
     * @return an Array that contains the move made
     */
    public abstract int[] makeMove(Stone[][] board);

    /**
     * Get the stone type of player
     * @return object of type stone associated with player
     */
    public Stone getStoneType() {
        return stoneType;
    }

    /**
     * Get the last Move made by player
     * @return an array representing the last move made by player
     */
    public int[] getLastMove() {
        return lastMove;
    }

    /**
     * Sets the last move made by the player
     * @param move to set up the lastMove
     */

    public void setLastMove(int[] move) {
        this.lastMove = move;
    }

    /**
     * Sets the name of the player
     * @param name string that represents the name to be changed
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the name of the player
     * @return String presenting the name of the player
     */
    public String getName() {
        return name;
    }
}
