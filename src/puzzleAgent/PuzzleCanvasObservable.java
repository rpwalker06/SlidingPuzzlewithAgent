package puzzleAgent;

import feedBackTest.MoveData;
import feedBackTest.puzzleApplet;
import java.awt.Event;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import puzzleFunctions.PuzzleCanvas;

/**
 * @author Robert Walker
 */
public class PuzzleCanvasObservable extends PuzzleCanvas {
    
    //declare an agent to observe this puzzle 
    private agentThread myObserver;
    private MoveData moves = new MoveData();
    
    public PuzzleCanvasObservable(agentThread observer)
    {
        super();
        myObserver = observer;
    }
    
    public PuzzleCanvasObservable()
        {super();}
    
    public String printBoardState(int[][] spaces)
    {
        String boardState="{";
        
        for (int[] board : spaces)
            boardState+=(Arrays.toString(board));
        
        boardState+= "}";
        
        return boardState;
    }
    
    public void setObserverAgent(agentThread observer)
        {myObserver = observer;}
    
    @Override
    //we augment the move function with a notification to the agent thread
    //what should the agent do now?
    public synchronized void move(int tile)
    {
        super.move(tile);
        myObserver.notifyMove(tile);
    }
    
    public synchronized boolean
	mouseUp(Event event, int x, int y) {
        
                if (super.mouseUp(event, x, y))
                {
                try {
                    //here we are waiting for the agent to make its updates
                    wait();
                    moves.setMovetime(event.when);
                    moves.setPuzzlesequencenum(puzzleApplet.puzzleSequenceNo);
                    moves.setTileclicked(coordinateToTile(x, y));
                    moves.setPuzzlestate(printBoardState(this.m_puzzle.getBoardState()));
                    moves.incrementMoveno();
                    moves.setAgentresponse(myObserver.getLastResponseInt());
                    moves.setPuzzlesolved(myObserver.intelligence.userSolvedPuzzle());
                    
                    
                    moves.writeCurrentData();
                } catch (InterruptedException ex) 
                {Logger.getLogger(PuzzleCanvasObservable.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                
                return true;
	}
}
