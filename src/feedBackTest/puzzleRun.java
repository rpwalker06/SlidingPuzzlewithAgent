/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feedBackTest;

import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert Walker
 */
public class puzzleRun {

    //<applet CODE ="feedBackTest.puzzleApplet.class" WIDTH=800 HEIGHT=800></applet>
    
    public static void main(String[] args) 
    {   puzzleWindow foo = new puzzleWindow();
        foo.setVisible(true);
        foo.setSize(new Dimension(800,800));
        try {Thread.sleep(3000);} 
        catch (InterruptedException ex) {System.out.println("Unexpected interruption, shutting donwn");}
     }
        
}
