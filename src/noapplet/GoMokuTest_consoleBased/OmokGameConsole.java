package noapplet.GoMokuTest_consoleBased;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;


public class OmokGameConsole {
    public final Board board = new Board();
    private Player currentPlayer;
    private static boolean gameOver = false;
    private boolean isAIGame;
    private final PrintStream outputStream;
    private final InputStream inputStream;
    private Scanner scanner;

    public OmokGameConsole(boolean isAIGame, PrintStream outputStream, InputStream inputStream) {
        this.isAIGame = isAIGame;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.scanner = new Scanner(inputStream);
        init();
    }
    public OmokGameConsole() {
        this(false, System.out, System.in); // This will already initialize the scanner
        outputStream.println("Do you want to play with AI? (yes/no)");
        String choice = scanner.nextLine();
        this.isAIGame = "yes".equalsIgnoreCase(choice);
        init();
    }

    public static void endGame() {
        gameOver = true;
    }

    public static boolean isGameOver() {
        return gameOver;
    }

    public void init() {
        board.init_board(outputStream);
        currentPlayer = new HumanPlayer(Stone.BLACK, outputStream, board, inputStream, scanner);
        outputStream.println("Human goes first!");
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
            if (move == null) {
                outputStream.println("Quitting Game! Have a great day!");
                return;
            }
            validMove = isMoveValid(move);
            if (!validMove) {
                outputStream.println("Invalid move. The position is already taken. Try again.");
            } else {
                placeStone(move, currentPlayer.getStoneType());
            }
        }
    }

    private boolean isMoveValid(int[] move) {
        return board.getGrid()[move[0]][move[1]] == Stone.EMPTY;
    }

    private void placeStone(int[] move, Stone stoneType) {
        board.getGrid()[move[0]][move[1]] = stoneType; // Place the new stone
    }

    private void checkEndGameConditions() {
        if (hasCurrentPlayerWon()) {
            board.renderBoard();
            outputStream.println((currentPlayer instanceof AIPlayer ? "AI" : "Human") + " WINS!");
            gameOver = true;
        } else if (isBoardFull()) {
            board.renderBoard();
            outputStream.println("It's a draw!");
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
            for (int j = 0; j < board.getGrid()[i].length; j++)
                if (board.getGrid()[i][j] == Stone.EMPTY) {
                    return false;
                }
        }
        return true;
    }

    private void switchPlayer() {
        Stone nextStone = currentPlayer.getStoneType() == Stone.BLACK ? Stone.WHITE : Stone.BLACK;
        if (currentPlayer instanceof HumanPlayer) {
            currentPlayer = isAIGame ? new AIPlayer(nextStone, board.getGrid(), outputStream) : new HumanPlayer(nextStone, outputStream, board, inputStream, scanner);
        } else {
            currentPlayer = new HumanPlayer(nextStone, outputStream, board, inputStream, scanner);
        }
    }

    private boolean checkWin(int x, int y, Stone s) {
        if (x == -1 || y == -1){
            gameOver = true;
            return true;
        }
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
        OmokGameConsole gameConsole = new OmokGameConsole();
        gameConsole.play();
    }
}

