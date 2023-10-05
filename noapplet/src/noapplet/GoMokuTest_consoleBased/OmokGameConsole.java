package noapplet.GoMokuTest_consoleBased;

import java.util.InputMismatchException;
import java.util.Scanner;
import noapplet.*;

public class OmokGameConsole {

    private static final int BOARD_SIZE = 15;
    private Stone[][] board = new Stone[BOARD_SIZE][BOARD_SIZE];
    private Player currentPlayer;
    private boolean gameOver = false;
    private boolean isAIGame;

    public OmokGameConsole(boolean isAIGame) {
        this.isAIGame = isAIGame;
        init();
    }

    public void init() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = Stone.EMPTY;
            }
        }

        currentPlayer = new HumanPlayer(Stone.BLACK);
        System.out.println("Human goes first!");
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (!gameOver) {
            renderBoard();
            if (currentPlayer instanceof AIPlayer) {
                int[] move = currentPlayer.makeMove(board);
                board[move[0]][move[1]] = currentPlayer.getStoneType();

                if (checkWin(move[0], move[1], currentPlayer.getStoneType())) {
                    renderBoard();
                    System.out.println("AI WINS!");
                    gameOver = true;
                    break;
                }
                switchPlayer();
            } else {
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

                if (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && board[x][y] == Stone.EMPTY) {
                    board[x][y] = currentPlayer.getStoneType();

                    if (checkWin(x, y, currentPlayer.getStoneType())) {
                        renderBoard();
                        System.out.println("Human WINS!");
                        gameOver = true;
                        break;
                    }

                    switchPlayer();
                } else {
                    System.out.println("Invalid move. Try again.");
                }
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
            if (i < 10) {
                System.out.print(" " + i + " ");
            } else {
                System.out.print(i + " ");
            }

            for (int j = 0; j < BOARD_SIZE; j++) {
                switch (board[j][i]) {
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
            }
            System.out.println();
        }
    }


    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void switchPlayer() {
        Stone nextStone = currentPlayer.getStoneType() == Stone.BLACK ? Stone.WHITE : Stone.BLACK;
        if (currentPlayer instanceof HumanPlayer) {
            currentPlayer = isAIGame ? new AIPlayer(nextStone, board) : new HumanPlayer(nextStone);
        } else {
            currentPlayer = new HumanPlayer(nextStone);
        }
    }

    private boolean checkWin(int x, int y, Stone s) {
        return checkDirection(x, y, s, 1, 0) ||
                checkDirection(x, y, s, 0, 1) ||
                checkDirection(x, y, s, 1, 1) ||
                checkDirection(x, y, s, 1, -1);
    }

    private boolean checkDirection(int x, int y, Stone s, int dx, int dy) {
        int count = 0;
        for (int i = -4; i <= 4; i++) {
            int newX = x + dx * i;
            int newY = y + dy * i;

            if (newX >= 0 && newX < BOARD_SIZE && newY >= 0 && newY < BOARD_SIZE && board[newX][newY] == s) {
                count++;
                if (count == 5) return true;
            } else {
                count = 0;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        OmokGameConsole game = new OmokGameConsole(true);
        game.play();
    }
}

