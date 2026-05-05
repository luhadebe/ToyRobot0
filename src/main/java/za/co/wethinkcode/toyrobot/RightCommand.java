package za.co.wethinkcode.toyrobot;

public class RightCommand extends Command{
    public RightCommand() {
        super("right");
    }

    @Override
    public boolean execute(Robot target) {
        target.setCurrentDirection(target.getCurrentDirection().turnRight());
        target.setStatus("Turned right.");
        return true;
    }
}
