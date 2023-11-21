package GUIOmokGameConsole;

/**
 * Extends player and represents the behavior a Human Player.
 */
public class HumanPlayer extends Player {
    private int[] lastMove;
    protected String name;

    /**
     * Sets the Human Player main features
     * @param stoneType Stone type that human will possess
     * @param name Name string that human will posses
     */
    public HumanPlayer(Stone stoneType, String name) {
        super(name, stoneType);
        this.name = name;
    }

    /**
     * Just sets the stone type of human player
     * @param stoneType Stone type that human will posses
     */
    public HumanPlayer(Stone stoneType) {
        super("NoName", stoneType);
        this.name = "Default name";
    }

    /**
     * Makes a move
     * @param board representing the current board
     * @return array representing human move
     */

    @Override
    public int[] makeMove(Stone[][] board) {
        return new int[0];
    }

    /**
     * Sets the last move of the human player
     * @param lastMove to set up the lastMove
     */
    @Override
    public void setLastMove(int[] lastMove) {
        this.lastMove = lastMove;
    }

    /**
     * get the last move of the human player
     * @return last move made
     */
    @Override
    public int[] getLastMove() {
        return lastMove;
    }
}
