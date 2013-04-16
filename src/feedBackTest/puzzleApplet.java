package feedBackTest;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Event;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import puzzleAgent.PuzzleCanvasObservable;
import puzzleAgent.agentPanel;
import puzzleAgent.agentThread;
import puzzleFunctions.PuzzleCanvas;
import puzzleFunctions.PuzzleGame;
import puzzleFunctions.Solution8Applet;

/**
 *
 * @author Robert Walker
 */
public class puzzleApplet extends Solution8Applet {

        PuzzleCanvasObservable m_eightPuzzleCanvasObservable;
        agentPanel aPanel;
        agentThread myAgent;
        int[][] boardState = {{4,3,7},{0,1,6},{8,2,5}};
        puzzleSQLWriter writer;
        public static int participantID;
        public static int puzzleSequenceNo;
        public static int sessionPuzzleNo;
        private static final int BOARDPOSITIONS = 9;
        public static JLabel agentOutput;
        
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
	public void
	init() 
        {
                //participantID = Integer.parseInt(this.getParameter("participantID"));
                participantID = 99;
                sessionPuzzleNo = 1;
                m_eightPuzzleCanvasObservable=new PuzzleCanvasObservable();
                m_eightPuzzleCanvasObservable.setPuzzle(new PuzzleGame(boardState));

                aPanel = new agentPanel();
                agentOutput = aPanel.getTextPanel();
                aPanel.sendMessageToUser("Welcome!");
                
		// Create the buttons and canvases
		m_solveButton	= new Button("Solve It!");
		                
                add(aPanel, BorderLayout.PAGE_START);
                add(m_eightPuzzleCanvasObservable, BorderLayout.PAGE_START);
                add(m_solveButton, BorderLayout.PAGE_START);

                enableUserInterface(true);
	}
    
    public boolean
	action(Event event, Object arg) {
        
                if (event.target == m_shuffleButton) {

			m_eightPuzzleCanvas.shuffle();

		} else if (event.target == m_solveButton) {

			myAgent = new agentThread(this);
                        m_eightPuzzleCanvasObservable.setObserverAgent(myAgent);
                        new Thread(myAgent).start();

		} else if (event.target == m_haltButton) {

			/* halt the
			* solver thread.
			*/
			enableUserInterface(true);
		}

		return true;
	}
    
    @Override
    public void
	enableUserInterface(boolean on) {
		if (on) {
			m_eightPuzzleCanvasObservable.enable();
			m_solveButton.enable();
			} 
                else    {
			m_eightPuzzleCanvasObservable.disable();
			m_solveButton.disable();
                        }
	}
    
    @Override
    public PuzzleCanvas getPuzzleCanvas() 
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