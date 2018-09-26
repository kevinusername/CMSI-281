package web_nav;

import java.util.Scanner;
import java.util.Stack;

public class WebNavigator {

    // Fields
    private String current; // Tracks currently visited site
    private Stack<String> history; // Tracks previously visited sites
    private Stack<String> future; // Tracks sites sites that have gone "back" from

    // Constructor
    WebNavigator() {
        history = new Stack<>();
        future  = new Stack<>();
        current = null;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        WebNavigator navi = new WebNavigator();

        System.out.println("Welcome to web_nav, enter a command from your web_nav user manual!");
        while (navi.getNextUserCommand(input)) {
        }
        System.out.println("Goodbye!");
    }

    // Methods
    // [!] YOU DO NOT HAVE TO MODIFY THIS METHOD FOR YOUR SOLUTION
    public boolean getNextUserCommand(Scanner input) {
        String command = input.nextLine();
        String[] parsedCommand = command.split(" ");

        // Switch on the command (issued first in input line)
        switch (parsedCommand[0]) {
            case "exit":
                System.out.println("Goodbye!");
                return false;
            case "visit":
                visit(parsedCommand[1]);
                break;
            case "back":
                back();
                break;
            case "forward":
                forw();
                break;
            default:
                System.out.println("[X] Invalid command, try again");
        }

        System.out.println("Currently Visiting: " + current);

        return true;
    }

    /*
     *  Visits the current site, clears the forward history,
     *  and records the visited site in the back history
     */
    public void visit(String site) {
        if (current != null) {
            history.push(current);
        }
        current = site;
        future.clear();
    }

    /*
     *  Changes the current site to the one that was last
     *  visited in the order on which visit was called on it
     */
    public void back() {
        if (!history.empty()) {
            future.push(current);
            current = history.pop();
        }
    }

    public void forw() {
        if (!future.empty()) {
            history.push(current);
            current = future.pop();
        }
    }

}
