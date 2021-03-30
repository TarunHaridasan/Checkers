import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, IOException {
        //Initializing the title screen.
        TitleScreen.initialize();
        /*
            2:28PM on March 30, 2021.
            Jason Su wrote the baseline loop code and input for user.
        */
        //Starting the board.
        Board board = new Board(TitleScreen.compColour, TitleScreen.playerColour, TitleScreen.borderColour, TitleScreen.borderChar, TitleScreen.cellSpacing);
        //Main game loop.
        //Temporary turn controlling variables.
        boolean player = true, gameOver = false;
        while(true) {
            //Printing the board every turn.
            Screen.printBoard(board);
            //Player turn.
            if(player) {
                //Placeholder variables.
                boolean valid = true;
                String input = "";
                String[] inputArray = null;
                //Looping until the player's input is valid.
                do {
                    //Resetting the valid variable to true.
                    valid = true;
                    //Temporary character placeholder.
                    char temp = ' ';
                    //Asking the user for input.
                    input = Screen.prompt("Input: ");
                    inputArray = input.split(" ");
                    //Checking for a valid input.
                    for(String current : inputArray) {
                        //Checking if the user only entered one value.
                        if(inputArray.length < 2) {
                            //If so... then this set of inputs is invalid.
                            valid = false;
                            Screen.println("You must enter more than just one set of coordinates! You may wish to re-read the help page.");
                            Screen.println("");
                            break;
                        }
                        /*
                            Checking if the first character of each input is a letter...
                            and if the second character of each input is a number within the range.
                        */
                        if(!(Board.firstChars.contains(Character.toString(current.charAt((0))))) || !(Board.secondChars.contains(Character.toString(current.charAt((1)))))) {
                            //If not... then this set of inputs is invalid.
                            valid = false;
                            Screen.println("That input set is invalid... Please try again!");
                            Screen.println("");
                            break;
                        }
                    }
                    if(valid) break;
                } while(true);
                Screen.println("You did it!");
                int[][] coords = new int[inputArray.length][2];
                for (String current : inputArray) {
                    int[] temporary = board.toCoords(current);
                }
                //Moving the piece all the way through the sequence.
                if(board.isValidMove(coords, true)) {
                    for(int i = 0; i < coords.length-1; i++) {
                        board.move(board.getPiece(coords[i]), coords[i+1]);
                    }
                }
                //Switching the turn variable.
                player = false;
            }
            //AI turn.
            else {
                Screen.println("");
                Screen.println("Computer does its turn...");
                Screen.println("");
                player = true;
            }
            if(gameOver) break;
        }
    }
}
