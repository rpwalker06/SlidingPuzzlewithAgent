package puzzleAgent;

import feedBackTest.puzzleApplet;
import java.awt.Image;

/**
 *
 * @author Robert Walker
 */
public class agentThread implements Runnable {
    
    Image agent_face;
    public agentPanel communicationMedium;
    agentBrain intelligence;
    public puzzleApplet parentApp;
    Integer newMove;
    boolean lastResponse;

    public boolean getLastResponse() {return lastResponse;}
    
    public int getLastResponseInt() {return lastResponse ? 1 : 0;}

    public void setLastResponse(boolean lastResponse) {this.lastResponse = lastResponse;}
    
    public void setNewMove(int moveNew)
        {newMove = moveNew;}
    
    //allow the PuzzleCanvas classes to notify the thread
    //of a user move
    public synchronized void notifyMove(int tile)
    {
        setNewMove(tile);
        notify();
    }
    
    //the agent's ability to move the board
    private synchronized void moveTile(int tile)
        {intelligence.boardGame.move(tile);}
            
    //each agent thread needs to be hosted by parent app
    public agentThread(puzzleApplet appParent)
    {
        newMove = null;
        parentApp = appParent;
        communicationMedium = appParent.getAgentPanel();
        intelligence = new agentBrain(appParent);
    }
    
    private boolean doneHelpingUser()
        {return intelligence.userSolvedPuzzle();}
    
    //thread which waits to be notified by the PuzzleCanvas of a user move
    //it also notifies the PuzzleCanvas when it has checked the move
    //this method will also respond to the user if there is a visible agentPanel
    public synchronized void observeUserMoves() throws InterruptedException
    {
        //spin and wait for the user to report a move made
        while(newMove==null) 
            {wait();}
        
        //we now check the move and respond accordingly
        setLastResponse(intelligence.checkUserMove(newMove));
        this.parentApp.getPuzzleCanvas().notifyMe();
        
        if (communicationMedium.getTextPanel() != null)
            giveUserFeedback();
        
        newMove = null;
    }
    
    //respond to the user based on the last move
    private synchronized void giveUserFeedback() throws InterruptedException
    {
        if (getLastResponse())
            {
                communicationMedium.doPositiveResponse();
                intelligence.optimalSolutionSequence.pop();
            }
            else 
            {
                //respond to user negatively and undo their last move
                communicationMedium.doNegativeResponse();
                parentApp.disableInterfaceActions();

                //be polite and wait...
                this.wait(1037);
                moveTile(newMove);
            }
    }
    
    @Override
    //when the agent starts it will find the optimal solution for its puzzle
    //and observe until the puzzle is solved
    public synchronized void run() 
    {
        //now start thinking, the agent can't help the user
        //without prior knowledge of the solution
        intelligence.determineOptimalSequence();
        boolean done=false;
        
        //now help the user 
        while (!done) 
            try { 
                    observeUserMoves();
                    parentApp.disableInterfaceActions();
                    Thread.sleep(1000);
                    parentApp.enableInterfaceActions();
                    communicationMedium.doNeutralResponse();
                    if (doneHelpingUser()) 
                    {
                        communicationMedium.doSolvedPuzzle();
                        parentApp.setPuzzleHeadingText("Solved the Puzzle!");
                        wait(1750);
                        communicationMedium.doNeutralResponse();
                        parentApp.setPuzzleHeadingText("Loading next puzzle");
                        done = true;
                    }
                } 
            catch (InterruptedException ex)  
            {System.out.println("I recieved an InterruptedException, so I'm terminating...bye!");}
        
        //we're done helping the user, so say goodbye!
        //Say goodnight to the bad guy!
        this.parentApp.nextPuzzle();
    }
}
