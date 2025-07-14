package am.aua.npuzzle.core;

/**
 * The {@code MatrixTiles} class represents the 15-puzzle game board in matrix form.
 * It provides methods to initialize the game state.
 */
public class MatrixTiles extends Tiles implements Cloneable
{
    /**
     * 2D array represents the puzzle grid.
     */
    private byte[][] tiles;
    /**
     * The column of the empty tile.
     */
    private int emptyCol;
    /**
     * The row of the empty tile.
     */
    private int emptyRow;



    /**
     * Constructor of {@code MatrxTiles} from an input string.
     * The input must be in the form: "15 2 1 12 : 8 5 6 11 : 4 9 10 7 : 3 14 13 0".
     *
     * @param format The string representing the initial state of the puzzle.
     */
    public MatrixTiles(String format)
    {
        super(format);
        tiles = new byte[SIZE][SIZE];

        try
        {
            getConfiguration().initialise(this);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.exit(1);
        }


        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (tiles[i][j] == EMPTY)
                {
                    emptyCol = j;
                    emptyRow = i;
                }
    }

    /**
     * Creates a copy of an existing {@code MatrixTiles}.
     *
     * @param that The puzzle instance to copy.
     */
    public MatrixTiles(MatrixTiles that)
    {
        super(that);
        tiles = new byte[SIZE][SIZE];
        this.emptyCol = that.emptyCol;
        this.emptyRow = that.emptyRow;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                this.tiles[i][j] = that.tiles[i][j];
    }

    /**
     * Constructor of {@code MartixTiles} from a configuration.
     * @param config Configuration to use
     */
    public MatrixTiles(Configuration config)
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
        int tileCol = 0, tileRow = 0;
        if (direction == Direction.UP)
        {
            tileCol = emptyCol;
            tileRow = emptyRow + 1;
        } else if (direction == Direction.DOWN)
        {
            tileCol = emptyCol;
            tileRow = emptyRow - 1;
        } else if (direction == Direction.LEFT)
        {
            tileCol = emptyCol + 1;
            tileRow = emptyRow;
        } else if (direction == Direction.RIGHT)
        {
            tileCol = emptyCol - 1;
            tileRow = emptyRow;
        } else
        {
            System.out.println("Invalid move");
            System.exit(0);
        }
        setTile(emptyRow, emptyCol, getTile(tileRow, tileCol));
        setTile(tileRow, tileCol, EMPTY);
        emptyCol = tileCol;
        emptyRow = tileRow;
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
            throw new PositionOutOfBoardException("Invalid tile number");
        return tiles[row][col];
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
        tiles[row][col] = value;
    }

    /**
     * Checks if the puzzle is solved correctly.
     *
     * @return {@code true} if the puzzle is solved, otherwise {@code false}.
     */
    @Override
    public boolean isSolved()
    {
        if (emptyCol + 1 != SIZE || emptyRow + 1 != SIZE)
            return false;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if ((i + 1 < SIZE || j + 1 < SIZE)
                        && tiles[i][j] != i * SIZE + j + 1)
                    return false;
        return true;
    }

    @Override
    public Object clone()
    {
        Object o = super.clone();
        MatrixTiles m = (MatrixTiles) o;
        byte[][] t = new byte[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
            {
                t[i][j] = m.tiles[i][j];

            }
        m.tiles = t;
        return m;
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
            MatrixTiles n = (MatrixTiles) other;
            for (int i = 0; i < SIZE; i++)
                for (int j = 0; j < SIZE; j++)
                    if (tiles[i][j] != n.tiles[i][j])
                        return false;

            return  emptyCol == n.emptyCol && emptyRow == n.emptyRow ;
        }
    }

    /**
     * @return the string representation of this class
     * */
    @Override
    public String toString()
    {
        return "Matrix tiles with empty position " + emptyCol + " " + emptyRow +" and tiles " + super.toString();
    }
}
