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
    public static String compColour = "WHITE", playerColour = "YELLOW", borderColour = "PURPLE", borderChar = ".";
    public static int cellSpacing = 1;
    //Choice functions.
    //Play game.
    public static void play() {
        Screen.print("Playing the game...");
    }
    //Loading game.
    public static void loadOpt() throws IOException, InterruptedException {
        //Prints out the title of the game.
        for(int i = 0; i < 2; i++) Screen.println("");
        Screen.printFromFile("./ASCII/name.txt");
        int code = 0;
        //Asking the user for a special code given to them when they saved their game.
        Screen.print("Enter your code (0 to go back): ");
        try {
            code = Integer.parseInt(input.nextLine());
        }
        catch(NumberFormatException err) {
            //If the user enters something that cannot be parsed into an integer.
            Screen.println("");
            Screen.println("There was an unknown error... Please try again!");
            Thread.sleep(1000);
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
        //Printing out the settings menu.
        Screen.printFromFile("./ASCII/settings.txt");
        //Asking for input
        Screen.print("Input:");
        int setting = input.nextInt();
        //Choosing input
        if (setting == 1 || setting == 2){
            //Changing Colour
            Screen.print("Enter Colour:");
            String colour = input.nextLine();
            Screen.println(colour);
            //Checking to see if colour is valid
            if (colour.equals("BLACK") || colour.equals("RED") || colour.equals("GREEN") || colour.equals("YELLOW") || colour.equals("BLUE") || colour.equals("PURPLE") || colour.equals("CYAN") || colour.equals("WHITE")) {
                Screen.println("Colour Changed.");
            }
            else {
                Screen.println("Please Choose a Valid Colour: Black, Red, Green, Yellow, Blue, Purple, Cyan, White, or Reset");
                colour = input.nextLine(); //******************NEEDS TO KEEP LOOPING UNTIL CORRECT COLOR IS ENTERED***************************//
            }
        }
        if (setting == 3){
            Screen.print("Enter a New Padding:");
            int padding = input.nextInt();
            while (padding % 2 == 0){
                Screen.print("Enter a New Padding It Must Be An Odd Number:");
                padding = input.nextInt();
            }
            Screen.print("Padding Has Been Changed To: "+padding);
        }
        if (setting == 4){
            Screen.print("Enter a Border Character:");
            String border = input.next();
            while (border.length() > 1){
                Screen.println("Border is too long.");
                Screen.println("Enter a Border Character:");
                border = input.next();
            }
            Screen.print("Border Has Been Changed To: " + "'"+ border + "'");
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
        Screen.print("Enter your name: ");
        username = input.nextLine();
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
        Screen.print("Input: ");
        try {
            userInput = Integer.parseInt(input.nextLine());
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
