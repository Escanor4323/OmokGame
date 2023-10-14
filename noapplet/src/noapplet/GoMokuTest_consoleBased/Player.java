package noapplet.GoMokuTest_consoleBased;

public interface Player {
    int[] move = new int[2];
    int[] makeMove(Stone[][] board);
    Stone getStoneType();
}
