import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Make board and AI
        Board board = new Board("WHITE", "YELLOW", "PURPLE", '.', 3);
        AI ai = new AI(board, 3);

        //Testing
        Screen.printBoard(board);
        Piece piece = board.getPiece(new int[] {3,3});
        Integer[][] moves = piece.visualize(board);
        for (int i=0;i<moves.length; i++) {
            System.out.println(Arrays.toString(moves[i]));
        }

    }
}
