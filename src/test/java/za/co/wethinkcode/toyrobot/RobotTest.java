package za.co.wethinkcode.toyrobot;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    @Test
    void initialPosition() {
        Robot robot = new Robot("CrashTestDummy");
        assertEquals(Robot.CENTRE, robot.getPosition());
        assertEquals(Direction.NORTH, robot.getCurrentDirection());
    }

    @Test
    void dump() {
        Robot robot = new Robot("CrashTestDummy");
        assertEquals("[0,0] CrashTestDummy> Ready", robot.toString());
    }

    @Test
    void shutdown() {
        Robot robot = new Robot("CrashTestDummy");
        ShutdownCommand command = new ShutdownCommand();
        assertFalse(robot.handleCommand(command));
    }

    @Test
    void forward() {
        Robot robot = new Robot("CrashTestDummy");
        ForwardCommand command = new ForwardCommand("10");
        assertTrue(robot.handleCommand(command));
        Position expectedPosition = new Position(Robot.CENTRE.getX(), Robot.CENTRE.getY() + 10);
        assertEquals(expectedPosition, robot.getPosition());
        assertEquals("Moved forward by 10 steps.", robot.getStatus());
    }

    @Test
    void back() {
        Robot robot = new Robot("CrashTestDummy");
        BackCommand command = new BackCommand("10");
        assertTrue(robot.handleCommand(command));
        Position expectedPosition = new Position(Robot.CENTRE.getX(), Robot.CENTRE.getY() - 10);
        assertEquals(expectedPosition, robot.getPosition());
        assertEquals("Moved back by 10 steps.", robot.getStatus());
    }

    @Test
    void forwardforward() {
        Robot robot = new Robot("CrashTestDummy");
        assertTrue(robot.handleCommand(new ForwardCommand("10")));
        assertTrue(robot.handleCommand(new ForwardCommand("5")));
        assertEquals("Moved forward by 5 steps.", robot.getStatus());
    }

    @Test
    void tooFarForward() {
        Robot robot = new Robot("CrashTestDummy");
        assertTrue(robot.handleCommand(new ForwardCommand("1000")));
        assertEquals(Robot.CENTRE, robot.getPosition());
        assertEquals("Sorry, I cannot go outside my safe zone.", robot.getStatus());
    }

    @Test
    void help() {
        Robot robot = new Robot("CrashTestDummy");
        Command command = new HelpCommand();
        assertTrue(robot.handleCommand(command));
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
    void turnLeft() {
        Robot robot = new Robot("CrashTestDummy");
        assertEquals(Direction.NORTH, robot.getCurrentDirection());

        robot.handleCommand(new LeftCommand());
        assertEquals(Direction.LEFT, robot.getCurrentDirection());

        robot.handleCommand(new LeftCommand());
        assertEquals(Direction.DOWN, robot.getCurrentDirection());
    }

    @Test
    void turnRight() {
        Robot robot = new Robot("CrashTestDummy");
        assertEquals(Direction.NORTH, robot.getCurrentDirection());

        robot.handleCommand(new RightCommand());
        assertEquals(Direction.RIGHT, robot.getCurrentDirection());

        robot.handleCommand(new RightCommand());
        assertEquals(Direction.DOWN, robot.getCurrentDirection());
    }

    @Test
    void sprint() {
        Robot robot = new Robot("CrashTestDummy");
        SprintCommand command = new SprintCommand("4");
        assertTrue(robot.handleCommand(command));

        Position expectedPosition = new Position(Robot.CENTRE.getX(), Robot.CENTRE.getY() + 10);
        assertEquals(expectedPosition, robot.getPosition());
        assertEquals("Moved forward by 1 steps.", robot.getStatus());
    }
}