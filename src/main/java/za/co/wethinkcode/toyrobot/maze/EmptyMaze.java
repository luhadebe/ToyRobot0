package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.world.Obstacle;

import java.util.Collections;
import java.util.List;

public class EmptyMaze implements Maze {

    @Override
    public List<Obstacle> getObstacles() {
        return Collections.emptyList();
    }

    @Override
    public boolean blocksPath(Position start, Position end) {
        return false;
    }
}