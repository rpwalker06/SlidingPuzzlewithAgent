package puzzleAgent;

import feedBackTest.MoveData;
import java.awt.Event;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import puzzleFunctions.PuzzleCanvas;

/**
 * Here we extend the PuzzleCanvas class from the original program add 
 * the necessary affordances needed for communication with the agent
 * @author Robert Walker
 */
public class PuzzleCanvasObservable extends PuzzleCanvas {
    
    //declare an agent to observe this puzzle 
    private agentThread myObserver;

    //object which packages data to write the dB
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
    
    public agentThread getObserver() {return myObserver;}
    
    @Override
    //we augment the move function with a notification to the agent thread
    public synchronized void move(int tile)
    {
        super.move(tile);
        myObserver.notifyMove(tile);
    }
    
    public synchronized void notifyMe()
        {notify();}
    
    @Override
    //if it is a valid move, we halt the PuzzleCanvas object until it
    //sends move details to the dB
    public synchronized boolean
	mouseUp(Event event, int x, int y) {
        
                if (this.m_puzzle.moveCheck(coordinateToTile(x, y)))
                {
                super.mouseUp(event, x, y);
                try {
                    //here we are waiting for the agent to make its updates
                    wait();
                    moves.setMovetime(event.when);
                    moves.setTileclicked(coordinateToTile(x, y));
                    moves.setPuzzlestate(printBoardState(this.m_puzzle.getBoardState()));
                    moves.incrementMoveno();
                    moves.setAgentresponse(myObserver.getLastResponseInt());
                    moves.setPuzzlesolved(myObserver.intelligence.userSolvedPuzzle());
                    
                    moves.fillFieldMap();
                    moves.writeCurrentData(this);
                    
                } catch (InterruptedException ex) 
                {Logger.getLogger(PuzzleCanvasObservable.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                
                return true;
	}
}
