package puzzleAgent;

import puzzleAgent.agentThread;
import puzzleFunctions.PuzzleCanvas;

/**
 * @author Robert Walker
 */
public class PuzzleCanvasObservable extends PuzzleCanvas {
    
    //declare an agent to observe this puzzle 
    private agentThread myObserver;
    
    public PuzzleCanvasObservable(agentThread observer)
    {
        super();
        myObserver = observer;
    }
    
    public PuzzleCanvasObservable()
        {super();}
    
    public void setObserverAgent(agentThread observer)
        {myObserver = observer;}
    
    @Override
    //we augment the move function with a notification to the agent thread
    //what should the agent do now?
    public synchronized void move(int tile)
    {
        super.move(tile);
        myObserver.notifyMove();
    }
}
