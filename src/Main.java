import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Make board and AI
        Board board = new Board("WHITE", "YELLOW", "PURPLE", '.', 1);
        //AI ai = new AI(board, 3);

        Screen.printBoard(board);

        if (board.isValidMove(new int[][] {{5,3}, {4,4}}, true)) {
            board.move(board.getPiece(new int[] {5,3}), new int[] {4,4});
            //System.out.print("VALID");
        }


        Screen.refresh(board, 5);

        /*
        //Testing
        Screen.printBoard(board);
        HashMap<String, Integer[][]> moves = ai.visualize(board, true);

        //Print out
        for (String key : moves.keySet()) {
            System.out.print(key+": ");
            Integer[][] value = moves.get(key);
            for (int i=0; i<value.length; i++) {
                System.out.print(Arrays.toString(value[i])+" ");
            }
            System.out.println();
        }
        //System.out.println(moves);
         */

    }
}
