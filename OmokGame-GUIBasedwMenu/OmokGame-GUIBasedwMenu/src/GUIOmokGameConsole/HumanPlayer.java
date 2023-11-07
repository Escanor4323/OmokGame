package GUIOmokGameConsole;

import java.util.Scanner;

public class HumanPlayer extends Player {
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
}
