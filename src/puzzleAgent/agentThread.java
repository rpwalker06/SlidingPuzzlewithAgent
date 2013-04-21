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
        this.parentApp.getPuzzleCanvas().notifyMe();
        
        if (getLastResponse())
        {
            communicationMedium.doPositiveResponse();
        }
        else 
        {
            //respond to user with 
            communicationMedium.doNegativeResponse();
        }
        
        newMove = null;
    }
    
    @Override
    public void run() 
    {
        //now that we are running the agent, we want it to learn about the current game puzzle
        //and introduce his/herself
        
        //say introduction!
        communicationMedium.doIntroduction();
        
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
                        done = true;
                    }
                    
                } 
            catch (InterruptedException ex)  
            {System.out.println("I recieved an InterruptedException, so I'm terminating...bye!");}
        
        //we're done helping the user, so say goodbye!
        //Say goodnight to the bad guy!
        try {Thread.sleep(1000);} catch (InterruptedException e) {this.parentApp.nextPuzzle();};
        this.parentApp.nextPuzzle();
    }
}
