import com.rits.cloning.Cloner;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, IOException {
        //Initializing the title screen.
        //TitleScreen.initialize();
        /*
            2:28PM on March 30, 2021.
            Jason Su wrote the baseline loop code and input for user.
        */
        //Starting the board.
        Board board = new Board(TitleScreen.compColour, TitleScreen.playerColour, TitleScreen.borderColour, TitleScreen.borderChar, TitleScreen.cellSpacing);
        Screen.printBoard(board);

        //Main game loop.
        boolean player = true;
        while(true) {
            //Player turn.
            if(player) {
                int[][] coords = null;

                //Looping until the player's input is valid.
                boolean valid = false;
                do {
                    String input = Screen.prompt("Input: ");
                    String[] inputArr = input.split(" ");

                    //Check if user input is valid
                    if (!board.isInputValid(inputArr)) continue;

                    //Convert the input to array indices
                    int[][] coordinateArr = new int[inputArr.length][2];
                    for (int i=0; i< inputArr.length; i++) coordinateArr[i] = board.toCoords(inputArr[i]);

                    //Deep clone the original array in case one of the move in the chain is valid
                    Cloner cloner = new Cloner();
                    Piece[][] originalBoard = cloner.deepClone(board.board);

                    //Check if the move (or moves if chained) are valid
                    boolean mustKill = false;
                    if (coordinateArr.length>2) mustKill = true; //If chaining moves, all moves must be ones to kill the enemey piece
                    for (int i=0; i< coordinateArr.length-1; i++) {
                        int[][] path = Arrays.copyOfRange(coordinateArr, i, i+2);
                        Screen.println(Arrays.toString(path[0])+""+Arrays.toString(path[1]));
                        if (board.isValidMove(path, true, mustKill)){
                            Screen.println("VALID");
                            board.move(board.getPiece(path[0]), path[1]);
                            valid = true;
                        }
                        else {
                            Screen.println("INVALID");
                            valid = false;
                            board.board = originalBoard;
                            break;
                        }
                    }

                    //If valid == true, then we don't need to ask the user for input again
                    if (valid) break;
                } while(!valid);

                //Switching the turn variable.
                player = false;
            }
            //AI turn.
            else {
                Screen.println("Computer does its turn...");
                player = true;
            }

            //Refreshing the board after every turn.
            Screen.refresh(board, 10);

            //Check if game over
            if(board.isGameOver()) break;
        }
        Screen.println("Game Over!");
    }
}
