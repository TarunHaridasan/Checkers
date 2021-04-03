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
        //Loop through the board
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                Piece piece = board[i][j];
                //If a piece is located in this cell, then decide how many points it is worth
                if (piece!=null) {
                    //A player's piece will increase the score (Regular piece = 1 point, King = 2 point)
                    if (piece.side) {
                        if (piece.isKing) score+=1;
                        score+=1;
                    }
                    //Enemy piece will reduce the score. (Regular piece = -1 point, King = -2 point)
                    else {
                        if (piece.isKing) score-=1;
                        score-=1;
                    }
                }
            }
        }
        return score;
    }

    //This method visualizes all possible board outcomes for this turn.
    public static HashMap<int[], int[][]> visualize(Board board, boolean turn) {
        HashMap<int[], int[][]> movesForBoard = new HashMap<int[], int[][]>();
        //Loop through all cells in the board
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                Piece piece = board.board[i][j];
                //If the cell is not empty and it is our turn to move it, then determine all possible end points
                if (piece!=null && piece.side == turn) {
                    //Visualize all possible piece locations
                    List<int[]> moves = piece.visualize(board);
                    //List<int[]> chainMoves = piece.visualizeChain(board);
                    //chainMoves.remove(0);
                    //Merge all locations and convert to array
                    //moves.addAll(chainMoves);
                    //Convert the arrayList to a 2D integer array
                    int[][] movesForPiece = new int[moves.size()][2];
                    moves.toArray(movesForPiece);
                    //Generate a map where the position of the current piece is the key and a 2D array of all its possibles moves is the value.
                    movesForBoard.put(new int[] {i, j}, movesForPiece);
                }
            }
        }
        return movesForBoard;
    }

    //This method uses the minimax algorithm to calculate the best possible piece
    public static MinimaxReturnType minimax(Board board, int depth, boolean side) {
        if (board.isGameOver()) {
            if (side) return new MinimaxReturnType(Integer.MIN_VALUE);
            else return new MinimaxReturnType(Integer.MAX_VALUE);
        }
        if (depth==0) {
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
                    Board simulator = Board.CLONER.deepClone(board);
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
                    Board simulator = board.CLONER.deepClone(board);
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
