package za.co.wethinkcode.toyrobot;

public class SprintCommand extends Command {
    public SprintCommand(String argument) {
        super("sprint", argument);
    }

    @Override
    public boolean execute(Robot target) {
        int totalSteps = Integer.parseInt(getArgument());

        for (int i = totalSteps; i > 0; i--) {
            if (target.updatePosition(i)) {
                target.setStatus("Moved forward by " + i + " steps.");
                System.out.println(target);
            } else {
                target.setStatus("Sorry, I cannot go outside my safe zone.");
                System.out.println(target);
                return false;
            }
        }

        return true;
    }
}