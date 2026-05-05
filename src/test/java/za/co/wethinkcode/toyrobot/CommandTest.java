package za.co.wethinkcode.toyrobot;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void getShutdownName() {
        Command test = new ShutdownCommand();
        assertEquals("off", test.getName());
    }

    @Test
    void executeShutdown() {
        Robot robot = new Robot("CrashTestDummy");
        Command shutdown = Command.create("shutdown");
        assertFalse(shutdown.execute(robot));
        assertEquals("Shutting down...", robot.getStatus());
    }

    @Test
    void getForwardName() {
        ForwardCommand test = new ForwardCommand("100");
        assertEquals("forward", test.getName());
        assertEquals("100", test.getArgument());
    }

    @Test
    void getBackName() {
        BackCommand test = new BackCommand("100");
        assertEquals("back", test.getName());
        assertEquals("100", test.getArgument());
    }

    @Test
    void executeForward() {
        Robot robot = new Robot("CrashTestDummy");
        Command forward100 = Command.create("forward 10");
        assertTrue(forward100.execute(robot));
        Position expectedPosition = new Position(Robot.CENTRE.getX(), Robot.CENTRE.getY() + 10);
        assertEquals(expectedPosition, robot.getPosition());
        assertEquals("Moved forward by 10 steps.", robot.getStatus());
    }

    @Test
    void executeBack() {
        Robot robot = new Robot("CrashTestDummy");
        Command back100 = Command.create("back 10");
        assertTrue(back100.execute(robot));
        Position expectedPosition = new Position(Robot.CENTRE.getX(), Robot.CENTRE.getY() - 10);
        assertEquals(expectedPosition, robot.getPosition());
        assertEquals("Moved back by 10 steps.", robot.getStatus());
    }

    @Test
    void executeLeft() {
        Robot robot = new Robot("CrashTestDummy");
        Command left = Command.create("left");

        // Default direction is null, set it to UP for testing
        robot.handleCommand(new RightCommand()); // Ensures robot starts facing RIGHT

        assertTrue(left.execute(robot));
        assertEquals(Direction.NORTH, robot.getCurrentDirection()); // Should now face UP
        assertEquals("Turned left.", robot.getStatus());
    }

    @Test
    void executeRight() {
        Robot robot = new Robot("CrashTestDummy");
        Command right = Command.create("right");

        // Default direction is null, set it to UP for testing
        robot.handleCommand(new LeftCommand()); // Ensures robot starts facing LEFT

        assertTrue(right.execute(robot));
        assertEquals(Direction.NORTH, robot.getCurrentDirection()); // Should now face UP
        assertEquals("Turned right.", robot.getStatus());
    }

    @Test
    void executeSprint() {
        Robot robot = new Robot("CrashTestDummy");
        Command sprint5 = new SprintCommand("5");
        assertTrue(sprint5.execute(robot));

        Position expectedPosition = new Position(Robot.CENTRE.getX(), Robot.CENTRE.getY() + 15);
        assertEquals(expectedPosition, robot.getPosition());
        assertEquals("Moved forward by 1 steps.", robot.getStatus());
    }
        @Test
    void getHelpName() {
        Command test = new HelpCommand();                                                               //<1>
        assertEquals("help", test.getName());
    }

    @Test
    void executeHelp() {
        Robot robot = new Robot("CrashTestDummy");
        Command help = Command.create("help");
        assertTrue(help.execute(robot));
        assertEquals("I can understand these commands:\n" +
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
                "REPLAY REVERSED - repeat previous commands in reverse order", robot.getStatus());
    }

    @Test
    void createCommand() {
        Command forward = Command.create("forward 10");                                                 //<1>
        assertEquals("forward", forward.getName());
        assertEquals("10", forward.getArgument());

        Command shutdown = Command.create("shutdown");                                                  //<2>
        assertEquals("off", shutdown.getName());

        Command help = Command.create("help");                                                          //<3>
        assertEquals("help", help.getName());
    }

    @Test
    void createInvalidCommand() {
        try {
            Command forward = Command.create("say hello");                                              //<4>
            fail("Should have thrown an exception");                                                    //<5>
        } catch (IllegalArgumentException e) {
            assertEquals("Unsupported command: say hello", e.getMessage());                             //<6>
        }
    }

    @Test
    void executeReplayAll() {
        Robot robot = new Robot("CrashTestDummy");
        robot.handleCommand(new ForwardCommand("10"));
        robot.handleCommand(new LeftCommand());
        robot.handleCommand(new BackCommand("5"));

        Command replay = new ReplayCommand();
        assertTrue(replay.execute(robot));

        assertEquals("replayed 3 commands.", robot.getStatus());
    }

    @Test
    void executeReplayLastTwo() {
        Robot robot = new Robot("CrashTestDummy");
        robot.handleCommand(new ForwardCommand("10"));
        robot.handleCommand(new LeftCommand());
        robot.handleCommand(new BackCommand("5"));

        Command replay = new ReplayCommand("2");
        assertTrue(replay.execute(robot));

        assertEquals("replayed 2 commands.", robot.getStatus());
    }

    @Test
    void executeReplayRange() {
        Robot robot = new Robot("CrashTestDummy");
        robot.handleCommand(new ForwardCommand("10"));
        robot.handleCommand(new LeftCommand());
        robot.handleCommand(new BackCommand("5"));

        Command replay = new ReplayCommand("3-2");
        assertTrue(replay.execute(robot));

        assertEquals("replayed 1 commands.", robot.getStatus());
    }

    @Test
    void executeReplayReversed() {
        Robot robot = new Robot("CrashTestDummy");
        robot.handleCommand(new ForwardCommand("10"));
        robot.handleCommand(new LeftCommand());
        robot.handleCommand(new BackCommand("5"));

        Command replay = new ReplayCommand("reversed");
        assertTrue(replay.execute(robot));

        assertEquals("replayed 3 commands.", robot.getStatus());
    }
}
