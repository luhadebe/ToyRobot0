package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import java.util.Collections;
import java.util.List;

public class SimpleMaze implements Maze {
    private final List<Obstacle> obstacles;

    public SimpleMaze() {
        this.obstacles = Collections.singletonList(new SquareObstacle(1, 1));
    }

    @Override
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    @Override
    public boolean blocksPath(Position start, Position end) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.blocksPath(start, end)) {
                return true;
            }
        }
        return false;
    }
}
