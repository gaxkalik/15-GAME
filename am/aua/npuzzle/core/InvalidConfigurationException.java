package am.aua.npuzzle.core;

public class InvalidConfigurationException extends Exception
{
    public InvalidConfigurationException(String message)
    {
        super(message);
    }

    public InvalidConfigurationException()
    {
        super("Invalid configuration");
    }
}
