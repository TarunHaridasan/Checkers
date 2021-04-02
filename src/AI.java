import com.rits.cloning.Cloner;

import java.util.*;

public class AI {
    //This custom data structure allows the minimax algorithm to return 3 different data types
    public static class MinimaxReturnType {
        int score = 0;
        Piece piece = null;
        int[] end = null;
        public MinimaxReturnType(int score) {
            this.score = score;
        }
        //Second version of constructor with different arguments and data storage
        public MinimaxReturnType(int score, Piece piece, int[] end) {
            this.score = score;
            this.piece = piece;
            this.end = end;
        }
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
    public static HashMap<int[], int[][]> visualize(Board board, boolean turn) {
        HashMap<int[], int[][]> movesForBoard = new HashMap<int[], int[][]>();
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                Piece piece = board.board[i][j];
                if (piece!=null && piece.side == turn) {
                    //Visualize all possible piece locations
                    int[][] movesForPiece = piece.visualize(board);
                    //Throw the moves for the piece into movesForBoard map
                    movesForBoard.put(new int[] {piece.pos[0], piece.pos[1]}, movesForPiece);
                }
            }
        }
        return movesForBoard;
    }

    //This method uses the minimax algorithm to calculate the best possible piece
    public static MinimaxReturnType minimax(Board board, int depth, boolean side) {
        if (depth==0 || board.isGameOver()) {
            return new MinimaxReturnType(AI.score(board.board));
        }

        if (side) {
            int maximum = Integer.MIN_VALUE;
            Piece bestPiece = null;
            int[] bestEnd = null;
            HashMap<int[], int[][]> possibleMoves = AI.visualize(board, true);
            for (Map.Entry<int[], int[][]> entry : possibleMoves.entrySet()) {
                int[] key = entry.getKey();
                int[][] value = entry.getValue();
                for (int[] i : value) {
                    //Make deep clone if board
                    Cloner cloner = new Cloner();
                    Board simulator = cloner.deepClone(board);
                    //Simulate the piece move
                    Piece piece = simulator.getPiece(key);
                    simulator.move(piece, i);
                    //Recursively get all the scores for its child
                    int score = minimax(simulator, depth-1, false).score;
                    //Check if this yields the maximum score, and if it does, save the piece and end point
                    maximum = Math.max(maximum, score);
                    if (maximum == score) {
                        bestPiece = board.getPiece(key);
                        bestEnd = i;
                    }
                }
            }
            return new MinimaxReturnType(maximum, bestPiece, bestEnd);
        }
        else {
            int minimum = Integer.MAX_VALUE;
            Piece bestPiece = null;
            int[] bestEnd = null;
            HashMap<int[], int[][]> possibleMoves = AI.visualize(board, false);
            for (Map.Entry<int[], int[][]> entry : possibleMoves.entrySet()) {
                int[] key = entry.getKey();
                int[][] value = entry.getValue();
                for (int[] i : value) {
                    //Make deep clone if board
                    Cloner cloner = new Cloner();
                    Board simulator = cloner.deepClone(board);
                    //Simulate the piece move
                    Piece piece = simulator.getPiece(key);
                    simulator.move(piece, i);
                    //Recursively get all the scores for its child
                    int score = minimax(simulator, depth-1, true).score;
                    //Check if this yields the maximum score, and if it does, save the piece and end point
                    minimum = Math.min(minimum, score);
                    if (minimum==score) {
                        bestPiece = board.getPiece(key);
                        bestEnd = i;
                    }
                }
            }
            return new MinimaxReturnType(minimum, bestPiece, bestEnd);
        }
    }
}
