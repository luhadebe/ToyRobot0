package za.co.wethinkcode.toyrobot.world;

import za.co.wethinkcode.toyrobot.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorld implements IWorld {
    protected Position position;
    protected Direction currentDirection;

    protected static final int MIN_X = -100;
    protected static final int MAX_X = 100;
    protected static final int MIN_Y = -200;
    protected static final int MAX_Y = 200;

    protected final List<Obstacle> obstacles;

    public AbstractWorld(List<Obstacle> obstacles) {
        this.position = IWorld.CENTRE;
        this.currentDirection = Direction.UP;
        this.obstacles = new ArrayList<>(obstacles);
    }

    @Override
    public UpdateResponse updatePosition(int nrSteps) {
        Position newPosition = calculateNewPosition(nrSteps);
        if (!isNewPositionAllowed(newPosition)) {
            return UpdateResponse.FAILED_OUTSIDE_WORLD;
        }

        for (Obstacle obstacle : obstacles) {
            if (obstacle.blocksPath(position, newPosition)) {
                return UpdateResponse.FAILED_OBSTRUCTED;
            }
        }

        this.position = newPosition;
        return UpdateResponse.SUCCESS;
    }

    protected Position calculateNewPosition(int nrSteps) {
        int x = position.getX();
        int y = position.getY();

        switch (currentDirection) {
            case UP -> y += nrSteps;
            case DOWN -> y -= nrSteps;
            case LEFT -> x -= nrSteps;
            case RIGHT -> x += nrSteps;
        }

        return new Position(x, y);
    }

    protected boolean isInBounds(Position position) {
        return position.getX() >= MIN_X && position.getX() <= MAX_X &&
                position.getY() >= MIN_Y && position.getY() <= MAX_Y;
    }

    @Override
    public boolean isNewPositionAllowed(Position position) {
        if (!isInBounds(position)) return false;
        for (Obstacle o : obstacles) {
            if (o.blocksPosition(position)) return false;
        }
        return true;
    }

    @Override
    public void updateDirection(boolean turnRight) {
        currentDirection = switch (currentDirection) {
            case UP -> turnRight ? Direction.RIGHT : Direction.LEFT;
            case RIGHT -> turnRight ? Direction.DOWN : Direction.UP;
            case DOWN -> turnRight ? Direction.LEFT : Direction.RIGHT;
            case LEFT -> turnRight ? Direction.UP : Direction.DOWN;
        };
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    @Override
    public void reset() {
        this.position = IWorld.CENTRE;
        this.currentDirection = Direction.UP;
        this.obstacles.clear(); // Only if obstacles are dynamic
    }

    @Override
    public List<Obstacle> getObstacles() {
        return new ArrayList<>(obstacles);
    }

    @Override
    public boolean isAtEdge() {
        return position.getX() == MIN_X || position.getX() == MAX_X ||
                position.getY() == MIN_Y || position.getY() == MAX_Y;
    }
}