package am.aua.npuzzle;

import java.io.*;
import java.util.*;
import am.aua.npuzzle.core.*;

/** The {@code Npuzzle} class is the entry point of the 15-puzzle game.
 * It is also used for testing purposes.
 */
public class NPuzzle
{
    private Tiles tiles;
    private ConfigurationStore store;
    private ArrayList<Tiles> cachedTiles;

    /**Constructor for {@code NPuzzle} that initializes the class.
     * @param tiles the game board.
     */
    public NPuzzle(Tiles tiles)
    {
        this.tiles = tiles;
        cachedTiles = new ArrayList<>();
    }

    public NPuzzle(ConfigurationStore store)
    {
        this.store = store;
        cachedTiles = new ArrayList<>();
    }

    /** Starts the game, allowing the user to enter movement commands.
     */

    public void play() throws IOException, ConfigurationFormatException, InvalidConfigurationException
    {

        String response = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please select a configuration to play (l to list):");
        while (!response.equals("q"))
        {
            response = in.readLine();
            //System.out.println(response);
            if (response.equals("LEFT") || response.equals("RIGHT") || response.equals("UP") || response.equals("DOWN"))
            {
                if (tiles == null)
                {
                    System.out.println("Please select a configuration to play (l to list):");
                }
                else
                {
                    try
                    {
                        tiles.move(Tiles.Direction.valueOf(response));
                        cachedTiles.add(copyTiles(true));
                    }
                    catch (PositionOutOfBoardException e)
                    {
                        System.out.println("Move position out of board. Please try again.");
                    }
                    print();
                    if (!tiles.isSolved())
                    {
                        System.out.println("Please make a move by inputting UP, DOWN, LEFT, RIGHT;");
                        System.out.println("or stop the game by inputting q: ");
                    }
                    else
                    {
                        System.out.println("You solved the puzzle!");
                        tiles = null;
                        System.out.println("Please select a configuration to play (l to list):");
                    }
                }
            }
            else if (response.equals("b"))
            {
                if(tiles.getMoveCunt() > 0)
                {
                    tiles = cachedTiles.get(tiles.getMoveCunt() - 1);
                    print();
                }
                else
                    System.out.println("this is the initial board");
            }
            else if (response.equals("f"))
            {
                if(tiles.getMoveCunt() != cachedTiles.size()-1)
                {
                    tiles = cachedTiles.get(tiles.getMoveCunt()+1);
                    print();
                }
                else
                    System.out.println("this is the current board");
            }
            else if (response.equals("l"))
            {
                Configuration[] configs = store.getConfigurations();
                int i = 0;
                for (Configuration c : configs)
                {
                    System.out.println(i + " (" + c.getData() + ")");
                    i++;
                }
            }
            else if (response.startsWith("c"))
            {
                Configuration[] configs = store.getConfigurations();

                response = response.substring(1);
                response = response.trim();
                int integer = -1;
                try
                {
                    integer = Integer.parseInt(response);
                    if(integer < 0 || integer > configs.length)
                        throw new Exception();                      //just to avoid repetition
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                    System.out.println("Starting over");
                    play();
                }

                Configuration config = configs[integer];
                tiles = new ArrayTiles(config.getData());
                cachedTiles.add(copyTiles(false));              //adding the initial board


                print();
                if (!tiles.isSolvable())
                {
                    System.out.println("The game is not solvable. Quitting.");
                    System.exit(0);
                }
                if (!tiles.isSolved())
                {
                    System.out.println("Please make a move by inputting UP, DOWN, LEFT, RIGHT;");
                    System.out.println("or stop the game by inputting q: ");
                }
                else
                {
                    System.out.println("You solved the puzzle!");
                    tiles = null;
                    System.out.println("Please select a configuration to play (l to list):");
                }
            }
        }
    }

