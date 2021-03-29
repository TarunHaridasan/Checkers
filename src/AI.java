import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class AI {
    //AI Settings
    int depthToCheck = 0;
    Board board = null;

    //Constructor
    public AI(Board board, int depth) {
        this.depthToCheck = depth;
        this.board = board;
    }

    //This method calculates the score of a board arrangement
    public static int score(Piece[][] board) {
        int score = 0;
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                Piece piece = board[i][j];
                if (piece!=null) {
                    if (piece.side==true)
                        score+=1;
                    else
                        score-=1;
                }
            }
        }
        return score;
    }

    //This method visualizes all possible board outcomes for this turn.
    public static HashMap<String, Integer[][]> visualize(Board board, boolean turn) {
        HashMap<String, Integer[][]> movesForBoard = new HashMap<String, Integer[][]>();
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                Piece piece = board.board[i][j];
                if (piece!=null && piece.side == turn) {
                    //Visualize all possible piece locations
                    Integer[][] movesForPiece = piece.visualize(board);
                    //Throw the moves for the piece into movesForBoard map
                    movesForBoard.put(piece.id, movesForPiece);
                }
            }
        }
        return movesForBoard;
    }
}
