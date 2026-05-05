package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.maze.EmptyMaze;
import za.co.wethinkcode.toyrobot.maze.Maze;
import za.co.wethinkcode.toyrobot.maze.RandomMaze;
import za.co.wethinkcode.toyrobot.maze.SimpleMaze;
import za.co.wethinkcode.toyrobot.world.IWorld;
import za.co.wethinkcode.toyrobot.world.TextWorld;

import java.util.Scanner;

public class Play {
    static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        Robot robot;
        Maze maze;

        int mazeSize = 10;

        if (args.length == 1) {
            String mazeType = args[0].toLowerCase();
            switch (mazeType) {
                case "emptymaze":
                    maze = new EmptyMaze();
                    System.out.println("Loaded EmptyMaze.");
                    break;
                case "randommaze":
                    maze = new RandomMaze(mazeSize);
                    System.out.println("Loaded RandomMaze.");
                    break;
                case "simplemaze":
                    maze = new SimpleMaze();
                    System.out.println("Loaded SimpleMaze.");
                    break;
                default:
                    System.out.println("Invalid maze name, loading RandomMaze by default.");
                    maze = new RandomMaze(mazeSize);
                    break;
            }
        } else {
            maze = new EmptyMaze();
            System.out.println("Loaded EmptyMaze.");
        }

        IWorld world = new TextWorld(maze);
        world.showObstacles();

        String name = getInput("What do you want to name your robot?");
        robot = new Robot(name);
        System.out.println("Hello Kiddo!");
        System.out.println(robot.toString());

        boolean shouldContinue = true;
        Command command = null; // ✅ Declare outside loop so it's accessible after try-catch

        do {
            String instruction = getInput(robot.getName() + "> What must I do next?").strip().toLowerCase();
            try {
                command = Command.create(instruction);
                shouldContinue = robot.handleCommand(command);
            } catch (IllegalArgumentException e) {
                robot.setStatus("Sorry, I did not understand '" + instruction + "'.");
            }

            // Print the robot status unless it's a SprintCommand or ReplayCommand
            if (!(command instanceof SprintCommand)) {
                if (robot.getStatus() != null && !robot.getStatus().isEmpty()) {
                    System.out.println(robot);  // Prints the robot's status and state
                }
            }

        } while (shouldContinue);
    }

    private static String getInput(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();

        while (input.isBlank()) {
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }
}