import com.rits.cloning.Cloner;

public class Board {
    Piece[][] board = new Piece[8][8];

    //Game Settings
    String computerColor = null;
    String playerColor = null;
    String borderColor = null;
    char borderChar = ' ';
    String borderString = null; //BorderChar + Color
    int cellSpacing = 0; //Must be an odd number or printing will be broken
    int sideLength = 0;
    final static String FIRSTCHARS = "abcedefgh", SECONDCHARS = "12345678";
    final static Cloner CLONER = new Cloner();

    //Constructor
    public Board(String compCol, String playerCol, String borderCol, char borderChar, int cellSpacing) {
        //Get and store game settings from user
        computerColor = compCol;
        playerColor = playerCol;
        borderColor = borderCol;
        this.borderChar = borderChar;
        borderString = Screen.COLORCODES.get(borderColor)+borderChar+Screen.COLORCODES.get("RESET")+" ";
        this.cellSpacing = cellSpacing;
        sideLength = (cellSpacing*8)+9;

        //Generate the computer pieces (top of the board)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) //Alternate the placement
                    board[i][j] = new Piece(false, "X", new int[]{i, j}, computerColor);
            }
        }

        //Generate the player pieces (Bottom of the board)
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) //Alternate the placement
                    board[i][j] = new Piece(true, "O", new int[]{i, j}, playerColor);
            }
        }
    }

    //The method converts the user input to array indexes (a1 will be converted to 0,0)
    public int[] toCoords(String input) {
        char letter = input.charAt(0);
        letter = Character.toLowerCase(letter);
        char num = input.charAt(1);

        int letterInt = (int)letter;
        int numInt = (int)num;

        letterInt-=97; //Adjust the value so that it becomes zero indexed.
        num-=49;

        return new int[]{num, letterInt};
    }

    //This method gets a piece from a coordinate
    public Piece getPiece(int[] coords) {
        return board[coords[0]][coords[1]];
    }
    //This method checks all the conditions to verify if a move about to be made is valid
    public Boolean isValidMove(int[][] path, boolean turn, boolean mustKill) {
        //Check if both start and end locations are within boundary
        for (int i=0; i<path.length; i++) {
            if (path[i][0]<0 || path[i][0]>7 || path[i][1]<0 || path[i][1]>7)
                return false;
        }

        //Check if piece even exists at the start locations
        int[] start = path[0];
        if (board[start[0]][start[1]]==null) return false;

        //Check if piece is owned by the player who is making the turn (True = player, and False = Computer)
        if (board[start[0]][start[1]].side!=turn) return false;

        //Check if the place to move to is empty
        int[] end = path[1];
        if (board[end[0]][end[1]]!=null) return false;

        //Non-attack (1 diagonal space moved) and Attack moves (2 diagonal space moved)
        int horizontalDistance =  end[1]-start[1];
        int verticalDistance = end[0]-start[0];
        boolean isKing = getPiece(path[0]).isKing;
        //Regular 1-diagonal space move
        if (Math.abs(horizontalDistance)==1 && Math.abs(verticalDistance)==1 && mustKill==false) {
            if ((verticalDistance==1 && turn==true && !isKing) || verticalDistance==-1 && turn==false && !isKing) return false;
            return true;
        }
        //Attack 2-diagonal space move
        else if (Math.abs(horizontalDistance)==2 && Math.abs(verticalDistance)==2){
            if ((verticalDistance==2 && turn==true && !isKing) || verticalDistance==-2 && turn==false && !isKing) return false;
            Piece attackPiece = board[start[0]+(verticalDistance/2)][start[1]+(horizontalDistance/2)];
            if (attackPiece==null) return false;
            if (attackPiece.side==turn) return false;
            return true;
        }

        //Anything other than 1 or 2 diagonal moves
        return false;
    }

    //This method relocates a piece on the board
    public void move(Piece piece, int[] end) {
        int[] start = piece.pos;
        int verticalDistance = end[0]-start[0];
        int horizontalDistance = end[1] - start[1];
        //If its an attack move, remove the piece to be attacked from array
        if (Math.abs(verticalDistance)==2 && Math.abs(horizontalDistance)==2) {
            board[start[0]+(verticalDistance/2)][start[1]+(horizontalDistance/2)] = null;
        }
        //Update the pieces position to end location
        piece.pos = end;
        //In the game board array, set the end index to have the piece and the start index to an empty cell.
        board[end[0]][end[1]] = piece;
        board[start[0]][start[1]] = null;
        //Check if the piece should be promoted to king
        if (piece.side && piece.pos[0]==0 || !piece.side && piece.pos[0]==7) {
            piece.promote();
        }
    }

    //This method checks if all of one side's piece are dead or if one team cannot make any moves to determine if the game is over.
    public Boolean isGameOver() {
        boolean isFoundX = false;
        boolean isFoundO = false;
        //Loop through all board cells
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                //Keep track of what types of pieces we have seen so far
                Piece piece = getPiece(new int[] {i, j});
                if (piece==null) continue;
                if (board[i][j].side && piece.visualize(this).size()!=0) isFoundO=true;
                else if (!board[i][j].side && piece.visualize(this).size()!=0) isFoundX=true;
                //If both types of pieces have been seen at least once, then the game is not over yet
                if (isFoundO && isFoundX) return false;
            }
        }
        return true;
    }

    //This method checks if the input coordinates the user enters is valid and in the correct format
    public static Boolean isInputValid(String[] input) {
        //Check if the user only entered 1 value
        if (input.length<=1) {
            Screen.println("You must enter more than just one set of coordinates! You may wish to re-read the help page.");
            return false;
        }
        //Check the formatting of each coordinate in array
        for (String current : input) {
            //Check the length of each string
            if (current.length()!=2) {
                Screen.println("Invalid coordinates! Each pair must consist of 2 characters. ");
                return false;
            }
            //Check if the first character is a letter and the second is a number
            if(!(Board.FIRSTCHARS.contains(Character.toString(current.charAt((0))))) || !(Board.SECONDCHARS.contains(Character.toString(current.charAt((1)))))) {
                Screen.println("Invalid coordinates! The first character must be a character between a and h, and the second character must be a number from 1 to 8");
                return false;
            }
        }
        //If all checks have passed, then this input string must be valid
        return true;
    }
}
