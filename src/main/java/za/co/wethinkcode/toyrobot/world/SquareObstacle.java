package za.co.wethinkcode.toyrobot.world;

import za.co.wethinkcode.toyrobot.Position;

public class SquareObstacle implements Obstacle {
    private final int bottomLeftX;
    private final int bottomLeftY;
    private final int size = 5;

    public SquareObstacle(int x, int y) {
        this.bottomLeftX = x;
        this.bottomLeftY = y;
    }

    @Override
    public int getBottomLeftX() {
        return bottomLeftX;
    }

    @Override
    public int getBottomLeftY() {
        return bottomLeftY;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean blocksPosition(Position position) {
        int x = position.getX();
        int y = position.getY();
        return x >= bottomLeftX && x < bottomLeftX + size &&
                y >= bottomLeftY && y < bottomLeftY + size;
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        // Move is vertical
        if (a.getX() == b.getX()) {
            int x = a.getX();
            int startY = Math.min(a.getY(), b.getY());
            int endY = Math.max(a.getY(), b.getY());
            for (int y = startY; y <= endY; y++) {
                if (blocksPosition(new Position(x, y))) return true;
            }
        }
        // Move is horizontal
        else if (a.getY() == b.getY()) {
            int y = a.getY();
            int startX = Math.min(a.getX(), b.getX());
            int endX = Math.max(a.getX(), b.getX());
            for (int x = startX; x <= endX; x++) {
                if (blocksPosition(new Position(x, y))) return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "- At position " + bottomLeftX + "," + bottomLeftY +
                " (to " + (bottomLeftX + 4) + "," + (bottomLeftY + 4) + ")";
    }
}
