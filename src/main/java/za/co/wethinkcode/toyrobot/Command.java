package za.co.wethinkcode.toyrobot;

public abstract class Command {
    private final String name;
    private String argument;

    public Command(String name) {
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    public Command(String name, String argument) {
        this(name);
        this.argument = argument.trim();
    }

    public abstract boolean execute(Robot target);

    public String getName() {
        return name;
    }

    public String getArgument() {
        return this.argument;
    }

    public String getFeedbackMessage() {
        return "Command executed: " + name;
    }

    public static Command create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ");
        switch (args[0]) {
            case "shutdown":
            case "off":
                return new ShutdownCommand();
            case "help":
                return new HelpCommand();
            case "forward":
                return new ForwardCommand(args[1]);
            case "back":
                return new BackCommand(args[1]);
            case "left":
                return new LeftCommand();
            case "right":
                return new RightCommand();
            case "sprint":
                return new SprintCommand(args[1]);
            case "mazerun":
                if (args.length == 2) {
                    String directionArg = args[1].toLowerCase();
                    Direction dir = switch (directionArg) {
                        case "top" -> Direction.NORTH;
                        case "bottom" -> Direction.DOWN;
                        case "left" -> Direction.LEFT;
                        case "right" -> Direction.RIGHT;
                        default -> throw new IllegalArgumentException("Invalid direction: " + directionArg);
                    };
                    return new MazeRunCommand(dir);
                }
                break;
            case "replay":
                if (args.length == 1) {
                    return new ReplayCommand("no arguments");
                } else if (args.length == 2) {
                    if (args[1].equals("reversed")) {
                        return new ReplayCommand("reversed");
                    } else {
                        return new ReplayCommand(args[1]); // count or range
                    }
                } else if (args.length == 3 && args[1].equals("reversed")) {
                    return new ReplayCommand("reversed " + args[2]); // reversed with count or range
                }
                break;
        }
        throw new IllegalArgumentException("Unsupported command: " + instruction);
    }
}