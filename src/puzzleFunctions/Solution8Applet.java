package puzzleFunctions;

/*
 * Solution8Applet
 * Charita Brent
 * @02177832
 * Art. Intelligence
 * SYCS 660
 * Eight Puzzle Program
 */
//package AIEightPuzzleGame;

import java.awt.*;

/*
 * This is the eightPuzzle applet applet
 */

public class Solution8Applet
extends java.applet.Applet {

	/** The layout manager */
	GridBagLayout m_layoutManager;

	// Buttons to control the applet
	Button m_shuffleButton;
	Button m_solveButton;
	Button m_haltButton;

	/** A canvas for the eight puzzle */
	PuzzleCanvas m_eightPuzzleCanvas;


	/*
	 * A solver thread
	 */

	Solution8 m_solver;


	/**
	 * Initialize the eight puzzle applet
	 */

	public void
	init() {
		// Set up the top-level layout
		m_layoutManager = new GridBagLayout();
		setLayout(m_layoutManager);

		// For later...
		GridBagConstraints constraint;

		// The viewer canvases
		Dimension d = size();

		m_eightPuzzleCanvas	= new PuzzleCanvas();


		// Create the buttons and canvases
		m_shuffleButton	= new Button("Shuffle");
		m_solveButton	= new Button("Solve");
		m_haltButton	= new Button("Halt");

		// Add the items to the top-level applet layout
		addComponent(m_eightPuzzleCanvas, 0, 0, 6, 6, GridBagConstraints.BOTH, 1, 1);
		addComponent(m_shuffleButton,     3, 6, 2, 1, GridBagConstraints.NONE, 1, 0);
		addComponent(m_solveButton,       5, 6, 2, 1, GridBagConstraints.NONE, 1, 0);
		addComponent(m_haltButton,        7, 6, 2, 1, GridBagConstraints.NONE, 1, 0);

		// Enable/disable the appropriate UI controls.
		enableUserInterface(true);
	}


	/*
	 * Adds a component to the screen
	 */

	private void
	addComponent(Component comp,
							int gridx, int gridy,
							int gridwidth, int gridheight,
							int fill,
							int weightx,
							int weighty) {

		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx		= gridx;
		constraints.gridy		= gridy;
		constraints.gridwidth	= gridwidth;
		constraints.gridheight	= gridheight;
		constraints.fill		= fill;
		constraints.weightx     = weightx;
		constraints.weighty     = weighty;

		m_layoutManager.setConstraints(comp, constraints);
		add(comp);
	}



	/*
	 * Starts
	 */

	public void
	start() {

		if (m_solver != null && m_solver.isAlive())
			m_solver.resume();
	}

	/*
	 * Stops the execution
	 */

	public void
	stop() {

		if (m_solver != null && m_solver.isAlive())
			m_solver.suspend();
	}



	public boolean
	action(Event event, Object arg) {
		if (event.target == m_shuffleButton) {


			m_eightPuzzleCanvas.shuffle();

		} else if (event.target == m_solveButton) {


			m_solver = new Solution8(this);
			m_solver.start();

		} else if (event.target == m_haltButton) {

			/* halt the
			* solver thread.
			*/
			m_solver.stop();
			enableUserInterface(true);

		}

		return true;
	}



	/*
	 * Returns the PuzzleCanvas that belongs to the applet
	 */

	public PuzzleCanvas
	getPuzzleCanvas() {
		return m_eightPuzzleCanvas;
	}




	/*
	 * Enables or disables the user interface controls
	 */

	public void
	enableUserInterface(boolean on) {
		if (on) {
			m_eightPuzzleCanvas.enable();
			m_shuffleButton.enable();
			m_solveButton.enable();
			m_haltButton.disable();
		} else {
			m_eightPuzzleCanvas.disable();
			m_shuffleButton.disable();
			m_solveButton.disable();
			m_haltButton.enable();
		}
	}
}


