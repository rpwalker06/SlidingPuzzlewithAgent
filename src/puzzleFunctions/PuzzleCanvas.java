package puzzleFunctions;

/*
 * PuzzleCanvas
 * Charita Brent
 * @02177832
 * Art. Intelligence
 * SYCS 660
 * Eight Puzzle Program
 */
//package AIEightPuzzleGame;

import java.awt.*;
import java.util.*;

 //  paints an eight-puzzle
public class PuzzleCanvas extends Canvas {

	protected PuzzleGame m_puzzle = new PuzzleGame();
	protected int[] m_x = new int[9];
	protected int[] m_y = new int[9];
        public static Color tileColor = Color.gray;
        public static Color textColor = Color.darkGray;

        // size of the canvans
        private Dimension m_oldSize = new Dimension();
	
        // Just initialize it to garbage
        
        // An image for double buffering
        private Image m_imageBuffer;

        // The image's graphics context
        private Graphics m_imageBufferGraphics;

        // A separate thread to deal with moving tiles around
        TileMover m_tileMover = new TileMover(this);

        // Create a new PuzzleCanvas
        public
	PuzzleCanvas() {
		// Start the tile mover thread
		m_tileMover.start();
	}
        
        public Dimension
	preferredSize() {
		FontMetrics m = getFontMetrics(getFont());

		int width = (m.getMaxAdvance() * 5) * 3;
		int height = (m.getHeight() * 5) * 3;

		return new Dimension(width, height);
	}

        public Dimension
	minimumSize() {
		FontMetrics m = getFontMetrics(getFont());

		int width = (m.getMaxAdvance() * 3) * 3;
		int height = (m.getHeight() * 3) * 3;

		return new Dimension(width, height);
	}
        
	public void paint(Graphics g) {
                super.paint(g);
		update(g);
	}

        public void update(Graphics g) {
                
                Dimension d = size();

		int dx = d.width / 3;
		int dy = d.height / 3;

		// Create a new image buffer if the size of the component changes
		if (m_oldSize.height != d.height
				|| m_oldSize.width != d.width
				|| m_imageBuffer == null) {


			//dispose of the old graphics context, if one exists
			if (m_imageBufferGraphics != null) m_imageBufferGraphics.dispose();

			m_imageBuffer = createImage(d.width, d.height);
			m_imageBufferGraphics = m_imageBuffer.getGraphics();

			// reset the locations of each tile
			setTileLocations();

			m_oldSize = d;
		}

		// clear the image
		m_imageBufferGraphics.setColor(getBackground());
		m_imageBufferGraphics.fillRect(0, 0, d.width, d.height);
		FontMetrics fontMetrics = m_imageBufferGraphics.getFontMetrics();

		// raw each tile
		for (int i = 1; i <= 8; ++i) {

			// raw the tile itself
			m_imageBufferGraphics.setColor(tileColor);
			m_imageBufferGraphics.fill3DRect(m_x[i], m_y[i], dx, dy, true);

			// Draw the tile's number
			m_imageBufferGraphics.setColor(textColor);

			m_imageBufferGraphics.drawString(String.valueOf(i),
				m_x[i] + (dx / 2) - (fontMetrics.stringWidth(String.valueOf(i)) / 2),
				m_y[i] + (dy / 2) + (fontMetrics.getHeight() / 2));
		}

		// Draw the double buffer
		g.drawImage(m_imageBuffer, 0, 0, getBackground(), this);
	}

	 //Reset the locaton of each tile
	protected void
	setTileLocations() {
		Dimension d = size();

		for (int i = 0; i <= 8; ++i) {
			SpaceLocal loc = m_puzzle.getSpaceLocal(i);
			m_x[i] = loc.x * (d.width / 3);
			m_y[i] = loc.y * (d.height / 3);
		}
	}

