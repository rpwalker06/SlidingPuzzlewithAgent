package feedBackTest;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Event;
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
        
        public agentPanel getAgentPanel()
            {return aPanel;}
    
    @Override
	public void
	init() 
        {
                m_eightPuzzleCanvasObservable=new PuzzleCanvasObservable();
                m_eightPuzzleCanvasObservable.setPuzzle(new PuzzleGame(boardState));
                aPanel = new agentPanel();

		// Create the buttons and canvases
		m_solveButton	= new Button("Solve");
		                
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