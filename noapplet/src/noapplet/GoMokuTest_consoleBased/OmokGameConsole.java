package noapplet.GoMokuTest_consoleBased;

import java.util.InputMismatchException;
import java.util.Scanner;


public class OmokGameConsole {
    private Board board = new Board();
    private Player currentPlayer;
    private boolean gameOver = false;
    private boolean isAIGame;

    public OmokGameConsole(boolean isAIGame) {
        this.isAIGame = isAIGame;
        init();
    }

    public void init() {
        board.init_board();
        currentPlayer = new HumanPlayer(Stone.BLACK);
        System.out.println("Human goes first!");
    }

    public void play() {
        while (!gameOver) {
            board.renderBoard();
            executeMove();
            checkEndGameConditions();
        }
    }

    private void executeMove() {
        boolean validMove = false;
        while (!validMove) {
            int[] move = currentPlayer.makeMove(board.getGrid());
            validMove = isMoveValid(move);
            if (!validMove) {
                System.out.println("Invalid move. The position is already taken. Try again.");
            } else {
                placeStone(move, currentPlayer.getStoneType());
            }
        }
    }

    private boolean isMoveValid(int[] move) {
        return board.getGrid()[move[0]][move[1]] == Stone.EMPTY; // Valid if the chosen position is empty
    }

    private void placeStone(int[] move, Stone stoneType) {
        board.getGrid()[move[0]][move[1]] = stoneType; // Place the new stone
    }

    private void checkEndGameConditions() {
        if (hasCurrentPlayerWon()) {
            board.renderBoard();
            System.out.println((currentPlayer instanceof AIPlayer ? "AI" : "Human") + " WINS!");
            gameOver = true;
        } else if (isBoardFull()) {
            board.renderBoard();
            System.out.println("It's a draw!");
            gameOver = true;
        } else {
            switchPlayer();
        }
    }

    private boolean hasCurrentPlayerWon() {
        for (int i = 0; i < board.getGrid().length; i++) {
            for (int j = 0; j < board.getGrid()[i].length; j++) {
                if (board.getGrid()[i][j] == currentPlayer.getStoneType() &&
                        checkWin(i, j, currentPlayer.getStoneType())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < board.getGrid().length; i++) {
            for (int j = 0; j < board.getGrid()[i].length; j++) {
                if (board.getGrid()[i][j] == Stone.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private void switchPlayer() {
        Stone nextStone = currentPlayer.getStoneType() == Stone.BLACK ? Stone.WHITE : Stone.BLACK;
        if (currentPlayer instanceof HumanPlayer) {
            currentPlayer = isAIGame ? new AIPlayer(nextStone, board.getGrid()) : new HumanPlayer(nextStone);
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

            if (newX >= 0 && newX < board.getGrid().length && newY >= 0 && newY < board.getGrid().length && board.getGrid()[newX][newY] == s) {
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

