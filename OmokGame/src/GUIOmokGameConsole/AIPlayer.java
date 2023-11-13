package GUIOmokGameConsole;

import java.util.ArrayList;
import java.util.List;

public class AIPlayer extends Player{
    private final Board board;
    private static final int MAX_DEPTH = 3;
    private final Stone stoneType;
    private final Player me;
    private final Player opponent;
    private int[]lastMove = {0, 0};


    public AIPlayer(Stone stoneType, Board currentBoard, Player opponent) {
        super("Gomok AI :3", stoneType);
        this.stoneType = stoneType;
        this.opponent = opponent;
        this.board = currentBoard;
        this.me = new HumanPlayer(stoneType, "Gomok AI :3");
    }


    public int[] bestMove() {

        int[] immediateMove = checkImmediateMoves();
        if (immediateMove != null) {
            return immediateMove;
        }
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        List<int[]> possibleMoves = getPossibleMoves();
        for (int[] move : possibleMoves) {
            int i = move[0];
            int j = move[1];

            if (createsWinningOpportunity(i, j, 1, 0, me) ||
                    createsWinningOpportunity(i, j, 0, 1, me) ||
                    createsWinningOpportunity(i, j, 1, 1, me) ||
                    createsWinningOpportunity(i, j, -1, 1, me)) {
                return new int[]{i, j};
            }

            board.placeStone(i, j, me);
            int score = minimax(MAX_DEPTH, opponent, Integer.MIN_VALUE, Integer.MAX_VALUE);
            board.placeStone(i, j, null);

            if (score > bestScore) {
                bestScore = score;
                bestMove[0] = i;
                bestMove[1] = j;
            }
        }
        lastMove = bestMove;
        return bestMove;
    }
    private int[] checkImmediateMoves() {
        for (int i = 0; i < board.sizeBoard(); i++) {
            for (int j = 0; j < board.sizeBoard(); j++) {
                if (board.isEmpty(i, j)) {
                    if (isWinningMove(i, j, me)) {
                        return new int[]{i, j};
                    }
                    if (isWinningMove(i, j, opponent)) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return null;
    }

    private boolean isWinningMove(int x, int y, Player player) {
        return createsWinningOpportunity(x, y, 1, 0, player) ||  // Horizontal
                createsWinningOpportunity(x, y, 0, 1, player) ||  // Vertical
                createsWinningOpportunity(x, y, 1, 1, player) ||  // Diagonal (down-right)
                createsWinningOpportunity(x, y, -1, 1, player);   // Diagonal (up-right)
    }



    private List<int[]> getPossibleMoves() {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < board.sizeBoard(); i++) {
            for (int j = 0; j < board.sizeBoard(); j++) {
                if (board.isEmpty(i, j) && isNearStone(i, j)) {
                    moves.add(new int[]{i, j});
                }
            }
        }
        return moves;
    }

    private boolean isNearStone(int x, int y) {
        int distance = 2;
        for (int i = -distance; i <= distance; i++) {
            for (int j = -distance; j <= distance; j++) {
                if (x + i >= 0 && x + i < board.sizeBoard() && y + j >= 0 && y + j < board.sizeBoard()) {
                    if (!board.isEmpty(x + i, y + j)) {
                        return true;
                    }
                }
            }
        }
        return false;
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
                        board.placeStone(i, j, null);
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

    private int evaluateBoard() {
        int score = 0;
        for (int i = 0; i < board.sizeBoard(); i++) {
            for (int j = 0; j < board.sizeBoard(); j++) {

                if (board.isEmpty(i, j)) {
                    score += evaluatePosition(i, j);
                }
            }
        }
        return score;
    }

    @Override
    public int[] getLastMove() {
        return lastMove;
    }

    private int evaluatePosition(int x, int y) {
        int score = 0;

        for (int[] direction : new int[][]{{1, 0}, {0, 1}, {1, 1}, {-1, 1}}) {
            if (createsWinningOpportunity(x, y, direction[0], direction[1], opponent)) {
                score -= 100000;
            }
        }

        for (int[] direction : new int[][]{{1, 0}, {0, 1}, {1, 1}, {-1, 1}}) {
            int consecutive = countConsecutiveStones(x, y, direction[0], direction[1], me);
            int blocks = countConsecutiveStones(x, y, direction[0], direction[1], opponent);

            score += getScoreForConsecutive(consecutive);
            score -= getScoreForConsecutive(blocks);
        }

        return score;
    }

    private boolean createsWinningOpportunity(int x, int y, int dx, int dy, Player player) {
        board.placeStone(x, y, player);
        boolean wins = checkDirection(x, y, player, dx, dy);
        board.placeStone(x, y, null);
        return wins;
    }

    private int countConsecutiveStones(int x, int y, int dx, int dy, Player player) {
        int count = 0;
        for (int i = -1; i <= 4; i++) {
            int newX = x + dx * i;
            int newY = y + dy * i;
            if (newX < 0 || newY < 0 || newX >= board.sizeBoard() || newY >= board.sizeBoard()) {
                continue;
            }
            if (board.playerAt(newX, newY) == player) {
                count++;
            } else if (i != -1) {
                count = 0;
            }
        }
        return count;
    }

    private int getScoreForConsecutive(int consecutive) {
        return switch (consecutive) {
            case 5 -> 10000;
            case 4 -> 1000;
            case 3 -> 100;
            case 2 -> 10;
            default -> 0;
        };
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
}