	protected int
	coordinateToTile(int x, int y) {
		Dimension d = size();

		int dx = d.width / 3;
		int dy = d.height / 3;

		return m_puzzle.where_is_Space(x / dx, y / dy);
	}

	// Shuffle the eight puzzle
	public void
	shuffle() {

		m_puzzle.randomize(100);

		// update of the (x, y) coordinates...
		setTileLocations();

		repaint();
	}

        public void
	move(int tile) {
		// Queue a move to the tile mover
		m_tileMover.move(tile);
	}

        public PuzzleGame getPuzzle() 
            {return m_puzzle;}
        
        public void setPuzzle(PuzzleGame gamePuzzle) 
            { m_puzzle=gamePuzzle;}

        public boolean
	mouseUp(Event event, int x, int y) {
		int tile = coordinateToTile(x, y); 
		move(tile);

		return true;
	}
}

class TileMover extends Thread {

	private static final long REDRAW_INTERVAL = 60;

	//steps it will take
	private static final int ANIMATION_STEPS = 10;

        PuzzleCanvas m_canvas;
        
        //a queue
	private Vector m_moves = new Vector();

        public
	TileMover(PuzzleCanvas canvas) {

		m_canvas = canvas;
	}

        public synchronized void
	move(int tile) {
		m_moves.addElement(new Integer(tile));
		notify();
	}

        public synchronized int
	getNextMove()
	throws InterruptedException {
		while (m_moves.size() == 0) {wait();}

		Integer move = (Integer) m_moves.elementAt(0);
		m_moves.removeElementAt(0);

		return move.intValue();
	}

        public void
	run() {
		try {
			while (true) {
				int tile = getNextMove();
				if (m_canvas.m_puzzle.moveCheck(tile))
					animateMove(tile);
			}
		} catch (java.lang.InterruptedException e) {
			System.out.println("TileMover.run: I recieved an InterruptedException, so I'm terminating...bye!");
			e.printStackTrace();
		}
	}

        private void
	animateMove(int tile) {
		// determine the location of the blank and the tile
		SpaceLocal blankLoc = m_canvas.m_puzzle.getSpaceLocal(PuzzleGame.BLANK);
		SpaceLocal tileLoc  = m_canvas.m_puzzle.getSpaceLocal(tile);

		Dimension d = m_canvas.size();

		int dx = (blankLoc.x - tileLoc.x) * (d.width  / (3 * ANIMATION_STEPS));
		int dy = (blankLoc.y - tileLoc.y) * (d.height / (3 * ANIMATION_STEPS));

		// Compute the clipping region
		int left, right, top, bottom;

		if (blankLoc.x < tileLoc.x) {
			left   = blankLoc.x * (d.width / 3);
			right  = (tileLoc.x + 1) * (d.width / 3);
		} else {
			right  = (blankLoc.x + 1) * (d.width / 3);
			left   = tileLoc.x * (d.width / 3);
		}

		if (blankLoc.y < tileLoc.y) {
			top    = blankLoc.y * (d.height / 3);
			bottom = (tileLoc.y + 1) * (d.height / 3);
		} else {
			bottom = (blankLoc.y + 1) * (d.height / 3);
			top    = tileLoc.y * (d.height / 3);
		}

		// animate the move of the tile
		try {
			for (int i = 0; i < ANIMATION_STEPS; ++i) {
				sleep(REDRAW_INTERVAL);

				// move the tile on the canvas
				m_canvas.m_x[tile] += dx;
				m_canvas.m_y[tile] += dy;

				// Re-paint the screen
				m_canvas.repaint(REDRAW_INTERVAL / 2, left, top, right, bottom);
			}
		} catch (InterruptedException e) {
			// Just leave quietly. This is thrown by the call to sleep().
		}

		//move the tile (underlining puzzle)
		m_canvas.m_puzzle.willMove(tile);

		m_canvas.setTileLocations();
		m_canvas.repaint(REDRAW_INTERVAL);
	}
}