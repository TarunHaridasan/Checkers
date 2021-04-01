/*
  Jason Su, Tarun Haridasan, Fahad Mateen
  04/06/2021
  Title Screen Code
  This class file contains the code for the title screen of our checkers game.
  Each members' contribution to the project will be noted in this comments above the methods.
*/
import java.io.*;
import java.util.*;
/*
    10:21PM on March 25, 2021.
    Jason Su finished up the template for the title screen with a placeholder for each required method.
    3:35PM on March 26, 2021.
    Jason Su finished the about method for the title screen and optimized the load game method.
    3:50PM on March 26, 2021.
    Jason Su finished the help method for the title screen.
*/
public class TitleScreen {
    // Class variables.
    public static String username = "";
    private final static Scanner input = new Scanner(System.in);
    public static String compColour = "BLUE", playerColour = "RED", borderColour = "GREEN";
    public static char borderChar = '.';
    public static int cellSpacing = 1;
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
            //Going back to the main menu.
            loadOpt();
            return;
        }
        //If the user enters 0, then go back to the main menu.
        if(code == 0) {
            mainMenu();
            return;
        }
        //Load the saved game...
        Screen.println(load(code));
    }
    /*
        <->OVERALL EDIT HISTORY<->
        10:21PM on March 25, 2021.
        Fahad Mateen made the load function to read and get stuff from the file cause im a legend.
        2:50PM on March 26, 2021.
        Jason Su added comments and error handling to the code block below.
        3:31PM on March 28, 2021.
        Jason Su added compatibility for a settings page and added the empty boiler functions for Fahad.
    */
    public static String load(int code) throws IOException, InterruptedException {
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
            //Return to the main menu.
            Thread.sleep(1000);
            mainMenu();
            return "";
        }
        //Creating a string builder for building the contents of the file.
        StringBuilder data = new StringBuilder(file.readLine());
        String line = file.readLine();
        //Looping through the entire file and concatenating a data string.
        while (line != null) {
            data.append("\n").append(line);
            line = file.readLine();
        }
        //Returning the data object as a string.
        return data.toString();
    }
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
            Thread.sleep(1000);
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
