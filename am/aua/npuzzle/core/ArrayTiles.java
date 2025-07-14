package am.aua.npuzzle.core;

import am.aua.npuzzle.NPuzzle;

/**
 * The {@code ArrayTiles} class represents the 15-puzzle game board in array form.
 * It provides methods to initialize the game state.
 */
public class ArrayTiles extends Tiles implements Cloneable
{
    /**
     * The position of the empty tile.
     */
    private int emptyPos;
    /**
     * The array that represents the puzzle grid.
     */
    private byte[] tiles;

    /**
     * Constructor of {@code ArrayTiles} from an input string.
     * The input must be in the form: "15 2 1 12 : 8 5 6 11 : 4 9 10 7 : 3 14 13 0".
     *
     * @param format The string representing the initial state of the puzzle.
     */
    public ArrayTiles(String format)
    {
        super(format);
        tiles = new byte[SIZE * SIZE];

        try
        {
            getConfiguration().initialise(this);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        for (int i = 0; i < SIZE * SIZE; i++)
            if (tiles[i] == EMPTY)
                emptyPos = i;
    }

    /**
     * Creates a copy of an existing {@code ArrayTiles}.
     *
     * @param that The puzzle instance to copy.
     */
    public ArrayTiles(ArrayTiles that)
    {
        super(that);
        tiles = new byte[SIZE * SIZE];
        emptyPos = that.emptyPos;
        for (int i = 0; i < SIZE * SIZE; i++)
            tiles[i] = that.tiles[i];
    }

    /**
     * Constructor of {@code ArrayTiles} from a configuration.
     * @param config Configuration to use
     */
    public ArrayTiles(Configuration config)
    {
        this(config.getData());
    }

    /**
     * Moves corresponding tile in the specified direction (UP, DOWN, LEFT, RIGHT).
     *
     * @param direction The direction to move the empty tile.
     */
    @Override
    public void moveImpl(Direction direction)
    {
        int tilePos = 0;
        if (direction == Direction.UP)
            tilePos = emptyPos + SIZE;
        else if (direction == Direction.DOWN)
            tilePos = emptyPos - SIZE;
        else if (direction == Direction.LEFT)
            tilePos = emptyPos + 1;
        else if (direction == Direction.RIGHT)
            tilePos = emptyPos - 1;
        else
        {
            System.out.println("Invalid move");
            System.exit(0);
        }
        int eCol = emptyPos % SIZE;
        int eRow = (emptyPos - eCol) / SIZE;
        int tCol = tilePos % SIZE;
        int tRow = (tilePos - tCol) / SIZE;
        setTile(eRow, eCol, getTile(tRow, tCol));
        setTile(tRow, tCol, EMPTY);
        emptyPos = tilePos;
        //incrementMoveCount();
    }

    /**
     * Getter for the tile value at the specified position.
     *
     * @param row The row of element.
     * @param col The column of element.
     * @return The tile value at the specified position.
     */
    @Override
    public byte getTile(int row, int col)
    {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE)
            throw new PositionOutOfBoardException();
        return tiles[row * SIZE + col];
    }

    /**
     * Setter for a tile value at the specified position if the value is valid and unique.
     *
     * @param row   The row index.
     * @param col   The column index.
     * @param value The tile value (must be between 0 and 15).
     */
    @Override
    public void setTile(int row, int col, byte value)
    {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE)
            throw new PositionOutOfBoardException("Invalid tile number");
        tiles[row * SIZE + col] = value;
    }

    /**
     * Checks if the puzzle is solved correctly.
     *
     * @return {@code true} if the puzzle is solved, otherwise {@code false}.
     */
    @Override
    public boolean isSolved()
    {
        if (emptyPos + 1 != SIZE * SIZE)
            return false;
        for (int i = 0; i + 1 < SIZE * SIZE; i++)
            if (tiles[i] != i + 1)
                return false;
        return true;
    }

    @Override
    public Object clone()
    {
        Object o = super.clone();
        ArrayTiles a = (ArrayTiles) o;
        byte[] t = new byte[SIZE * SIZE];
        for (int i = 0; i < SIZE * SIZE; i++)
        {
            t[i] = a.tiles[i];
        }
        a.tiles = t;
        return a;
    }

    /**Checks for equality
     * @param other the class to check for equality
     * @return true if both are equal, otherwise false
     * */
    @Override
    public boolean equals(Object other)
    {
        if (other == null)
            return false;
        else if (getClass() != other.getClass())
            return false;
        else
        {
            ArrayTiles n = (ArrayTiles) other;
            for (int i = 0; i < SIZE * SIZE; i++)
                if (tiles[i] != n.tiles[i])
                    return false;

            return emptyPos == n.emptyPos;
        }
    }

    /**
     * @return the string representation of this class
     * */
    @Override
    public String toString()
    {
        return "Array tiles with empty position " + emptyPos + " and tiles " + super.toString();
    }

}
