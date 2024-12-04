package aoc;

import aoc.common.Day;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";

    public static void main(String[] args) {
        printChristmasTree();

        boolean isDevMode = isDevMode();

        if (isDevMode) {
            devMode();
        } else {
            visitorMode();
        }
    }

    private static void printChristmasTree() {
        System.out.println(GREEN +
                "        *        " + RESET + RED + "   ~ Advent of Code ~" + RESET);
        System.out.println(GREEN +
                "       ***       ");
        System.out.println("      *****      ");
        System.out.println("     *******     ");
        System.out.println("    *********    ");
        System.out.println("       ***       ");
        System.out.println(RED +
                "       ***       " + RESET + GREEN + "   Merry Coding!" + RESET);
        System.out.println();
    }

    private static boolean isDevMode() {
        System.out.print(GREEN + "Are you Feli? " + RED + "(yes/no): " + RESET + BLUE);
        String response = scanner.nextLine().trim().toLowerCase();
        System.out.print(RESET);
        return response.equals("yes") || response.equals("y");
    }

    private static void devMode() {
        System.out.println(GREEN + "Dev-Mode activated! 🎄" + RESET);
        boolean running = true;

        while (running) {
            System.out.println(RED + "1: Fetch new puzzle input" + RESET);
            System.out.println(RED + "2: Quit" + RESET);
            System.out.print(GREEN + "Select an option: " + RESET + BLUE);
            String choice = scanner.nextLine().trim();
            System.out.print(RESET);

            switch (choice) {
                case "1":
                    fetchPuzzleInput();
                    break;
                case "2":
                    running = false;
                    break;
                default:
                    System.out.println(RED + "Invalid option. Try again." + RESET);
            }
        }
        System.out.println(GREEN + "Exiting Dev-Mode. Goodbye!" + RESET);
    }

    private static void fetchPuzzleInput() {
        System.out.println(GREEN + "Fetching new puzzle input... (Feature coming soon!)" + RESET);
    }

    private static void visitorMode() {
        System.out.println(GREEN + "Welcome to Advent of Code!" + RESET);
        boolean running = true;
        String year = "2024";
        boolean testMode = false;

        while (running) {
            System.out.println(GREEN + "Main Menu:" + RESET);
            System.out.println(RED + "1: Set Year (Current: " + year + ")" + RESET);
            System.out.println(RED + "2: Set Test-Mode (Current: " + (testMode ? "Enabled" : "Disabled") + ")" + RESET);
            System.out.println(RED + "3: Enter Main Loop" + RESET);
            System.out.println(RED + "4: Quit" + RESET);
            System.out.print(GREEN + "Select an option: " + RESET + BLUE);
            String choice = scanner.nextLine().trim();
            System.out.print(RESET);

            switch (choice) {
                case "1":
                    year = setYear();
                    break;
                case "2":
                    testMode = setTestMode();
                    break;
                case "3":
                    mainLoop(year, testMode);
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println(RED + "Invalid option. Try again." + RESET);
            }
        }
        System.out.println(GREEN + "Goodbye!" + RESET);
    }

    private static String setYear() {
        System.out.print(GREEN + "Enter year " + RED + "(####): " + RESET + BLUE);
        String year = scanner.nextLine().trim();
        System.out.print(RESET);
        return year;
    }

    private static boolean setTestMode() {
        System.out.print(GREEN + "Enable Test-Mode? " + RED + "(yes/no): " + RESET + BLUE);
        String response = scanner.nextLine().trim().toLowerCase();
        System.out.print(RESET);
        return response.equals("yes") || response.equals("y");
    }

    private static void mainLoop(String year, boolean testMode) {
        boolean inLoop = true;

        while (inLoop) {
            System.out.println(GREEN + "Main Loop:" + RESET);
            System.out.println(RED + "1: Enter Day to Execute" + RESET);
            System.out.println(RED + "2: Go Back to Main Menu" + RESET);
            System.out.println(RED + "3: Quit" + RESET);
            System.out.print(GREEN + "Select an option: " + RESET + BLUE);
            String choice = scanner.nextLine().trim();
            System.out.print(RESET);

            switch (choice) {
                case "1":
                    executeDay(year, testMode);
                    break;
                case "2":
                    inLoop = false;
                    break;
                case "3":
                    System.out.println(GREEN + "Goodbye!" + RESET);
                    System.exit(0);
                default:
                    System.out.println(RED + "Invalid option. Try again." + RESET);
            }
        }
    }

    private static void executeDay(String year, boolean testMode) {
        System.out.print(GREEN + "Enter Day " + RED + "(##): " + RESET + BLUE);
        String day = scanner.nextLine().trim();
        System.out.print(RESET);

        String filePath = constructFilePath(year, day, testMode);
        try {
            String className = constructClassName(year, day);
            Day dayInstance = createDayInstance(className);

            System.out.println(GREEN + "Executing Day " + day + ":" + RESET);
            System.out.println(GREEN + "Part 1: " + RESET + dayInstance.solvePart1(filePath));
            System.out.println(GREEN + "Part 2: " + RESET + dayInstance.solvePart2(filePath));
        } catch (ClassNotFoundException e) {
            System.err.println(RED + "Day " + day + " of year " + year + " doesn't exist yet. Try again!" + RESET);
        } catch (Exception e) {
            System.err.println(RED + "Ouuups! Feli fricked up programming. Here's what went wrong:" + RESET);
            e.printStackTrace();
        }
    }

    private static String constructFilePath(String year, String day, boolean testMode) {
        String mode = testMode ? "test" : "real";
        return "input/year" + year + "/" + mode + "/Day" + day + ".txt";
    }

    private static String constructClassName(String year, String day) {
        return "aoc.year" + year + ".Day" + day;
    }

    private static Day createDayInstance(String className) throws Exception {
        Class<?> dayClass = Class.forName(className);
        return (Day) dayClass.getDeclaredConstructor().newInstance();
    }
}
