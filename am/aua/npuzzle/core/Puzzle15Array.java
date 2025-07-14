package am.aua.npuzzle.core;
import java.util.Scanner;

/** The {@code Puzzle15Array} class represents the 15-puzzle game by array.
 * It provides methods to initialize and play the game.
 */
public class Puzzle15Array
{
    /** The size of the puzzle is 4x4. */
    public static final int SIZE = 4;
    /** The value representing the empty tile is 0. */
    public static final byte EMPTY = 0;
    /** The array that represents the puzzle grid. */
    private byte [] tiles ;
    /** The position of the empty tile. */
    private int emptyPos ;
    /** The configuration of the game. */
    private Configuration configuration;


    /**Constructor of {@code Puzzle15Array} from an input string.
     * The input must be in the form: "15 2 1 12 : 8 5 6 11 : 4 9 10 7 : 3 14 13 0".
     * @param format The string representing the initial state of the puzzle.
     */
    public Puzzle15Array(String format)
    {
        tiles = new byte[SIZE*SIZE];
        configuration = new Configuration(format);
        configuration.initialise(tiles);
        setEmpty();
    }

    /** Creates a copy of an existing {@code Puzzle15Matrix}.
     * @param puzzle The puzzle instance to copy.
     */
    public Puzzle15Array(Puzzle15Array puzzle)
    {
        tiles = new byte[SIZE*SIZE];
        for (int i = 0; i < SIZE*SIZE; i++)
            tiles[i] = puzzle.tiles[i];
        puzzle.setEmpty();
    }

    /** Getter for the tile value at the specified position.
     * @param pos The position of the element in 1D array.
     * @return The tile value at the specified position.
     */
    public byte getTile(int pos)
    {
        return tiles[pos];
    }

    /** Getter for the tile value at the specified position.
     * @param row The row of the element in 2D array.
     * @param col The column of the element in 2D array.
     * @return The tile value at the specified position.
     */
    public byte getTile(int row, int col)
    {
        return tiles[row * SIZE + col];
    }

    /** Getter for the position of the empty tile.
     * @return The position of the empty tile.
     */
    public int getEmptyPos()
    {
        return emptyPos;
    }

    /** Setter for a tile value at the specified position if the value is valid and unique.
     * @param pos The position of the value in 1D array.
     * @param value The tile value (must be between 0 and 15).
     */
    public void setTiles(int pos, int value)
    {
        if (value >= 0 && value < 16)
        {
            tiles[pos] = (byte)value;
            if (value == EMPTY)
                setEmpty();
        }
    }

    /** Setter for a tile value at the specified position if the value is valid and unique.
     * @param row The row index.
     * @param col The column index.
     * @param value The tile value (must be between 0 and 15).
     */
    public void setTiles(int row, int col, int value)
    {
        setTiles(row * SIZE + col, value);
    }


    /** Converts 1D array to 2D array.
     * @return The Matrix representation of the game state
     */
    private byte[][] conversion()
    {
        byte[][] matrix = new byte[SIZE][SIZE];
        int pos = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
            {
                matrix[i][j] = tiles[pos++];
            }
        return matrix;
    }

    /**Prints the current state of the puzzle with special format.
    */
    private void print()
    {
        new Puzzle15Matrix(conversion()).print();
    }

    /** Moves corresponding tile in the specified direction (U = Up, D = Down, L = Left, R = Right).
     * @param direction The direction to move the empty tile.
     */
    private void move(char direction)
    {
        Puzzle15Matrix temp = new Puzzle15Matrix(conversion());
        temp.move(direction);
        int pos = 0;
        for (int i=0; i<Puzzle15Matrix.SIZE; i++)
            for (int j=0 ;j<Puzzle15Matrix.SIZE; j++)
                this.tiles[pos++]=(byte)temp.getTile(i,j);
    }

    /** Checks if the puzzle is solved correctly.
     * @return {@code true} if the puzzle is solved, otherwise {@code false}.
     */
    private boolean isSolved()
    {
        return new Puzzle15Matrix(conversion()).isSolved();
    }

    /** Starts the game, allowing the user to enter movement commands.
     */
    public void play()
    {
        Scanner keyboard = new Scanner(System.in);
        while (!isSolved())
        {
            print();
            System.out.print("Please make a move by inputting U, D, L, R; or stop the game by inputting q: ");
            char command = keyboard.next().charAt(0);
            if (command == 'q')
            {
                System.out.println("Ending the game.");
                return;
            }
            move(command);

        }
        System.out.println("You solved the puzzle!");
    }

    /** Finds and sets the position of the empty tile.
     */
    private void setEmpty()
    {
        for (int i = 0; i < SIZE*SIZE; i++)
            if (tiles[i] == EMPTY)
                emptyPos = i;
    }
}
