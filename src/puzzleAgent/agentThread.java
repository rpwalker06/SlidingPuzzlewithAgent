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
    boolean newMove;
    
    public void setNewMove(boolean moveNew)
        {newMove = moveNew;}
    
    public synchronized void notifyMove()
    {
        setNewMove(true);
        notify();
    }
    
    public agentThread(puzzleApplet appParent)
    {
        parentApp = appParent;
        communicationMedium = appParent.getAgentPanel();
        intelligence = new agentBrain(appParent);
    }
    
    private boolean doneHelpingUser()
        {return intelligence.userSolvedPuzzle();}
    
    public synchronized void observeUserMoves() throws InterruptedException
    {
        //spin and wait for the user to report a move made
        while (!newMove) {wait();}
        
        //we now check the move and respond accordingly
        if (intelligence.checkUserMove())
        {
            communicationMedium.sendMessageToUser("This is correct!");
            //intelligence.incrementBoardState();
        }
        else 
        {
            //respond to user with 
            communicationMedium.sendMessageToUser("This is incorrect!");
        }
            
        newMove = false;
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
