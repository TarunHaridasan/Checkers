/*
    Tarun Haridasan, Fahad Mateen, Jason Su
    04/6/2021
    AI.java
    This java file contains the class and methods that allow the AI to find the best move.
 */

import java.util.*;
public class AI {
    /*************************************Tarun Haridasan- 8:10PM on March 31, 2021.**************************************/
    //This custom data structure allows the minimax algorithm to return 3 different data types
    public static class MinimaxReturnType {
        int score = 0;
        Board board = null;
        public MinimaxReturnType(int score) {
            this.score = score;
        }
        //Second version of constructor with different arguments and data storage
        public MinimaxReturnType(int score, Board board) {
            this.score = score;
            this.board = board;
        }
    }
    /*************************************Tarun Haridasan- 4:50PM on March 30, 2021.**************************************/
    //This custom data structure allows the visualize algorithm to return 2 different data types
    public static class VisualizeReturnType {
        List<int[]> regularMoves = null;
        List<Board> chainMoves = null;
        public VisualizeReturnType(List<int[]> regularMoves, List<Board> chainMoves) {
            this.regularMoves = regularMoves;
            this.chainMoves = chainMoves;
        }
    }
    /*
     ****************************************************************************************
     *************** FUNCTION WITH 2D ARRAY AS A PARAMETER AND RETURN VALUE *****************
     ****************************************************************************************
    */
    /*************************************Tarun Haridasan- 1:10PM on March 30, 2021.**************************************/
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
    /*************************************Tarun Haridasan- 4:26PM on March 30, 2021.**************************************/
    //This method visualizes all possible board outcomes for this turn.
    public static HashMap<int[], VisualizeReturnType> visualize(Board board, boolean turn) {
        HashMap<int[], VisualizeReturnType> movesForBoard = new HashMap<int[], VisualizeReturnType>();
        //Loop through all cells in the board
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                Piece piece = board.board[i][j];
                //If the cell is not empty and it is our turn to move it, then determine all possible end points
                if (piece!=null && piece.side == turn) {
                    //Visualize all regular piece locations
                    List<int[]> regMoves = piece.visualize(board);
                    //Visualize all chained moves
                    List<Board> chainMoves = piece.visualizeChain(board);
                    if (chainMoves.size()>0) chainMoves.remove(0);
                    //Generate a map where the position of the current piece is the key and an array of all its possibles moves is the value.
                    movesForBoard.put(new int[] {i, j}, new VisualizeReturnType(regMoves, chainMoves));
                }
            }
        }
        return movesForBoard;
    }
    /*************************************Tarun Haridasan- 7:50PM on March 31, 2021.**************************************/
    //This method uses the minimax algorithm to calculate the best possible piece
    public static MinimaxReturnType minimax(Board board, int depth, boolean side) {
        if (board.isGameOver()) {
            if (side) return new MinimaxReturnType(Integer.MIN_VALUE);
            else return new MinimaxReturnType(Integer.MAX_VALUE);
        }
        if (depth==0) {
            return new MinimaxReturnType(AI.score(board.board));
        }
        Board bestBoard = null;
        if (side) {
            int maximum = Integer.MIN_VALUE;
            HashMap<int[], VisualizeReturnType> possibleMoves = AI.visualize(board, true);
            for (Map.Entry<int[], VisualizeReturnType> entry : possibleMoves.entrySet()) {
                int[] key = entry.getKey();
                VisualizeReturnType value = entry.getValue();
                //Check regular moves
                for (int[] i : value.regularMoves) {
                    //Make deep clone if board
                    Board simulator = Board.CLONER.deepClone(board);
                    //Simulate the piece move
                    Piece piece = simulator.getPiece(key);
                    simulator.move(piece, i);
                    //Recursively get all the scores for its child
                    int score = minimax(simulator, depth-1, false).score;
                    //Check if this yields the maximum score, and if it does, save the piece and end point
                    maximum = Math.max(maximum, score);
                    if (maximum == score) bestBoard = simulator;
                }
                //Check chain moves
                for (Board i : value.chainMoves) {
                    int score = minimax(i, depth-1, false).score;
                    //Check if this yields the maximum score, and if it does, save the piece and end point
                    maximum = Math.max(maximum, score);
                    if (maximum == score) bestBoard = i;
                }
            }
            return new MinimaxReturnType(maximum, bestBoard);
        }
        else {
            int minimum = Integer.MAX_VALUE;
            HashMap<int[], VisualizeReturnType> possibleMoves = AI.visualize(board, false);
            for (Map.Entry<int[], VisualizeReturnType> entry : possibleMoves.entrySet()) {
                int[] key = entry.getKey();
                VisualizeReturnType value = entry.getValue();
                //Check regular moves
                for (int[] i : value.regularMoves) {
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
                        bestBoard = simulator;
                    }
                }
                //Check chain moves
                for (Board i : value.chainMoves) {
                    int score = minimax(i, depth-1, true).score;
                    //Check if this yields the maximum score, and if it does, save the piece and end point
                    minimum = Math.min(minimum, score);
                    if (minimum == score) {
                        bestBoard = i;
                    }
                }
            }
            return new MinimaxReturnType(minimum, bestBoard);
        }
    }

    /*
     ****************************************************************************************
     ********* FUNCTION WITH 2D ARRAY AS A PARAMETER AND 2D ARRAY RETURN VALUE **************
     ****************************************************************************************
    */
    /*************************************Jason Su- 4:52 PM on April 5, 2021.**************************************/
    //This method checks what AI piece has moved from 2 boards
    public static int[][] hasMoved(Piece[][] start, Piece[][] end) {
        int[] startLocation = null;
        int[] endLocation = null;
        //Loop through all cells in the board.
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                //Find start location. If start board has an X but end board at the same index has null, this is the start position
                if (start[i][j]!=null && !start[i][j].side && end[i][j]==null) startLocation = new int[] {i, j};
                //Find end location. If end board has an X but start board at the same index has null, this is the end position.
                else if (end[i][j]!=null && !end[i][j].side && start[i][j]==null) endLocation = new int[] {i, j};
                //When we find both, no need to continue the loop
                if (startLocation!=null && endLocation!=null) return new int[][] {startLocation, endLocation};
            }
        }
        return new int[][] {startLocation, endLocation};
    }
}
