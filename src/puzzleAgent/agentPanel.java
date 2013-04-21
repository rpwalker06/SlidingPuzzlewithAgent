
package puzzleAgent;

import java.applet.AudioClip;
import java.util.HashMap;
import javax.swing.JLabel;

public class agentPanel extends javax.swing.JPanel {
    
    private JLabel textVoice;
    HashMap<String, AudioClip> phrases = new HashMap<String,AudioClip>();
    boolean hasaudio=false;
    public static final String WELCOME_RESPONSE="welcome";
    public static final String POSITIVE_RESPONSE="correct";
    public static final String NEGATIVE_RESPONSE="incorrect";
    public static final String SOLVED_PUZZLE_RESPONSE="great";
    public static final String AGENT_EXIT_RESPONSE="goodbye";
    
    public agentPanel(){}
    
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
        
        message = "";
        message+="That is correct.";
    
        if (hasaudio) phrases.get(POSITIVE_RESPONSE).play();
        textVoice.setText(message);
    }
    
    public void doNegativeResponse()
    {   String message;
        
        message = "";
        message+="That is incorrect.  Try another move.";
    
        if (hasaudio) phrases.get(NEGATIVE_RESPONSE).play();
        textVoice.setText(message);
    }
    
    public void doNeutralResponse()
    { 
        String message="";
        
        message = "";
        textVoice.setText(message);
    }
    
    public void doSolvedPuzzle()
    {   String message;
        
        message = "";
        message+="Great!  That is how to solve the puzzle using the fewest possible moves.";
        message+="Let's try another one!";
    
        if (hasaudio) phrases.get(SOLVED_PUZZLE_RESPONSE).play();
        textVoice.setText(message);
    }
    
    public void doIntroduction()
    {   String message;
        
        message = "";
        message+="Hi!  I'm Alan.  And I will be helping to teach you to solve the puzzle using the fewest moves.";
        message+="We will begin with me helping you to solve a "
                + "I do not mean to correct you, just to show";    
        
        if (hasaudio) phrases.get(WELCOME_RESPONSE).play();
        textVoice.setText(message);
    }
    
}
