package noapplet.GoMokuTest_consoleBased.Tests;

import noapplet.GoMokuTest_consoleBased.Board;
import noapplet.GoMokuTest_consoleBased.HumanPlayer;
import noapplet.GoMokuTest_consoleBased.Stone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class HumanPlayerTest {

    private HumanPlayer player;
    private ByteArrayOutputStream output;
    private Board board;
    @BeforeEach
    void setUp() {
        output = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(output);
        InputStream inputStream = new ByteArrayInputStream("0 0".getBytes());
        board = new Board();
        player = new HumanPlayer(Stone.BLACK, printStream, board, inputStream, new Scanner(inputStream));
    }

    @Test
    void getStoneType() {
        assertEquals(Stone.BLACK, player.getStoneType());
    }

    @Test
    void getLastMove() {
        int[] move = new int[]{0, 0};
        player.setLastMove(move);

        assertArrayEquals(move, player.getLastMove());
    }

    @Test
    void setLastMove() {
        int[] move = new int[]{0, 0};
        player.setLastMove(move);

        assertArrayEquals(move, player.getLastMove());
    }
}
