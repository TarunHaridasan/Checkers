import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Make board
        Board board = new Board("WHITE", "YELLOW", "PURPLE", '.', 3);
        Scanner scan = new Scanner(System.in);

        //Print board onto screen
        Screen.printBoard(board);

        //Testing
        int[] start = {2,6};
        int[] end = {3,7};

        //Piece
        Piece piece = board.getPiece(start);
        if (board.isValidMove(new int[][] {start,end}, false)) board.move(piece, end);

        Screen.refresh(board, 5);
    }
}
