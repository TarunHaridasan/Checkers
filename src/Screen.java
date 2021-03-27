import java.io.*;
import java.util.*;

public class Screen {
    //Mapping color to color codes
    final static Map<String, String> COLORCODES = new HashMap<String, String>() {{
        put("BLACK", "\u001B[30m");
        put("RED", "\u001B[31m");
        put("GREEN", "\u001B[32m");
        put("YELLOW", "\u001B[33m");
        put("BLUE", "\u001B[34m");
        put("PURPLE", "\u001B[35m");
        put("CYAN", "\u001B[36m");
        put("RESET", "\u001B[0m");
        put("WHITE", "\u001B[37m");
    }};

    //This method prints the checker board onto the console window with the right formatting
    public static void printBoard(Board board) {
        for(int i=0; i<board.SIDELENGTH; i++) {
            for (int j=0; j<board.SIDELENGTH; j++) {
                if (i%(board.CELLSPACING+1)==0) //Print a horizontal line all the way
                    System.out.print(board.BORDERSTRING+" ");
                else {
                    if (j%(board.CELLSPACING+1)==0)
                        System.out.print(board.BORDERSTRING+" ");  //Print the border to the cell
                    else { //Print the icon text in the middle of the cell
                        if (i%((board.CELLSPACING/2)+1)==0 && j%((board.CELLSPACING/2)+1)==0) {
                            Piece piece = board.board[i/(board.CELLSPACING+1)][j/(board.CELLSPACING+1)];
                            if (piece!=null) { //Print the piece if is in this cell with color specified
                                System.out.print(COLORCODES.get(piece.color)+piece+COLORCODES.get("RESET")+" "); //piece will auto return icon
                            }

                            else
                                System.out.print("  "); //Print empty space if no piece in this cell
                        }
                        else
                            System.out.print("  ");
                    }
                }
            }
            System.out.println();
        }
    }

    //This command clears the console window (It adds lots of spaces)
    public static void clear(int space) {
        for (int i=0; i<space; i++) {
            System.out.println();
        }
    }

    //This command clears the console window and then reprints the board to the screen
    public static void refresh(Board board, int space) {
        clear(space);
        printBoard(board);
    }

    //This command reads from ASCII file and prints it
    public static void printFromFile(String fp) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fp));
        String line = br.readLine();
        while (line!=null) {
            System.out.println(line);
            line = br.readLine();
        }
    }
}
