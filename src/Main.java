import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Make board
        Board board = new Board("WHITE", "YELLOW", "PURPLE", '.', 1);
        Scanner scan = new Scanner(System.in);

        //Print board onto screen
        Screen.printBoard(board);

        /*
        //Testing
        int xi = 5;
        int yi = 7;
        int xf = 4;
        int yf = 6;
        Piece piece = board.getPiece(new int[] {xi, yi});

        //Check move
        if (board.isValidMove(new int[][] {{xi,yi},{xf, yf}}, true))
            board.move(piece, new int[] {xf, yf});
         */
    }
}
