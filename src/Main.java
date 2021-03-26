public class Main {
    public static void main(String[] args) {
        //Testing
        int[] pos = {1, 2};
        Piece piece = new Piece(true, "X", pos, "red");
        int[] newPos = {1,1};

        //Using methods
        System.out.println(piece.pos[1]);
        piece.updatePos(newPos);
        System.out.println(piece.pos[1]);

    }
}
