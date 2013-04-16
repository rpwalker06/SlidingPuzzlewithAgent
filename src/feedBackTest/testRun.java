package feedBackTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *You are getting closer!
 * @author Robert Walker
 */
public class testRun {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String boardTest = "{[4, 3, 7][0, 1, 6][8, 2, 5]}";
        
        int[][] board = puzzleApplet.parseBoardState(boardTest);
        
        System.out.println("" + board[1][1]);
        System.out.println("" + board[0][0]);
        System.out.println("" + board[2][2]);
        
        System.out.println("Success!");
    }
}
