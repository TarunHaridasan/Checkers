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
*/
public class TitleScreen {
    // Class variables.
    public static String username = "";
    private final static Scanner input = new Scanner(System.in);
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
        }
        //If the user enters 0, then go back to the main menu.
        if(code == 0) mainMenu();
        //Load the saved game...
    }
    //About game.
    public static void about() {
        Screen.print("About the game...");
    }
    //Help menu.
    public static void help() {
        Screen.print("Needing help...");
    }
    //Choices handler interface.
    public static interface Choices {
        void run() throws IOException, InterruptedException;
    }
    //Choices array.
    public static Choices[] choices = new Choices[] {
        new Choices() {public void run() { play(); } },
        new Choices() {public void run() throws IOException, InterruptedException { loadOpt(); } },
        new Choices() {public void run() { about(); } },
        new Choices() {public void run() { help(); } },
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

