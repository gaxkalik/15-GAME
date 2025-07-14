package am.aua.npuzzle.core;


public class ConfigurationFormatException extends Exception
{
    public ConfigurationFormatException(String message)
    {
        super(message);
    }

    public ConfigurationFormatException()
    {
      super("Input data is malformed or missing.");
    }
}
