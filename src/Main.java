import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //Testing
        String[][] board = {
            {"o", "O", "x", "X", "X", "O", "X", "O"},
            {"X", "O", "X", "O", "X", "O", "X", "O"},
            {"X", "O", "X", "O", "X", "O", "X", "O"},
            {"X", "O", "X", "O", "X", "O", "X", "O"},
            {"X", "O", "X", "O", "X", "O", "X", "O"},
            {"X", "O", "X", "O", "X", "O", "X", "O"},
            {"X", "O", "X", "O", "X", "O", "X", "O"},
            {"X", "O", "X", "O", "X", "O", "X", "O"}
        };
        Screen screen = new Screen();

        //Print the board
        screen.printBoard(board);
        //screen.refresh(board, 20);

        //Print ASCII Art
        //screen.printFromFile("./art/test.txt");
    }
}
