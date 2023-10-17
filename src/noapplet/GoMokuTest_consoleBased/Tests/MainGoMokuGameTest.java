package noapplet.GoMokuTest_consoleBased.Tests;

import noapplet.GoMokuTest_consoleBased.Board;
import noapplet.GoMokuTest_consoleBased.MainGoMokuGame;
import noapplet.GoMokuTest_consoleBased.OmokGameConsole;
import noapplet.GoMokuTest_consoleBased.Stone;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

class MainGoMokuGameTest {

    private ByteArrayOutputStream outContent;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
    }

    @Order(1)
    @Test
    void humanPlayingWithHuman() {
        simulateGame("no\n", "5 5\n6 6\n", "-1\n");
        String result = outContent.toString();
        assertTrue(result.contains("Human goes first!"), "Expected Instantiation message not found!");
        System.err.println(result);
        assertTrue(result.contains("Quitting Game! Have a great day!"), "Game over message not found!");
    }


    @Order(2)
    @Test
    void shouldQuitGameWhenUserEntersThree() {
        String input = "3\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        MainGoMokuGame.getResponseStartStates(new PrintStream(outContent), inContent);

        String expectedOutput = "Quitting Game! Have a great day!";
        assertTrue(outContent.toString().contains(expectedOutput));
    }

    @Order(3)
    @Test
    void printMenu() {
        MainGoMokuGame.printMenu(new PrintStream(outContent));

        String expectedOutput = "Please Select a number from the given Menu";
        assertTrue(outContent.toString().contains(expectedOutput));
    }

    // Helper function to avoid repetition
    private void simulateGame(String chooseAI, String... moves) {
        String simulatedInput = chooseAI + String.join("\n", moves) + "\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(simulatedInput.getBytes());

        OmokGameConsole game = new OmokGameConsole(true, new PrintStream(outContent), inContent);
        game.play();
    }

    /**
     * This tests the instantiation of the OmokGameConsole asserting the content of the output for
     * key components of the ConsoleGUI
     * @Class: OmokGameConsole
     * @result This tests for a successful instantiation of the OmokGameConsole class
     */
    @Order(4)
    @Test
    void testInstantiation() {
        // Using local streams without modifying the System's streams
        ByteArrayOutputStream localOutContent = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(localOutContent);
        InputStream testIn = new ByteArrayInputStream("".getBytes()); // Empty input

        // Instantiate the game with the test streams
        OmokGameConsole game = new OmokGameConsole(false, testOut, testIn);

        // Checking the output message
        assertTrue(localOutContent.toString().contains("Human goes first!"));

        // Assuming Board has a method to get its state or is public
        for (int i = 0; i < game.board.getGrid().length; i++) {
            for (int j = 0; j < game.board.getGrid()[i].length; j++) {
                Assertions.assertEquals(Stone.EMPTY, game.board.getGrid()[i][j]); // Assuming the board starts empty
            }
        }
    }
}
