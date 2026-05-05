package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.Direction;
import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.Robot;
import za.co.wethinkcode.toyrobot.world.IWorld;

public class SimpleMazeRunner implements MazeRunner {
    private int mazeRunCost = 0;

    @Override
    public boolean mazeRun(Robot robot, IWorld.Direction edgeDirection) {
        mazeRunCost = 0;

        Direction targetDirection = mapDirection(edgeDirection);

        while (robot.getCurrentDirection() != targetDirection) {
            robot.setCurrentDirection(turnRight(robot.getCurrentDirection()));
            mazeRunCost++;
        }

        while (!isAtEdge(robot)) {
            boolean moved = robot.updatePosition(1);
            mazeRunCost++;

            if (!moved) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int getMazeRunCost() {
        return mazeRunCost;
    }

    private Direction turnRight(Direction dir) {
        return switch (dir) {
            case NORTH -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.NORTH;
        };
    }

    private boolean isAtEdge(Robot robot) {
        Position pos = robot.getPosition();
        int x = pos.getX();
        int y = pos.getY();

        return switch (robot.getCurrentDirection()) {
            case NORTH -> y >= 200;
            case DOWN  -> y <= -200;
            case LEFT  -> x <= -200;
            case RIGHT -> x >= 100;
        };
    }

    private Direction mapDirection(IWorld.Direction edgeDirection) {
        return switch (edgeDirection) {
            case UP -> Direction.NORTH;
            case RIGHT -> Direction.RIGHT;
            case DOWN -> Direction.DOWN;
            case LEFT -> Direction.LEFT;
        };
    }
}










