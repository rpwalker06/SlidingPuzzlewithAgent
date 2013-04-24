
package puzzleAgent;

import java.applet.AudioClip;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.JLabel;

/**
 * This is a parent class for the actual visual classes of the agent panel
 * Here we just define the methods common to each agent type of agent display
 * @author Robert Walker
 */

public class agentPanel extends javax.swing.JPanel {
    
    private JLabel textVoice=null;
    HashMap<String, AudioClip> phrases = new HashMap<String,AudioClip>();
    boolean hasaudio=false;
    public static final String WELCOME_RESPONSE="welcome";
    public static final String POSITIVE_RESPONSE="correct";
    public static final String NEGATIVE_RESPONSE="incorrect";
    public static final String SOLVED_PUZZLE_RESPONSE="great";
    public static final String AGENT_EXIT_RESPONSE="goodbye";
    public static Dimension maxLabelSize;
    
    public agentPanel(){}
    
    //turn audio flag by overloaded constructor
    public agentPanel(HashMap<String, AudioClip> words)
    {
        phrases = words; 
        hasaudio=true;
    }
    
    public JLabel getTextPanel()
    {return textVoice;}
    
    public void setTextPanel(JLabel panelText)
    {textVoice = panelText;}
    
    public void doPositiveResponse()
    {   String message;
        
        message="<html>";
        message+="That is correct.";
        message+="</html>";
    
        if (hasaudio) phrases.get(POSITIVE_RESPONSE).play();
        if (textVoice != null) textVoice.setText(message);
    }
    
    public void doNegativeResponse()
    {   String message;
        
        message="<html>";
        message+="That is incorrect.  Try another move.";
        message+="</html>";
    
        if (hasaudio) phrases.get(NEGATIVE_RESPONSE).play();
        if (textVoice != null) textVoice.setText(message);
    }
    
    public void doNeutralResponse()
    { 
        String message="";
        
        message = "";
        if (textVoice != null) textVoice.setText(message);
    }
    
    public void doSolvedPuzzle()
    {   String message;
        
        message="<html>";
        message+="Great!  That is how to solve this puzzle using the fewest possible moves.  ";
        message+="Let's try another one!";
        message+="</html>";
    
        if (hasaudio) phrases.get(SOLVED_PUZZLE_RESPONSE).play();
        if (textVoice != null) textVoice.setText(message);
    }
    
    public void doIntroduction()
    {   String message;
        
        message="<html>";
        message+="Hi!  I'm Alan.  And I will be helping to teach you to solve this puzzle using the fewest possible moves.  ";
        message+="I will work with you on a couple of puzzles, then you will try some on your own.  ";
        message+="Let's start by solving this puzzle.";
        message+="</html>";
        
        if (hasaudio) phrases.get(WELCOME_RESPONSE).play();
        if (textVoice != null) textVoice.setText(message);
    }
    
    public void doGoodbye()
    {   String message;
        
        message="<html>";
        message+="That was excellent!  You should be ready to try some puzzles on your own.  ";
        message+="It was great working with you today.  Goodbye!";
        message+="</html>";
    
        if (hasaudio) phrases.get(AGENT_EXIT_RESPONSE).play();
        if (textVoice != null) textVoice.setText(message);
    }
    
}
