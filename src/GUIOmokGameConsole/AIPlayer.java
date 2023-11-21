package GUIOmokGameConsole;

import java.util.ArrayList;
import java.util.List;

/**
 *  Represents a computer-controlled player, with methods for choosing
 * moves based on the game state
 */
public class AIPlayer extends Player{
    private final Board board;
    private static final int MAX_DEPTH = 2;
    private final Stone stoneType;
    private final Player me;
    private final Player opponent;
    private int[]lastMove = {0, 0};

    /**
     * Constructs an AIPlayer instance for a game, initializing it with the specified stone type, game board,
     * and opponent. The AIPlayer is also associated with a 'me' player, which is a representation of the
     * AI itself in the game, created as a HumanPlayer instance.
     *
     * @param stoneType    the type of stone that the AI player will use in the game.
     * @param currentBoard the current state of the game board.
     * @param opponent     the opponent player against whom the AI is playing.
     */

    public AIPlayer(Stone stoneType, Board currentBoard, Player opponent) {
        super("Gomok AI :3", stoneType);
        this.stoneType = stoneType;
        this.opponent = opponent;
        this.board = currentBoard;
        this.me = new HumanPlayer(stoneType, "Gomok AI :3");
    }

    /**
     * Determines the best move for the player based on the current state of the game board.
     * The method first checks for any immediate winning moves using {@code checkImmediateMoves}.
     * If an immediate winning move is found, it is returned. Otherwise, the method evaluates
     * potential moves using the {@code getPossibleMoves} method and assesses their scores
     * through the {@code minimax} method. It also checks if any move creates a winning opportunity
     * directly, without going deeper into the minimax tree.
     *
     * @return an integer array containing the coordinates [x, y] of the best move determined.
     *         If no move is found to improve the position, it returns the coordinates of a default move.
     *         The coordinates of the last move are also stored in {@code lastMove}.
     */

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

    /**
     * Scans the game board to identify any immediate winning moves for either the player or the opponent.
     * This method iterates through each position on the board, checking if it is empty, and then
     * determines if placing a stone at that position would result in a win for either the player ('me')
     * or the opponent. The check is done using the {@code isWinningMove} method.
     *
     * @return an integer array containing the coordinates [x, y] of an immediate winning move, if any.
     *         The move is prioritized for the player ('me') first and then for the opponent.
     *         Returns null if no immediate winning moves are found for either player.
     */

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

    /**
     * Determines if placing a stone at the specified coordinates for the given player results in a winning move.
     * This method checks four directions: horizontal, vertical, and two diagonals (down-right and up-right)
     * to see if placing a stone at the (x, y) coordinates creates a line of stones that satisfies the win condition.
     * The checks are performed using the {@code createsWinningOpportunity} method.
     *
     * @param x       the x-coordinate where the stone is to be placed.
     * @param y       the y-coordinate where the stone is to be placed.
     * @param player  the player for whom the move is being checked.
     * @return        true if placing a stone at (x, y) for the specified player results in a win;
     *                false otherwise.
     */
    private boolean isWinningMove(int x, int y, Player player) {
        return createsWinningOpportunity(x, y, 1, 0, player) ||  // Horizontal
                createsWinningOpportunity(x, y, 0, 1, player) ||  // Vertical
                createsWinningOpportunity(x, y, 1, 1, player) ||  // Diagonal (down-right)
                createsWinningOpportunity(x, y, -1, 1, player);   // Diagonal (up-right)
    }

    /**
     * Generates a list of all possible moves on the game board based on the current state.
     * A possible move is defined as an empty position on the board that is near an existing stone.
     * This method iterates through each position on the board, checking if it is both empty and
     * adjacent to at least one stone, as determined by the {@code isNearStone} method.
     *
     * @return  a list of integer arrays, where each array represents the coordinates of a possible move
     *          in the format [x, y]. If no moves are possible, return an empty list.
     */

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

    /**
     * Checks if there is a stone placed within a specified distance from a given position on the board.
     * The method iterates through a square area centered on the given coordinates (x, y),
     * with a side length of two * distance + 1, to determine if any adjacent positions are occupied.
     *
     * @param x  the x-coordinate of the position to check around.
     * @param y  the y-coordinate of the position to check around.
     * @return   true if there is at least one stone within the specified distance from the position (x, y);
     *           false otherwise.
     */

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

    /**
     * Implements the minimax algorithm with alpha-beta pruning for a two-player game.
     * This method recursively calculates the optimal move for the current player at a given
     * depth of the game tree.
     *
     * @param depth   the depth of the game tree to explore. A depth of 0 indicates the leaf nodes.
     * @param player  the current player for whom the move is being calculated.
     * @param alpha   the "alpha" value in alpha-beta pruning, representing the best already
     *                explored option along the path to the root for the maximizer.
     * @param beta    the "beta" value in alpha-beta pruning, representing the best already
     *                explored option along the path to the root for the minimizer.
     * @return        the heuristic value of the board at the given depth. A high value is
     *                favorable for the 'me' player and a low value for the 'opponent'.
     */

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

    /**
     * Evaluates the current state of the game board and computes a score representing the
     * overall position for the AI player. This method iterates through each position on the board.
     * For empty positions, it calculates a score using the {@code evaluatePosition} method and
     * aggregates these scores to provide an overall evaluation of the board.
     *
     * @return an integer score representing the evaluated state of the board. A higher score
     *         indicates a more favorable position for the AI player.
     */
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

    /**
     * Retrieves the last move made by the AI player. This method overrides the corresponding
     * method in the superclass (if applicable) to provide the specific implementation for the AI player.
     *
     * @return an integer array containing the coordinates [x, y] of the last move made by the AI player.
     *         Returns null if no moves have been made yet.
     */
    @Override
    public int[] getLastMove() {
        return lastMove;
    }

