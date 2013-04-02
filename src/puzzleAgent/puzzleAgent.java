package puzzleAgent;

import puzzleFunctions.PuzzleGame;

/**
 *
 * @author Robert Walker
 */
public class puzzleAgent {
    
    //what does the agent need to know?
        //the optimal game board path
        //the current state of the game board
        //the state that results when a user makes a move
        //the state of the program sequence
    
       PuzzleGame optimalPath;
       int[][] optimalPathSpaces;
       int[][] currentBoardSpaces;
       
       void introduction()
       {
           String response = "";
           
           response += "Welcome to the Optimal 8-Puzzle Teacher";
           response += "This program will help you to learn how to solve the 8-Sliding Puzzle in the Fewest Number of Moves";
           
       
       }
       
    
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
    

}
