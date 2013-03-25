package puzzleFunctions;

/*
 * Comparator.java
 * Charita Brent
 * @02177832
 * Art. Intelligence
 * SYCS 660
 * Eight Puzzle Program
 */

/**
 *
 *
 */
//package AIEightPuzzleGame;

import java.util.*;

public interface Comparator {

    //Will compare the order of the child nodes in the Priority Queue

    //returns a negative integer, zero, or a positive integer to
    //indicate the o1 is less than, equal to , or greater than o2
    public abstract int compare(Object o1, Object o2);

    //returns tru if this Object equals obj; fals, otherwise
    //public abstract boolean equals(Object obj);

}
