import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        /*
            2:28PM on March 30, 2021.
            Jason Su wrote the baseline loop code and input for user.
        */
        //Initializing the title screen.
        TitleScreen.initialize();
        //Asking for difficulty.
        int difficulty = TitleScreen.difficulty();
        //Starting the board.
        Board board = new Board(TitleScreen.compColour, TitleScreen.playerColour, TitleScreen.borderColour, TitleScreen.borderChar, TitleScreen.cellSpacing);
        //Loading the board from the save, or defaulting the board if no save was loaded.
        //board.board = TitleScreen.board;
        Screen.printBoard(board);

        //Main game loop.
        boolean player = TitleScreen.turn;
        while(true) {
            //Player turn.
            if(player) {
                //Looping until the player's input is valid.
                boolean valid = false;
                do {
                    /*
                    Piece piece = board.getPiece(new int[] {1, 1});
                    List<Board> chainMoves = piece.visualizeChain(board);
                    for (Board i : chainMoves) {
                        Screen.printBoard(i);
                    }
                     */

                    /*
                    HashMap<int[], AI.VisualizeReturnType> moves = AI.visualize(board, false);
                    for (Map.Entry<int[], AI.VisualizeReturnType> entry : moves.entrySet()) {
                        int[] key = entry.getKey();
                        System.out.print(Arrays.toString(key)+": ");
                        AI.VisualizeReturnType value = entry.getValue();
                        List<int[]> reg = value.regularMoves;
                        List<Board> chain = value.chainMoves;
                        //Print reg
                        for (int[] i : reg) {
                            System.out.print(Arrays.toString(i)+" ");
                        }
                        System.out.println();
                        //Print chain
                        for (Board i : chain) {
                            Screen.printBoard(i);
                        }
                    }

                     */
                    String input = Screen.prompt("Input: ");
                    String[] inputArr = input.split(" ");

                    //Check if user input is valid
                    if (!Board.isInputValid(inputArr)) continue;

                    //Convert the input to array indices
                    int[][] coordinateArr = new int[inputArr.length][2];
                    for (int i=0; i< inputArr.length; i++) coordinateArr[i] = board.toCoords(inputArr[i]);

                    //Deep clone the original array in case one of the move in the chain is valid
                    Piece[][] originalBoard = Board.CLONER.deepClone(board.board);

                    //Check if the move (or moves if chained) are valid
                    boolean mustKill = false;
                    if (coordinateArr.length>2) mustKill = true; //If chaining moves, all moves must be ones to kill the enemy piece
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
                } while(!valid);

                //Switching the turn variable.
                player = false;
            }
            //AI turn.
            else {
                /*
                //Testing the AI
                HashMap<int[], int[][]> moves = AI.visualize(board, true);
                for (Map.Entry<int[], int[][]> entry: moves.entrySet()) {
                    int[] key = entry.getKey();
                    System.out.print(Arrays.toString(key)+": ");
                    int[][] value = entry.getValue();
                    for (int[] i : value) {
                        System.out.print(Arrays.toString(i)+" ");
                    }
                    System.out.println();
                }
                */

                AI.MinimaxReturnType computerMove = AI.minimax(board, difficulty, false);
                System.out.println("Score: "+computerMove.score);
                board = computerMove.board;
                Screen.println("AI is thinking...");
                //Thread.sleep(1000);
                player = true;

            }

            //Refreshing the board after every turn.
            Screen.refresh(board, 10);

            //Check if game over
            if(board.isGameOver()) {
                if (player) Screen.println("Game Over. Computer wins!");
                else Screen.println("Game over. You win!");
                break;
            };
        }

    }
}
