package za.co.wethinkcode.toyrobot;


import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.maze.RandomMaze;
import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RandomMazeTest {

    @Test
    void createsCorrectNumberOfObstacles() {
        RandomMaze maze = new RandomMaze(10);
        List<Obstacle> obstacles = maze.getObstacles();
        assertEquals(10, obstacles.size());
    }

    @Test
    void obstaclesAreSquareObstacles() {
        RandomMaze maze = new RandomMaze(5);
        for (Obstacle obs : maze.getObstacles()) {
            assertTrue(obs instanceof SquareObstacle);
        }
    }

    @Test
    void obstaclesHaveRandomPositions() {
        RandomMaze maze1 = new RandomMaze(10);
        RandomMaze maze2 = new RandomMaze(10);

        Set<String> positions1 = new HashSet<>();
        for (Obstacle o : maze1.getObstacles()) {
            positions1.add(o.getBottomLeftX() + "," + o.getBottomLeftY());
        }

        Set<String> positions2 = new HashSet<>();
        for (Obstacle o : maze2.getObstacles()) {
            positions2.add(o.getBottomLeftX() + "," + o.getBottomLeftY());
        }

        // Randomness: at least some positions should differ
        assertNotEquals(positions1, positions2);
    }

    @Test
    void blocksPathReturnsTrueIfObstacleOnPath() {
        // Create maze with 0 random obstacles
        RandomMaze maze = new RandomMaze(0);

        // Add a known obstacle manually
        SquareObstacle blocking = new SquareObstacle(0, 0);
        maze.getObstacles().add(blocking);

        Position start = new Position(0, -5);
        Position end = new Position(0, 5);
        assertTrue(maze.blocksPath(start, end));
    }

    @Test
    void blocksPathReturnsFalseIfNoObstacleOnPath() {
        RandomMaze maze = new RandomMaze(0); // no obstacles
        Position start = new Position(0, -5);
        Position end = new Position(0, 5);
        assertFalse(maze.blocksPath(start, end));
    }
}
