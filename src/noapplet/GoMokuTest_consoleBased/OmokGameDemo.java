package noapplet.GoMokuTest_consoleBased;

import java.io.*;

public class OmokGameDemo {

    public static void demoPlayWithAI() throws IOException {
        // Capture the console output.
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Simulate user choosing to play with AI, making a move, and then quitting.
        InputStream originalIn = System.in;
        String chooseAI = "yes\n"; // Choosing to play with AI
        String humanMove = "5 5\n"; // Assuming the input format is "x y"
        String quitCommand = "-1\n";

        ByteArrayInputStream simulatedInput = new ByteArrayInputStream((chooseAI + humanMove + quitCommand).getBytes());

        System.err.println("Debug: Simulated input commands set.");
        System.err.println("Debug: Simulated input content: " + new String(simulatedInput.readAllBytes()));

        // Reset the input for the game to read it.
        simulatedInput.reset();
        System.setIn(simulatedInput);

        System.err.println("Available bytes in simulated input: " + System.in.available());

        OmokGameConsole game = new OmokGameConsole(true, System.out, System.in); // Instantiating an OmokGameConsole object
        game.play();

        // Print the captured output to the original console
        System.setOut(originalOut);
        System.out.println(outContent);

        // Reset the input stream to its original.
        System.setIn(originalIn);
    }


    public static void main(String[] args) throws IOException {
        demoPlayWithAI(); // This will run the demo when you execute the class.
    }
}
