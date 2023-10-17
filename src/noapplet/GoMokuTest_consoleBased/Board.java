package noapplet.GoMokuTest_consoleBased;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.UIManager.put;

public class Board {
    private static final int BOARD_SIZE = 15;
    private Stone[][] grid = new Stone[BOARD_SIZE][BOARD_SIZE];
    private int lastMoveX = -1;
    private int lastMoveY = -1;
    private PrintStream outputStream;
    private final Map<String, String> colors = new HashMap<>() {{
        put("reset", "\u001B[0m");
        put("black", "\u001B[30m");
        put("red", "\u001B[31m");
        put("green", "\u001B[32m");
        put("yellow", "\u001B[33m");
        put("blue", "\u001B[34m");
        put("purple", "\u001B[35m");
        put("cyan", "\u001B[36m");
        put("white", "\u001B[37m");
    }};

    public void init_board(PrintStream output){
        this.outputStream = output;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                grid[i][j] = Stone.EMPTY;
            }
        }
    }

    public void renderBoard() {
        clearScreen();
        outputStream.print("    ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            outputStream.print(i + "  ");
            if (i < 10) {
                outputStream.print(" ");
            }
        }
        outputStream.println();

        for (int i = 0; i < BOARD_SIZE; i++) {
            outputStream.print(i < 10 ? (" " + i + " ") : (i + " "));

            for (int j = 0; j < BOARD_SIZE; j++) {
                boolean isMostRecentMove = i == lastMoveY && j == lastMoveX;

                outputStream.print(colors.get("reset"));

                switch (grid[j][i]) {
                    case BLACK:
                        if (!isMostRecentMove) outputStream.print(colors.get("green"));
                        outputStream.print(" B  ");
                        break;
                    case WHITE:
                        if (!isMostRecentMove) outputStream.print(colors.get("cyan"));
                        outputStream.print(" W  ");
                        break;
                    default:
                        outputStream.print(" .  ");
                        break;
                }
            }
            outputStream.println();
        }
    }



    public int getLastMoveX() {
        return lastMoveX;
    }

    public int getLastMoveY() {
        return lastMoveY;
    }

    public void updateLastMove(int x, int y) {
        this.lastMoveX = x;
        this.lastMoveY = y;
    }

    public void clearScreen() {
        outputStream.print("\033[H\033[2J");
        outputStream.flush();
    }

    public Stone[][] getGrid(){
        return grid;
    }

    public int getSize(){
        return grid.length;
    }

    public void setGrid(Stone[][] board){
        grid = board;
    }
}
