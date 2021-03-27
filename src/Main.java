import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Make board
        Board board = new Board("WHITE", "YELLOW", "PURPLE", '.', 3);

        //Print board onto screen
        Screen.printBoard(board);



        /*
        //Boundary testing
        System.out.println(Board.isBoundary(new int[] {-1, 8}));
        System.out.println(Board.isBoundary(new int[] {0, 8}));
        System.out.println(Board.isBoundary(new int[] {-1, 7}));
        System.out.println(Board.isBoundary(new int[] {0, 7}));
        System.out.println(Board.isBoundary(new int[] {2, 7}));

        //Move testing
        Piece piece = board.getPiece(new int[] {7,7});
        board.move(piece, new int[] {3,7});
        piece = board.getPiece(new int[] {0,0});
        board.move(piece, new int[] {7,7});
        Screen.refresh(board, 20);


         */
    }
}
