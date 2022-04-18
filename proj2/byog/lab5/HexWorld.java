package byog.lab5;
import byog.SaveDemo.World;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static class Position {
        public int x;
        public int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public Position(Position other) {
            this.x = other.x;
            this.y = other.y;
        }
    }

    private static final int WIDTH = 49;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Computes the width of row i for a size s hexagon.
     *
     * @param s The size of the hex.
     * @param i The row number where i = 0 is the bottom row.
     * @return
     */
    public static int hexRowWidth(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return s + 2 * effectiveI;
    }

    /**
     * Computesrelative x coordinate of the leftmost tile in the ith
     * row of a hexagon, assuming that the bottom row has an x-coordinate
     * of zero. For example, if s = 3, and i = 2, this function
     * returns -2, because the row 2 up from the bottom starts 2 to the left
     * of the start position, e.g.
     * xxxx
     * xxxxxx
     * xxxxxxxx
     * xxxxxxxx <-- i = 2, starts 2 spots to the left of the bottom of the hex
     * xxxxxx
     * xxxx
     *
     * @param s size of the hexagon
     * @param i row num of the hexagon, where i = 0 is the bottom
     * @return
     */
    public static int hexRowOffset(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return -effectiveI;
    }

    /**
     * Adds a row of the same tile.
     *
     * @param world the world to draw on
     * @param p     the leftmost position of the row
     * @param width the number of tiles wide to draw
     * @param t     the tile to draw
     */
    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi += 1) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /**
     * Adds a hexagon to the world.
     *
     * @param world the world to draw on
     * @param p     the bottom left coordinate of the hexagon
     * @param s     the size of the hexagon
     * @param t     the tile to draw
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
        // hexagons have 2*s rows. this code iterates up from the bottom row,
        // which we call row 0.
        for (int yi = 0; yi < 2 * s; yi += 1) {
            int thisRowY = p.y + yi;
            int xRowStart = p.x + hexRowOffset(s, yi);
            Position rowStartP = new Position(xRowStart, thisRowY);
            int rowWidth = hexRowWidth(s, yi);
            addRow(world, rowStartP, rowWidth, t);
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(4);
        switch (tileNum) {
            case 0: return Tileset.MOUNTAIN;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.SAND;
            default: return Tileset.TREE;
        }
    }

    public static void addDiagonal(TETile[][] world, Position p, int s, int length) {
        Position hexP = new Position(p);
        for (int i = 0; i < length; i += 1) {
            addHexagon(world, p, s, randomTile());
            p.x -= 2 * s - 1;
            p.y += s;
        }
    }

    private static Position getStartP(Position startP, int s, int i) {
        int x = startP.x;
        int y = startP.y;
        if (i < 3) {
            x += i * (2 * s - 1);
            y += i * s;
        } else {
            x += 2 * (2 * s - 1);
            y += 2 * s;
            y += (i - 2) * 2 * s;
        }
        return new Position(x, y);
    }

    private static int getLength(int i) {
        if (i < 3) {
            return 3 + i;
        } else {
            return 7 - i;
        }
    }

    public static void addHexagonWorld(TETile[][] world, Position startP, int s) {
        for(int i = 0; i < 5; i += 1) {
            HexWorld.addDiagonal(world, getStartP(startP, s, i), s, getLength(i));
        }
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        // initialize tiles
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        int size = 5;
        int startW = (WIDTH - size) / 2;
        Position startP = new Position(startW, 0);
        addHexagonWorld(world, startP, size);

        // draws the world to the screen
        ter.renderFrame(world);
    }
}
