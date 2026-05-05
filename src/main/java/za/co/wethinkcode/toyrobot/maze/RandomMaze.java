package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMaze implements Maze {
    private final List<Obstacle> obstacles;
    private static final Random RANDOM = new Random();

    public RandomMaze(int numberOfObstacles) {
        obstacles = new ArrayList<>();
        for (int i = 0; i < numberOfObstacles; i++) {
            int x = RANDOM.nextInt(100) - 50;
            int y = RANDOM.nextInt(100) - 50;
            obstacles.add(new SquareObstacle(x, y));
        }
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