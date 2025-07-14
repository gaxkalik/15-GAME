package am.aua.npuzzle.core;

public class PositionOutOfBoardException extends RuntimeException
{
    public PositionOutOfBoardException(String message)
    {
        super(message);
    }

    public PositionOutOfBoardException()
    {
      super("Position Out Of Board Exception");
    }
}
