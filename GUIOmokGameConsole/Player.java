package noapplet.GUIOmokGameConsole;

public interface Player {
    int[] move = new int[2];
    int[] makeMove(Stone[][] board);
    Stone getStoneType();
    int[] getLastMove();
    void setLastMove(int[] move);
}
