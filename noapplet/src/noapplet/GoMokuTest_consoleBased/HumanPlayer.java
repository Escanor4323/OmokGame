package noapplet.GoMokuTest_consoleBased;

public class HumanPlayer implements Player {
    private Stone stoneType;

    public HumanPlayer(Stone stoneType) {
        this.stoneType = stoneType;
    }

    @Override
    public int[] makeMove(Stone[][] board) {
        return new int[]{0, 0};
    }

    @Override
    public Stone getStoneType() {
        return stoneType;
    }
}
