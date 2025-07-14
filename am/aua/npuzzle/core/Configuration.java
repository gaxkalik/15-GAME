package am.aua.npuzzle.core;

/** The {@code Configuration} class is the configurator of the 15-puzzle game. This class is immutable
*/
public class Configuration
{
    /** The variable to store imputed game state. */
    private String data;

    /*
    public Configuration()
    {
        data = "";
    }
    */

    /** Constructor to initialize the data
     * @param data The string containing the current state of the game(e.g. "15 2 1 12 : 8 5 6 11 : 4 9 10 7 : 3 14 13 0").
     */
    public Configuration(String data)
    {
        this.data = data;
    }

    /** Creates a copy of an existing {@code Configuration}.
     * @param configuration The instance to copy.
     */
    public Configuration (Configuration configuration)
    {
        this.data = configuration.data;
    }

    /** Getter for the data String.
     * @return The data.
     */
    public String getData()
    {
        return data;
    }


    /** For a given a 2D array of ints, representing the values of
     * 'tiles’, updates its contents using the instance variable
     * @param tiles game state
     */
    public void initialise (int [][] tiles)
    {
        String[] rows = data.split(" : ");
        if (rows.length != Puzzle15Matrix.SIZE)
        {
            System.out.println("ERROR: Invalid Puzzle 15 matrix");
            System.exit(10);
        }
        for (int i = 0; i < Puzzle15Matrix.SIZE; i++)
        {
            String[] elements = rows[i].split(" ");
            for (int j = 0; j < Puzzle15Matrix.SIZE; j++)
            {
                if (Integer.parseInt(elements[j]) < 16 && Integer.parseInt(elements[j]) >= 0)
                {
                    tiles[i][j] = Integer.parseInt(elements[j]);
                } else
                {
                    System.out.println("ERROR: Invalid Puzzle 15 matrix");
                    System.exit(10);
                }
            }
        }
    }

    /** For a given a 1D array of bytes, representing the values of
     * 'tiles’, updates its contents using the instance variable
     * @param tiles game state
     */
    public void initialise(byte[] tiles)
    {
        String[] temp = data.split("[ :]+");

        for (int i = 0; i < Puzzle15Array.SIZE*Puzzle15Array.SIZE; i++)
            tiles[i] = Byte.parseByte(temp[i]);
    }


}
