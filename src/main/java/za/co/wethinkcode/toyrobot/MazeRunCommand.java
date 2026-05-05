package za.co.wethinkcode.toyrobot;

public class MazeRunCommand extends Command {
    private final Direction direction;

    public MazeRunCommand(Direction direction) {
        super("mazerun");
        this.direction = direction;
    }

    @Override
    public boolean execute(Robot target) {
        target.setCurrentDirection(direction);
        int stepsToEdge = 0;

        Position current = target.getPosition();

        switch (direction) {
            case NORTH -> stepsToEdge = target.getTopLeft().getY() - current.getY();
            case DOWN  -> stepsToEdge = current.getY() - target.getBottomRight().getY();
            case LEFT  -> stepsToEdge = current.getX() - target.getTopLeft().getX();
            case RIGHT -> stepsToEdge = target.getBottomRight().getX() - current.getX();
        }

        // Move to edge
        boolean moved = target.updatePosition(stepsToEdge);

        if (moved) {
            target.setStatus("Mazed out towards " + direction.toString().toLowerCase() + " in " + stepsToEdge + " steps.");
        } else {
            target.setStatus("Mazerun failed.");
        }

        return moved;
    }
}