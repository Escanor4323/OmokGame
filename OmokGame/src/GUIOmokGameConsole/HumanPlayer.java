package GUIOmokGameConsole;

public class HumanPlayer extends Player {
    private int[] lastMove;
    protected String name;

    public HumanPlayer(Stone stoneType, String name) {
        super(name, stoneType);
        this.name = name;
    }

    public HumanPlayer(Stone stoneType) {
        super("NoName", stoneType);
        this.name = "Default name";
    }

    @Override
    public int[] makeMove(Stone[][] board) {
        return new int[0];
    }

    @Override
    public void setLastMove(int[] lastMove) {
        this.lastMove = lastMove;
    }

    @Override
    public int[] getLastMove() {
        return lastMove;
    }
}
