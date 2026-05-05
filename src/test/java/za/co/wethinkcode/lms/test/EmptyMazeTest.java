package za.co.wethinkcode.lms.test;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.maze.Maze;
import za.co.wethinkcode.toyrobot.world.Obstacle;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmptyMazeTest {

    @Test
    void testEmptyMazeIsEmpty() {
        Maze maze = new Maze() {
            @Override
            public List<Obstacle> getObstacles() {
                return List.of();
            }

            @Override
            public boolean blocksPath(Position a, Position b) {
                return false;
            }
        };
        assertEquals(0, maze.getObstacles().size());
    }

}