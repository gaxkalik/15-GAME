package am.aua.npuzzle.core;

import java.util.Scanner;

/** The {@code Puzzle15Matrix} class represents the 15-puzzle game by matrix.
 * It provides methods to initialize and play the game.
 */
public class Puzzle15Matrix
{
    /** The size of the puzzle is 4x4. */
    public static final int SIZE = 4;
    /** The value representing the empty tile is 0. */
    public static final int EMPTY = 0;
    /** 2D array represents the puzzle grid. */
    private int [][] tiles ;
    /** The column of the empty tile. */
    private int emptyCol ;
    /** The row of the empty tile. */
    private int emptyRow ;
    /** The configuration of the game. */
    private Configuration configuration ;


    /*
    public Puzzle15Matrix()
    {
        tiles = new int[SIZE][SIZE];
    }
     */

    /**Constructor of {@code Puzzle15Matrix} from an input string.
     * The input must be in the form: "15 2 1 12 : 8 5 6 11 : 4 9 10 7 : 3 14 13 0".
     * @param input The string representing the initial state of the puzzle.
     */
    public Puzzle15Matrix(String input)
    {
        tiles = new int[SIZE][SIZE];
        configuration = new Configuration(input);
        configuration.initialise(tiles);
        setEmpty();
    }

    /** Creates a copy of an existing {@code Puzzle15Matrix}.
     * @param puzzle The puzzle instance to copy.
     */
    public Puzzle15Matrix(Puzzle15Matrix puzzle)
    {
        tiles = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
                this.tiles[i][j] = puzzle.tiles[i][j];
        }
        puzzle.setEmpty();
    }

    /** Constructor of {@code Puzzle15Matrix} from a byte matrix.
     * This constructor is intended for use in Puzzle15Array.
     * @param matrix The state of the game in 2D array
     */
    Puzzle15Matrix(byte[][] matrix)
    {
        tiles = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                tiles[i][j] = matrix[i][j];
            }
        }
        setEmpty();
    }


    /** Getter for the column index of the empty tile.
     * @return The column index of the empty tile.
     */
    public int getEmptyCol()
    {
        return emptyCol;
    }

    /** Getter for the row index of the empty tile.
     * @return The row index of the empty tile.
     */
    public int getEmptyRow()
    {
        return emptyRow;
    }

    /** Getter for the tile value at the specified position.
     * @param row The row of element.
     * @param col The column of element.
     * @return The tile value at the specified position.
     */
    public int getTile(int row, int col)
    {
        if ((col >= 0 && col < SIZE) && (row >= 0 && row < SIZE))
            return tiles[row][col];
        else
        {
            System.out.println("wrong position");
            System.exit(0);
            return 0;
        }
    }

    /** Setter for a tile value at the specified position if the value is valid and unique.
     * @param row The row index.
     * @param col The column index.
     * @param value The tile value (must be between 0 and 15).
     */
    public void setTiles(int row, int col, int value)
    {
        if((row >= 0 && row < SIZE) && (col >= 0 && col < SIZE) && (value >= 0 && value < 16))
        {
            for(int i = 0; i<SIZE; i++)
            {
                for (int j = 0 ; j < SIZE; j++)
                {
                    if (tiles[i][j] == value && value != EMPTY)
                    {
                        System.out.println("invalid value");
                        System.exit(0);
                    }
                }
            }
            if (tiles[row][col] == 0)
            {
                tiles[row][col] = value;
                return;
            }
        }
    }

    /** Starts the game, allowing the user to enter movement commands.
     */
    public void play()
    {
        Scanner keyboard = new Scanner(System.in);
        print();
        while (!isSolved())
        {
            System.out.print("Please make a move by inputting U, D, L, R; or stop the game by inputting q: ");
            char command = keyboard.next().charAt(0);
            if (command == 'q')
            {
                System.out.println("Ending the game.");
                return;
            }
            move(command);
            print();
        }
        System.out.println("You solved the puzzle!");
    }

    /**Prints the current state of the puzzle with special format.
     */
    void print()
    {
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
                System.out.print("-----");
            System.out.println("-");
            for (int j = 0; j < SIZE; j++)
                if (i == emptyRow && j == emptyCol)
                    System.out.printf("|    ");
                else
                    System.out.printf("| %2d ", tiles[i][j]);
            System.out.println("|");
        }
        for (int j = 0; j < SIZE; j++)
            System.out.print("-----");
        System.out.println("-");
    }

    /** Moves corresponding tile in the specified direction (U = Up, D = Down, L = Left, R = Right).
     * @param direction The direction to move the empty tile.
     */
    void move(char direction)
    {
        if(direction == 'U')
        {
            if(getEmptyRow() == SIZE)
            {
                System.out.println("Invalid move");
                System.exit(0);
            }
            else
            {
                tiles[emptyRow][emptyCol] = tiles[1+emptyRow][emptyCol];
                tiles[1+emptyRow][emptyCol] = EMPTY;
            }
        }
        else if(direction == 'D')
        {
            if(getEmptyRow() == 0)
            {
                System.out.println("Invalid move");
                System.exit(0);
            }
            else
            {
                tiles[emptyRow][emptyCol] = tiles[-1+emptyRow][emptyCol];
                tiles[-1+emptyRow][emptyCol] = EMPTY;
            }
        }
        else if(direction == 'R')
        {
            if(getEmptyCol() == 0)
            {
                System.out.println("Invalid move");
                System.exit(0);
            }
            else
            {
                tiles[emptyRow][emptyCol] = tiles[emptyRow][-1+emptyCol];
                tiles[emptyRow][-1+emptyCol] = EMPTY;
            }
        }
        else if(direction == 'L')
        {
            if(getEmptyCol() == SIZE)
            {
                System.out.println("Invalid move");
                System.exit(0);
            }
            else
            {
                tiles[emptyRow][emptyCol] = tiles[emptyRow][1+emptyCol];
                tiles[emptyRow][1+emptyCol] = EMPTY;
            }
        }
        setEmpty();
    }

    /** Checks if the puzzle is solved correctly.
     * @return {@code true} if the puzzle is solved, otherwise {@code false}.
     */
    boolean isSolved()
    {
        int temp = 1;
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                if(tiles[i][j] == temp)
                    temp++;
                if(temp == 16 && j == 2)
                    return true;
                //return false;
            }
        }
        return false;
    }

    /** Finds and sets the position of the empty tile.
     */
    private void setEmpty()
    {
        for(int i = 0; i<SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                if (tiles[i][j] == EMPTY)
                {
                    emptyRow = i;
                    emptyCol = j;
                }
            }
        }
    }
}