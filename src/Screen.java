import java.io.*;
public class Screen {
    //Declare properties
    final int CELLSPACING = 3; //Must be an odd number of program will be fucked
    final int SIDELENGTH = (CELLSPACING*8)+9;

    //This method prints the checker board onto the console window with the right formatting
    public void printBoard(String[][] board) {
        for(int i=0; i<SIDELENGTH; i++) {
            for (int j=0; j<SIDELENGTH; j++) {
                if (i%(CELLSPACING+1)==0) {
                    System.out.print("# ");
                }
                else {
                    if (j%(CELLSPACING+1)==0) {
                        System.out.print("# ");
                    }
                    else {
                        if (i%((CELLSPACING/2)+1)==0 && j%((CELLSPACING/2)+1)==0) { //Fix later
                            System.out.print(board[i/(CELLSPACING+1)][j/(CELLSPACING+1)]+" ");
                        }
                        else {
                           System.out.print("  ");
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    //This command clears the console window (It adds lots of spaces)
    public void clear(int space) {
        for (int i=0; i<space; i++) {
            System.out.println();
        }
    }

    //This command clears the console window and then reprints the board to the screen
    public void refresh(String[][] board, int space) {
        clear(space);
        printBoard(board);
    }

    //This command reads from ASCII file and prints it
    public void printFromFile(String fp) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fp));
        String line = br.readLine();
        while (line!=null) {
            System.out.println(line);
            line = br.readLine();
        }
    }
}
