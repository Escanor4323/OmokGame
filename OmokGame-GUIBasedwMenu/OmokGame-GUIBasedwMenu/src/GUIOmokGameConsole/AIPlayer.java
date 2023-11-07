package GUIOmokGameConsole;

public class AIPlayer extends Player{

    private final Board board;
    private static final int MAX_DEPTH = 5;
    private Stone stoneType;
    private int[] lastMove;
    private Player me;
    private Player opponent;


    public AIPlayer(Stone stoneType, Board currentBoard, Stone myStone) {
        super("Gomok AI :3", stoneType);
        this.board = currentBoard;
        this.me = new HumanPlayer(myStone, "Gomok AI :3");
    }


    public int[] bestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        for (int i = 0; i < board.sizeBoard(); i++) {
            for (int j = 0; j < board.sizeBoard(); j++) {

                if (board.isEmpty(i, j)) {

                    board.placeStone(i, j, me);


                    int score = minimax(MAX_DEPTH, opponent, Integer.MIN_VALUE, Integer.MAX_VALUE);


                    board.placeStone(i, j, null);


                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }


    private int minimax(int depth, Player player, int alpha, int beta) {
        if (depth == 0 || board.isFull() || board.isWonBy(opponent) || board.isWonBy(me)) {
            return evaluateBoard();
        }

        if (player.equals(me)) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < board.sizeBoard(); i++) {
                for (int j = 0; j < board.sizeBoard(); j++) {
                    if (board.isEmpty(i, j)) {
                        board.placeStone(i, j, me);
                        int eval = minimax(depth - 1, opponent, alpha, beta);
                        board.placeStone(i, j, null); // Undo the move
                        maxEval = Math.max(maxEval, eval);
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < board.sizeBoard(); i++) {
                for (int j = 0; j < board.sizeBoard(); j++) {
                    if (board.isEmpty(i, j)) {
                        board.placeStone(i, j, opponent);
                        int eval = minimax(depth - 1, me, alpha, beta);
                        board.placeStone(i, j, null); // Undo the move
                        minEval = Math.min(minEval, eval);
                        beta = Math.min(beta, eval);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }



    private boolean gameIsOver() {
        for (int i = 0; i < board.sizeBoard(); i++) {
            for (int j = 0; j < board.sizeBoard(); j++) {
                Player currentPlayer = board.playerAt(i, j);
                if (currentPlayer != null) {
                    if (board.isWonBy(currentPlayer)) {
                        return true;
                    }
                }
            }
        }
        return board.isFull();
    }

    private int evaluateBoard() {
        int score = 0;
        for (int i = 0; i < board.sizeBoard(); i++) {
            for (int j = 0; j < board.sizeBoard(); j++) {

                if (board.playerAt(i, j) != null) {
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

        Player player = board.playerAt(x, y);
        if (player == null) {
            return 0;
        }

        int consecutiveStones = 0;

        for (int i = 0; i < 5; i++) {
            int newX = x + i * dx;
            int newY = y + i * dy;


            if (newX < 0 || newX >= board.sizeBoard() || newY < 0 || newY >= board.sizeBoard()) {
                break;
            }


            if (board.playerAt(newX, newY) == player) {
                consecutiveStones++;
            } else {
                break;
            }
        }


        int score;
        switch (consecutiveStones) {
            case 5:
                score = player.getStoneType().equals(Stone.BLACK) ? 10000 : -10000;
                break;
            case 4:
                score = player.getStoneType().equals(Stone.BLACK) ? 1000 : -1000;
                break;
            case 3:
                score = player.getStoneType().equals(Stone.BLACK) ? 100 : -100;
                break;
            case 2:
                score = player.getStoneType().equals(Stone.BLACK) ? 10 : -10;
                break;
            default:
                score = 0;
                break;
        }

        return score;
    }


    private boolean checkDirection(int x, int y, Player player, int dx, int dy) {
        int count = 0;
        for (int i = -4; i <= 4; i++) {
            int newX = x + dx * i;
            int newY = y + dy * i;
            if (newX >= 0 && newX < board.sizeBoard() && newY >= 0 && newY < board.sizeBoard()) {
                Player current = board.getGrid()[newX][newY];
                if (current != null && current.equals(player)) {
                    count++;
                    if (count == 5) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }
        return false;
    }


    @Override
    public int[] makeMove(Stone[][] currentBoard) {

        Board.Place bestMove = decideNextMove();
        if (bestMove != null) {
            board.placeStone(bestMove.x, bestMove.y, this);

            return new int[]{bestMove.x, bestMove.y};
        }
        return null;
    }

    private Board.Place decideNextMove() {

        for (int i = 0; i < board.sizeBoard(); i++) {
            for (int j = 0; j < board.sizeBoard(); j++) {
                if (board.isEmpty(i, j)) {
                    return new Board.Place(i, j);
                }
            }
        }
        return null;
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
