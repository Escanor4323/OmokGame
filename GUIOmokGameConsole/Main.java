package noapplet.GUIOmokGameConsole;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Omok Game");
        Board_HW board = new Board_HW();

        Player player1 = new HumanPlayer(Stone.BLACK, System.out, System.in);
        Player player2 = new HumanPlayer(Stone.WHITE, System.out, System.in);

        for (int i = 0; i <= 6; i++){
            if (i %2 == 0) board.placeStone(i, i, player1);
            else board.placeStone(i, i, player2);
        }

        frame.add(board);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

