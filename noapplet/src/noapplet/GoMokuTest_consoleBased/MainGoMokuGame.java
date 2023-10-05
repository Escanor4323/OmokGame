package noapplet.GoMokuTest_consoleBased;

import java.util.InputMismatchException;
import java.util.Scanner;
import noapplet.*;

public class MainGoMokuGame {

    public static void printMenu() {
        String separator = "+";
        String horizontalBar = "-";
        String wall = "|";
        String[] options = {
                "→ Play Human Players! (2 Player mode)      |",
                "→ Play Computer! (AI based Strategy Game)  |",
                "→ Exit                                     |"
        };
        int width = options[0].length() + 1;

        String Tops = separator + horizontalBar.repeat(width) + separator;
        System.out.println(Tops);

        System.out.println(wall + " Please Select a number from the given Menu   " + wall);

        for (int i = 0; i < options.length; i++) {
            System.out.println(wall + " (" + (i + 1) + ") " + options[i].substring(2, options[i].length() - 2) + wall);
        }

        System.out.println(Tops);
    }
    public static void getResponseStartStates() {
        Scanner scn = new Scanner(System.in);
        boolean validInput = false;

        while (!validInput) {
            System.out.print("→ User option: ");

            try {
                int choice = scn.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Starting Human based game!\n Get a partner to play with...");
                        OmokGameConsole game = new OmokGameConsole(false);
                        game.play();
                        validInput = true;
                        break;
                    case 2:
                        System.out.println("Starting AI based game!\n How good are you against GoMokuAI :3");
                        System.out.flush();
                        OmokGameConsole gameAI = new OmokGameConsole(true);
                        gameAI.play();
                        validInput = true;
                        break;
                    case 3:
                        System.out.println("Quitting Game! Have a great day!");
                        validInput = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please select a valid number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scn.next(); // To consume the invalid token and avoid an infinite loop.
            }
        }
        scn.close();
    }


    public static void main(String[] args) {

        printMenu();
        getResponseStartStates();

    }
}
