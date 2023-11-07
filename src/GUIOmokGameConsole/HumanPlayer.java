package GUIOmokGameConsole;

import java.util.Scanner;

public class HumanPlayer extends Player {
    protected String name;

    public HumanPlayer(Stone stoneType, String name) {
        super(stoneType);
        this.name = name;
    }

    public HumanPlayer(Stone stoneType) {
        super(stoneType);
        this.name = "Default name";
    }

    @Override
    public int[] makeMove(Stone[][] board) {
        return new int[0];
    }
}
