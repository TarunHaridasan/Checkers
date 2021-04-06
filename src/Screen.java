/*
    Tarun Haridasan, Fahad Mateen, Jason Su
    04/6/2021
    Screen.java
    This java file contains the Screen class. The screen class is used for printing items to the screen, like ASCII text or the board, and collecting input from the user.
 */
import java.io.*;
import java.util.*;

public class Screen {
    /*************************************Tarun Haridasan- 11:50AM on March 25, 2021.**************************************/
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

    /*
     ****************************************************************************************
     ********************************* FUNCTION WITH PARAMETERS *****************************
     ****************************************************************************************
    */
    /*************************************Tarun Haridasan- 9:50AM on March 27, 2021.**************************************/
    //This method prints the checker board onto the console window with the right formatting
    public static void printBoard(Board board) {
        //Print the horizontal ruler
        for(int i=0; i<8; i++) {
            for (int j=0; j< board.cellSpacing+1; j++)
                System.out.print(" ");
            System.out.print(Character.toString(97+i));
            for (int j=0; j<board.cellSpacing; j++)
                System.out.print(" ");
        }
        System.out.println();
        //Print the board
        for (int i=0; i< board.sideLength; i++) {
            //Print a horizontal line every cellspacing+1
            if (i%(board.cellSpacing+1)==0) {
                for (int j=0; j< board.sideLength; j++) {
                    System.out.print(board.borderString);
                }
            }
            //Do not print a horizontal line
            else {
                for (int j=0; j<board.sideLength; j++) {
                    //Print vertical ruler at the beginning of each row, in the middle of each cell
                    if (j==0 && (board.cellSpacing-1==0 || i%(board.cellSpacing-1)==0))
                        System.out.print(Character.toString(49+ i/(board.cellSpacing+1))+" ");
                    //Print part of the vertical line every cellspacing+1
                    else if (j%(board.cellSpacing+1)==0)
                        System.out.print(board.borderString);
                    //Do not print vertical border here
                    else {
                        //Print a checker piece right in the middle of the cell
                        if (j%((board.cellSpacing/2)+1)==0 && i%((board.cellSpacing/2)+1)==0) {
                            //If a checker piece exists in this coordinates, print it
                            Piece piece = board.getPiece(new int[] {i/(board.cellSpacing+1), j/(board.cellSpacing+1)});
                            if (piece!=null)
                                System.out.print(COLORCODES.get(piece.color)+piece+COLORCODES.get("RESET")+" ");
                            //No checker piece at this location, so print empty space
                            else
                                System.out.print("  ");
                        }
                        //Just print empty space
                        else
                            System.out.print("  ");
                    }
                }

            }
            System.out.println();
        }
    }

    /*
     ****************************************************************************************
     ********************************* FUNCTIONS WITH PARAMETERS ****************************
     ****************************************************************************************
    */
    /*************************************Fahad Mateen- 7:05PM on March 27, 2021.**************************************/
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

    /*************************************Jason Su- 10:50PM on March 28, 2021.**************************************/
    //This command reads from ASCII file and prints it
    public static void printFromFile(String fp) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fp));
        String line = br.readLine();
        while (line!=null) {
            System.out.println(line);
            line = br.readLine();
        }
    }

    /*************************************Jason Su- 2:53PM on March 29, 2021.**************************************/
    //Shortcut print and println.
    public static void print(String message) {
        System.out.print(message);
    }
    public static void println(String message) {
        System.out.println(message);
    }

    /*
     ****************************************************************************************
     ************************************** CONSOLE INPUT ***********************************
     ****************************************************************************************
    */
    //This method is used throughout the program as as shortcut for console input.
    //Shortcut prompt method.
    public static String prompt(String message) {
        //Variables and scanner
        String input = "";
        Scanner scan = new Scanner(System.in);
        //Printing the message and asking for user response.
        Screen.print(message);
        input = scan.nextLine();
        //Returning the user's response as a string.
        return input;
    }
}
