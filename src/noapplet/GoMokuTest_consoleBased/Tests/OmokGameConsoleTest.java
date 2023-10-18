package noapplet.GoMokuTest_consoleBased.Tests;
import noapplet.GoMokuTest_consoleBased.OmokGameConsole;
import noapplet.GoMokuTest_consoleBased.Stone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class OmokGameConsoleTest {

    private OmokGameConsole game;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream printStream = new PrintStream(outputStreamCaptor);


    @Test
    void endGame() {
        OmokGameConsole.endGame();
        assertTrue(OmokGameConsole.isGameOver());
    }

    @Test
    void init() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream("".getBytes()));

        OmokGameConsole game = new OmokGameConsole(false, System.out, System.in);

        Assertions.assertEquals(Stone.EMPTY, game.board.getGrid()[0][0]);
        assertTrue(outContent.toString().contains("Human goes first!"));

        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testInstantiation() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream("".getBytes()));

        OmokGameConsole game = new OmokGameConsole(false, System.out, System.in);

        assertTrue(outContent.toString().contains("Human goes first!"));

        for (int i = 0; i < game.board.getGrid().length; i++) {
            for (int j = 0; j < game.board.getGrid()[i].length; j++) {
                Assertions.assertEquals(Stone.EMPTY, game.board.getGrid()[i][j]);
            }
        }

        System.setOut(originalOut);
        System.setIn(originalIn);
    }
}
