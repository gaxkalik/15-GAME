package am.aua.npuzzle.core;

import java.io.*;
import java.net.*;
import java.util.*;

/**The {@code ConfigurationStore} class handles loading and validating
 * N-Puzzle configurations from a file, URL, or Reader source.
 *
 * @author Armen Balagyozyan
 */
public class ConfigurationStore
{
    /** Array storing all the configurations as strings.*/
    private ArrayList<Configuration> storedConfigs;

    /**Loads configurations from a given source (URL or file path).
     * Automatically detects whether the source is a URL or a file.
     *
     * @param source URL or file path to load configurations from
     * @throws IOException if there is an error loading the data
     */
    public ConfigurationStore(String source) throws IOException
    {
        storedConfigs = new ArrayList<>();
        if (source.startsWith("http://") || source.startsWith("https://"))
        {
            loadFromURL(source);
        }
        else
        {
            loadFromDisk(source);
        }
    }

    /**Loads configurations from a {@code Reader} input stream.
     *
     * @param source Reader to load configurations from
     * @throws IOException if there is an error reading from the stream
     */
    public ConfigurationStore(Reader source) throws IOException
    {
        storedConfigs = new ArrayList<>();
        load(source);
    }

    public Configuration[] getConfigurations()
    {
        Configuration [] result = new Configuration[storedConfigs.size()];
        for (int i = 0; i < storedConfigs.size(); i++)
        {
            try
            {
                result[i] = new Configuration(storedConfigs.get(i).getData());
            }
            catch (ConfigurationFormatException e)
            {
                System.out.println(e.getMessage());
            }

        }
        return result;
    }

    /**Loads and validates puzzle configurations from the provided Reader.
     * Supports up to 1000 configurations.
     *
     * @param r Reader input stream
     * @throws IOException if an I/O error occurs
     */
    private void load(Reader r) throws IOException
    {
        BufferedReader s = new BufferedReader(r);
        String data;

        while ((data = s.readLine()) != null)
        {

            String[] rows = data.split(" : ");
            if (rows.length != Tiles.SIZE)
                System.out.println("Invalid configuration format: \t Incorrect number of rows in configuration");

            for (int i = 0; i < Tiles.SIZE; i++)
            {
                String[] elements = rows[i].split(" ");
                if (elements.length != Tiles.SIZE)
                    System.out.println("Invalid configuration format: \t Incorrect number of columns in configuration");
                for (int j = 0; j < Tiles.SIZE; j++)
                {
                    if (Byte.parseByte(elements[j]) < 0 || Byte.parseByte(elements[j]) >= Tiles.SIZE * Tiles.SIZE)
                        System.out.println("The numbers are too big/small.");
                }
            }

            try
            {
                storedConfigs.add(new Configuration(data));
            }
            catch (ConfigurationFormatException e)
            {
                System.out.println("Invalid configuration format");
            }

            //System.out.println(data);

        }
    }


    /**Loads configurations from a URL.
     *
     * @param url the URL string
     * @throws IOException if unable to read from the URL
     */
    private void loadFromURL(String url) throws IOException
    {
        URL destination = new URL(url);
        URLConnection conn = destination.openConnection();
        Reader r = new InputStreamReader(conn.getInputStream());
        load(r);
    }

    /**Loads configurations from a file on disk.
     * Looks inside the path: "am/aua/NPuzzle/{filename}"
     *
     * @param filename name of the file containing configurations
     * @throws IOException if unable to read from the file
     */
    private void loadFromDisk(String filename) throws IOException {
        Reader r = new FileReader(filename);
        load(r);
    }


    /**Main method is thhe entry point of the program
     * Pass the configuration source (URL or file) as a command-line argument.
     *
     * @param args command-line arguments that expect one argument: source
     * @throws IOException if configuration loading fails
     */
    public static void main(String[] args) throws IOException //I know thar this should rethrow the exception(in HW10), but I cached to avoid errors
    {
        try
        {
            ConfigurationStore p = new ConfigurationStore(args[0]);     //("https://bit.ly/3dL0iGa");
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            System.exit(123678);
        }
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
            ConfigurationStore cs = (ConfigurationStore) other;
            return storedConfigs.equals(cs.storedConfigs);
        }
    }

    /**
     * @return the string representation of this class
     * */
    @Override
    public String toString()
    {
        return "Configuration store containing " + storedConfigs.size() + " configurations";
    }


}
