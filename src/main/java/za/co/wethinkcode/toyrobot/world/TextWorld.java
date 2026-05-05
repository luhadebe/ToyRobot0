package za.co.wethinkcode.toyrobot.world;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.maze.Maze;

import java.util.List;

public class TextWorld extends AbstractWorld {
    private final Maze maze;

    public TextWorld(Maze maze) {
        super(maze.getObstacles());
        this.maze = maze;
    }

    @Override
    public UpdateResponse updatePosition(int nrSteps) {
        Position newPosition = calculateNewPosition(nrSteps);

        // Check maze-defined path blockage (like wall logic)
        if (maze.blocksPath(this.position, newPosition)) {
            return UpdateResponse.FAILED_OBSTRUCTED;
        }

        // Bounds + direct obstacle collision (handled in AbstractWorld)
        return super.updatePosition(nrSteps);
    }

    // You no longer override isNewPositionAllowed unless needed
    // because AbstractWorld already uses obstacles and bounds

    @Override
    public void showObstacles() {
        System.out.println("There are some obstacles:");
        for (Obstacle obstacle : maze.getObstacles()) {
            System.out.println("- At position " + obstacle.getBottomLeftX() + "," + obstacle.getBottomLeftY() +
                    " (to " + (obstacle.getBottomLeftX() + obstacle.getSize()) + "," +
                    (obstacle.getBottomLeftY() + obstacle.getSize()) + ")");
        }
    }
}