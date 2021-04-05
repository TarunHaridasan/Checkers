/*
    Tarun Haridasan, Fahad Mateen, Jason Su
    04/6/2021
    Piece.java
    This java file contains the Piece class which will be used to initialize each checker in the game.
 */

import java.util.ArrayList;
import java.util.List;
public class Piece {
    /*************************************Jason Su- 2:00PM on March 26, 2021**************************************/
    //Declare variables
    Boolean side = false; //False = enemy, True = Player
    String icon = null;
    String color = null;
    int[] pos = new int[2];
    Boolean isKing = false;
    Boolean isAlive = true;
    String id = null;

    //Constructor
    public Piece(Boolean side, String icon, int[] pos, String color) {
        this.side = side;
        this.icon = icon;
        this.pos = pos;
        this.color = color;
        this.id = pos[0]+""+pos[1];
    }

    /*************************************Fahad Mateen- 5:45PM on March 26, 2021**************************************/
    //This method is used to promote the piece to a king
    public void promote() {
        isKing = true;
        if (side) icon = "\uD83C\uDD7E"; //O king
        else icon = "\uD83C\uDD87"; //X king
    }

    /*************************************Tarun Haridasan- 11:56AM on March 27, 2021**************************************/
    //This method generates all the possible locations this piece  can move to (This will be used for the AI)
    public List<int[]> visualize(Board board) {
        List<int[]> moves = new ArrayList<int[]>();

        int directionMultiplier = 1;
        if (side) directionMultiplier = -1;

        //Regular move
        int x = pos[0] + directionMultiplier;
        for (int i=-1; i<=1; i+=2) {
            int y = pos[1] + i;
            if (board.isValidMove(new int[][] {pos, {x, y}}, side, false))
                moves.add(new int[] {x, y});
        }

        //Attack move
        x = pos[0] + (directionMultiplier*2);
        for (int i=-2; i<=2; i+=4) {
            int y = pos[1]+i;
            if (board.isValidMove(new int[][] {pos, {x,y}}, side, false)) moves.add(new int[] {x,y});
        }

        //If king, allow backwards moves
        if (isKing) {
            //Try regular piece, regular move
            x = pos[0] + (directionMultiplier*-1);
            for (int i=-1; i<=1; i+=2) {
                int y = pos[1] + i;
                if (board.isValidMove(new int[][] {pos, {x, y}}, side, false))
                    moves.add(new int[] {x, y});
            }

            //Try regular piece, attack move
            x = pos[0] + (directionMultiplier*-2);
            for (int i=-2; i<=2; i+=4) {
                int y = pos[1]+i;
                if (board.isValidMove(new int[][] {pos, {x,y}}, side, false))
                    moves.add(new int[] {x,y});
            }
        }

        return moves;
    }

    /*************************************Tarun Haridasan- 1:25AM on April 3, 2021**************************************/
    //This recursive method is used to calculate all the possible chain moves for a piece
    public List<Board> visualizeChain(Board board) {
        List<Board> moves = new ArrayList<>();
        int directionMultiplier = 1;
        if (side) directionMultiplier = -1;
        //Attack move - regular
        int x = this.pos[0] + (directionMultiplier*2);
        for (int i=-2; i<=2; i+=4) {
            int y = this.pos[1]+i;
            if (board.isValidMove(new int[][] {this.pos, {x,y}}, this.side, true)) {
               Board simulatorBoard = Board.CLONER.deepClone(board);
               Piece simulatorPiece = simulatorBoard.getPiece(this.pos);
               int[] endPoint = {x, y};
               simulatorBoard.move(simulatorPiece, endPoint);
               moves.add(simulatorBoard);
               moves.addAll(simulatorPiece.visualizeChain(simulatorBoard));
            }
        }
        //Attack move- king
        x = this.pos[0] + (directionMultiplier*-2);
        for (int i=-2; i<=2; i+=4) {
            int y = this.pos[1]+i;
            if (board.isValidMove(new int[][] {this.pos, {x,y}}, this.side, true)) {
                Board simulatorBoard = Board.CLONER.deepClone(board);
                Piece simulatorPiece = simulatorBoard.getPiece(this.pos);
                int[] endPoint = {x, y};
                simulatorBoard.move(simulatorPiece, endPoint);
                moves.add(simulatorBoard);
                moves.addAll(simulatorPiece.visualizeChain(simulatorBoard));
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        return icon;
    }
}
