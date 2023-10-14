package noapplet.GoMokuTest_consoleBased;
import java.util.Scanner;
import java.util.InputMismatchException;

public class HumanPlayer implements Player {
    private Stone stoneType;

    public HumanPlayer(Stone stoneType) {
        this.stoneType = stoneType;
    }

    @Override
    public int[] makeMove(Stone[][] board) {
        Scanner scanner = new Scanner(System.in);
        int x = 0;
        int y = 0;
        try
        {
            System.out.println("Enter your move as 'x y': ");
            x = scanner.nextInt();
            y = scanner.nextInt();

        }catch (InputMismatchException e)
        {
            System.out.println("Invalid Coordinates do as 'x(space)y'");
        }
        if (x >= 0 && x < board.length&& y >= 0 && y < board.length && board[x][y] == Stone.EMPTY) {
            move[0]= x;
            move[1] = y;
        }
        else {
            System.out.println("Invalid move. Try again.");
        }
        return move;
    }

    @Override
    public Stone getStoneType() {
        return stoneType;
    }
}
