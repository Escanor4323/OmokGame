package noapplet.GoMokuTest_consoleBased_original;

import java.util.Scanner;


public class MainGoMokuGame {

    public static void printMenu() {
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
        System.out.println(Tops);

        System.out.println(wall + " Please Select a number from the given Menu  " + wall);

        for (int i = 0; i < options.length; i++) {
            System.out.println(wall + " (" + (i + 1) + ") " + options[i].substring(2, options[i].length() - 2) + wall);
        }

        System.out.println(Tops);
    }
    public static void getResponseStartStates() {
        Scanner scn = new Scanner(System.in);
        System.out.print("➜ User option: ");
        int choice = scn.nextInt();
        OmokGameConsole game;
        switch (choice) {

            case 1:
                System.out.println("Starting Human based game!\n Get a partner to play with...");
                game = new OmokGameConsole(false);

                game.play();
                break;

            case 2:
                System.out.println("Starting AI based game!\n How good are you against GoMokuAI :3");
                System.out.flush();
                game = new OmokGameConsole(true);
                game.play();
                break;

            default:
            System.out.println("Quitting Game! Have a great day!");

        }
        scn.close();
    }

    public static void main(String[] args) {

        printMenu();
        getResponseStartStates();

    }
}
