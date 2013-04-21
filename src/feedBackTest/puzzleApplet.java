package feedBackTest;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Label;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import puzzleAgent.PuzzleCanvasObservable;
import puzzleAgent.agentPanel;
import puzzleAgent.agentThread;
import puzzleAgent.picAgentPanel;
import puzzleAgent.textAgentPanel;
import puzzleFunctions.PuzzleGame;
import puzzleFunctions.Solution8Applet;

/**
 *
 * @author Robert Walker
 */
public class puzzleApplet extends Solution8Applet {

        public Label puzzleAppletHeading = new Label("Puzzle #1");
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
                agentType = 3;//Integer.parseInt(getParameter("c"));
                sessionPuzzleNo = -1;
                loadPhrases();
            }
        }
    
    @Override
    //when we start we should draw the image on the screen and initialize the first puzzle
    //there should also be an introduction by the interface done here and here only
    public synchronized void start()
    {
        //add the interface elements
        buildLayout();
        
        //initialize the first puzzle and show the board
        loadAndShowObservedPuzzle(new PuzzleGame(parseBoardState(experiment.boardStates.get(++sessionPuzzleNo))));
        
        //start the agent thread
        new Thread(myAgent).start();

    }
    
    //here we use a board state string to initialize a puzzle state and attach the agent object
    //in the window to observe the puzzle.  The agent will start observing based on the initial 
    //state of the board. 
    private void loadAndShowObservedPuzzle(PuzzleGame setPuzzle)
    {
        disableInterfaceActions();
        remove(m_eightPuzzleCanvasObservable);
        puzzleAppletHeading.setText("Loading Next Puzzle..");
        try {Thread.sleep(1000);} catch (InterruptedException ex) {this.repaint();}
        puzzleAppletHeading.setText("Puzzle #" + (sessionPuzzleNo+2));
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
    private void buildLayout()
    {
        aPanel = getAgentType(agentType);
        puzzleAppletHeading.setSize(400, 100);
        
        add(puzzleAppletHeading, BorderLayout.PAGE_START);
        add(aPanel, BorderLayout.PAGE_START);
        add(m_eightPuzzleCanvasObservable, BorderLayout.PAGE_END);
    }
    
    public void nextPuzzle()
    {
        if (sessionPuzzleNo < 2)
        {
           loadAndShowObservedPuzzle(new PuzzleGame(parseBoardState(experiment.boardStates.get(++sessionPuzzleNo))));
        }
       
        else if(sessionPuzzleNo < 5)
        {
            //disableInterfaceActions();
            loadAndShowObservedPuzzle(new PuzzleGame(parseBoardState(experiment.boardStates.get(++sessionPuzzleNo))));
            //enableInterfaceActions();
            //start the agent thread
            //new Thread(myAgent).start();
        }
    }
    
    public void disableInterfaceActions()
    {
     m_eightPuzzleCanvasObservable.setEnabled(false);   
    }
    
    public void enableInterfaceActions()
    {
     m_eightPuzzleCanvasObservable.setEnabled(true);   
    }
    
    private agentPanel getAgentType(int index)
    {
        switch(index)
        {
            case 0: 
                return new textAgentPanel();
            case 1: 
                return new picAgentPanel();
            case 2: 
                return new textAgentPanel(phrases);
            case 3: 
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