    /**Prints the current state of the puzzle with special format.
     */
    public void print()
    {
        System.out.println("- " + tiles.getMoveCunt() + " move(s)");
        for(int i = 0; i < Tiles.SIZE; i++)
        {
            for (int j = 0; j < Tiles.SIZE; j++)
                System.out.print("-----");

            System.out.println("-");

            for (int j = 0; j < Tiles.SIZE; j++)
                if (tiles.getTile(i, j) == Tiles.EMPTY)
                    System.out.printf("|    ");
                else
                    System.out.printf("| %2d ", tiles.getTile(i,j));
            System.out.println("|");
        }
        for (int j = 0; j < Tiles.SIZE; j++)
            System.out.print("-----");

        System.out.println("-");
    }


    /**Makes a copy of this Tiles object
     * @param useCloning specifies cloning method true for cloning false for copy constructor
     * @return new Tiles object with same data
     * */
    private Tiles copyTiles(boolean useCloning)
    {
        if (!useCloning)
        {
            if (tiles instanceof ArrayTiles)
                return new ArrayTiles((ArrayTiles) tiles);
            else if (tiles instanceof MatrixTiles)
                return new MatrixTiles((MatrixTiles) tiles);
        }
        return (Tiles) tiles.clone();
    }

    /**Checks for equality
     * @param other the class to check for equality
     * @return true, if both are equal, otherwise false
     * */
    @Override
    public boolean equals(Object other)
    {
        if (other==null)
            return false;
        else if(getClass()!= other.getClass())
            return false;
        else
        {
            NPuzzle n = (NPuzzle) other;
            return cachedTiles.equals(n.cachedTiles) && tiles.equals(n.tiles) && store.equals(n.store);
        }
    }

    /**
     * @return the string representation of this class
     * */
    @Override
    public String toString()
    {
        return "NPuzzle with " + tiles + store + " and " + cachedTiles.size() + "configs";
    }

    /** Starts the game tests.
     * @param arg Command-line argument which should be the initial puzzle state as a string.
     */
    public static void main(String[] arg)
    {
        if (arg.length != 1)
        {
            System.out.println("Usage: java NPuzzle <path/url to store>");
            return;
        }
        try
        {
            ConfigurationStore cs = new ConfigurationStore(arg[0]);
            NPuzzle np = new NPuzzle(cs);
            np.play();
        }
        catch (IOException ioe)
        {
            System.out.println("Failed to load configuration store");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /*
    public static void main2(String[] arg)
    {
        String command = null;
        try
        {
            command = arg[0];
        }
        catch (Exception e)
        {
            System.out.println("NO COMMAND SPECIFIED, RUNNING THE DEFAULT STATE OF THE GAME");  //this is done for testing purposes
        }

        Tiles tiles;

        if (command == null)
            tiles = new ArrayTiles("1 2 3 4 : 5 6 0 8 : 9 10 7 12 : 13 14 11 15");
        else if (command.equals("--array"))
            tiles = new ArrayTiles(arg[1]);
        else if (command.equals("--matrix"))
            tiles = new MatrixTiles(arg[1]);
        else
            tiles = new MatrixTiles(command);

        if (!tiles.isSolvable()) {
            System.out.println("The game is not solvable. Quitting.");
            System.exit(0);
        }


        NPuzzle nPuzzle = new NPuzzle(tiles);
        nPuzzle.play();

        //"15 2 1 12 : 8 6 5 11 : 4 9 0 7 : 3 14 13 10"
        // "1 2 3 4 : 5 6 7 8 : 9 10 11 12 : 13 15 14 0" unsolvable example
    }


    public void play()
    {
        Scanner keyboard = new Scanner(System.in);
        print();
        while (!tiles.isSolved())
        {
            System.out.print("Please make a move by inputting UP, DOWN, LEFT, RIGHT; or stop the game by inputting q: ");
            String command = keyboard.next();
            if (command.equalsIgnoreCase("q"))
            {
                System.out.println("Ending the game.");
                return;
            }
            tiles.move(Tiles.Direction.valueOf(command));
            print();
        }
        System.out.println("You solved the puzzle!");
    }
     */
}
