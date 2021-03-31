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
                int[][] coords = null;
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
                        Screen.println("1. " + current);
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
                            Screen.println("That input set is invalid... Please try again!4");
                            Screen.println("");
                            break;
                        }
                    }
                    if(!valid) continue;
                    //Creating a 2d array for the set of coordinates that the piece will move.
                    coords = new int[inputArray.length][2];
                    for (int i = 0; i < inputArray.length; i++) {
                        String current = inputArray[i];
                        //Filling up the array with coordinates using a helper method from the Board class.
                        int[] temporary = board.toCoords(current);
                        coords[i] = temporary;
                    }
                    //Checking the move set to see if it is valid.
                    //Checking to see if there is a piece on the first coordinate.
                    if(board.getBetterPiece(coords[0][0], coords[0][1]) == null) {
                        valid = false;
                        Screen.println("That input set is invalid... Please try again!");
                        Screen.println("");
                        continue;
                    }
                    //Temporary variable to store original position of the piece in case the chain is invalid.
                    int[] original = coords[0];
                    for(int i = 0; i < coords.length-1; i++) {
                        //Temporary variable to store two coords at a time.
                        int[][] tempCoords = new int[2][2];
                        tempCoords[0] = coords[i];
                        tempCoords[1] = coords[i+1];
                        //Checking if one set of moves is valid.
                        String message = "";
                        message += tempCoords[0][1]+" "+tempCoords[0][0];
                        Screen.println(message);
                        if(board.isValidMove(tempCoords, true))
                            board.move(board.getBetterPiece(tempCoords[0][0], tempCoords[0][1]), tempCoords[1]);
                        else {
                            //If the move is invalid, move it back to the original spot.
                            board.move(board.getBetterPiece(tempCoords[0][0], tempCoords[0][1]), original);
                            //Afterwards, ask user for another input.
                            valid = false;
                            Screen.println("That input set is invalid... Please try again!5");
                            Screen.println("");
                            break;
                        }
                    }
                    if(valid) break;
                } while(true);
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
