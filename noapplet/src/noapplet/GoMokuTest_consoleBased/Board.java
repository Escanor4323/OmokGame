package noapplet.GoMokuTest_consoleBased;

public class Board {
    private static final int BOARD_SIZE = 15;
    private Stone[][] grid = new Stone[BOARD_SIZE][BOARD_SIZE];
    private int lastMoveRow = -1;
    private int lastMoveCol = -1;

    public void init_board(){
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                grid[i][j] = Stone.EMPTY;
            }
        }
    }

    public void renderBoard() {
        clearScreen();
        System.out.print("    ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + "  ");
            if (i < 10) {
                System.out.print(" ");
            }
        }
        System.out.println();

        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i < 10 ? (" " + i + " ") : (i + " "));

            for (int j = 0; j < BOARD_SIZE; j++) {
                boolean isLastMove = i == lastMoveRow && j == lastMoveCol;

                if (isLastMove) {
                    System.out.print("\033[0;32m");  // Set the color to green before printing the stone
                }

                switch (grid[j][i]) {
                    case BLACK:
                        System.out.print(" B  ");
                        break;
                    case WHITE:
                        System.out.print(" W  ");
                        break;
                    default:
                        System.out.print(" .  ");
                        break;
                }

                if (isLastMove) {
                    System.out.print("\033[0m");  // Reset the color after printing the stone
                }
            }
            System.out.println();
        }

    }

    public void setLastMove(int row, int col) {
        lastMoveRow = row;
        lastMoveCol = col;
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
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