    /**
     * Evaluates the score of a specific position on the board based on its potential impact on the game.
     * The method considers various directions around the position (horizontal, vertical, and two diagonals)
     * to determine its strategic value. It checks if placing a stone at this position could lead to a
     * winning opportunity for the opponent, deducting a significant score in such cases. The method also
     * calculates the number of consecutive stones for both the AI player and the opponent in each direction
     * and adjusts the score based on these counts using {@code getScoreForConsecutive}.
     *
     * @param x  the x-coordinate of the position to evaluate.
     * @param y  the y-coordinate of the position to evaluate.
     * @return   an integer score for the position, where a higher score indicates a more favorable position
     *           for the AI player.
     */
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

    /**
     * Determines whether placing a stone by a specific player at the given coordinates
     * creates a winning opportunity in a particular direction. This method temporarily places
     * a stone at the specified position for the given player, checks if this creates a win
     * in the specified direction using {@code checkDirection}, and then undoes the move.
     *
     * @param x      the x-coordinate where the stone is to be placed.
     * @param y      the y-coordinate where the stone is to be placed.
     * @param dx     the x-direction component for checking the line (e.g., 1 for right, -1 for a left).
     * @param dy     the y-direction component for checking the line (e.g., 1 for down, -1 for up).
     * @param player the player for whom the move is being checked.
     * @return       true if placing a stone at (x, y) for the specified player in the given direction
     *               results in a winning opportunity; false otherwise.
     */
    private boolean createsWinningOpportunity(int x, int y, int dx, int dy, Player player) {
        board.placeStone(x, y, player);
        boolean wins = checkDirection(x, y, player, dx, dy);
        board.placeStone(x, y, null);
        return wins;
    }

    /**
     * Counts the number of consecutive stones placed by a specific player in a given direction
     * from the specified starting position. The method checks a line segment of up to five positions
     * (including the starting position) along the specified direction. The count resets if a stone
     * belonging to the other player is encountered, except for the immediate predecessor of the starting position.
     *
     * @param x      the x-coordinate of the starting position.
     * @param y      the y-coordinate of the starting position.
     * @param dx     the x-direction component for checking the line (e.g., 1 for right, -1 for a left).
     * @param dy     the y-direction component for checking the line (e.g., 1 for down, -1 for up).
     * @param player the player whose stones are being counted.
     * @return       the number of consecutive stones belonging to the specified player
     *               along the given direction from the starting position.
     */
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

    /**
     * Assigns a score based on the number of consecutive stones a player has on the board.
     * This method uses a switch statement to return different scores for different counts of
     * consecutive stones. Higher counts of consecutive stones receive exponentially higher scores,
     * reflecting their increased strategic value in the game.
     *
     * @param consecutive the number of consecutive stones for a player in any direction.
     * @return an integer score corresponding to the strategic value of the consecutive stones.
     *         Returns 10,000 for 5 stones, 1,000 for 4 stones, 100 for 3 stones, 10 for 2 stones,
     *         and 0 for any other count.
     */
    private int getScoreForConsecutive(int consecutive) {
        return switch (consecutive) {
            case 5 -> 10000;
            case 4 -> 1000;
            case 3 -> 100;
            case 2 -> 10;
            default -> 0;
        };
    }

    /**
     * Checks if there are five consecutive stones of a given player in a specified direction
     * starting from the specified coordinates. This method iterates through positions in a
     * line segment centered at (x, y) and extending four positions in both directions along the
     * given dx and dy components. It counts consecutive stones of the specified player and
     * resets the count if a stone of a different player or an empty space is encountered.
     *
     * @param x      the x-coordinate of the center position from where to start checking.
     * @param y      the y-coordinate of the center position from where to start checking.
     * @param player the player whose consecutive stones are being checked.
     * @param dx     the x-direction component for checking the line (e.g., 1 for right, -1 for a left).
     * @param dy     the y-direction component for checking the line (e.g., 1 for down, -1 for up).
     * @return       true if there are five consecutive stones of the specified player in the
     *               given direction; false otherwise.
     */
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

    /**
     * Determines and makes the next move for the AI player on the provided game board.
     * This method overrides the corresponding method in the superclass and uses the {@code decideNextMove}
     * method to determine the best move. If a valid move is found, it places a stone for the AI player
     * at the determined coordinates on the board.
     *
     * @param currentBoard the current state of the game board represented as a 2D array of {@code Stone} objects.
     * @return an integer array containing the coordinates [x, y] of the move made by the AI player.
     *         Returns null if no valid move is found or possible.
     */
    @Override
    public int[] makeMove(Stone[][] currentBoard) {

        Board.Place bestMove = decideNextMove();
        if (bestMove != null) {
            board.placeStone(bestMove.x, bestMove.y, this);

            return new int[]{bestMove.x, bestMove.y};
        }
        return null;
    }

    /**
     * Determines the next move for the AI player by finding the first empty position on the game board.
     * This method iterates through each position of the board in a sequential manner, checking for
     * the first available (empty) spot. Once found, it returns this position as the next move.
     *
     * @return a {@code Board.Place} object representing the coordinates of the next move (x, y).
     *         Returns null if there are no empty positions available on the board.
     */
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

    /**
     * Retrieves the type of stone used by the AI player.
     * This method overrides the corresponding method in the superclass, providing the specific
     * implementation for the AI player to return its stone type.
     *
     * @return the {@code Stone} type associated with this AI player.
     */
    @Override
    public Stone getStoneType() {
        return stoneType;
    }
}
