package za.co.wethinkcode.toyrobot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Robot {
    private final Position TOP_LEFT = new Position(-200,100);
    private final Position BOTTOM_RIGHT = new Position(100,-200);
    private List<Command> commandHistory = new ArrayList<>();
    public static final Position CENTRE = new Position(0,0);

    private Position position;
    private Direction currentDirection;
    private String status;
    private String name;

    public Robot(String name) {
        this.name = name;
        this.status = "Ready";
        this.position = CENTRE;
        this.currentDirection = Direction.NORTH;
    }

    public void storeCommand(Command command) {
        if (command instanceof ForwardCommand || command instanceof BackCommand ||
                command instanceof LeftCommand || command instanceof RightCommand ||
                command instanceof SprintCommand) {
            commandHistory.add(command);
        }
    }

    public List<Command> getCommandHistory() {
        return new ArrayList<>(commandHistory);
    }

    public String getStatus() {
        return this.status;
    }

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    public boolean handleCommand(Command command) {
        if (!(command instanceof ReplayCommand)) {
            storeCommand(command);
        }
        return command.execute(this);
    }

    public boolean updatePosition(int nrSteps){
        int newX = this.position.getX();
        int newY = this.position.getY();

        if (Direction.NORTH.equals(this.currentDirection)) {
            newY += nrSteps;
        } else if (Direction.DOWN.equals(this.currentDirection)) {
            newY -= nrSteps;
        } else if (Direction.LEFT.equals(this.currentDirection)) {
            newX -= nrSteps;
        } else if (Direction.RIGHT.equals(this.currentDirection)) {
            newX += nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        if (newPosition.isIn(TOP_LEFT,BOTTOM_RIGHT)){
            this.position = newPosition;
            return true;
        }
        return false;
    }
    public boolean moveBackward(int nrSteps) {
        int newX = this.position.getX();
        int newY = this.position.getY();

        if (Direction.NORTH.equals(this.currentDirection)) {
            newY -= nrSteps;
        } else if (Direction.DOWN.equals(this.currentDirection)) {
            newY += nrSteps;
        } else if (Direction.LEFT.equals(this.currentDirection)) {
            newX += nrSteps;
        } else if (Direction.RIGHT.equals(this.currentDirection)) {
            newX -= nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        if (newPosition.isIn(TOP_LEFT, BOTTOM_RIGHT)) {
            this.position = newPosition;
            return true;
        }
        return false;
    }

    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }

    @Override
    public String toString() {
       return "[" + this.position.getX() + "," + this.position.getY() + "] "
               + this.name + "> " + this.status;
    }

    public Position getPosition() {
        return this.position;
    }

    public Position getTopLeft() {
        return TOP_LEFT;
    }

    public Position getBottomRight() {
        return BOTTOM_RIGHT;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }
}