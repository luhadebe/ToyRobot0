package za.co.wethinkcode.toyrobot;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help");
    }

    @Override
    public boolean execute(Robot target) {
        target.setStatus("I can understand these commands:\n" +
                "OFF  - Shut down robot\n" +
                "HELP - provide information about commands\n" +
                "FORWARD - move forward by specified number of steps, e.g. 'FORWARD 10'\n" +
                "BACK - move backward by specified number of steps\n" +
                "LEFT - turn left\n" +
                "RIGHT - turn right\n" +
                "SPRINT steps - sprint forward decreasing steps (e.g. 5+4+3+2+1)\n" +
                "REPLAY - repeat all previous movement commands\n" +
                "REPLAY n - repeat the last n movement commands\n" +
                "REPLAY n-m - repeat commands from n to m (exclusive)\n" +
                "REPLAY REVERSED - repeat previous commands in reverse order");
        return true;
    }
}
