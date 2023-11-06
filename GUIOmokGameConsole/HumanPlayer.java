package noapplet.GUIOmokGameConsole;

import noapplet.GoMokuTest_consoleBased.OmokGameConsole;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Stone stoneType;
    private final PrintStream outputStream;
    private final InputStream inputStream;
    private Board currentBoard;
    private int[] lastMove;
    private Scanner scanner;

    public HumanPlayer(Stone stoneType, PrintStream outputStream, InputStream inputStream) {
        this.stoneType = stoneType;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }
    public HumanPlayer(Stone stoneType, PrintStream outputStream, Board board, InputStream inputStream, Scanner scanner) {
        this.stoneType = stoneType;
        this.outputStream = outputStream;
        this.currentBoard = board;
        this.inputStream = inputStream;
        this.scanner = scanner;
    }

    @Override
    public int[] makeMove(Stone[][] board) {
        int x = 0;
        int y = 0;

        while (true) {
            try {
                outputStream.println("Enter your move as 'x y' or -1 to end the game: ");
                String inputLine = scanner.nextLine();
                String[] coordinates = inputLine.split(" ");

                if (coordinates.length == 1 && "-1".equals(coordinates[0])) {
                    OmokGameConsole.endGame();
                    return null;
                }

                if (coordinates.length != 2) {
                    throw new InputMismatchException();
                }

                x = Integer.parseInt(coordinates[0]);
                y = Integer.parseInt(coordinates[1]);

                if (x >= 0 && x < board.length && y >= 0 && y < board.length && board[x][y] == Stone.EMPTY) {
                    move[0] = x;
                    move[1] = y;
                    currentBoard.updateLastMove(x, y);
                    break;
                } else {
                    outputStream.println("Invalid move. Try again.");
                }
            } catch (NumberFormatException | InputMismatchException e) {
                outputStream.println("Invalid Coordinates. Format as 'x(space)y'");
            }
        }

        outputStream.println("\u001B[31m" + "Last Move made: [" + currentBoard.getLastMoveX() + "] [" + currentBoard.getLastMoveY() + "]" + "\u001B[0m");
        setLastMove(move);
        return move;
    }


    @Override
    public Stone getStoneType() {
        return stoneType;
    }

    @Override
    public int[] getLastMove() {
        return lastMove;
    }

    @Override
    public void setLastMove(int[] move) {
        this.lastMove = move;
    }
}
