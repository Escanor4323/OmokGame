package noapplet.GoMokuTest_consoleBased;

public interface Player {
    int[] makeMove(Stone[][] board);
    Stone getStoneType();
}
