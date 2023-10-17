package noapplet.GoMokuTest_consoleBased_original;

public class AIPlayer implements Player{

    private Board board = new Board();
    private static final int MAX_DEPTH = 2;
    private Stone stoneType;


    public AIPlayer(Stone[][] currentBoard) {
        this.board.setGrid(currentBoard);
    }

    public AIPlayer(Stone stoneType, Stone[][] currentBoard) {
        this.stoneType = stoneType;
        this.board.setGrid(currentBoard);
    }


    public int[] bestMove() {
        int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getGrid()[i][j] == Stone.EMPTY) {
                    board.getGrid()[i][j] = Stone.BLACK;  // AI is BLACK for this example
                    int moveScore = minimax(MAX_DEPTH, Stone.WHITE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board.getGrid()[i][j] = Stone.EMPTY;

                    if (moveScore > bestScore) {
                        bestScore = moveScore;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }

        return move;
    }

    private int minimax(int depth, Stone player, int alpha, int beta) {
        if (depth == 0 || gameIsOver()) {
            return evaluateBoard();
        }

        if (player == Stone.BLACK) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < board.getSize(); i++) {
                for (int j = 0; j < board.getSize(); j++) {
                    if (board.getGrid()[i][j] == Stone.EMPTY) {
                        board.getGrid()[i][j] = Stone.BLACK;
                        int eval = minimax(depth - 1, Stone.WHITE, alpha, beta);
                        board.getGrid()[i][j] = Stone.EMPTY;
                        maxEval = Math.max(maxEval, eval);
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha)
                            return maxEval;
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < board.getSize(); i++) {
                for (int j = 0; j < board.getSize(); j++) {
                    if (board.getGrid()[i][j] == Stone.EMPTY) {
                        board.getGrid()[i][j] = Stone.WHITE;
                        int eval = minimax(depth - 1, Stone.BLACK, alpha, beta);
                        board.getGrid()[i][j] = Stone.EMPTY;
                        minEval = Math.min(minEval, eval);
                        beta = Math.min(beta, eval);
                        if (beta <= alpha)
                            return minEval;
                    }
                }
            }
            return minEval;
        }
    }

    private boolean gameIsOver() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getGrid()[i][j] != Stone.EMPTY) {
                    if (checkDirection(i, j, board.getGrid()[i][j], 1, 0) ||
                            checkDirection(i, j, board.getGrid()[i][j], 0, 1) ||
                            checkDirection(i, j, board.getGrid()[i][j], 1, 1) ||
                            checkDirection(i, j, board.getGrid()[i][j], 1, -1)) {
                        return true;  // there's a winner
                    }
                }
            }
        }
        return false;
    }

    private int evaluateBoard() {
        int score = 0;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getGrid()[i][j] != Stone.EMPTY) {
                    score += evaluatePosition(i, j, 1, 0);
                    score += evaluatePosition(i, j, 0, 1);
                    score += evaluatePosition(i, j, 1, 1);
                    score += evaluatePosition(i, j, 1, -1);
                }
            }
        }
        return score;
    }

    private int evaluatePosition(int x, int y, int dx, int dy) {
        Stone s = board.getGrid()[x][y];
        int consecutiveStones = 0;

        for (int i = 0; i < 5 && x + i*dx >= 0 && x + i*dx < board.getSize() && y + i*dy >= 0 && y + i*dy < board.getSize(); i++) {
            if (board.getGrid()[x + i*dx][y + i*dy] == s) {
                consecutiveStones++;
            } else {
                break;
            }
        }

        switch (consecutiveStones) {
            case 5:
                return s == Stone.BLACK ? 10000 : -10000;
            case 4:
                return s == Stone.BLACK ? 1000 : -1000;
            case 3:
                return s == Stone.BLACK ? 100 : -100;
            case 2:
                return s == Stone.BLACK ? 10 : -10;
            default:
                return 0;
        }
    }


    private boolean checkDirection(int x, int y, Stone s, int dx, int dy) {
        int count = 0;
        for (int i = -4; i <= 4; i++) {
            int newX = x + dx * i;
            int newY = y + dy * i;
            if (newX >= 0 && newX < board.getSize() && newY >= 0 && newY < board.getSize() && board.getGrid()[newX][newY] == s) {
                count++;
                if (count == 5) return true;
            } else {
                count = 0;
            }
        }
        return false;
    }

    @Override
    public int[] makeMove(Stone[][] board) {
        this.board.setGrid(board);
        return bestMove();
    }

    @Override
    public Stone getStoneType() {
        return stoneType;
    }
}
