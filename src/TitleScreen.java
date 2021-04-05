/*
  Jason Su, Tarun Haridasan, Fahad Mateen
  04/06/2021
  Title Screen Code
  This java file contains the code for the title screen of our checkers game. The title screen is used to navigate to all the different screens, like help, about, load, and play, when the game launches
*/
import java.io.*;
import java.util.*;
/*************************************Jason Su- 10:21AM on March 25, 2021.**************************************/
public class TitleScreen {
    // Class variables.
    public static String username = "";
    private final static Scanner input = new Scanner(System.in);
    public static String compColour = "BLUE", playerColour = "RED", borderColour = "GREEN";
    public static char borderChar = '.';
    public static int cellSpacing = 1;
    public static Boolean turn = true;
    public static int difficulty = 0;
    public static Piece[][] loadedBoard = null;
    //Choice functions.
    //Play game.
    public static void play() {
        Screen.println("Playing the game...");
    }
    //Loading game.
    public static void loadOpt() throws IOException, InterruptedException {
        //Prints out the title of the game.
        for(int i = 0; i < 2; i++) Screen.println("");
        Screen.printFromFile("./ASCII/name.txt");
        int code = 0;
        //Asking the user for a special code given to them when they saved their game.
        try {
            code = Integer.parseInt(Screen.prompt("Enter your code (0 to go back): "));
        }
        catch(NumberFormatException err) {
            //If the user enters something that cannot be parsed into an integer.
            Screen.println("");
            Screen.println("There was an unknown error... Please try again!");
            //Going back to the load menu.
            loadOpt();
            return;
        }
        //If the user enters 0, then go back to the main menu.
        if(code == 0) {
            mainMenu();
            return;
        }
        //Load the saved game...
        String data = readSave(code);
        if(data.isEmpty()) return;
        String[] rows = data.split("\r\n");
        //Setting the username and difficulty values.
        String[] userData = rows[0].split(" ");
        TitleScreen.username = userData[0];
        TitleScreen.difficulty = Integer.parseInt(userData[1]);
        //Recreating the board.
        TitleScreen.loadedBoard = new Piece[8][8];
        String[] boardString = Arrays.copyOfRange(rows, 1, rows.length);
        for(int i = 0; i < boardString.length; i++) {
            //Creating a temporary array of each piece.
            String[] temp = boardString[i].split(" ");
            //Deciding what to add to the board array.
            for(int j = 0; j < temp.length; j++) {
                switch (temp[j]) {
                    case "O":
                        TitleScreen.loadedBoard[i][j] = null;
                        break;
                    case "P": {
                        int[] pos = {i, j};
                        Piece tempPiece = new Piece(true, "O", pos, TitleScreen.playerColour);
                        TitleScreen.loadedBoard[i][j] = tempPiece;
                        break;
                    }
                    case "C": {
                        int[] pos = {i, j};
                        Piece tempPiece = new Piece(false, "X", pos, TitleScreen.compColour);
                        TitleScreen.loadedBoard[i][j] = tempPiece;
                        break;
                    }
                    case "K": {
                        int[] pos = {i, j};
                        Piece tempPiece = new Piece(true, "\uD83C\uDD7E", pos, TitleScreen.playerColour);
                        tempPiece.isKing = true;
                        TitleScreen.loadedBoard[i][j] = tempPiece;
                        break;
                    }
                    default: {
                        int[] pos = {i, j};
                        Piece tempPiece = new Piece(false, "\uD83C\uDD87", pos, TitleScreen.compColour);
                        tempPiece.isKing = true;
                        TitleScreen.loadedBoard[i][j] = tempPiece;
                        break;
                    }
                }
            }
        }
        TitleScreen.play();
    }
    /*************************************Fahad Mateen- 10:21PM on March 25, 2021.**************************************/
    public static String readSave(int code) throws IOException, InterruptedException {
        //Variables
        String filePath = "saves/" +code+".txt";
        BufferedReader file = null;
        //Attempting to create buffered reader from provided file name.
        try {
            file = new BufferedReader(new FileReader(filePath));
        }
        catch(FileNotFoundException err) {
            //If the code was not found.
            Screen.println("");
            Screen.println("That is an invalid code. Please try again.");
            //Return to the load menu.
            Thread.sleep(1000);
            loadOpt();
            return "";
        }
        //Creating a string builder for building the contents of the file.
        StringBuilder data = new StringBuilder(file.readLine());
        //String array for rows of the board.
        String[][] boardFile = new String[8][8];
        String line = file.readLine();
        //Looping through the entire file and concatenating a data string.
        while (line != null) {
            data.append("\r\n").append(line);
            line = file.readLine();
        }
        //Returning the data object as a string.
        return data.toString();
    }
    /*************************************Fahad Mateen- 11:10PM on March 25, 2021.**************************************/
    //About game.
    public static void about() throws IOException, InterruptedException {
        //Prints out the title of the game.
        for(int i = 0; i < 2; i++) Screen.println("");
        Screen.printFromFile("./ASCII/name.txt");
        //Printing out the about menu.
        Screen.printFromFile("./ASCII/about.txt");
        //Waiting for user's input.
        input.nextLine();
        //Going back to the main menu.
        mainMenu();
    }
    /*************************************Fahad Mateen- 4:05PM on March 26, 2021.**************************************/
    //Help menu.
    public static void help() throws IOException, InterruptedException {
        //Prints out the title of the game.
        for(int i = 0; i < 2; i++) Screen.println("");
        Screen.printFromFile("./ASCII/name.txt");
        //Printing out the about menu.
        Screen.printFromFile("./ASCII/help.txt");
        //Waiting for user's input.
        input.nextLine();
        //Going back to the main menu.
        mainMenu();
    }
    /*************************************Fahad Mateen and Jason Su optimized - 6:10PM on March 26, 2021.**************************************/
    //Settings menu.
    public static void settings() throws IOException, InterruptedException {
        //Prints out the title of the game.
        for(int i = 0; i < 2; i++) Screen.println("");
        Screen.printFromFile("./ASCII/name.txt");
        //Printing out the settings page.
        Screen.println("==============================================");
        Screen.println("1. Computer Colour: " + TitleScreen.compColour);
        Screen.println("");
        Screen.println("2. Player Colour: " + TitleScreen.playerColour);
        Screen.println("");
        Screen.println("3. Border Colour: " + TitleScreen.borderColour);
        Screen.println("");
        Screen.println("4. Cell Padding: " + TitleScreen.cellSpacing);
        Screen.println("");
        Screen.println("5. Border Character: '" + TitleScreen.borderChar + "'");
        Screen.println("");
        Screen.println("Enter 0 to go back.");
        Screen.println("==============================================");
        Screen.println("");
        //Asking for input
        int setting = 0;
        //Getting the user's input.
        try {
            setting = Integer.parseInt(Screen.prompt("Input: "));
        } catch(NumberFormatException err) {
            //If the input cannot be converted into an integer, go back to the settings page.
            Screen.println("That is an invalid option... Please try again!");
            Screen.println("");
            Thread.sleep(1000);
            settings();
            return;
        }
        //Checking if the number is within the range.
        if(setting == 0) {
            //If the input cannot be converted into an integer, go back to the settings page.
            Screen.println("Returning to the main menu...");
            Screen.println("");
            Thread.sleep(1000);
            mainMenu();
            return;
        }
        else if(setting < 1 || setting > 5) {
            //If the input is out of range, go back to the settings page.
            Screen.println("That is an invalid option... Please try again!");
            Screen.println("");
            Thread.sleep(1000);
            settings();
            return;
        }
        //Processing input
        if (setting == 1 || setting == 2 || setting == 3) {
            //Variables
            String[] colours = {"BLACK", "RED", "GREEN", "YELLOW", "BLUE", "PURPLE", "CYAN", "WHITE"};
            //Changing Colour
            Screen.printFromFile("./ASCII/colours.txt");
            //Asking for input
            int colourChoice = 0;
            //Getting the user's input.
            try {
                colourChoice = Integer.parseInt(Screen.prompt("Input: "));
            } catch(NumberFormatException err) {
                //If the input cannot be converted into an integer, go back to the settings page.
                Screen.println("That is an invalid option... Please try again!");
                Screen.println("");
                Thread.sleep(1000);
                settings();
                return;
            }
            //If the input is 0, go back to settings page.
            if(colourChoice == 0) {
                //If the input is 0, go back to settings page.
                Screen.println("Returning to settings...");
                Screen.println("");
                Thread.sleep(1000);
                settings();
                return;
            }
            //Checking if the number is within the range.
            if(colourChoice < 1 || colourChoice > 8) {
                //If the input is out of range, go back to the settings page.
                Screen.println("That is an invalid option... Please try again!");
                Screen.println("");
                Thread.sleep(1000);
                settings();
                return;
            }
            //Settings the proper variables to the proper values.
            if(setting == 1) TitleScreen.compColour = colours[colourChoice-1];
            else if(setting == 2) TitleScreen.playerColour = colours[colourChoice-1];
            else TitleScreen.borderColour = colours[colourChoice-1];
            //Returning to the settings page.
            settings();
        }
        else if (setting == 4){
            //Temporary variable for padding.
            int padding = 0;
            //Getting the user's input.
            Screen.printFromFile("./ASCII/padding.txt");
            try {
                padding = Integer.parseInt(Screen.prompt("Input: "));
            } catch(NumberFormatException err) {
                //If the input cannot be converted into an integer, go back to the settings page.
                Screen.println("That is an invalid option... Please try again!");
                Screen.println("");
                Thread.sleep(1000);
                settings();
                return;
            }
            //If the input is 0, go back to settings page.
            if(padding == 0) {
                //If the input is 0, go back to settings page.
                Screen.println("Returning to settings...");
                Screen.println("");
                Thread.sleep(1000);
                settings();
                return;
            }
            //If the number is out of the range.
            if(padding < 1 || padding > 9) {
                //If the input is out of the range, go back to the settings page.
                Screen.println("That is an invalid option... Please try again!");
                Screen.println("");
                Thread.sleep(1000);
                settings();
                return;
            }
            //If the number is even.
            if(padding % 2 == 0) {
                //If the input is out of the range, go back to the settings page.
                Screen.println("That is an invalid option... Input MUST be an odd number. Please try again!");
                Screen.println("");
                Thread.sleep(1000);
                settings();
                return;
            }
            TitleScreen.cellSpacing = padding;
            settings();
        }
        else {
            //Printing a message.
            Screen.printFromFile("./ASCII/border.txt");
            //Asking for input and assigning the border character value.
            TitleScreen.borderChar = Screen.prompt("Input: ").charAt(0);
            //Going back to the settings screen.
            settings();
        }
    }
    /*************************************Fahad Mateen- 10:46AM on April 2, 2021.**************************************/
    public static int difficulty() throws IOException, InterruptedException {
        //Asking for difficulty.
        Screen.printFromFile("./ASCII/difficulties.txt");
        //Getting the user's input.
        int difficulty = 0;
        try {
            difficulty = Integer.parseInt(Screen.prompt("Input: "));
        } catch(NumberFormatException err) {
            //If the input cannot be converted into an integer, go back to the settings page.
            Screen.println("That is an invalid option... Please try again!");
            Screen.println("");
            Thread.sleep(1000);
            return difficulty();
        }
        if(difficulty < 1 || difficulty > 4) {
            //If the input is out of range, go back to the settings page.
            Screen.println("That is an invalid option... Please try again!");
            Screen.println("");
            Thread.sleep(1000);
            return difficulty();
        }
        return difficulty;
    }
    /*************************************Jason Su- 2:10PM on March 26, 2021.**************************************/
    //Choices handler interface.
    public static interface Choices {
        void run() throws IOException, InterruptedException;
    }
    //Choices array.
    public static Choices[] choices = new Choices[] {
        new Choices() {public void run() { play(); } },
        new Choices() {public void run() throws IOException, InterruptedException { loadOpt(); } },
        new Choices() {public void run() throws IOException, InterruptedException { about(); } },
        new Choices() {public void run() throws IOException, InterruptedException { help(); } },
            new Choices() {public void run() throws IOException, InterruptedException { settings(); } }
    };
    //Initializing the title screen.
    public static void initialize() throws IOException, InterruptedException {
        //Prints out the title of the game.
        for(int i = 0; i < 2; i++) Screen.println("");
        Screen.printFromFile("./ASCII/name.txt");
        //Getting username.
        username = Screen.prompt("Enter your name: ");
        //Calling the main menu method.
        TitleScreen.loadedBoard = null;
        TitleScreen.difficulty = 0;
        mainMenu();
    }
    //Printing the main menu.
    public static void mainMenu() throws IOException, InterruptedException {
        //Prints out the title of the game.
        for(int i = 0; i < 2; i++) Screen.println("");
        Screen.printFromFile("./ASCII/name.txt");
        //Printing out the menu.
        Screen.printFromFile("./ASCII/menu.txt");
        //Calling the input handling function...
        input();
    }
    /*************************************Jason Su- 4:00PM on March 26, 2021.**************************************/
    public static void input() throws IOException, InterruptedException {
        int userInput = 0;
        //Asking for user input.
        Screen.println("");
        try {
            userInput = Integer.parseInt(Screen.prompt("Input: "));
        }
        catch(NumberFormatException err) {
            //If the user enters something that cannot be parsed into an integer.
            Screen.println("");
            Screen.println("There was an unknown error... Please try again!");
            Thread.sleep(1000);
            //Going back to the main menu.
            mainMenu();
            return;
        }
        Screen.println("");
        //Subtracting 1 from the user's choice to convert it to an index value.
        userInput--;
        //Processing choice...
        if(!handleChoice(userInput)) {
            Screen.println("Invalid option! Try again.");
            Thread.sleep(1000);
            //Going back to the main menu.
            Thread.sleep(1000);
            mainMenu();
        }
    }
    /*************************************Jason Su and Fahad- 12: 04PM on March 26, 2021.**************************************/
    //User input handling.
    public static boolean handleChoice(int userInput) throws IOException, InterruptedException {
        // Validating the user's input.
        if((userInput < 0) || (userInput >= choices.length)) return false;
        //Running the function stored in the array at the index of the user's index choice.
        choices[userInput].run();
        Thread.sleep(1000);
        return true;
    }
}
