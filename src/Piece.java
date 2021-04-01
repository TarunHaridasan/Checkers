import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Piece {
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

    //This method is used to promote the piece to a king
    public void promote() {
        isKing = true;
        if (side) icon = "@"; //O king
        else icon = "!"; //X king
    }

    //This method changes the position of the piece
    public void updatePos(int[] end) {
        pos = end;
    }

    //This method kills a piece
    public void kill() {
        isAlive = true;
    }

    //This method generates all the possible locations this piece can move to (This will be used for the AI)
    public Integer[][] visualize(Board board) {
        List<Integer[]> moves = new ArrayList<Integer[]>();

        int directionMultiplier = 1;
        if (side) directionMultiplier = -1;

        //Try regular piece, regular move (INTEGRATE FOR KING)
        int x = pos[0] + directionMultiplier;
        for (int i=-1; i<=1; i+=2) {
            int y = pos[1] + i;
            if (board.isValidMove(new int[][] {pos, {x, y}}, side, false))
                moves.add(new Integer[] {x, y});
        }

        //Try regular piece, attack move
        x = pos[0] + (directionMultiplier*2);
        for (int i=-2; i<=2; i+=4) {
            int y = pos[1]+i;
            if (board.isValidMove(new int[][] {pos, {x,y}}, side, false))
                moves.add(new Integer[] {x,y});
        }

        Integer[][] newMoves= new Integer[moves.size()][2];
        moves.toArray(newMoves);
        return newMoves;
    }

    @Override
    public String toString() {
        return icon;
    }
}
