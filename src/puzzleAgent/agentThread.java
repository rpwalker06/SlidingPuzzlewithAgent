package puzzleAgent;

import feedBackTest.puzzleApplet;
import java.awt.Image;

/**
 *
 * @author Robert Walker
 */
public class agentThread implements Runnable {
    
    Image agent_face;
    agentPanel communicationMedium;
    agentBrain intelligence;
    puzzleApplet parentApp;
    Integer newMove;
    boolean lastResponse;

    public boolean getLastResponse() {return lastResponse;}
    
    public int getLastResponseInt() {return lastResponse ? 1 : 0;}

    public void setLastResponse(boolean lastResponse) {this.lastResponse = lastResponse;}
    
    public void setNewMove(int moveNew)
        {newMove = moveNew;}
    
    public synchronized void notifyMove(int tile)
    {
        setNewMove(tile);
        notify();
    }
    
    public agentThread(puzzleApplet appParent)
    {
        newMove = null;
        parentApp = appParent;
        communicationMedium = appParent.getAgentPanel();
        intelligence = new agentBrain(appParent);
    }
    
    private boolean doneHelpingUser()
        {return intelligence.userSolvedPuzzle();}
    
    public synchronized void observeUserMoves() throws InterruptedException
    {
        //spin and wait for the user to report a move made
        while(newMove==null) {wait();}
        
        //we now check the move and respond accordingly
        setLastResponse(intelligence.checkUserMove(newMove));
        this.parentApp.getPuzzleCanvas().notify();
        
        if (getLastResponse())
        {
            communicationMedium.sendMessageToUser("This is correct!");
            //intelligence.incrementBoardState();
        }
        else 
        {
            //respond to user with 
            communicationMedium.sendMessageToUser("This is incorrect!");
        }
        
        
            
        newMove = null;
    }
    
    @Override
    public void run() 
    {
        //now that we are running the agent, we want it to learn about the current game puzzle
        //and introduce his/herself
        
        //say introduction!
        communicationMedium.sendMessageToUser("Hello and welcome!");
        
        //now start thinking, the agent can't help the user
        //without prior knowledge of the solution
        intelligence.determineOptimalSequence();
        
        //now help the user 
        while (true) 
            try { 
                    observeUserMoves(); 
                    if (doneHelpingUser()) break;
                } 
            catch (InterruptedException ex)  
            {System.out.println("I recieved an InterruptedException, so I'm terminating...bye!");}
        
        //we're done helping the user, so say goodbye!
        //Say goodnight to the bad guy!
    }
}
