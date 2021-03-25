public class Piece {
    //Declare variables
    Boolean side = false; //False = enemy, True = Player
    String icon = null;
    String color = null;
    int[] pos = new int[2];
    Boolean isKing = false;
    Boolean isAlive = true;

    //Constructor
    public Piece(Boolean side, String icon, int[] pos, String color) {
        this.side = side;
        this.icon = icon;
        this.pos = pos;
        this.color = color;
    }

    //This method is used to promote the piece to a king
    public void promote() {
        isKing = true;
    }

    //This method changes the position of the piece
    public void updatePos(int[] end) {
        pos = end;
    }

    //This method kills a piece
    public void kill() {
        isAlive = true;
    }


}
