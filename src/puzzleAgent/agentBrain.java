package puzzleAgent;

import feedBackTest.puzzleApplet;
import java.util.Arrays;
import java.util.Stack;
import puzzleFunctions.GameNode;
import puzzleFunctions.PuzzleCanvas;
import puzzleFunctions.Solution8;

/**
 *
 * @author Robert Walker
 */
public class agentBrain {
    
    //what does the agent need to know?
        //the optimal game board path
        //the current state of the game board
        //the state that results when a user makes a move
        //the state of the program sequence
       int[][] targetBoardSpaces;
       int[][] currentBoardSpaces;
       String agent_communication;
       
       //here we associate an ordered optimal move sequence with a puzzle configuration
       //this can be further abstracted to hide the stack implementation
       Stack<GameNode> optimalSolutionSequence;
       //Stack<GameNode> optimalMoveSequence = new Stack<GameNode>();
       
       //the agent needs the knowledge of how to solve the problem
       //expound on that...
       Solution8 expertKnowledge;
       
       PuzzleCanvas boardGame;
       Solution8 solutionKnowledge;
       
       public agentBrain(puzzleApplet parentApplet)
       {
           //before the agent can observe the game board, he/she should
           //initialize the expert knowledge they will need
           expertKnowledge = new Solution8(parentApplet);

           //now that we have confirmed that we have the expertise
           //we can start observing the board
           boardGame = parentApplet.getPuzzleCanvas();
       }
       
       public void determineOptimalSequence()
            {optimalSolutionSequence = getOptimalSolutionSequence(boardGame);}
       
       public void setPuzzleCanvas(PuzzleCanvas gameBoard)
        {boardGame = gameBoard;}
       
       //here we will observe a puzzle and 
       private Stack<GameNode> getOptimalSolutionSequence(PuzzleCanvas puzzle)
       {
           //observe the puzzle using expert knowledge and save
           GameNode optimalNode = expertKnowledge.solve(puzzle.getPuzzle());
           
           //let's add this puzzle to our knowledge bank
           return recordOptimalSolutionSequence(optimalNode);
           
           //now that we know how to solve the puzzle, we can do something
       }
       
       public void incrementBoardState()
           { optimalSolutionSequence.pop(); }
       
       private Stack<GameNode> recordOptimalSolutionSequence(GameNode optimalSequence)
       {
           Stack<GameNode> optimalMoveSequence = new Stack<GameNode>();
           
           if (optimalSequence != null)
           {
            //priming read of the move sequence
            optimalMoveSequence.push(optimalSequence);
            int i=0;
            //push the correct sequence onto the agent's stack
            while (optimalSequence.getParent() != null) 
            {
                optimalSequence.getPuzzleGame().printOut(++i);
                optimalMoveSequence.push(optimalSequence.getParent());
                optimalSequence = optimalSequence.getParent();
            }
           }
           
           return optimalMoveSequence;
       }
       
       public boolean checkUserMove()
       {
           currentBoardSpaces = boardGame.getPuzzle().getBoardState();
           targetBoardSpaces = optimalSolutionSequence.peek().getPuzzleGame().getBoardState();
           
           boolean correctMove = Arrays.deepEquals(currentBoardSpaces, targetBoardSpaces);
                      
           if(correctMove) optimalSolutionSequence.pop();
           
           return correctMove;
       }
       
       public boolean userSolvedPuzzle()
            {return optimalSolutionSequence.empty();}

       //what does the agent react to?
        //introducing the user to the itself as well as the task
            //Hi I'm ....
            //This is the task you will be doing, here are my suggestions
    
        //the user moving a tile
            //if the tile is the wrong move
            //if the tile is the correct move
    
        //the user has solved the puzzle
            //congratulations!
            //show statistics
    
        //transition to the next activity
            //now we will...
       
       public void introduction()
       {
           agent_communication = "";
           
           agent_communication += "Welcome to the Optimal 8-Puzzle Teacher";
           agent_communication += "This program will help you to learn how to solve the 8-Sliding Puzzle in the Fewest Number of Moves";
           
           setAgentCommunication(agent_communication);
           //sendMessageToUser();
           
       }
       
       public void setAgentCommunication(String message)
       {
           agent_communication = message;
       }
       
       public String getAgentCommunication()
       {
           return (agent_communication != null) ? agent_communication : "I have nothing to say.";
       }
}
