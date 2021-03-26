public class Board {
    //Declare variables
    Piece[][] board = new Piece[8][8];
    final String COMPUTERCOLOR = "red";
    final String PLAYERCOLOR = "yellow";

    //Constructor
    public Board() {
        /*
        //Generate the computer pieces (top of the board)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) //Alternate the placement
                    board[i][j] = new Piece(false, "X", new int[]{i, j}, COMPUTERCOLOR);
            }
        }
        //Generate the player pieces (Bottom of the board)
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) //Alternate the placement
                    board[i][j] = new Piece(true, "O", new int[]{i, j}, PLAYERCOLOR);
            }
        }
        */
        board[3][2] = new Piece(false, "X", new int[]{3, 2}, COMPUTERCOLOR);
        board[4][3] = new Piece(true, "O", new int[]{4, 3}, PLAYERCOLOR);
    }

    //The method converts the user input to array indexes (a1 will be converted to 0,0)
    public static int[] toCoors(String input) {
        char letter = input.charAt(0);
        letter = Character.toLowerCase(letter);
        char num = input.charAt(1);

        int letterInt = (int)letter;
        int numInt = (int)num;

        letterInt-=97; //Adjust the value so that it becomes zero indexed.
        num-=49;

        return new int[]{letterInt, num};
    }

    //This method gets a piece from a coordinate
    public Piece getPiece(int[] coords) {
        return board[coords[0]][coords[1]];
    }

    //This method checks all the conditions to verify if a move about to be made is valid
    public Boolean isValidMove(int[][] path, boolean turn) {
        //Check if piece even exists at the start locations
        int[] start = path[0];
        if (board[start[0]][start[1]]==null) return false;

        //Check if piece is owned by the player who is making the turn (True = player and False = Computer)
        if (board[start[0]][start[1]].side!=turn) return false;

        //Check if the place to move to is empty
        int[] end = path[1];
        if (board[end[0]][end[1]]!=null) return false;

        //Non-attack (1 diagonal space moved) and Attack moves (2 diagonal space moved)
        int horizontalDistance =  end[1]-start[1];
        int verticalDistance = end[0]-start[0];
        if (Math.abs(horizontalDistance)==1 && Math.abs(verticalDistance)==1) {
            if (horizontalDistance!=1) return false;
            else if ((verticalDistance!=-1 && turn==true) || verticalDistance!=1 && turn==false) return false;
        }
        else {
            if (Math.abs(horizontalDistance)!=2) return false;
            else if ((verticalDistance!=-2 && turn==true) || verticalDistance!=2 && turn==false) return false;
            Piece attackPiece = board[start[0]+(verticalDistance/2)][start[1]+(horizontalDistance/2)];
            if (attackPiece==null) return false;
        }

        //All checks passed means it is a valid move
        return true;
    }
}
