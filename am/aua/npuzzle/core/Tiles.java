package am.aua.npuzzle.core;

/**
 * The {@code Tiles} class represents the 15-puzzle game board.
 * It provides methods to initialize the game state.
 */
public abstract class Tiles implements Cloneable
{
    /**
     * Keeps the values of possible directions
     */
    public enum Direction
    {
        UP, DOWN, LEFT, RIGHT;
    }

    /**
     * The size of the puzzle is 4x4.
     */
    public static final int SIZE = 4;
    /**
     * The value representing the empty tile is 0.
     */
    public static final byte EMPTY = 0;
    /**
     * Keeps track of moves
     */
    private int moves;
    /**
     * The configuration of the game.
     */
    private Configuration configuration;



    /**
     * Constructor of {@code Tiles} from an input string.
     * The input must be in the form: "15 2 1 12 : 8 5 6 11 : 4 9 10 7 : 3 14 13 0".
     *
     * @param format The string representing the initial state of the puzzle.
     */
    public Tiles(String format)
    {
        try
        {
            this.configuration = new Configuration(format);
        } catch (ConfigurationFormatException e)
        {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Constructor of {@code Tiles} from a configuration.
     * @param config Configuration to use
     */
    public Tiles(Configuration config)
    {
        this.configuration = config;
    }

    /**
     * Creates a copy of an existing {@code Tiles}.
     *
     * @param that The puzzle board instance to copy.
     */
    protected Tiles(Tiles that)
    {
        this.configuration = new Configuration(that.configuration);
        this.moves = that.moves;
    }

    /* I don't need this part,but i keep it
    /**Increments move count after every move

    protected void incrementMoveCount()
    {
        ++moves;
    }
    */


    /**
     * Moves corresponding tile in the specified direction (UP, DOWN, LEFT, RIGHT) and increments the number of moves.
     *
     * @param direction The direction to move the empty tile.
     */
    public void move(Direction direction)
    {
        moveImpl(direction);
        moves++;
    }

    /**
     * Ensures the validity of the configuration
     *
     * @throws InvalidConfigurationException if the config is not valid
     */
    public void ensureValidity() throws InvalidConfigurationException {
        boolean[] found = new boolean[SIZE * SIZE];
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                byte value = getTile(j, i);
                if (value != EMPTY) {
                    if (value < 1 || value >= SIZE * SIZE)
                        throw new InvalidConfigurationException("Invalid configuration: incorrect tile value " + value + ".");
                    else if (found[value])
                        throw new InvalidConfigurationException("Invalid configuration: multiple tiles with the value" + value + ".");
                    else
                        found[value] = true;
                } else if (found[value])
                    throw new InvalidConfigurationException("Invalid configuration: multiple empty spaces.");
                else
                    found[value] = true;
            }
    }

    /**
     * Checks if the configuration is solvable or no
     *
     * @return true if the config is solvable ,false otherwise
     */
    public boolean isSolvable() {
        int inversions = 0;
        int row = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                if (getTile(j, i) == EMPTY) {
                    row = SIZE - i;
                    continue;
                }
                for (int l = j + 1; l < SIZE; l++)
                    if (getTile(j, i) > getTile(l, i) && getTile(l, i) != EMPTY)
                        inversions++;
                for (int k = i + 1; k < SIZE; k++)
                    for (int l = 0; l < SIZE; l++)
                        if (getTile(j, i) > getTile(l, k) && getTile(l, k) != EMPTY)
                            inversions++;
            }
        int check = inversions + (((SIZE & 1) == 0) ? row : 1);
        return (check & 1) == 1;
    }

    /**
     * Getter fot moves count.
     *
     * @return moves
     */
    public int getMoveCunt()
    {
        return this.moves;
    }

    /**
     * Getter for configuration.
     *
     * @return {@code Configuration} of the board
     */
    protected Configuration getConfiguration()
    {
        return this.configuration;
    }

    /**
     * Getter for the tile value at the specified position.
     *
     * @param pos The position of element.
     * @return The tile value at the specified position.
     */
    private byte getTile(int pos)
    {
        int row = pos / SIZE;
        int col = pos % SIZE;
        byte value = -1;
        try
        {
            value = getTile(row, col);
        } catch (PositionOutOfBoardException e)
        {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return value;
    }

    /**
     * Calculates the number of inversions in tiles.
     *
     * @return the number of inversions
     */
    private int numberOfInv()
    {
        int inv = 0;
        int pos = 1;
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = i + 1; j < SIZE; j++)
            {
                if (getTile(i, j) != EMPTY && getTile(i, j) > getTile(pos++)) //here there is no need to check for exception because i and j are from[0;SIZE)
                    inv++;
            }
        }
        return inv;
    }


    /**
     * Checks if the puzzle is solved correctly.
     *
     * @return {@code true} if the puzzle is solved, otherwise {@code false}.
     */
    public abstract boolean isSolved();

    /**
     * Getter for the tile value at the specified position.
     *
     * @param row The row of element.
     * @param col The column of element.
     * @return The tile value at the specified position.
     */
    public abstract byte getTile(int row, int col) throws PositionOutOfBoardException;

    /**
     * Setter for a tile value at the specified position if the value is valid and unique.
     *
     * @param row   The row index.
     * @param col   The column index.
     * @param value The tile value (must be between 0 and 15).
     */
    public abstract void setTile(int row, int col, byte value) throws PositionOutOfBoardException;

    @Override
    public Object clone()
    {
        try
        {
            return super.clone();           //configuration is immutable
        } catch (CloneNotSupportedException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Moves corresponding tile in the specified direction (UP, DOWN, LEFT, RIGHT).
     *
     * @param direction The direction to move the empty tile.
     */
    protected abstract void moveImpl(Direction direction);

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
            Tiles t = (Tiles) other;
            return moves == t.moves && configuration.equals(t.configuration);
        }
    }

    /**
     * @return the string representation of this class
     * */
    @Override
    public String toString()
    {
        return "Tiles [moves=" + moves + ", configuration=" + configuration + "]";
    }

}
