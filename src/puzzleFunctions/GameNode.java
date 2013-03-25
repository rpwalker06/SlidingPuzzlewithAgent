package puzzleFunctions;

/*
 * GameNode.java
 *
 * Charita Brent
 * @02177832
 * Art. Intelligence
 * SYCS 660
 * Eight Puzzle Program
 */
//package AIEightPuzzleGame;

public class GameNode {

    /** Creates a new instance of GameNode */


        /*A node should have
         *...the state space to which the node corresponds
         *...node in the search tree that generated this node (parent node)
         *...the operator that was applied to generate the node
         *...the number of nodes on the path from the root to this node(the depth of the node)
         *...the path cost of the path from the intial state to the node
         */

        //This will be the state associated with the node
         PuzzleGame puzzle;
        //This is the parent node for this game
        GameNode parent_node;
        //This is the operator used to reach the node
        int operator;
        //This is used to find the depth of the node;
        int depth;
        //finds the direction of the path
        int direction;
        //Finds the cost of the path
        int path_cost;
        //Finds the cost of the nodes, used with heuristic stuff
        int node_cost;
        //Array of children of this parent node
        GameNode[] child_nodes /*= new GameNode[4]*/;

        //Number of children inside the array
        int no_children;

        
    /* A node is a bookkeeping data structure used to represent the search tree
     * for a particular instance.
     * A state represents a configuration (or set of configurations) of the world
     */
    public GameNode(PuzzleGame eightPuzzle){
        puzzle = eightPuzzle;
       // parent_node = 0;
       // operator = 0;
       // depth = 0;
        path_cost = 0;
        node_cost =0;
        //GameNode [] child_nodes =
        no_children = 0;
        child_nodes = new GameNode[4];
    }
    /*Need an Expand function
     *responsible for calculating each component of the nodes it generates
     */
   public void expansion()
   {       SpaceLocal empty = puzzle.getSpaceLocal(0);

       //start expanding one space(move) away
       for(int i = 0; i < 9; ++i)
       {
           if(puzzle.moveCheck(i))
            {
           PuzzleGame eightPuzzle= new PuzzleGame(puzzle);
          eightPuzzle.willMove(i);
           //Create a new child with the puzzle
           GameNode childX = new GameNode(eightPuzzle);

           SpaceLocal sp_locate = puzzle.getSpaceLocal(i);
           int xVal = empty.x - sp_locate.x;
           if (xVal < 0)
               childX.direction = 1;
           else if
           (xVal > 0)
               childX.direction = 2;
           else
           {
           int yVal = empty.y - sp_locate.y;
           //doesn't abs resolve this?
           if(yVal < 0)
                childX.direction = 3;
           else
               childX.direction = 4;
           }

           childX.parent_node = this;
           childX.operator = i;
           childX.path_cost = path_cost +1;


           //child goes through A*
           childX.node_cost = path_cost + eightPuzzle.cityBlockDistance();

          
           child_nodes[no_children] = childX;
           no_children++;
       }
  }
 }

   public PuzzleGame getPuzzleGame(){
        return puzzle;
    }

    public GameNode getParent(){
        return (GameNode) parent_node;

    }
    public int getNo_children(){
        return no_children;
    }
    public GameNode getchild_nodes(int that_child)
    {
        return (GameNode) child_nodes[that_child];
    }
    public int getOperator()
    {
        return operator;
    }
    public int getNode_Cost()
    {
        return node_cost;
    }
    public int getPath_Cost()
    {
        return path_cost;
    }
}