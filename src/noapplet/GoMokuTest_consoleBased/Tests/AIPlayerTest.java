package noapplet.GoMokuTest_consoleBased.Tests;

import noapplet.GoMokuTest_consoleBased.AIPlayer;
import noapplet.GoMokuTest_consoleBased.Board;
import noapplet.GoMokuTest_consoleBased.Stone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AIPlayerTest {

    private AIPlayer aiPlayer;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream printStream = new PrintStream(outputStreamCaptor);

    @BeforeEach
    void setUp() {
        Stone[][] currentBoard = new Stone[15][15];
        PrintStream printStream = new PrintStream(outputStreamCaptor);
        aiPlayer = new AIPlayer(Stone.BLACK, currentBoard, printStream);
    }

    @Test
    void bestMove() {
        int[] move = aiPlayer.bestMove();
        assertNotNull(move);
        assertEquals(2, move.length);
    }

    @Test
    void makeMove() {
        Stone[][] board = new Stone[15][15];
        int[] move = aiPlayer.makeMove(board);
        assertNotNull(move);
        assertEquals(2, move.length);
    }

    @Test
    void getStoneType() {
        Stone stoneType = aiPlayer.getStoneType();
        assertNotNull(stoneType);
        assertEquals(Stone.BLACK, stoneType);
    }

    @Test
    void getLastMove() {
        aiPlayer.bestMove();
        int[] lastMove = aiPlayer.getLastMove();
        assertNotNull(lastMove);
        assertEquals(2, lastMove.length);
    }

    @Test
    void setLastMove() {
        int[] newMove = {1, 1};
        aiPlayer.setLastMove(newMove);
        int[] lastMove = aiPlayer.getLastMove();
        assertArrayEquals(newMove, lastMove);
    }

    private String normalizeAnsi(String input) {
        return input.replace("\u001B", "ESC");
    }

    private String removeAnsi(String input) {
        return input.replaceAll("\\x1B\\[[;\\d]*m", "");
    }

    @Test
    void testConsoleOutput() {

        aiPlayer.bestMove();
        int[] lastMoves = aiPlayer.getLastMove();
        String actualOutput = outputStreamCaptor.toString().trim();
        String expected = ("\u001B[31mLast Move made: [" + lastMoves[0] + "] [" + lastMoves[1] + "]\u001B[0m").trim();
        assertEquals(normalizeAnsi(expected), normalizeAnsi(actualOutput));
    }

    @Test
    void TestBoardInstance(){
        Board board = new Board();
        board.init_board(printStream);
        Stone[][] grid = board.getGrid();
        for (Stone[] row : grid) {
            for (Stone cell : row) {
                assertEquals(Stone.EMPTY, cell, "The board should start empty, but found a non-empty cell.");
            }
        }

    }
}
