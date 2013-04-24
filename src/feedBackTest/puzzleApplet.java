package feedBackTest;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Label;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import puzzleAgent.PuzzleCanvasObservable;
import puzzleAgent.agentPanel;
import puzzleAgent.agentThread;
import puzzleAgent.picAgentPanel;
import puzzleAgent.textAgentPanel;
import puzzleFunctions.PuzzleGame;
import puzzleFunctions.Solution8Applet;

/**
 * This runs in the browser as part of a larger interface experiment
 * @author Robert Walker
 */
public class puzzleApplet extends Solution8Applet {

        public Label puzzleAppletHeading = new Label("<html>Puzzle #1</html>");
        PuzzleCanvasObservable m_eightPuzzleCanvasObservable=new PuzzleCanvasObservable();
        agentPanel aPanel;
        agentThread myAgent;
        int[][] boardState;
        puzzleSQLWriter writer;
        public static int participantID;
        public static String appletHostName;
        public static int agentType;
        public static int sessionPuzzleNo;
        HashMap<String, AudioClip> phrases = new HashMap<String,AudioClip>();
        
        Container holder = new Container();
        puzzleTestSequence experiment = new puzzleTestSequence();
        
        private void loadPhrases()
        {
            phrases.put(agentPanel.WELCOME_RESPONSE, getAudioClip(getClass().getResource("/feedBackTest/sounds/"+agentPanel.WELCOME_RESPONSE+".wav")));
            phrases.put(agentPanel.POSITIVE_RESPONSE, getAudioClip(getClass().getResource("/feedBackTest/sounds/"+agentPanel.POSITIVE_RESPONSE+".wav")));
            phrases.put(agentPanel.NEGATIVE_RESPONSE, getAudioClip(getClass().getResource("/feedBackTest/sounds/"+agentPanel.NEGATIVE_RESPONSE+".wav")));
            phrases.put(agentPanel.SOLVED_PUZZLE_RESPONSE, getAudioClip(getClass().getResource("/feedBackTest/sounds/"+agentPanel.SOLVED_PUZZLE_RESPONSE+".wav")));
            phrases.put(agentPanel.AGENT_EXIT_RESPONSE, getAudioClip(getClass().getResource("/feedBackTest/sounds/"+agentPanel.AGENT_EXIT_RESPONSE+".wav")));
        }
        
        public static int[][] parseBoardState(String stateBoard)
        {
            int[][] board = new int[3][3];
            
            int listpos=0;            
            for(int i=0; i<3;i++)
                for(int j=0;j<3;j++)
                    {board[i][j] =  Character.getNumericValue(stateBoard.charAt(BoardStringParse.getTilePositionList(listpos++)));}
            return board;
        }
        
        public agentPanel getAgentPanel()
            {return aPanel;}
    
    @Override
    
        //parse input parameters, these are global variables
	public void
	init() 
        {
            boolean kluge=false;
            
            //make a better configure switch
            if (kluge)
            {
                appletHostName = getParameter("a");
                participantID = Integer.parseInt(getParameter("b"));
                agentType = Integer.parseInt(getParameter("c"));
                sessionPuzzleNo = -1;
                loadPhrases();
            }
            else
            {
                appletHostName = "localhost";//getParameter("a");
                participantID = 99;//Integer.parseInt(getParameter("b"));
                agentType = 4;//Integer.parseInt(getParameter("c"));
                sessionPuzzleNo = -1;
                loadPhrases();
            }
        }
    
    @Override
    //when we start we should draw the image on the screen and initialize the first puzzle
    //there should also be an introduction by the interface done here and here only
    public synchronized void start()
    {
        //build the screen layout 
        buildAgentLayout();
        
        //initialize the first puzzle and show the board
        loadAndShowObservedPuzzle(new PuzzleGame(parseBoardState(experiment.boardStates.get(++sessionPuzzleNo))));
        
        //running the agent for the first time, we want it to learn about the current game puzzle
        //and introduce his/herself
        myAgent.communicationMedium.doIntroduction();

    }
    
    //here we use a board state string to initialize a puzzle state and attach the agent object
    //in the window to observe the puzzle.  The agent will start observing based on the initial 
    //state of the board. 
    private void loadAndShowObservedPuzzle(PuzzleGame setPuzzle)
    {
        //add the interface elements
        disableInterfaceActions();
        remove(m_eightPuzzleCanvasObservable);
        try {Thread.sleep(1000);} catch (InterruptedException ex) {this.repaint();}
        String nextPuzzleMessage = "Puzzle #" + (sessionPuzzleNo+1);
        if (agentType>0 && sessionPuzzleNo > 1)
            nextPuzzleMessage+="\nPlease note that you will not have any agent assistance on this puzzle.";
        puzzleAppletHeading.setText(nextPuzzleMessage);
        //suggest garbage collection here
        System.gc();
        m_eightPuzzleCanvasObservable=new PuzzleCanvasObservable();
        m_eightPuzzleCanvasObservable.setPuzzle(setPuzzle);
        
        myAgent = new agentThread(this);
        m_eightPuzzleCanvasObservable.setObserverAgent(myAgent);
        add(m_eightPuzzleCanvasObservable, BorderLayout.PAGE_END);
        
        new Thread(myAgent).start();
        m_eightPuzzleCanvasObservable.setVisible(true);
        this.validate();
        this.repaint();
        enableInterfaceActions();
    }
    
