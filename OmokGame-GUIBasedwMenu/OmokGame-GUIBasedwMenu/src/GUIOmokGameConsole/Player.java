package GUIOmokGameConsole;

public abstract class Player {
    protected Stone stoneType;
    protected String name;
    protected int[] lastMove;

    public Player(String name ,Stone stoneType) {
        this.stoneType = stoneType;
        this.name = name;
    }

    public abstract int[] makeMove(Stone[][] board);

    public Stone getStoneType() {
        return stoneType;
    }

    public int[] getLastMove() {
        return lastMove;
    }

    public void setLastMove(int[] move) {
        this.lastMove = move;
    }



    public String getName() {
        return name;
    }
}
