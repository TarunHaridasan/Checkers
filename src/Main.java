public class Main {
    public static void main(String[] args) {
        //Make board
        Board board = new Board("WHITE", "YELLOW", "PURPLE", ',', 5);

        //Print board onto screen
        Screen.printBoard(board);

        Screen.refresh(board, 20);

        Screen.clear(20);
    }
}
