/*
    Tarun Haridasan, Fahad Mateen, Jason Su
    04/6/2021
    Main.java
    This is the main java file of the ICS4U checkers console game.
 */
import java.awt.image.AreaAveragingScaleFilter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        /*************************************Jason Su- 2:28PM on March 30, 2021.**************************************/
        //Initializing the title screen.
        TitleScreen.initialize();
        //Asking for difficulty.
        int difficulty = 0;
        if(TitleScreen.loadedBoard == null) difficulty = TitleScreen.difficulty();
        else difficulty = TitleScreen.difficulty;
        //Starting the board.
        Board board = new Board(TitleScreen.compColour, TitleScreen.playerColour, TitleScreen.borderColour, TitleScreen.borderChar, TitleScreen.cellSpacing);
        //Loading the board from the save, or defaulting the board if no save was loaded.
        if(TitleScreen.loadedBoard != null) board.board = TitleScreen.loadedBoard;
        //Printing the board to the user.
        Screen.printBoard(board);

        //Main game loop.
        boolean player = TitleScreen.turn;
        while(true) {
            //Player turn.
            if(player) {
                //Looping until the player's input is valid.
                boolean valid = false;
                do {
                    String input = Screen.prompt("Input: ");
                    if(input.equalsIgnoreCase("save")) {
                        //Generating the random code.
                        int code = 0;
                        //Creating a file object for the saves directory.
                        File folder = new File("./saves/");
                        File[] saves = folder.listFiles();
                        //Making sure that the code is unique.
                        boolean validCode = true;
                        do {
                            validCode = true;
                            code = (int) Math.floor(Math.random()*(999999-100000+1)+100000);
                            assert saves != null;
                            for (File current : saves) if(current.getName().equals(code + ".txt")) validCode = false;
                        } while(!validCode);
                        String codeString = String.valueOf(code);
                        //Writing to the file.
                        BufferedWriter file = new BufferedWriter(new FileWriter("./saves/"+codeString+".txt"));
                        //Saving the player's name and difficulty value.
                        file.write(TitleScreen.username + ' ' + String.valueOf(difficulty) + "\r\n");
                        //Saving the player's board.
                        for(Piece[] row : board.board) {
                            for(Piece current : row) {
                                //Deciding what character to write to the space on the file.
                                if(current == null) file.write('O');
                                else if(current.side)
                                    if(current.isKing)file.write('K');
                                    else file.write('P');
                                else if(current.isKing)file.write('k');
                                else file.write('C');
                                file.write(' ');
                            }
                            //New line for each row.
                            file.write("\r\n");
                        }
                        //Closing the file.
                        file.close();
                        //Telling the user the code.
                        Screen.println("Your game has been saved! Here is your code: " + codeString);
                        Screen.println("");
                        //Returning to the main menu.
                        Main.main(null);
                        return;
                    }
                    String[] inputArr = input.split(" ");

                    //Check if user input is valid
                    if (!Board.isInputValid(inputArr)) continue;

                    //Convert the input to array indices
                    int[][] coordinateArr = new int[inputArr.length][2];
                    for (int i=0; i< inputArr.length; i++) coordinateArr[i] = Board.toCoords(inputArr[i]);

                    //Deep clone the original array in case one of the move in the chain is valid
                    Piece[][] originalBoard = Board.CLONER.deepClone(board.board);

                    //Check if the move (or moves if chained) are valid
                    boolean mustKill = false;
                    if (coordinateArr.length>2) mustKill = true; //If chaining moves, all moves must be ones to kill the enemy piece
                    for (int i=0; i< coordinateArr.length-1; i++) {
                        int[][] path = Arrays.copyOfRange(coordinateArr, i, i+2);
                        if (board.isValidMove(path, true, mustKill)){
                            board.move(board.getPiece(path[0]), path[1]);
                            valid = true;
                        }
                        else {
                            Screen.println("That was not a valid move. Please try again...");
                            valid = false;
                            board.board = originalBoard;
                            break;
                        }
                    }
                } while(!valid);

                //Switching the turn variable.
                player = false;
            }
            //AI turn.
            /* ************************************Fahad Mateen- 6:05PM on March 31, 2021.************************************* */
            else {
                AI.MinimaxReturnType computerMove = AI.minimax(board, difficulty, false);
                int[][] path = AI.hasMoved(board.board, computerMove.board.board);
                board = computerMove.board;
                Screen.println("");
                Screen.println("AI is thinking...");
                Thread.sleep(2000);
                Screen.println("AI moved from "+ Board.fromCoords(path[0])+" to "+Board.fromCoords(path[1]));
                player = true;
            }

            //Refreshing the board after every turn.
            Screen.refresh(board, 1);

            //Check if game over
            if(board.isGameOver()) {
                if (player) Screen.println("Game Over. Computer wins!");
                else Screen.println("Game over. You win!");
                break;
            };
        }

    }
}
