package puzzleFunctions;

/*
 * PriorityQueue.java
 *
 * Charita Brent
 * @02177832
 * Art. Intelligence
 * SYCS 660
 * Eight Puzzle Program
 */

//package AIEightPuzzleGame;

import java.lang.*;
import java.util.*;
import java.util.Vector;


//note to self, remember we are using a heap!

public class PriorityQueue {
    //stores the contents of the queue
    protected Vector elements;
    //makes the comparisons of the tree Objects
    protected Comparator comp;
    /** Creates a new instance of PriorityQueue */

    public PriorityQueue(Comparator compares)
    {

        elements = new Vector();
        comp = compares;

    }
    public PriorityQueue(Comparator compares, int size)
    {
        elements = new Vector(size);
        comp = compares;
    }

    public PriorityQueue(Comparator compares, int size, int increment)
    {
        elements = new Vector(size, increment);
        comp = compares;
    }

    protected void upPriorityQueue(int cIndex)
    {   //cIndex would be the last
  /*     int pIndex = (cIndex - 1) / 2;

        while((cIndex > 0) && (pIndex > 0));
            //indexs for objects inside the vector "elements"
            //locate the parent node
            Object cObject = elements.elementAt(cIndex);
            Object pObject = elements.elementAt(pIndex);

            //swap if child is greater than parent
   //         if(/*comp.compare*//*comp.greaterThan(cObject, pObject)/* == 1)*/
   /*         {
                elements.setElementAt(pObject, cIndex);
                elements.setElementAt(cObject, pIndex);

                //recursive call for the parent index
                upPriorityQueue(pIndex);
            }
*/
	if (cIndex == 0) return;

		// locate the parent node in the PriorityQueue. The "-1" is to ensure that both nodes
		// 1 and 2 have parent of zero, etc.
		int parentIndex = (cIndex - 1) / 2;

		Object childObject = elements.elementAt(cIndex);
		Object parentObject = elements.elementAt(parentIndex);

		// If the child is greaterThan the parent, then swap
		if ( comp.compare(childObject, parentObject) == -1) {
			elements.setElementAt(parentObject, cIndex);
			elements.setElementAt(childObject, parentIndex);

			upPriorityQueue(parentIndex);
		}

       }

    protected void downPriorityQueue(int pIndex)
    {
        //left should be (2 * pIndex) +1)
        int cIndex = (2 * pIndex) + 1;

        //check if we are still inside the queue
        if(cIndex >= elements.size())

            return;// null;

        //left child object
        Object cObject = elements.elementAt(cIndex);
        //Object pObject = elements.elementAt(pIndex);
        //does the right child exists in the vector?
      // Object rightCObject= elements.elementAt(cIndex + 1);
        if(cIndex + 1 < elements.size())
        {   //if so, which is larger
            //I moved this because I don't think the compare
            Object rightCObject = elements.elementAt(cIndex + 1);
			if ( comp.compare(rightCObject, cObject) == -1) {
                cObject = rightCObject;
               //move
               cIndex++;
            }
        }
        //swap if parent if child is greater
        Object pObject = elements.elementAt(pIndex);
		if ( comp.compare(cObject, pObject) == -1) {
 //       if(comp.greaterThan(cObject, pObject)){
            elements.setElementAt(pObject, cIndex);
            elements.setElementAt(cObject, pIndex);

            //recursive call for child
            downPriorityQueue(cIndex);
        }
    }

    public void addElement(Object obj)
    {
        elements.addElement(obj);

        //let the queue sort it with the current size
        upPriorityQueue(elements.size() - 1);

    }

    public boolean isEmpty()
    {
        return elements.isEmpty();
        //return(elements.size() > 0);
    }

    public Object dequeue() throws PriQUnderflowException{
        //top element off the queue
        Object hold = elements.elementAt(0);
       //finding the alst element in the queue
        Object lelement = elements.elementAt(elements.size() - 1);
        elements.removeElementAt(elements.size() - 1);

        if(elements.size() > 0)
        {
            //set the last element back to index 0
            elements.setElementAt(lelement, 0);
            //resort
            downPriorityQueue(0);
        }
        //this is going to return the largest element
        return hold;


    }


    //need an is empty fucntion
    //need an is full
    //enqueue
    //dequeue

    //return the current number of elements in the queue
    public int size()
    {
        return elements.size();
    }

    public String toString()
    {
        return elements.toString();
    }
}
