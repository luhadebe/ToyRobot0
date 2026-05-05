package za.co.wethinkcode.toyrobot;

public enum Direction {
    NORTH,
    DOWN,
    LEFT,
    RIGHT;

    public Direction turnLeft() {
        switch (this) {
            case NORTH: return LEFT;
            case LEFT: return DOWN;
            case DOWN: return RIGHT;
            case RIGHT: return NORTH;
            default: throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public Direction turnRight() {
        switch (this) {
            case NORTH: return RIGHT;
            case RIGHT: return DOWN;
            case DOWN: return LEFT;
            case LEFT: return NORTH;
            default: throw new IllegalStateException("Unexpected value: " + this);
        }
    }

}