    //here we hold any universal layout code.  This should only be called once
    private void buildAgentLayout()
    {
        removeAll();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        aPanel = getAgentType(agentType);
        //puzzleAppletHeading.setSize(100, 100);
        
        puzzleAppletHeading.setBackground(Color.DARK_GRAY);
        puzzleAppletHeading.setFont(new Font("Arial",Font.BOLD,16));
        puzzleAppletHeading.setForeground(Color.white);
        puzzleAppletHeading.setVisible(true);
        puzzleAppletHeading.setAlignment(Label.CENTER);
        
        add(puzzleAppletHeading);
        add(aPanel);
        add(m_eightPuzzleCanvasObservable);
    }
    
    private void buildNoAgentLayout()
    {
        removeAll();
        aPanel = new agentPanel();
        puzzleAppletHeading.setSize(100, 100);
        
        add(puzzleAppletHeading, BorderLayout.PAGE_START);
        add(m_eightPuzzleCanvasObservable, BorderLayout.PAGE_END);
    }
    
    //controls what type of 
    public void nextPuzzle()
    {
        if (sessionPuzzleNo < 1)
        {
           buildAgentLayout();
           loadAndShowObservedPuzzle(new PuzzleGame(parseBoardState(experiment.boardStates.get(++sessionPuzzleNo))));
        }
        else if(sessionPuzzleNo < 3)
        {
            disableInterfaceActions();
            myAgent.communicationMedium.doGoodbye();
            try{Thread.sleep(3100);} catch (InterruptedException ex) {System.out.println("Interrupted Exception at next puzzle!");}
            enableInterfaceActions();
            buildNoAgentLayout();
            loadAndShowObservedPuzzle(new PuzzleGame(parseBoardState(experiment.boardStates.get(++sessionPuzzleNo))));
        }
        else 
        {
            removeAll();
            showGameOverLabel();
            validate();
            repaint();
        }
    }
    
    //needs to be cleaned up, but works well for now
    private void showGameOverLabel()
    {
        Label gameOver = new Label("Thank you for playing.");
        Label gameOverer = new Label("Please continue to the next page via the button at the bottom.");
        gameOver.setBackground(Color.DARK_GRAY);
        gameOver.setFont(new Font("Arial",Font.BOLD,16));
        gameOver.setForeground(Color.white);
        gameOver.setVisible(true);
        puzzleAppletHeading.setAlignment(Label.CENTER);
        
        gameOverer.setBackground(Color.DARK_GRAY);
        gameOverer.setFont(new Font("Arial",Font.BOLD,12));
        gameOverer.setForeground(Color.white);
        gameOverer.setVisible(true);
        puzzleAppletHeading.setAlignment(Label.CENTER);
        
        add(gameOver, BorderLayout.PAGE_START);
        add(gameOverer, BorderLayout.PAGE_END);
    }
    
    public void disableInterfaceActions()
    {
     m_eightPuzzleCanvasObservable.setEnabled(false);   
    }
    
    public void enableInterfaceActions()
    {
     m_eightPuzzleCanvasObservable.setEnabled(true);   
    }
    
    public void setPuzzleHeadingText(String message)
        {this.puzzleAppletHeading.setText(message);}
    
    
    private agentPanel getAgentType(int index)
    {
        switch(index)
        {
            case 0: 
                return new agentPanel();
            case 1: 
                return new textAgentPanel();
            case 2: 
                return new picAgentPanel();
            case 3: 
                return new textAgentPanel(phrases);
            case 4: 
                return new picAgentPanel(phrases);
                
        }
        return new picAgentPanel();
    }
    
    @Override
    public PuzzleCanvasObservable getPuzzleCanvas() 
            { return m_eightPuzzleCanvasObservable; }
            
}

class BoardStringParse
{
    private static List<Integer> tilePositions = new ArrayList<Integer>();
    
    public static int getTilePositionList(int index)
    {
        tilePositions.add(2);
        tilePositions.add(5);
        tilePositions.add(8);
        
        tilePositions.add(11);
        tilePositions.add(14);
        tilePositions.add(17);
        
        tilePositions.add(20);
        tilePositions.add(23);
        tilePositions.add(26);
        
        return tilePositions.get(index);
    }
}