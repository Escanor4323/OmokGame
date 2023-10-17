package noapplet.GoMokuTest_consoleBased;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;


public class MainGoMokuGame {

    public static void printMenu(@org.jetbrains.annotations.NotNull PrintStream outputStream) {
        String separator = "+";

        String horizontalBar = "-";
        String wall = "|";
        String[] options = {
                "➜ Play Human Players! (2 Player mode)      |",
                "➜ Play Computer! (AI based Strategy Game)  |",
                "➜ Exit                                     |"
        };
        int width = options[0].length() + 1;

        String Tops = separator + horizontalBar.repeat(width) + separator;
        outputStream.println(Tops);

        outputStream.println(wall + " Please Select a number from the given Menu  " + wall);

        for (int i = 0; i < options.length; i++) {
            outputStream.println(wall + " (" + (i + 1) + ") " + options[i].substring(2, options[i].length() - 2) + wall);
        }

        outputStream.println(Tops);
    }
    public static void getResponseStartStates(PrintStream outputStream, InputStream inputStream) {
        Scanner scn = new Scanner(inputStream);
        int choice = 0;
        boolean validChoice = false;

        while (!validChoice) {
            try {
                outputStream.print("➜ User option: ");
                choice = scn.nextInt();
                // Validate the range of choice if necessary.
                // For example, if the valid choices are 1, 2, and 3:
                if (choice >= 1 && choice <= 3) {
                    validChoice = true;
                } else {
                    outputStream.println("Please select a valid option.");
                }
            } catch (InputMismatchException e) {
                // Clear the current input (invalid one).
                scn.nextLine();
                // Then inform the user about the invalid input.
                outputStream.println("Invalid input. Please enter a number.");
                // The loop will continue as 'validChoice' is still false.
            }
        }
        OmokGameConsole game;
        switch (choice) {

            case 1:
                outputStream.println("Starting Human based game!\n Get a partner to play with...");
                game = new OmokGameConsole(false, outputStream, inputStream);
                game.play();
                break;

            case 2:
                outputStream.println("Starting AI based game!\nHow good are you against GoMokuAI :3");
                game = new OmokGameConsole(true, outputStream, inputStream);
                game.play();
                break;

            default:
                // This will only be reached if the user inputs a number
                // that is not a valid choice (not 1 or 2 in this case)
                outputStream.println("Quitting Game! Have a great day!");
                break;
        }

    }



    public static void main(String[] args) {
        PrintStream outputStream = System.out;
        InputStream inputStream = System.in;
        printMenu(outputStream);
        getResponseStartStates(outputStream, inputStream);

    }
}
