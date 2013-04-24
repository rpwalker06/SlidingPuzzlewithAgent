package feedBackTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Here we define the sequence of puzzles we will be using
 * @author Robert Walker
 */
public class puzzleTestSequence {
    
    List<String> boardStates = new ArrayList<String>();
    
        puzzleTestSequence()
        {
            //populate the board states for the study
            boardStates.add("{[5, 2, 4][6, 0, 7][1, 3, 8]}");
            boardStates.add("{[5, 8, 1][4, 3, 7][2, 6, 0]}");
            
            boardStates.add("{[5, 2, 4][6, 0, 3][1, 8, 7]}");
            boardStates.add("{[1, 7, 5][0, 6, 4][2, 3, 8]}");
        }

}