package puzzleAgent;

import feedBackTest.puzzleApplet;
import java.util.Arrays;
import java.util.Stack;
import puzzleFunctions.GameNode;
import puzzleFunctions.PuzzleCanvas;
import puzzleFunctions.Solution8;

/**
 * The agent brain represents the ability of the agent to figure out the puzzle
 * @author Robert Walker
 */
public class agentBrain {
    
    //what does the agent need to know?
        //the optimal game board path
        //the current state of the game board
        //the state that results when a user makes a move
        //the state of the program sequence
       static int[][] targetBoardSpaces =
        {/*The matrix in the proposal is this.  It appears that there will
        *two blanks and not one blank and a zero number, so I will change it
        */
       // {{0,1,2},{3,4,5},{6,7,0}};
        {1,4,7},
        {2,5,8},
        {3,6,0}};
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
       
       private Stack<GameNode> recordOptimalSolutionSequence(GameNode optimalSequence)
       {
           Stack<GameNode> optimalMoveSequence = new Stack<GameNode>();
           
           if (optimalSequence != null)
           {
            //priming read of the move sequence
            optimalMoveSequence.push(optimalSequence);
            int i=0;
            //push the correct sequence onto the agent's stack
            while (optimalSequence.getParent().getParent() != null) 
            {
                //optimalSequence.getPuzzleGame().printOut(++i);
                optimalMoveSequence.push(optimalSequence.getParent());
                optimalSequence = optimalSequence.getParent();
            }
           }
           
           return optimalMoveSequence;
       }
       
       public boolean checkUserMove(int newMove)
       {
           int targetMove = optimalSolutionSequence.peek().getOperator();
           
           boolean correctMove = targetMove == newMove ? true : false;
                      
           return correctMove;
       }
       
       private boolean comparePuzzleToSolution()
            {
                boolean truth = (Arrays.deepEquals(boardGame.getPuzzle().getBoardState(),targetBoardSpaces));
            return truth;}
       
       public boolean userSolvedPuzzle()
            {return optimalSolutionSequence.empty() || comparePuzzleToSolution();}
}
