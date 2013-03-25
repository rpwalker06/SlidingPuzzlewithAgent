package puzzleFunctions;

/*
 * PriQUnderflowException.java
 *
 * Charita Brent
 * @02177832
 * Art. Intelligence
 * SYCS 660
 * Eight Puzzle Program
 */
//package AIEightPuzzleGame;

public class PriQUnderflowException extends RuntimeException
{

    /** Creates a new instance of PriQUnderflowException */
    public PriQUnderflowException()
    {
    }
    public PriQUnderflowException(String message)
    {
        super(message);
    }
}
