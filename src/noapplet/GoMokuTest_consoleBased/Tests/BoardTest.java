package noapplet.GoMokuTest_consoleBased.Tests;

import noapplet.GoMokuTest_consoleBased.Board;
import noapplet.GoMokuTest_consoleBased.Stone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        Board board = new Board();
        board.init_board(System.out);
    }

    void simulateGame(String... inputs) {
        String combinedInputs = String.join("", inputs);
        ByteArrayInputStream inContent = new ByteArrayInputStream(combinedInputs.getBytes());
        System.setIn(inContent);
        System.setOut(new PrintStream(outputStreamCaptor));

        Board board = new Board();
        board.init_board(System.out);
        board.renderBoard();
    }
    @Test
    void init_boardTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        Board b = new Board();
        b.init_board(new PrintStream(outContent));
        for (int i = 0; i < b.getGrid().length; i++) {
            for (int j = 0; j < b.getGrid()[i].length; j++) {
                assertSame(b.getGrid()[i][j], Stone.EMPTY, "array not initialized");
            }
        }
    }

    @Test
    void boardRenderingTest() {
        simulateGame();
        String result = outputStreamCaptor.toString();
        // testing if it contains the number of the grid
        for (int i = 0; i < 15;i++){
            assertTrue(result.contains(String.valueOf(i)), "Board headers not found!");
        }
        // testing if it contains the units since we tested for the number units must be there
        assertTrue(result.contains("."), "Board initial state not found!");
    }
}