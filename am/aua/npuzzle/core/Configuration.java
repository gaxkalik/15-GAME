package am.aua.npuzzle.core;

/** The {@code Configuration} class is the configurator of the 15-puzzle game. This class is immutable
*/
public class Configuration
{
    /** The variable to store imputed game state. */
    private final String data;

    /**
     * Constructor to initialize the data
     *
     * @param data The string containing the current state of the game(e.g. "15 2 1 12 : 8 5 6 11 : 4 9 10 7 : 3 14 13 0").
     */
    public Configuration(String data) throws ConfigurationFormatException
    {
        if (data == null)
            throw new ConfigurationFormatException("NULL configuration data");
        else if (data.length() == 0)
            throw new ConfigurationFormatException("Empty configuration data");
        else
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

    /**
     * For a given object of type Tiles, updates its contents using the instance variable
     *
     * @param tiles game bord
     */
    public void initialise(Tiles tiles) throws ConfigurationFormatException, InvalidConfigurationException
    {
        String[] rows = data.split(": ");
        if (rows.length != Tiles.SIZE)
            throw new ConfigurationFormatException("Invalid configuration format: Incorrect number of rows in configuration (found " + rows.length + ").");
        for (int i = 0; i < Tiles.SIZE; i++)
        {
            String[] values = rows[i].split(" ");
            if (values.length != Tiles.SIZE)
                throw new ConfigurationFormatException("Invalid configuration format: Incorrect number of columns in configuration (found " + values.length + ").");
            try
            {
                for (int j = 0; j < Tiles.SIZE; j++)
                    tiles.setTile(j, i, Byte.parseByte(values[j]));
            } catch (NumberFormatException e)
            {
                throw new ConfigurationFormatException("Invalid configuration format: Malformed configuration '" + data + "'.");
            }
        }
        tiles.ensureValidity();
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
            Configuration c = (Configuration) other;
            return data.equals(c.data);
        }
    }

    /**
     * @return the string representation of this class
     * */
    @Override
    public String toString()
    {
        return "Configuration containing" +data;
    }


}
