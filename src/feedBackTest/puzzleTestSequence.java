package feedBackTest;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Robert Walker
 */
public class puzzleTestSequence {
    
        List<String> assistedboardStates = new ArrayList<String>();
        List<String> unassistedboardStates = new ArrayList<String>();
        List<String> boardStates = new ArrayList<String>();
    
        puzzleTestSequence()
        {
            //populate assisted board states
            //assistedboardStates.add("{[1, 4, 7][2, 5, 8][3, 0, 6]}");
            
            //populate unassisted board states
            //unassistedboardStates.add("{[1, 4, 7][2, 5, 8][0, 3, 6]}");
            
            boardStates.add("{[1, 4, 7][2, 5, 8][0, 3, 6]}");
            boardStates.add("{[1, 4, 7][2, 5, 8][0, 3, 6]}");
            boardStates.add("{[1, 4, 7][2, 5, 8][0, 3, 6]}");
            boardStates.add("{[1, 4, 7][2, 5, 8][0, 3, 6]}");
            boardStates.add("{[1, 4, 7][2, 5, 8][0, 3, 6]}");
            boardStates.add("{[1, 4, 7][2, 5, 8][0, 3, 6]}");
        
        }

}

class BoardStates {}