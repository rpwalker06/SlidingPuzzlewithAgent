package puzzleFunctions;

/*
 * Solution8.java
 * Charita Brent
 * @02177832
 * Art. Intelligence
 * SYCS 660
 * Eight Puzzle Program
 */
//package AIEightPuzzleGame;
/*
 *  search engine for puzzle
 */
public class Solution8
extends Thread {

	/*
	 *  contains the solution finder
	 */
	Solution8Applet m_containerApplet;

	/**
	 *  new solution finder
	 */
	public
	Solution8(Solution8Applet containerApplet) {
		m_containerApplet = containerApplet;
	}

	/*
	 * solve the eight puzzle
	 */
	public GameNode
	solve(PuzzleGame puzzle) {

		//  store the nodes on queue
		PriorityQueue queue = new PriorityQueue(new QueueComparator());

		// root node
		GameNode root = new GameNode(puzzle);
		root.direction = 0;

		queue.addElement(root);

		int numNodesExpanded = 0;

		// is queue empty.
		while (!queue.isEmpty()) {

			// Pop the next node off the queue
			GameNode node = (GameNode) queue.dequeue();

			// Have we solved it?
			if (node.getPuzzleGame().isSpacesCorrect()) {

				m_containerApplet.showStatus("Solved!");
				return node;
			}

                        //expand the current node
			node.expansion();

			//  add each child to the queue, and do some
			// miscellaneous user interface stuff.
			for (int i = 0; i < node.getNo_children(); ++i) {

				// Add the child to the queue
				GameNode child = (GameNode) node.getchild_nodes(i);

				queue.addElement(child);

				// Indicate the additional nodes have been expanded
				++numNodesExpanded;
				m_containerApplet.showStatus(numNodesExpanded
					+ " nodes expanded...");
			}
		}
		return null;
	}
	/*
	 * Performs the solution discovered at the specified node
	 * by recursing back through the path used to reach the
	 * solution.
	 */
	private void
	doSolution(GameNode node) {
		if (node.getParent() != null)
			doSolution((GameNode) node.getParent());

		m_containerApplet.getPuzzleCanvas().move(node.getOperator());
	}
	/*
	 * The main hook into the Thread.
	 */
	public void
	run() {
		m_containerApplet.enableUserInterface(false);

		// gets the puzzle from the container applet's puzzle canvas
		PuzzleGame puzzle = m_containerApplet.getPuzzleCanvas().getPuzzle();
		GameNode solutionNode = solve(puzzle);

		if (solutionNode != null) doSolution(solutionNode);

		m_containerApplet.enableUserInterface(true);
	}
}
/*
 * A utility class for sorting nodes in the closed list.
 */
 class QueueComparator implements Comparator
    {
        public int compare(Object left, Object right)
        {
 		GameNode l = (GameNode) left;
 		GameNode r = (GameNode) right;

            //shortest path first
            if(l.getNode_Cost() < r.getNode_Cost())
            {
                return -1;
            }
            else if(l.getNode_Cost() == r.getNode_Cost())
            {
                return 0;
            }
            else
                return 1;
        }
    }