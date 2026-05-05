package za.co.wethinkcode.toyrobot;


import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import static org.junit.jupiter.api.Assertions.*;

class SquareObstacleTest {

    @Test
    void testGetters() {
        SquareObstacle obstacle = new SquareObstacle(5, 5);
        assertEquals(5, obstacle.getBottomLeftX());
        assertEquals(5, obstacle.getBottomLeftY());
        assertEquals(5, obstacle.getSize());
    }

    @Test
    void testBlocksPositionInside() {
        SquareObstacle obstacle = new SquareObstacle(0, 0);
        Position inside = new Position(2, 3);
        assertTrue(obstacle.blocksPosition(inside));
    }

    @Test
    void testBlocksPositionOutside() {
        SquareObstacle obstacle = new SquareObstacle(0, 0);
        Position outside = new Position(5, 5); // exactly at top-right corner, outside the obstacle
        assertFalse(obstacle.blocksPosition(outside));
    }

    @Test
    void testBlocksPositionOnEdge() {
        SquareObstacle obstacle = new SquareObstacle(0, 0);
        assertTrue(obstacle.blocksPosition(new Position(0, 0)));
        assertTrue(obstacle.blocksPosition(new Position(4, 4)));
        assertFalse(obstacle.blocksPosition(new Position(5, 4))); // just outside
    }

    @Test
    void testBlocksVerticalPath() {
        SquareObstacle obstacle = new SquareObstacle(0, 0);
        Position start = new Position(2, -2);
        Position end = new Position(2, 7);
        assertTrue(obstacle.blocksPath(start, end));
    }

    @Test
    void testBlocksHorizontalPath() {
        SquareObstacle obstacle = new SquareObstacle(0, 0);
        Position start = new Position(-2, 2);
        Position end = new Position(7, 2);
        assertTrue(obstacle.blocksPath(start, end));
    }

    @Test
    void testDoesNotBlockDiagonalPath() {
        SquareObstacle obstacle = new SquareObstacle(0, 0);
        Position start = new Position(-2, -2);
        Position end = new Position(7, 7);
        assertFalse(obstacle.blocksPath(start, end)); // diagonal movement is ignored
    }

    @Test
    void testDoesNotBlockPathFarAway() {
        SquareObstacle obstacle = new SquareObstacle(0, 0);
        Position start = new Position(100, 100);
        Position end = new Position(100, 110);
        assertFalse(obstacle.blocksPath(start, end));
    }

    @Test
    void testToString() {
        SquareObstacle obstacle = new SquareObstacle(10, 20);
        String expected = "- At position 10,20 (to 14,24)";
        assertEquals(expected, obstacle.toString());
    }
}
