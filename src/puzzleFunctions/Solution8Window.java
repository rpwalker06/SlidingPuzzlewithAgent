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

import feedBackTest.puzzleWindow;
import java.util.List;
import java.util.ArrayList;



/*
 *  search engine for puzzle
 */
public class Solution8Window
extends Thread {

	/*
	 *  contains the solution finder
	 */

	puzzleWindow p_containerWindow;


	/**
	 *  new solution finder
	 */

	public
	Solution8Window(puzzleWindow topLevel) {
		p_containerWindow = topLevel;
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


				p_containerWindow.setFrameStatus("Solved!");
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
				p_containerWindow.setFrameStatus(numNodesExpanded
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

		p_containerWindow.getPuzzleCanvas().move(node.getOperator());
	}
        
        private void printSolution(GameNode node)
        {
            List<GameNode> solutionNodes = new ArrayList<GameNode>();
            
            GameNode currentNode = node;
            solutionNodes.add(0,node);
            
            //add the parents to the front of the list
            while (currentNode.getParent() != null)
            {
                solutionNodes.add(0,currentNode.getParent());
                currentNode = currentNode.getParent();
            }
            
            //print from the front of the list forward
            for (int i=0; i < solutionNodes.size(); i++)
            {
                solutionNodes.get(i).getPuzzleGame().printOut(i+1);
            }
        }



	/*
	 * The main hook into the Thread.
	 */

	public void
	run() {

                p_containerWindow.enableUserInterface(false);

		// gets the puzzle from the container applet's puzzle canvas

		PuzzleGame puzzle = p_containerWindow.getPuzzleCanvas().getPuzzle();
		GameNode solutionNode = solve(puzzle);
                
                //debug code to print out the solution node, now that it has been determined
                printSolution(solutionNode);
                
                if (solutionNode != null) doSolution(solutionNode);


		p_containerWindow.enableUserInterface(true);
	}
}